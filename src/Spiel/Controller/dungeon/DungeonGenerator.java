package Spiel.model;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.model.Entities.ChestFactory;
import Spiel.model.Entities.Door;
import Spiel.model.Entities.Key;
import Spiel.model.Entities.MonsterFactory;
import Spiel.model.Entities.Stairs;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * Klasse zur Dungeon Generierung.
 * @author Lukas P.
 */
public class DungeonGenerator implements Serializable {

   private char[][] map;
   private LinkedList rooms;
   private LinkedList<Room> dungeonTree = new LinkedList<>();
   private LinkedList<Room> roomswithDoors = new LinkedList<>();
   private LinkedList<Door> doorEntities = new LinkedList<>();
   final static char WAND = '*';
   final static char TÜR = 'D';
   private Room level;
   private MainModel main;
   private LinkedList entitiesinLevel = new LinkedList<>();
   private Stairs stairs;
   private Key key;
   private int width;
   private int height;

   /**
    *
    * @param w Breite des Dungeons
    * @param h Höhe des Dungeons
    * @param main Main-Model
    */
   public DungeonGenerator(int w, int h, MainModel main) {
      this.main = main;
      this.width=w;
      this.height=h;

      map = generate();
      rooms = dungeonTree;

   }

   /**
    * Alle erstellten Objekte in Liste einfügen
    */
   public void addEntities() {
      entitiesinLevel.addAll(doorEntities);
      generateStairsAndKey();
      entitiesinLevel.add(stairs);
      entitiesinLevel.add(key);
      entitiesinLevel.addAll(new MonsterFactory(main).populateDungeon(rooms));
      entitiesinLevel.addAll(new ChestFactory(main).populateDungeon(rooms));



   }
   /**
    * Treppe und Schlüssel erstellen
    */
   private void generateStairsAndKey() {



      int rand1 = UtilFunctions.randomizer(0, rooms.size() - 1);
      Room stairRoom = (Room) rooms.get(rand1);
      stairs = new Stairs(stairRoom.getX1() + 1, stairRoom.getY1() + 1, stairRoom.getBreite() - 2, stairRoom.getHoehe() - 2, main, entitiesinLevel);

      int rand2 = UtilFunctions.randomizer(0, rooms.size() - 1);
      while (rand2 == rand1) {
         rand2 = UtilFunctions.randomizer(0, rooms.size() - 1);
      }
      Room keyRoom = (Room) rooms.get(rand2);
      key = new Key(keyRoom.getX1(), keyRoom.getY1(), keyRoom.getBreite(), keyRoom.getHoehe(), main);
   }

   /**
    * Generiert den Dungeon mit allen Räumen inklusive Türen
    * @return fertig generierten Dungeon als Character-Map
    */
   private char[][] generate() {
      map = new char[height][width];
      level = new Room(0, 0, width - 1, height - 1, this);

      for (int y = 0; y < map.length; y++) {
         for (int x = 0; x < map[0].length; x++) {
            if (y == 0 || x == map[0].length - 1 || x == 0 || y == map.length - 1) {
               map[y][x] = ' ';
            } else {
               map[y][x] = ' ';
            }
         }
      }
      for (Room r : dungeonTree) {

         for (int i = r.getX1(); i <= (r.getX1() + r.getBreite()); i++) {
            for (int j = r.getY1(); j <= (r.getY1() + r.getHoehe()); j++) {
               if (i == r.getX1() || i == r.getX1() + r.getBreite() || j == r.getY1() || j == r.getY1() + r.getHoehe()) {
                  map[j][i] = WAND;



               }

            }
         }

      }

      generateDoors(map, level);
      return map;
   }


   /**
    * Generiert Türen
    * @return Dungeon mit Türen als Character-Map
    */
   private char[][] generateDoors(char[][] map, Room level) {
      boolean fertig;
      traversedoors(level);

      for (Room r : roomswithDoors) {
         int x1 = r.getDoorx();
         int y1 = r.getDoory();



         Door door;
         boolean leftway = true;
         fertig = false;

         //Rekursiver durchlauf um passenden Platz für Tür auf Raumseite zu finden
         do {
            if (y1 < 1 || x1 < 1 || !(y1 < map.length - 1) || !(x1 < map[0].length - 1)) {
               break;
            }

            //Vertikale Wand
            if (map[y1 - 1][x1] == WAND && map[y1 + 1][x1] == WAND) {
               if (map[y1][x1 - 1] == WAND || map[y1][x1 + 1] == WAND) {

                  //Obere Grenze erreicht
                  if (y1 > r.getY1()) {
                  } else {
                     leftway = false;
                  }
                  if (leftway) {
                     y1--;
                  } else {
                     y1++;
                  }

                  r.setDoory(y1);
               } else {
                  map[y1][x1] = TÜR;
                  door = new Door(x1, y1, this.main);
                  doorEntities.add(door);
                  door.setRoom(r);
                  fertig = true;
               }

               //Horizontale Wand
            } else {
               if (map[y1 - 1][x1] == WAND || map[y1 + 1][x1] == WAND) {
                  //Linke Grenze erreicht
                  if (x1 > r.getX1() + 1) {
                  } else {
                     leftway = false;
                  }

                  if (leftway) {
                     x1--;
                  } else {
                     x1++;
                  }
                  r.setDoorx(x1);
               } else {
                  map[y1][x1] = TÜR;
                  door = new Door(x1, y1, this.main);
                  doorEntities.add(door);
                  door.setRoom(r);
                  fertig = true;

               }
            }

         } while (fertig == false);

      }

      roomswithDoors.clear();
      return map;
   }

   //Hilfsfunktion für Türgenerierung
   private void traversedoors(Room tree) {
      if (tree.getlchild() != null && tree.getrchild() != null) {
         roomswithDoors.add(tree.getlchild());
         traversedoors(tree.getlchild());
         traversedoors(tree.getrchild());

      } else {
      }
   }

   /**
    *
    * @return Dungeon-Map
    */
   public char[][] getMap() {
      return map;
   }

   /**
    *
    * @return alle generierten Räume
    */
   public LinkedList getRooms() {
      return rooms;
   }

   /**
    *
    * @return alle Türen
    */
   public LinkedList getDoors() {
      return doorEntities;
   }




   /**
    *
    * @return Alle Räume als Baumstruktur
    */
   public LinkedList<Room> getTree() {
      return dungeonTree;
   }










   /**
    *
    * @return Alle generierten Spielobjekte
    */
   public LinkedList getEntitiesinLevel() {
      return entitiesinLevel;
   }

   /**
    *
    * @return Die Treppe ins nächste Level
    */
   public Stairs getStairs() {
      return stairs;
   }
}
