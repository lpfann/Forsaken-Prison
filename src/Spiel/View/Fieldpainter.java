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

/**
 * Zeichen-Klasse für Spielfeld
 * @author Lukas
 */
public class Fieldpainter extends JPanel implements Observer {
   //Bilder aller Spielobjekte
   private static BufferedImage[][] playerimage;
   private static BufferedImage[][] playerattackingimage;
   private static BufferedImage[][] skelettimage;
   private static BufferedImage[][] skelettattackingimage;
   private static BufferedImage[][] monkimage;
   private static BufferedImage[][] monkattackingimage;
   private static BufferedImage[] orkimage;
   private static BufferedImage[] trollimage;
   private static BufferedImage[] chestimage;
   private static BufferedImage[] doorimage;
   private static BufferedImage[] bloodimage;
   private static BufferedImage groundimage;
   private static BufferedImage wallimage;
   private static BufferedImage keyimage;
   private static BufferedImage heartimage;
   private static BufferedImage[] knightimage;
   private static BufferedImage[] stairsimage;
   private static BufferedImage coinimage;
   //Offscreenimage für den Dungeon
   private static Image dungeonoffscreenImage;

   //Image für den FogofWar ausschnitt wo der Spieler schon war
   private static BufferedImage transpImg;
   private static Image fowoffscreenImage;
   private static Image compoImage;    //Das Image für alle gezeichneten unterabschnitte. wird am Ende auf das Graphics Objekt gepaintet
   private static Graphics offscreenGraph;
   private int FIELDSIZE; //Größe der Blöcke in der Spiellogik
   private int viewportx; // X Punkt in der Oberen linken Ecke der den Viewport bestimmt
   private int viewporty; //Y Punkt in der Oberen linken Ecke der den Viewport bestimmt
   private final int radiusy = 5;
   private final int radiusx = 8;
   private final int VIEWPORTWIDTH = 2 * radiusx;
   private final int VIEWPORTHEIGHT = 2 * radiusy;
   private int animcounter = 0; //Counter für Animationsausführung
   private Player player;
   private char[][] map; //Das Spielfeld
   private boolean[][] fogofwar; //Das Feld für den Kriegsnebel
   private LinkedList<NPC> entities = new LinkedList(); //Liste der Objekte auf der Karte
   private LinkedList<NPC> entcopy = new LinkedList(); //Kopie der Liste ^
   private LinkedList<Effect> effects = new LinkedList(); //Liste der Effekte

   public final int BLOCKSIZE = 50; // Die Größe der Blöcke in der Grafikausgabe am Ende
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
   private boolean dungeonrepaint = true;
   private boolean fogofwarrepaint = true;


   /**
    * Konstrukor der Spielfeld Zeichners
    * @param breite Breite des Fensters
    * @param hoehe Höhe des Fensters
    * @param p Der Spieler. wird nur für Initalisierungszwecke übergeben
    * @param fieldsize die breite der Blöcke. auch nur für ^
    */
   public Fieldpainter(int breite, int hoehe, Player p, int fieldsize) {


      this.setPreferredSize(new Dimension(BLOCKSIZE * VIEWPORTWIDTH, BLOCKSIZE * VIEWPORTHEIGHT));
      this.setDoubleBuffered(true);
      this.FIELDSIZE = fieldsize;
      player = p;
      updateViewportCoord(p);

      try {
         groundimage = ImageIO.read(getClass().getResource("/resources/groundDun.png"));
         wallimage = ImageIO.read(getClass().getResource("/resources/HBlockDun.png"));
         keyimage = ImageIO.read(getClass().getResource("/resources/key.png"));
         heartimage = ImageIO.read(getClass().getResource("/resources/heart.png"));
         coinimage = ImageIO.read(getClass().getResource("/resources/coin.png"));
         playerimage = enlargePic(loadPic("/resources/new-player.png", 64, 64), 10, 15);
         playerattackingimage = enlargePic(loadPic("/resources/new-player-attack.png", 64, 64), 10, 15);
         skelettimage = enlargePic(loadPic("/resources/skeleton-walking.png", 64, 64), 10, 15);
         skelettattackingimage = enlargePic(loadPic("/resources/skeleton-attacking.png", 64, 64), 10, 15);
         monkimage = enlargePic(loadPic("/resources/evil-monk-walking.png", 64, 64), 10, 15);
         monkattackingimage = enlargePic(loadPic("/resources/evil-monk-attacking.png", 64, 64), 10, 15);
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

   /**
    * Die Update Methode des Observers
    * @param enu Art des Updates
    * @param mm Kopie des MainModel
    */
   @Override
   public void update(transEnum enu, MainModel mm) {
      if (enu == transEnum.entities) {
         entities = mm.getEntities();
         entcopy = (LinkedList<NPC>) entities.clone();
         effects = (LinkedList<Effect>) mm.getEffects().clone();

      } else if (enu == transEnum.fps) {
         this.fps = (int) mm.getFps();
         this.delta = mm.getDelta();
         animationdelay += this.delta / 1e6;
         if (animationdelay > 100) {
            animationdelay = 0;
            animcounter++;
         }

      } else if (enu == transEnum.playerstats) {
         this.player = mm.getPlayer();
         updateViewportCoord(player);
         this.gameover = mm.isGameover();

      } else if (enu == transEnum.fogofwar) {
         this.fogofwar = mm.getFogofwar();



      }
      repaint();
   }

   /**
    * Spielfeld wird übergeben
    * @param map Spielfeld
    */
   @Override
   public void update(char[][] map) {
      this.map = map;
      repaint();

   }
/**
 * Hauptzeichenfunktion. ruft mehrere unter Methoden auf
 * @param g Graphics Objekt
 */
   @Override
   protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      if (map != null) {

         g.setColor(Color.red);
         compoImage = createImage(VIEWPORTWIDTH * FIELDSIZE, VIEWPORTHEIGHT * FIELDSIZE);

         Graphics2D cg = (Graphics2D) compoImage.getGraphics();
         //Qualitätseinstellungen
         cg.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
         cg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         cg.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);


         //Dungeon Zeichnen
         paintDungeon(cg);
         //Entities Zeichnen
         paintallEntities(cg);
         //Fog of War zeichnen
         paintFogofWar(cg);
         //Effekte zeichnen
         paintEffects(cg);

         cg.dispose();


         //Zeichnen des zusammengefügten Objekts
         g.drawImage(compoImage, 0, 0, VIEWPORTWIDTH * BLOCKSIZE, VIEWPORTHEIGHT * BLOCKSIZE, this);




         //Hilfe text zeichnen
         g.setColor(Color.white);
         g.drawString("ESC - Menü", 10, 10);
         g.drawString("i - Inventar", this.getPreferredSize().width - 90, 10);



      }
   }
/**
 * Alle Effekte zeichnen
 * @param cg
 */
   private void paintEffects(Graphics2D cg) {
      if (!effects.isEmpty()) {
         for (Effect e : effects) {
            if (e.getX() < viewportx - FIELDSIZE || e.getX() > viewportx + FIELDSIZE + VIEWPORTWIDTH * FIELDSIZE
                    || e.getY() < viewporty - FIELDSIZE || e.getY() > viewporty + FIELDSIZE + VIEWPORTHEIGHT * FIELDSIZE) {
            } else {
               int x1 = e.getX() - viewportx;
               int y1 = e.getY() - viewporty;

               cg.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
               cg.setColor(((Effect) e).getColor());

               cg.drawString(((Effect) e).getContent(), x1 + FIELDSIZE / 2, y1);



            }



         }

      }



   }
/**
 *
 * Zeichnen des Dungeons. Nur wenn dungeonrepaint=true ist, wird komplett neugezeichnet, ansonsten ein offscreenimage
 * @param g
 */
   private void paintDungeon(Graphics2D g) {


      if (MainModel.dungeonrepaint) {
         MainModel.dungeonrepaint = false;
         dungeonoffscreenImage = createImage(map[0].length * FIELDSIZE, map.length * FIELDSIZE);
         offscreenGraph = dungeonoffscreenImage.getGraphics();

         for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
               if (map[y][x] == '*') {
                  offscreenGraph.drawImage(wallimage, x * FIELDSIZE,
                          y * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
               } else {
                  offscreenGraph.drawImage(groundimage, x * FIELDSIZE,
                          y * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
               }
            }
         }

      }


      //Zeichen des Dungeon Bildes
      compoImage.getGraphics().drawImage(dungeonoffscreenImage, -viewportx, -viewporty, this);

   }
/**
 * Fog of War wird auf ein transparentes Bild gepaintet.
 * Der transparente Ausschnitt ist der sichtbare Bereich des Spielers
 * @param g
 */
   private void paintFogofWar(Graphics2D g) {
      if (MainModel.fogofwarrepaint) {
         MainModel.fogofwarrepaint = false;
         //erstellt ein transparentes Bild
         transpImg = new BufferedImage(map[0].length * FIELDSIZE, map.length * FIELDSIZE, BufferedImage.TRANSLUCENT);
         fowoffscreenImage = transpImg;
         Graphics2D q = transpImg.createGraphics();

         for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
               if (fogofwar[y][x] == true) {
                  q.setColor(Color.black);
                  q.fillRect(x * FIELDSIZE, y * FIELDSIZE, FIELDSIZE, FIELDSIZE);
               } else {
               }
            }
         }
         q.dispose();
      }

      compoImage.getGraphics().drawImage(fowoffscreenImage, -viewportx, -viewporty, this);



   }

/**
 * Zeichnen aller Spielobjekte in einem Switch
 * @param cg
 */
   private void paintallEntities(Graphics2D cg) {

      if (!entities.isEmpty()) {

         for (ListIterator<NPC> it = entcopy.listIterator(); it.hasNext();) {
            NPC e = it.next();
            try {
               //Bestimmt ob Objekt im Viewportbereich liegt
               if (e.getX() < viewportx - FIELDSIZE || e.getX() > viewportx + FIELDSIZE + VIEWPORTWIDTH * FIELDSIZE
                       || e.getY() < viewporty - FIELDSIZE || e.getY() > viewporty + FIELDSIZE + VIEWPORTHEIGHT * FIELDSIZE)
               {
                  //liegt nicht im Viewport-> wird nicht gezeichnet
               } else {
                  //Koordinate in Relation zum Viewportpunkt
                  int x1 = e.getX() - viewportx;
                  int y1 = e.getY() - viewporty;


                  switch (e.getClass().getSimpleName()) {
                     case "Player":
                        switch (e.getOrientierung()) {
                           case DOWN:
                              if (player.isWalking()) {
                                 animateWalking(cg, Richtung.DOWN, x1, y1, playerimage);
                              } else if (player.isAttacking()) {
                                 animateAttack(cg, Richtung.DOWN, x1, y1, playerattackingimage);
                              } else {
                                 cg.drawImage(playerimage[0][2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case LEFT:
                              if (player.isWalking()) {
                                 animateWalking(cg, Richtung.LEFT, x1, y1, playerimage);
                              } else if (player.isAttacking()) {
                                 animateAttack(cg, Richtung.LEFT, x1, y1, playerattackingimage);
                              } else {
                                 cg.drawImage(playerimage[0][1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case UP:
                              if (player.isWalking()) {
                                 animateWalking(cg, Richtung.UP, x1, y1, playerimage);
                              } else if (player.isAttacking()) {
                                 animateAttack(cg, Richtung.UP, x1, y1, playerattackingimage);
                              } else {
                                 cg.drawImage(playerimage[0][0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case RIGHT:
                              if (player.isWalking()) {
                                 animateWalking(cg, Richtung.RIGHT, x1, y1, playerimage);
                              } else if (player.isAttacking()) {
                                 animateAttack(cg, Richtung.RIGHT, x1, y1, playerattackingimage);
                              } else {
                                 cg.drawImage(playerimage[0][3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                        }
                        break;
                     case "EvilMonk":
                        switch (e.getOrientierung()) {
                           case DOWN:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.DOWN, x1, y1, monkimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.DOWN, x1, y1, monkattackingimage);
                              } else {
                                 cg.drawImage(monkimage[0][2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case LEFT:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.LEFT, x1, y1, monkimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.LEFT, x1, y1, monkattackingimage);
                              } else {
                                 cg.drawImage(monkimage[0][1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case UP:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.UP, x1, y1, monkimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.UP, x1, y1, monkattackingimage);
                              } else {
                                 cg.drawImage(monkimage[0][0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case RIGHT:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.RIGHT, x1, y1, monkimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.RIGHT, x1, y1, monkattackingimage);
                              } else {
                                 cg.drawImage(monkimage[0][3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                        }
                        break;
                     case "Skelett":
                        switch (e.getOrientierung()) {
                           case DOWN:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.DOWN, x1, y1, skelettimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.DOWN, x1, y1, skelettattackingimage);
                              } else {
                                 cg.drawImage(skelettimage[0][2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case LEFT:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.LEFT, x1, y1, skelettimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.LEFT, x1, y1, skelettattackingimage);
                              } else {
                                 cg.drawImage(skelettimage[0][1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case UP:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.UP, x1, y1, skelettimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.UP, x1, y1, skelettattackingimage);
                              } else {
                                 cg.drawImage(skelettimage[0][0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                           case RIGHT:
                              if (e.isWalking()) {
                                 animateWalking(cg, Richtung.RIGHT, x1, y1, skelettimage);
                              } else if (e.isAttacking()) {
                                 animateAttack(cg, Richtung.RIGHT, x1, y1, skelettattackingimage);
                              } else {
                                 cg.drawImage(skelettimage[0][3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              }
                              break;
                        }
                        break;
                     case "Door":
                        if (((Door) e).getOpen()) {
                           cg.drawImage(doorimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);

                        } else {
                           cg.drawImage(doorimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);

                        }
                        break;
                     case "Chest":
                        if (((Chest) e).isOpened()) {
                           cg.drawImage(chestimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);

                        } else {
                           cg.drawImage(chestimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);

                        }
                        break;
                     case "Ork":
                        switch (e.getOrientierung()) {
                           case DOWN:
                              cg.drawImage(orkimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                           case LEFT:
                              cg.drawImage(orkimage[3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                           case UP:
                              cg.drawImage(orkimage[2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                           case RIGHT:
                              cg.drawImage(orkimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                        }
                        break;
                     case "Knight":
                        switch (e.getOrientierung()) {
                           case DOWN:
                              cg.drawImage(knightimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                           case LEFT:
                              cg.drawImage(knightimage[3], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                           case UP:
                              cg.drawImage(knightimage[2], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                           case RIGHT:
                              cg.drawImage(knightimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);
                              break;
                        }
                        break;
                     case "Troll":
                        if (((Monster) e).isHit()) {
                           cg.drawImage(trollimage[1], x1, y1, FIELDSIZE, FIELDSIZE, this);

                        } else {
                           cg.drawImage(trollimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);

                        }
                        break;
                     case "Stairs":
                        cg.drawImage(stairsimage[0], x1, y1, FIELDSIZE, FIELDSIZE, this);


                        break;
                     case "Key":
                        cg.drawImage(keyimage, x1, y1, FIELDSIZE, FIELDSIZE, this);


                        break;
                     case "Heart":
                        cg.drawImage(heartimage, x1, y1, FIELDSIZE, FIELDSIZE, this);


                        break;
                     case "Coin":
                        cg.drawImage(coinimage, x1, y1, FIELDSIZE, FIELDSIZE, this);


                        break;




                  }


                  if (e.isHit()) {

                     cg.drawImage(bloodimage[e.getCounter()], x1, y1, FIELDSIZE, FIELDSIZE, this);

                  }



               }
            } catch (NullPointerException ex) {
               ex.printStackTrace();
            }
         }
      }




   }
/**
 * Berechnen der Viewport-Daten wenn Spieler sich bewegt hat.
 * @param p Spieler
 */
   private void updateViewportCoord(Player p) {

      if (p != null && map != null) {
         if (p.getX() / FIELDSIZE < radiusx) {
            //Spieler ist am Rand des Feldes
            this.viewportx = 0;
         } else {
            this.viewportx = p.getX() - radiusx * FIELDSIZE;

         }
         if (p.getY() / FIELDSIZE < radiusy) {
            //Spieler ist am Rand des Feldes
            this.viewporty = 0;
         } else {
            this.viewporty = p.getY() - radiusy * FIELDSIZE;

         }
         if (map[0].length * FIELDSIZE - p.getX() < radiusx * FIELDSIZE) {
            this.viewportx = p.getX() - radiusx * FIELDSIZE - (radiusx * FIELDSIZE - (map[0].length * FIELDSIZE - p.getX()));
         } else {
         }
         if (map.length * FIELDSIZE - p.getY() < radiusy * FIELDSIZE) {
            this.viewporty = p.getY() - radiusy * FIELDSIZE - (radiusy * FIELDSIZE - (map.length * FIELDSIZE - p.getY()));
         } else {
         }

         //Bestimmen des Minimum und es Maximums für den Rahmen um den Viewport damit das SubPixel Verschieben ohne Bildartefakte geht
         if (viewportx < FIELDSIZE) {
            minimumx = 0;
         } else {
            minimumx = viewportx / FIELDSIZE - 1;
         }
         if (viewporty < FIELDSIZE) {
            minimumy = 0;
         } else {
            minimumy = viewporty / FIELDSIZE - 1;
         }
         if (viewporty / FIELDSIZE + VIEWPORTHEIGHT + 1 > map.length) {
            maximumy = viewporty / FIELDSIZE + VIEWPORTHEIGHT;
         } else {
            maximumy = viewporty / FIELDSIZE + VIEWPORTHEIGHT + 1;
         }
         if (viewportx / FIELDSIZE + VIEWPORTWIDTH + 1 > map[0].length) {
            maximumx = viewportx / FIELDSIZE + VIEWPORTWIDTH;
         } else {
            maximumx = viewportx / FIELDSIZE + VIEWPORTWIDTH + 1;
         }
      }
   }
/**
 * Methode zum einlesen von Bildern
 * Erstellt Array aus Sub-Bildern
 * @param path Der Pfad zum Bild
 * @param width Die Breite eines einzelnen Sub-Bildes
 * @return  Bild-Array
 */
   private BufferedImage[] loadPic(String path, int width) {

      BufferedImage pic = null;

      URL pathtopic = getClass().getResource(path);

      try {
         pic = ImageIO.read(pathtopic);

      } catch (IOException e) {
         e.printStackTrace();
      }
      int anzahl = pic.getWidth() / width;
      BufferedImage[] pics = new BufferedImage[anzahl];
      for (int i = 0; i < anzahl; i++) {
         pics[i] = pic.getSubimage(i * pic.getWidth() / anzahl, 0, pic.getWidth() / anzahl, pic.getHeight());
      }
      return pics;



   }
/**
 * Methode zum einlesen von Bildern
 * Erstellt 2 dimensionales Array aus Sub-Bildern
 * @param path Pfad zur Bilddatei
 * @param width Breite eines Sub-Bilders
 * @param height Höhe eines Sub-Bilders
 * @return 2-dimensionales Array aus Sub-Bildern
 */
   private BufferedImage[][] loadPic(String path, int width, int height) {

      BufferedImage pic = null;

      URL pathtopic = getClass().getResource(path);

      try {
         pic = ImageIO.read(pathtopic);

      } catch (IOException e) {
         e.printStackTrace();
      }
      int anzahlx = pic.getWidth() / width;
      int anzahly = pic.getHeight() / height;
      BufferedImage[][] pics = new BufferedImage[anzahlx][anzahly];
      for (int i = 0; i < anzahlx; i++) {
         for (int j = 0; j < anzahly; j++) {
            pics[i][j] = pic.getSubimage(i * width, j * height, width, height);

         }
      }
      return pics;



   }

   /**
    *
    * @return
    */
   public boolean isFogofwarrepaint() {
      return fogofwarrepaint;
   }

   /**
    *
    * @param fogofwarrepaint
    */
   public void setFogofwarrepaint(boolean fogofwarrepaint) {
      this.fogofwarrepaint = fogofwarrepaint;
   }
/**
 * Animation der Laufbewegung
 *
 * @param cg Graphics Object
 * @param dir Richtung der Laufbewegung
 * @param x1 Koordinate des Objekts
 * @param y1 Koordinate des Objekts
 * @param i Bild-Array mit Bildern der Bewegung
 */
   private void animateWalking(Graphics2D cg, Richtung dir, int x1, int y1, BufferedImage[][] i) {

      if (animcounter >= 8) {
         animcounter = 0;
      }
      switch (dir) {
         case DOWN:
            cg.drawImage(i[animcounter][2], x1, y1, FIELDSIZE, FIELDSIZE, this);


            break;
         case LEFT:

            cg.drawImage(i[animcounter][1], x1, y1, FIELDSIZE, FIELDSIZE, this);
            break;
         case UP:

            cg.drawImage(i[animcounter][0], x1, y1, FIELDSIZE, FIELDSIZE, this);
            break;
         case RIGHT:

            cg.drawImage(i[animcounter][3], x1, y1, FIELDSIZE, FIELDSIZE, this);
            break;


      }
   }
/**
 * Animation der Angriffsbewegung
 *
 * @param cg Graphics Object
 * @param dir Richtung des Objekts
 * @param x1
 * @param y1
 * @param i Bild-Arrays
 */
   private void animateAttack(Graphics2D cg, Richtung dir, int x1, int y1, BufferedImage[][] i) {

      if (animcounter >= 5) {
         animcounter = 0;
      }
      switch (dir) {
         case DOWN:
            cg.drawImage(i[animcounter][2], x1, y1, FIELDSIZE, FIELDSIZE, this);


            break;
         case LEFT:

            cg.drawImage(i[animcounter][1], x1, y1, FIELDSIZE, FIELDSIZE, this);
            break;
         case UP:

            cg.drawImage(i[animcounter][0], x1, y1, FIELDSIZE, FIELDSIZE, this);
            break;
         case RIGHT:

            cg.drawImage(i[animcounter][3], x1, y1, FIELDSIZE, FIELDSIZE, this);
            break;


      }
   }
/**
 * Vergrößert eingelesenes Bild Array
 *
 * @param img Eingabe Bild
 * @param cropx Teil der vom Eingangsbild abgeschnitten werden soll
 * @param cropy ^
 * @return Bild Array mit vergrößterten Sub-Bildern
 */
   private BufferedImage[][] enlargePic(BufferedImage img[][], int cropx, int cropy) {
      BufferedImage biggerimg[][] = new BufferedImage[img.length][img[0].length];
      for (int i = 0; i < img.length; i++) {
         for (int j = 0; j < img[0].length; j++) {
            try {
               biggerimg[i][j] = img[i][j].getSubimage(cropx, cropy, img[i][j].getWidth() - 2 * cropx, img[i][j].getHeight() - cropy);

            } catch (Exception e) {
               e.printStackTrace();
            }

         }
      }

      return biggerimg;




   }

   @Override
   public void update(sounds s, long delta) {
   }
}
