package Spiel.View;



import Spiel.View.Observer.transEnum;
import Spiel.model.Entities.*;
import Spiel.model.MainModel;
import Spiel.model.MainModel.Richtung;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fieldpainter extends JPanel implements Observer {

    private BufferedImage[] playerimage;
    private BufferedImage[] orkimage;
    private BufferedImage[] trollimage;
    private BufferedImage[] chestimage;
    private BufferedImage[] doorimage;
    private BufferedImage[] bloodimage;
    
    private BufferedImage groundimage;
    private BufferedImage wallimage;
    
    private Image dungeonoffscreenImage;
    BufferedImage transpImg;
    private Image fowoffscreenImage;
    private Image compoImage;
    private Graphics offscreenGraph;
    private BufferedImage finalimage;
    private BufferedImage tempimg;
    private boolean dungeonrepaint=true;
    private boolean fogofwarrepaint=true;
    private int viewportx;
    private int viewporty;
    private int radiusy=5;
    private int radiusx=8;
    private int viewportwidth=2*radiusx;
    private int viewportheight=2*radiusy;
    private int fps;
    private int animationCounter=0;
    private Player player;
    private char[][] map;
    private boolean[][] fogofwar;
    private LinkedList<NPC> entities= new LinkedList();
    private LinkedList<NPC> entcopy= new LinkedList();
    public static final int FIELDSIZE=40;
    public final int BLOCKSIZE=50;
    public static final int RESOLUTIONX=800;
    public static final int RESOLUTIONY=600;
    
    public Fieldpainter(int breite, int hoehe,Player p) {
        

        this.setPreferredSize(new Dimension(BLOCKSIZE*viewportwidth, BLOCKSIZE*viewportheight));
        this.setDoubleBuffered(true);
        player=p;
        updateViewportCoord(p);
        try {
            groundimage = ImageIO.read(getClass().getResource("/resources/groundDun.png"));
            wallimage = ImageIO.read(getClass().getResource("/resources/HBlockDun.png"));
            playerimage= loadPic("/resources/player.png",20);
            orkimage= loadPic("/resources/ork.png",38);
            trollimage= loadPic("/resources/troll.png",20);
            doorimage= loadPic("/resources/door.png",20);
            chestimage= loadPic("/resources/chest.png",32);
            bloodimage= loadPic("/resources/bloodsplatter.png",20);

        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }

}


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        updateViewportCoord(player);
        //Dungeon Zeichnen
        compoImage= createImage(viewportwidth*FIELDSIZE, viewportheight*FIELDSIZE);

        paintDungeon(g);
        paintallEntities(g);
        paintFogofWar(g);
        
        
        
        
        try {
        g.drawImage(compoImage, 0, 0, viewportwidth*BLOCKSIZE, viewportheight * BLOCKSIZE, this);
            
        } catch (Exception e) {
          e.printStackTrace(); 
        }
        
        g.setColor(Color.white);
        g.drawString("ESC - Menü", 10, 10);

    }
    
    private void paintDungeon(Graphics g) {


            offscreenGraph = compoImage.getGraphics();
            for (int y = viewporty; y < viewporty+viewportheight; y++) {
                for (int x = viewportx; x < viewportx+viewportwidth; x++) {
                    if (map[y][x] == '*') {
                        offscreenGraph.drawImage(wallimage, (x-viewportx) * FIELDSIZE, (y-viewporty) * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                    } else {
                        offscreenGraph.drawImage(groundimage, (x-viewportx )* FIELDSIZE, (y-viewporty) * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                    }
                }
            }
        
        compoImage.getGraphics().drawImage(dungeonoffscreenImage, 0, 0, this);
        
    }
    
    //Zeichnen der Entities
    private void paintallEntities(Graphics g) {
        if (!entities.isEmpty()) {
            
            for (ListIterator<NPC> it = entcopy.listIterator(); it.hasNext();) {
                NPC e = it.next();
                try {
                if (e.getX() < viewportx || e.getX() > viewportx + viewportwidth || e.getY() < viewporty || e.getY() > viewporty + viewportheight) {
                } else {
                    int x1=e.getX()-viewportx;
                    int y1=e.getY()-viewporty;
                    switch (e.getClass().getSimpleName()) {
                        case "Player":
                            switch (e.getOrientierung()) {
                                case DOWN:
                                    compoImage.getGraphics().drawImage(playerimage[0], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                                case LEFT:
                                    compoImage.getGraphics().drawImage(playerimage[1], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                                case UP:
                                    compoImage.getGraphics().drawImage(playerimage[2], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                                case RIGHT:
                                    compoImage.getGraphics().drawImage(playerimage[3], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                            }
                            break;
                        case "Door":
                            if (((Door) e).getOpen()) {
                                compoImage.getGraphics().drawImage(doorimage[1], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);

                            } else {
                                compoImage.getGraphics().drawImage(doorimage[0], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);

                            }
                            break;
                        case "Truhe":
                            if (((Truhe) e).isOpened()) {
                                compoImage.getGraphics().drawImage(chestimage[1], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);

                            } else {
                                compoImage.getGraphics().drawImage(chestimage[0], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);

                            }
                            break;
                        case "Ork":
                                switch (e.getOrientierung()) {
                               case DOWN:
                                    compoImage.getGraphics().drawImage(orkimage[1], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                                case LEFT:
                                    compoImage.getGraphics().drawImage(orkimage[3], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                                case UP:
                                    compoImage.getGraphics().drawImage(orkimage[2], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                                case RIGHT:
                                    compoImage.getGraphics().drawImage(orkimage[0], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                                    break;
                                }
                            break;
                        case "Troll":
                            if (((Monster) e).isHit()) {
                                compoImage.getGraphics().drawImage(trollimage[1], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);

                            } else {
                                compoImage.getGraphics().drawImage(trollimage[0], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);

                            }
                            break;


                    }
                    
                    
                    if (e.isHit()) {
                        
                        compoImage.getGraphics().drawImage(bloodimage[e.getCounter()], x1 * FIELDSIZE, y1 * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                        
                    }



                }
                } catch ( Exception ex) {
                        ex.printStackTrace();
                }
            }
        }




    }
    
    private void paintFogofWar(Graphics g) {
            //if (fogofwarrepaint) {
            fogofwarrepaint = false;
            //erstellt ein transparentes Bild
            transpImg= new BufferedImage(viewportwidth * FIELDSIZE, viewportheight * FIELDSIZE, BufferedImage.TRANSLUCENT);
            fowoffscreenImage = transpImg;
            Graphics2D q = transpImg.createGraphics();
            
            for (int y = viewporty; y < viewporty+viewportheight; y++) {
                for (int x = viewportx; x < viewportx+viewportwidth; x++) {
                    if (fogofwar[y][x]==true) {
                        q.setColor(Color.black);
                        q.fillRect((x-viewportx) * FIELDSIZE, (y-viewporty)  * FIELDSIZE, FIELDSIZE, FIELDSIZE);
                    } else {
                            
                    }
                }
            }
            q.dispose();
        //}
        
        compoImage.getGraphics().drawImage(fowoffscreenImage, 0, 0, this);
            
            
            
    }

    @Override
    public void update(transEnum enu, MainModel mm) {
          if (enu==transEnum.entities) {
               entities=mm.getEntities();
               entcopy=(LinkedList<NPC>) entities.clone();
          } else if (enu==transEnum.fps) {
              this.fps=(int)mm.getFps();
          } else if (enu==transEnum.playerstats) {
              this.player=mm.getPlayer();
              
          } else if (enu==transEnum.fogofwar) {
                  this.fogofwar=mm.getFogofwar();
                  this.fogofwarrepaint=true;
          }
          repaint();
    }


    @Override
    public void update(char[][] map) {
        this.map=map;
        repaint();

    }


    private void updateViewportCoord(Player p){
        
        if (p!=null && map!=null) {
            if (p.getX() < radiusx) {
                this.viewportx = 0;
            } else {
                this.viewportx = p.getX() - radiusx;

            }
            if (p.getY() < radiusy) {
                this.viewporty = 0;
            } else {
                this.viewporty = p.getY() - radiusy;

            }
            if (map[0].length - p.getX() < radiusx) {
                this.viewportx= p.getX()-radiusx-(radiusx-(map[0].length - p.getX())) ;
            } else {
            }
            if (map.length - p.getY() < radiusy) {
                this.viewporty = p.getY()-radiusy-(radiusy-(map.length - p.getY()));
            } else {
            }
            
        }
    }
    private BufferedImage[] loadPic(String path,int width) {

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

    
        
    public void setFlag(boolean flag) {
        this.dungeonrepaint = flag;
    }

        public boolean isFogofwarrepaint() {
                return fogofwarrepaint;
        }

        public void setFogofwarrepaint(boolean fogofwarrepaint) {
                this.fogofwarrepaint = fogofwarrepaint;
        }


}
