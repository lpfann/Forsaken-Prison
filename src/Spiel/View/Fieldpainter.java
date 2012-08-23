package Spiel.View;

import Spiel.View.Observer.transEnum;
import Spiel.model.Entities.*;
import Spiel.model.MainModel;
import Spiel.model.MainModel.Richtung;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fieldpainter extends JPanel implements Observer {

     private BufferedImage[][] playerimage;
     private BufferedImage[] orkimage;
     private BufferedImage[] trollimage;
     private BufferedImage[] chestimage;
     private BufferedImage[] doorimage;
     private BufferedImage[] bloodimage;
     private BufferedImage groundimage;
     private BufferedImage wallimage;
     private BufferedImage[] knightimage;
     private  BufferedImage[] stairsimage;
     private Image dungeonoffscreenImage;
     BufferedImage transpImg;
     private Image fowoffscreenImage;
     private Image compoImage;
     private Graphics offscreenGraph;
     private boolean fogofwarrepaint = true;
     private int FIELDSIZE;
     private int viewportx;
     private int viewporty;
     private int radiusy = 5;
     private int radiusx = 8;
     private int viewportwidth = 2 * radiusx;
     private int viewportheight = 2 * radiusy;
     private int animcounter=0;
     private Player player;
     private char[][] map;
     private boolean[][] fogofwar;
     private LinkedList<NPC> entities = new LinkedList();
     private LinkedList<NPC> entcopy = new LinkedList();
     public final int BLOCKSIZE = 50;
     public static final int RESOLUTIONX = 800;
     public static final int RESOLUTIONY = 600;
     private int fps;
     private boolean gameover;
     private double delta;
     private int animationdelay;
               int maximumx;
          int maximumy;
          int minimumx;
          int minimumy;


     public Fieldpainter(int breite, int hoehe, Player p,int fieldsize) {


          this.setPreferredSize(new Dimension(BLOCKSIZE * viewportwidth, BLOCKSIZE * viewportheight));
          this.setDoubleBuffered(true);
          this.FIELDSIZE=fieldsize;
          player = p;
          updateViewportCoord(p);
          try {
               groundimage = ImageIO.read(getClass().getResource("/resources/groundDun.png"));
               wallimage = ImageIO.read(getClass().getResource("/resources/HBlockDun.png"));
               playerimage= enlargePic(loadPic("/resources/new-player.png", 64, 64),10,15);
               orkimage = loadPic("/resources/ork.png", 38);
               trollimage = loadPic("/resources/troll.png", 20);
               doorimage = loadPic("/resources/new-door.png", 24);
               chestimage = loadPic("/resources/chest.png", 32);
               bloodimage = loadPic("/resources/bloodsplatter.png", 20);
               knightimage = loadPic("/resources/knight.png", 40);
               stairsimage = loadPic("/resources/stairs.png", 40);

          } catch (IOException e) {
               JOptionPane.showMessageDialog(null, e.getMessage(), "Bild konnte nicht eingelesen werden", 0);
          }

     }

     @Override
     protected void paintComponent(Graphics g) {
          super.paintComponent(g);
          updateViewportCoord(player);
          compoImage = createImage(viewportwidth * FIELDSIZE, viewportheight * FIELDSIZE);

          //Dungeon Zeichnen
          paintDungeon(g);
          //Entities Zeichnen
          paintallEntities(g);
          //Fog of War zeichnen
          paintFogofWar(g);




          try {
               g.drawImage(compoImage, 0, 0, viewportwidth * BLOCKSIZE, viewportheight * BLOCKSIZE, this);

          } catch (Exception e) {
               e.printStackTrace();
          }


          //Hilfe text zeichnen
          g.setColor(Color.white);
          g.drawString("ESC - Menü", 10, 10);
          
          //GAME OVER Text
          if (gameover) {
               g.setColor(Color.red);
               g.setFont(new Font("Monospaced", Font.BOLD, 100));
               g.drawString("GAME OVER", viewportwidth*BLOCKSIZE/2-300, viewportheight*BLOCKSIZE/2);
          }

     }

     private void paintDungeon(Graphics g) {

          //Erstellen eines DungeonImages. Der Viewport wird verbreitert um genug "Verschiebefläche" zu haben. siehe Kommentar unten
          dungeonoffscreenImage = createImage((2+viewportwidth)*FIELDSIZE, (2+viewportheight)*FIELDSIZE);
          offscreenGraph = dungeonoffscreenImage.getGraphics();
         
          for (int y = minimumy; y < maximumy; y++) {
               for (int x = minimumx; x < maximumx; x++) {
                    if (map[y][x] == '*') {
                         offscreenGraph.drawImage(wallimage, (x - viewportx/FIELDSIZE)*FIELDSIZE, (y - viewporty/FIELDSIZE)*FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                    } else {
                         offscreenGraph.drawImage(groundimage, (x - viewportx/FIELDSIZE)*FIELDSIZE, (y - viewporty/FIELDSIZE)*FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                    }
               }
          }
          //Zeichen des Dungeon Bildes
          //wichtig ist hierbei die Verschiebung um %FIELDSIZE. dadurch wird der Hintergrund weich verschoben genau wie sich der Spieler bewegt.
          compoImage.getGraphics().drawImage(dungeonoffscreenImage, -viewportx%FIELDSIZE, -viewporty%FIELDSIZE, this);

     }
          private void paintFogofWar(Graphics g) {
          //if (fogofwarrepaint) {
          fogofwarrepaint = false;
          //erstellt ein transparentes Bild
          transpImg = new BufferedImage((viewportwidth+2) * FIELDSIZE, (viewportheight+2) * FIELDSIZE, BufferedImage.TRANSLUCENT);
          fowoffscreenImage = transpImg;
          Graphics2D q = transpImg.createGraphics();

                    for (int y = minimumy; y < maximumy; y++) {
               for (int x = minimumx; x < maximumx; x++) {
                    if (fogofwar[y][x] == true) {
                         q.setColor(Color.black);
                         q.fillRect((x - viewportx/FIELDSIZE) * FIELDSIZE, (y - viewporty/FIELDSIZE) * FIELDSIZE, FIELDSIZE, FIELDSIZE);
                    } else {
                    }
               }
          }
          q.dispose();
          //}

          compoImage.getGraphics().drawImage(fowoffscreenImage, -viewportx%FIELDSIZE, -viewporty%FIELDSIZE, this);



     }


     //Zeichnen der Entities
     private void paintallEntities(Graphics g) {
          if (!entities.isEmpty()) {

               for (ListIterator<NPC> it = entcopy.listIterator(); it.hasNext();) {
                    NPC e = it.next();
                    try {
                         if (e.getX() < viewportx || e.getX()> viewportx + viewportwidth*FIELDSIZE || e.getY() < viewporty || e.getY()> viewporty + viewportheight*FIELDSIZE) {
                         } else {
                              int x1 = e.getX() - viewportx;
                              int y1 = e.getY() - viewporty;
                              switch (e.getClass().getSimpleName()) {
                                   case "Player":
                                        switch (e.getOrientierung()) {
                                             case DOWN:
                                                  if (player.isWalking()) {
                                                      animateWalking(Richtung.DOWN,x1,y1); 
                                                  } else {
                                                  compoImage.getGraphics().drawImage(playerimage[0][2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  }
                                                  break;
                                             case LEFT:
                                                  if (player.isWalking()) {
                                                      animateWalking(Richtung.LEFT,x1,y1); 
                                                  } else {
                                                  compoImage.getGraphics().drawImage(playerimage[0][1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  }
                                                  break;
                                             case UP:
                                                  if (player.isWalking()) {
                                                      animateWalking(Richtung.UP,x1,y1); 
                                                  } else {
                                                  compoImage.getGraphics().drawImage(playerimage[0][0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  }
                                                  break;
                                             case RIGHT:
                                                  if (player.isWalking()) {
                                                      animateWalking(Richtung.RIGHT,x1,y1); 
                                                  } else {
                                                  compoImage.getGraphics().drawImage(playerimage[0][3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  }
                                                  break;
                                        }
                                        break;
                                   case "Door":
                                        if (((Door) e).getOpen()) {
                                             compoImage.getGraphics().drawImage(doorimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);

                                        } else {
                                             compoImage.getGraphics().drawImage(doorimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);

                                        }
                                        break;
                                   case "Chest":
                                        if (((Chest) e).isOpened()) {
                                             compoImage.getGraphics().drawImage(chestimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);

                                        } else {
                                             compoImage.getGraphics().drawImage(chestimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);

                                        }
                                        break;
                                   case "Ork":
                                        switch (e.getOrientierung()) {
                                             case DOWN:
                                                  compoImage.getGraphics().drawImage(orkimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                             case LEFT:
                                                  compoImage.getGraphics().drawImage(orkimage[3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                             case UP:
                                                  compoImage.getGraphics().drawImage(orkimage[2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                             case RIGHT:
                                                  compoImage.getGraphics().drawImage(orkimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                        }
                                        break;
                                   case "Knight":
                                        switch (e.getOrientierung()) {
                                             case DOWN:
                                                  compoImage.getGraphics().drawImage(knightimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                             case LEFT:
                                                  compoImage.getGraphics().drawImage(knightimage[3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                             case UP:
                                                  compoImage.getGraphics().drawImage(knightimage[2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                             case RIGHT:
                                                  compoImage.getGraphics().drawImage(knightimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                                                  break;
                                        }
                                        break;
                                   case "Troll":
                                        if (((Monster) e).isHit()) {
                                             compoImage.getGraphics().drawImage(trollimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);

                                        } else {
                                             compoImage.getGraphics().drawImage(trollimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);

                                        }
                                        break;
                                   case "Stairs":
                                             compoImage.getGraphics().drawImage(stairsimage[0], x1 , y1, FIELDSIZE, FIELDSIZE, this);


                                        break;


                              }


                              if (e.isHit()) {

                                   compoImage.getGraphics().drawImage(bloodimage[e.getCounter()], x1, y1, FIELDSIZE, FIELDSIZE, this);

                              }



                         }
                    } catch (Exception ex) {
                         ex.printStackTrace();
                    }
               }
          }




     }


     @Override
     public void update(transEnum enu, MainModel mm) {
          if (enu == transEnum.entities) {
               entities = mm.getEntities();
               entcopy = (LinkedList<NPC>) entities.clone();
          } else if (enu == transEnum.fps) {
               this.fps = (int) mm.getFps();
               this.delta=mm.getDelta();
               animationdelay+=this.delta/1e6;
               if (animationdelay>20) {
                    animationdelay=0;
                    animcounter++;
               }
          } else if (enu == transEnum.playerstats) {
               this.player = mm.getPlayer();
               this.gameover=mm.isGameover();

          } else if (enu == transEnum.fogofwar) {
               this.fogofwar = mm.getFogofwar();
               this.fogofwarrepaint = true;
          }
          repaint();
     }

     @Override
     public void update(char[][] map) {
          this.map = map;
          repaint();

     }

     private void updateViewportCoord(Player p) {
         
          if (p != null && map != null) {
               if (p.getX()/FIELDSIZE < radiusx) {
                    this.viewportx = 0;
               } else {
                    this.viewportx = p.getX() - radiusx*FIELDSIZE;

               }
               if (p.getY()/FIELDSIZE < radiusy) {
                    this.viewporty = 0;
               } else {
                    this.viewporty = p.getY() - radiusy*FIELDSIZE;

               }
               if (map[0].length*FIELDSIZE - p.getX() < radiusx*FIELDSIZE) {
                    this.viewportx = p.getX() - radiusx*FIELDSIZE - (radiusx*FIELDSIZE - (map[0].length*FIELDSIZE - p.getX()));
               } else {
               }
               if (map.length*FIELDSIZE - p.getY() < radiusy*FIELDSIZE) {
                    this.viewporty = p.getY() - radiusy*FIELDSIZE - (radiusy*FIELDSIZE - (map.length*FIELDSIZE - p.getY()));
               } else {
               }
 if (viewportx<FIELDSIZE) {
               minimumx=0;
          } else {
              minimumx = viewportx/FIELDSIZE-1;
          }
          if (viewporty<FIELDSIZE) {
               minimumy=0;
          } else {
              minimumy = viewporty/FIELDSIZE-1;
          }
          if (viewporty/FIELDSIZE + viewportheight+1>=map.length) {
               maximumy = viewporty/FIELDSIZE + viewportheight;
          } else {
               maximumy = viewporty/FIELDSIZE + viewportheight+1;
          }
          if (viewportx/FIELDSIZE + viewportwidth+1>=map[0].length) {
               maximumx = viewportx/FIELDSIZE + viewportwidth;
          } else {
               maximumx=viewportx/FIELDSIZE + viewportwidth+1;
          }
          }
     }

     private BufferedImage[] loadPic(String path, int width) {

          BufferedImage pic = null;

          URL pathtopic = getClass().getResource(path);

          try {
               pic = ImageIO.read(pathtopic);

          } catch (IOException e) {
          }
          int anzahl = pic.getWidth() / width;
          BufferedImage[] pics = new BufferedImage[anzahl];
          for (int i = 0; i < anzahl; i++) {
               pics[i] = pic.getSubimage(i * pic.getWidth() / anzahl, 0, pic.getWidth() / anzahl, pic.getHeight());
          }
          return pics;



     }
     
     private BufferedImage[][] loadPic(String path, int width,int height) {

          BufferedImage pic = null;

          URL pathtopic = getClass().getResource(path);

          try {
               pic = ImageIO.read(pathtopic);

          } catch (IOException e) {
          }
          int anzahlx = pic.getWidth() / width;
          int anzahly = pic.getHeight() / height;
          BufferedImage[][] pics = new BufferedImage[anzahlx][anzahly];
          for (int i = 0; i < anzahlx; i++) {
               for (int j = 0; j < anzahly; j++) {
                    pics[i][j] = pic.getSubimage(i * width, j*height, width, height);
                    
               }
          }
          return pics;



     }

     public boolean isFogofwarrepaint() {
          return fogofwarrepaint;
     }

     public void setFogofwarrepaint(boolean fogofwarrepaint) {
          this.fogofwarrepaint = fogofwarrepaint;
     }
     private void animateWalking(Richtung dir,int x1,int y1){
     
               if (animcounter>=8) {
                    animcounter=0;
               }
          switch (dir) {
               case DOWN:
                    compoImage.getGraphics().drawImage(playerimage[animcounter][2], x1, y1, FIELDSIZE, FIELDSIZE, this);

                    
                    break;
               case LEFT:

                    compoImage.getGraphics().drawImage(playerimage[animcounter][1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                    break;
               case UP:

                    compoImage.getGraphics().drawImage(playerimage[animcounter][0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                    break;
               case RIGHT:

                    compoImage.getGraphics().drawImage(playerimage[animcounter][3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                    break;
     
     
          }
     }
     
     
     private BufferedImage [][] enlargePic(BufferedImage img[][],int cropx,int cropy){
          BufferedImage biggerimg[][]= new BufferedImage [img.length][img[0].length];
          for (int i = 0; i < img.length; i++) {
               for (int j = 0; j < img[0].length; j++) {
                    try {
                    biggerimg[i][j]= img[i][j].getSubimage(cropx, cropy, img[i][j].getWidth()-2*cropx, img[i][j].getHeight()-cropy);
                         
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                   
               }
          }
          
          return biggerimg;
          
          
          
          
     }
             
             
}
