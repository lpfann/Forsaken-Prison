/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.MainModel;
import Spiel.model.MainModel.Richtung;
import Spiel.model.Room;
import Spiel.model.UtilFunctions;
import java.awt.Color;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author lukas
 */
public abstract class NPC implements Movable, Serializable {

   private int x;
   private int y;
   private char icon;
   private String name;
   private MainModel main;
   private Room room;
   private int animationCounter = 0;
   private int internalCounter = 0;
   private long delay;
   private long gametick = 0;
   private int dmg;
   private int hp;
   private int defence;
   private int movex = 0;
   private int movey = 0;
   private int targetx = 0;
   private int targety;
   private double walkspeed = 2e8;
   private boolean walking;
   private final int FIELDSIZE;
   private boolean hit = false;
   private Richtung orientierung = Richtung.DOWN;
   private boolean removethis = false;
   private boolean attacking = false;
   private int attackanim = 0;

   /**
    * Konstrukor für abstrake Klasse NPC, wird von den Subklassen aufgerufen
    *
    * @param x X-Koord
    * @param y Y-Koord
    * @param icon Char Icon zur Identifizierung auf Map
    * @param main MainModel wird übergeben
    */
   public NPC(int x, int y, char icon, MainModel main) {
      this.FIELDSIZE = main.getFIELDSIZE();
      this.x = x * FIELDSIZE;
      this.y = y * FIELDSIZE;
      this.targetx = x;
      this.targety = y;
      this.icon = icon;
      this.main = main;
   }

   /**
    * Bewegung des Objekts auf dem Spielfeld
    */
   @Override
   public void move() {

      if (walking && movex == 0 && movey == 0 && getMain().map[fieldinFront(1)[1]][fieldinFront(1)[0]] == ' ') {
         main.map[targety / FIELDSIZE][targetx / FIELDSIZE] = ' ';

         switch (orientierung) {
            case LEFT:
               movex = -FIELDSIZE;
               targetx = x - FIELDSIZE;

               break;
            case RIGHT:
               movex = +FIELDSIZE;
               targetx = x + FIELDSIZE;

               break;
            case UP:
               movey = -FIELDSIZE;
               targety = y - FIELDSIZE;
               break;
            case DOWN:
               movey = +FIELDSIZE;
               targety = y + FIELDSIZE;
               break;
         }

         main.map[targety / FIELDSIZE][targetx / FIELDSIZE] = getIcon();
      }

      if (movex != 0) {
         if (movex > 0) {
            this.x = (int) (Math.ceil(this.x + (movex * main.getDelta() / walkspeed)));
            if (x >= targetx) {
               this.x = targetx;
               movex = 0;
            }
         }
         if (movex < 0) {
            this.x = (int) (Math.floor(this.x + (movex * main.getDelta() / walkspeed)));
            if (x <= targetx) {
               this.x = targetx;
               movex = 0;

            }
         }




      } else {
      }

      if (movey != 0) {
         if (movey > 0) {
            this.y = (int) (Math.ceil(this.y + (movey * main.getDelta() / walkspeed)));
            if (y >= targety) {
               this.y = targety;
               movey = 0;
            }
         }
         if (movey < 0) {
            this.y = (int) (Math.floor(this.y + (movey * main.getDelta() / walkspeed)));
            if (y <= targety) {
               this.y = targety;
               movey = 0;

            }
         }



      }

   }

   /**
    * Ausführung der Spiellogik
    *
    * @param delta Zeit seit dem letzten GameLoop-Durchlauf
    */
   @Override
   public void doLogic(long delta) {
      delay += main.getDelta() / 1e6;
      attackanim += delta / 1e6;
      if (attackanim > 500) {
         setAttacking(false);
         attackanim = 0;
      }
      if (hit) {
         if (internalCounter > 9) {
            internalCounter = 0;
            animationCounter = 0;
            hit = false;
         }
         if (animationCounter < 2) {
            animationCounter++;
         }
         internalCounter++;

      }
      if (gametick == 60) {
         gametick = 0;
      } else {
         gametick++;
      }




   }

   /**
    * Map im Model für das Objekt ändern
    */
   public void changeMapforObject() {
      main.map[getY() / FIELDSIZE][getX() / FIELDSIZE] = getIcon();

   }

   /**
    * Platzieren des NPC im Bereich von xy bis xy+breite/hoehe
    *
    * @param x1 Obere Linke Ecke x-Wert
    * @param y1 Obere Linke Ecke y-Wert
    * @param w Breite des Bereichs wo NPC platziert werden soll
    * @param h Höhe des Bereichs wo NPC platziert werden soll
    */
   public void setstartposition(int x1, int y1, int w, int h) {
      boolean fertig = false;
      while (!fertig) {
         int xx = Spiel.model.UtilFunctions.randomizer(x1, (x1 + w - 1));
         int yy = Spiel.model.UtilFunctions.randomizer(y1, (y1 + h - 1));
         if (main.map[yy][xx] == ' ') {
            this.x = xx * FIELDSIZE;
            this.y = yy * FIELDSIZE;
            this.targetx = this.x;
            this.targety = this.y;

            main.map[yy][xx] = this.icon;
            fertig = true;
         }


      }


   }

   /**
    * Platzieren des NPC im Bereich von xy bis xy+breite/hoehe , mit Abfrage ob
    * NPC andere Objekte blockiert
    *
    * @param x1 Obere Linke Ecke x-Wert
    * @param y1 Obere Linke Ecke y-Wert
    * @param w Breite des Bereichs wo NPC platziert werden soll
    * @param h Höhe des Bereichs wo NPC platziert werden soll
    * @param ent Liste von Objekten die nicht blockiert werden sollen
    */
   public void setstartpositionWithNPCcheck(int x1, int y1, int w, int h, LinkedList<NPC> ent) {
      boolean fertig = false;
      for (int i = 0; i < x1 * y1; i++) {

         while (!fertig) {
            int xx = Spiel.model.UtilFunctions.randomizer(x1, (x1 + w - 1));
            int yy = Spiel.model.UtilFunctions.randomizer(y1, (y1 + h - 1));

            if (main.map[yy][xx] == ' ' && notinFrontofDoor(xx, yy) && noOtheNpcs(xx * FIELDSIZE, yy * FIELDSIZE, ent)) {
               this.x = xx * FIELDSIZE;
               this.y = yy * FIELDSIZE;
               this.targetx = this.x;
               this.targety = this.y;

               main.map[yy][xx] = this.icon;
               fertig = true;
               break;
            }

         }
      }


   }

   /**
    * Prüfen ob neben der Koordinate andere Objekte sind
    *
    * @param x Koordinate
    * @param y Koordinate
    * @param ent Liste von Objekten
    * @return Keine anderen Objekte -> True
    */
   private boolean noOtheNpcs(int x, int y, LinkedList<NPC> ent) {

      if (findEntitieonMap(x / FIELDSIZE - 1, y / FIELDSIZE - 1, ent) == null && findEntitieonMap(x / FIELDSIZE, y / FIELDSIZE - 1, ent) == null && findEntitieonMap(x / FIELDSIZE - 1, y / FIELDSIZE, ent) == null
              && findEntitieonMap(x / FIELDSIZE + 1, y / FIELDSIZE - 1, ent) == null && findEntitieonMap(x / FIELDSIZE + 1, y / FIELDSIZE, ent) == null && findEntitieonMap(x / FIELDSIZE + 1, y / FIELDSIZE + 1, ent) == null
              && findEntitieonMap(x / FIELDSIZE - 1, y / FIELDSIZE + 1, ent) == null && findEntitieonMap(x / FIELDSIZE, y / FIELDSIZE + 1, ent) == null) {
         return true;


      } else {
         return false;
      }

   }

   /**
    * Suche in welchem Raum das Objekt ist
    *
    * @return Raum in dem sich Objekt befindet
    */
   public Room findRoomLocation() {
      LinkedList<Room> rooms = this.main.getDungeon().getRooms();

      for (Room r : rooms) {
         if (r.getX1() <= this.x / FIELDSIZE && this.x / FIELDSIZE <= r.getX1() + r.getBreite() && r.getY1() <= this.y / FIELDSIZE && this.y / FIELDSIZE <= r.getY1() + r.getHoehe()) {
            this.room = r;
         } else {
         }

      }
      return room;
   }

   /**
    * Raum an Koordinate XY
    *
    * @param x
    * @param y
    * @return Raum
    */
   public Room findRoomLocationatXY(int x, int y) {
      LinkedList<Room> rooms = this.main.getDungeon().getRooms();
      Room foundroom = null;

      for (Room r : rooms) {
         if (r.getX1() <= x && x <= r.getX1() + r.getBreite() && r.getY1() <= y && y <= r.getY1() + r.getHoehe()) {
            foundroom = r;
            return foundroom;
         }


      }
      return foundroom;
   }

   /**
    *
    * @return Objekt vor dem NPC
    */
   public NPC objectinFront() {
      switch (getOrientierung()) {
         case RIGHT:
            return findEntitieonMap(getX() / FIELDSIZE + 1, getY() / FIELDSIZE);
         case LEFT:
            return findEntitieonMap(getX() / FIELDSIZE - 1, getY() / FIELDSIZE);
         case UP:
            return findEntitieonMap(getX() / FIELDSIZE, getY() / FIELDSIZE - 1);
         case DOWN:
            return findEntitieonMap(getX() / FIELDSIZE, getY() / FIELDSIZE + 1);
         default:
            return null;

      }


   }

   /**
    *
    * @param x
    * @param y
    * @return NPC NPC an der Stelle XY
    */
   private NPC findEntitieonMap(int x, int y) {
      if (main.getEntities() != null) {

         for (ListIterator<NPC> it = main.getEntities().listIterator(); it.hasNext();) {
            NPC e = it.next();
            if (e.getX() / e.getFIELDSIZE() == x && e.getY() / e.getFIELDSIZE() == y) {
               return e;
            } else {
            }
         }

      } else {
         return null;
      }
      return null;



   }

   /**
    *
    * @param x
    * @param y
    * @param ents Eingabeliste
    * @return Objekt aus der Eingabeliste an der Stelle XY
    */
   private NPC findEntitieonMap(int x, int y, LinkedList<NPC> ents) {
      if (ents != null) {

         for (ListIterator<NPC> it = ents.listIterator(); it.hasNext();) {
            NPC e = it.next();
            if (e.getX() / e.getFIELDSIZE() == x && e.getY() / e.getFIELDSIZE() == y) {
               return e;
            } else {
            }
         }

      } else {
         return null;
      }
      return null;



   }

   /**
    *
    * @param n Zahl der Felder vor dem NPC
    * @return Koordinaten vor dem NPC
    */
   public int[] fieldinFront(int n) {
      int[] coord = new int[2];
      switch (getOrientierung()) {
         case RIGHT:
            coord[0] = x / FIELDSIZE + n;
            coord[1] = y / FIELDSIZE;
            return coord;
         case LEFT:
            coord[0] = x / FIELDSIZE - n;
            coord[1] = y / FIELDSIZE;
            return coord;
         case UP:
            coord[0] = x / FIELDSIZE;
            coord[1] = y / FIELDSIZE - n;
            return coord;
         case DOWN:
            coord[0] = x / FIELDSIZE;
            coord[1] = y / FIELDSIZE + n;
            return coord;
         default:
            return null;

      }



   }

   /**
    * Prüft ob NPC vor Tür steht
    *
    * @param x
    * @param y
    * @return
    */
   public boolean notinFrontofDoor(int x, int y) {
      if (main.map[y - 1][x] == 'D') {
         return false;

      }

      if (main.map[y + 1][x] == 'D') {
         return false;

      }

      if (main.map[y][x + 1] == 'D') {
         return false;

      }

      if (main.map[y][x - 1] == 'D') {
         return false;

      }

      return true;



   }

   /**
    * Angriffsfunktion
    *
    * @param d Das anzugreifende Ziel
    */
   public void attack(NPC d) {
      NPC a = this;

      int dmg = a.getDmg();
      int def = d.getDefence();
      int schaden = dmg - def;

      if (Spiel.model.UtilFunctions.distance(a, d) > 3) {
         System.out.println("Ausser Reichweite");
      } else {

         if (d.getHp() < 1) {
         } else {
            if (schaden <= 0) {
               System.out.println(d.getName() + " hat den Angriff abgeblockt");
            } else {
               d.setHp(d.getHp() - schaden);
               //System.out.println(a.getName() + " hat dem " + d.getName() + " " + schaden + " Schaden zugefügt");
               main.effects.add(new Effect(d.getX() / FIELDSIZE, d.getY() / FIELDSIZE, main, String.valueOf(schaden), Color.RED, 300));
               d.setHit(true);


            }
         }

      }





   }

   /**
    *
    * @return NPC der vor Objekt steht
    */
   public NPC enemyInFront() {

      for (int i = 0; i < getRoom().getEntities().size(); i++) {
         NPC e = getRoom().getEntities().get(i);
         if (e.getX() / FIELDSIZE == fieldinFront(1)[0] && e.getY() / FIELDSIZE == fieldinFront(1)[1] && (e instanceof Monster || e instanceof Player)) {
            return e;
         }
      }

      if (findEntitieonMap(fieldinFront(1)[0], fieldinFront(1)[1]) instanceof Monster
              || findEntitieonMap(fieldinFront(1)[0], fieldinFront(1)[1]) instanceof Player) {
         return findEntitieonMap(fieldinFront(1)[0], fieldinFront(1)[1]);

      } else if (findEntitieonMap(fieldinFront(1)[0], fieldinFront(1)[1]) instanceof Door) {
         for (NPC n : main.entities) {
            if (n.getX() / FIELDSIZE == fieldinFront(1)[0]
                    && n.getY() / FIELDSIZE == fieldinFront(1)[1] && !(n instanceof Door)) {
               return n;
            }
         }

      }
      return null;
   }

   //
   //
   //
   //
   //
   //Getter und Setter:
   /**
    *
    * @return
    */
   public int getDefence() {
      return defence;
   }

   /**
    *
    * @param defence
    */
   public void setDefence(int defence) {
      this.defence = defence;
   }

   /**
    *
    * @return
    */
   public int getCounter() {
      return animationCounter;
   }

   /**
    *
    * @param counter
    */
   public void setCounter(int counter) {
      this.animationCounter = counter;
   }

   /**
    *
    * @return
    */
   public int getDmg() {
      return dmg;
   }

   /**
    *
    * @param dmg
    */
   public void setDmg(int dmg) {
      this.dmg = dmg;
   }

   /**
    *
    * @return
    */
   public int getHp() {
      return hp;
   }

   /**
    *
    * @param hp
    */
   public void setHp(int hp) {
      this.hp = hp;
      if (hp < 1) {
         death();
      }
   }

   /**
    * Todesfunktion markiert NPC zum löschen vor
    *
    */
   private void death() {
      //Droppen eines Herzens
      if (UtilFunctions.gambler(20)) {
         main.getTempEntities().add(new Heart(x / FIELDSIZE, y / FIELDSIZE, main));
      } else if(UtilFunctions.gambler(70) ) {

         main.getTempEntities().add(new Coin(x / FIELDSIZE, y / FIELDSIZE, main));
      }
      //markieren
      this.removethis = true;
      //Sound
      getMain().notifyObserver(Observer.sounds.enemydead);

      //Aus Raum löschen
      try {
         this.room.getEntities().remove(this);
      } catch (NullPointerException e) {
         e.printStackTrace();
      }


   }

   /**
    *
    * @return
    */
   public char getIcon() {
      return icon;
   }

   /**
    *
    * @param icon
    */
   public void setIcon(char icon) {
      this.icon = icon;
   }

   /**
    *
    * @return
    */
   public MainModel getMain() {
      return main;
   }

   /**
    *
    * @param main
    */
   public void setMain(MainModel main) {
      this.main = main;
   }

   /**
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    *
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    *
    * @return
    */
   public Richtung getOrientierung() {
      return orientierung;
   }

   /**
    *
    * @param orientierung
    */
   public void setOrientierung(Richtung orientierung) {
      this.orientierung = orientierung;

   }

   /**
    *
    * @return
    */
   public Room getRoom() {
      return room;
   }

   /**
    *
    * @param room
    */
   public void setRoom(Room room) {
      this.room = room;
   }

   /**
    *
    * @return
    */
   public int getX() {
      return x;
   }

   /**
    *
    * @param x
    */
   public void setX(int x) {
      this.x = x;
   }

   /**
    *
    * @return
    */
   public int getY() {
      return y;
   }

   /**
    *
    * @param y
    */
   public void setY(int y) {
      this.y = y;
   }

   /**
    *
    * @return
    */
   public int getMovex() {
      return movex;
   }

   /**
    *
    * @param movex
    */
   public void setMovex(int movex) {
      this.movex = movex;
   }

   /**
    *
    * @return
    */
   public int getMovey() {
      return movey;
   }

   /**
    *
    * @param movey
    */
   public void setMovey(int movey) {
      this.movey = movey;
   }

   /**
    *
    * @return
    */
   public long getAnimation() {
      return gametick;
   }

   /**
    *
    * @param animation
    */
   public void setAnimation(long animation) {
      this.gametick = animation;
   }

   /**
    *
    * @return
    */
   public long getDelay() {
      return delay;
   }

   /**
    *
    * @param delay
    */
   public void setDelay(long delay) {
      this.delay = delay;
   }

   /**
    *
    * @return
    */
   public boolean isHit() {
      return hit;
   }

   /**
    *
    * @param hit
    */
   public void setHit(boolean hit) {
      this.hit = hit;
      this.animationCounter = 0;
   }

   /**
    *
    * @return
    */
   public boolean isRemovethis() {
      return removethis;
   }

   /**
    *
    * @param removethis
    */
   public void setRemovethis(boolean removethis) {
      this.removethis = removethis;
   }

   /**
    *
    * @return
    */
   public boolean isWalking() {
      return walking;
   }

   /**
    *
    * @param walking
    */
   public void setWalking(boolean walking) {
      this.walking = walking;
   }

   /**
    *
    * @return
    */
   public int getTargetx() {
      return targetx;
   }

   /**
    *
    * @param targetx
    */
   public void setTargetx(int targetx) {
      this.targetx = targetx;
   }

   /**
    *
    * @return
    */
   public int getTargety() {
      return targety;
   }

   /**
    *
    * @param targety
    */
   public void setTargety(int targety) {
      this.targety = targety;
   }

   /**
    *
    * @return
    */
   public double getWalkspeed() {
      return walkspeed;
   }

   /**
    *
    * @param walkspeed
    */
   public void setWalkspeed(double walkspeed) {
      this.walkspeed = walkspeed;
   }

   /**
    *
    * @return
    */
   public int getFIELDSIZE() {
      return FIELDSIZE;
   }

   /**
    *
    * @return
    */
   public boolean isAttacking() {
      return attacking;
   }

   /**
    *
    * @param attacking
    */
   public void setAttacking(boolean attacking) {
      this.attacking = attacking;
   }
}
