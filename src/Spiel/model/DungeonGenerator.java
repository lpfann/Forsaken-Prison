package Spiel.model;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.model.Entities.Door;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author Gamer
 */
public class DungeonGenerator implements Serializable{

    private static char[][] map;
    private LinkedList rooms;
    static LinkedList<Room> tree = new LinkedList<>();
    static LinkedList<Room> doors = new LinkedList<>();
    private LinkedList<Door> doorObjects = new LinkedList<>();
    final static char WAND = '*';
    final static char TÜR = ' ';
    public static Room level;
    private Main main;

    public DungeonGenerator(int w, int h, Main main) {
        this.main=main;
        map = generate(w, h);
        rooms = tree;
        
    }

    public char[][] generate(int width, int heigth) {
        tree.clear();
        map = new char[heigth][width];
        level = new Room(0, 0, width - 1, heigth - 1);

        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (y == 0 || x == map[0].length - 1 || x == 0 || y == map.length - 1) {
                    map[y][x] = ' ';
                } else {
                    map[y][x] = ' ';
                }
            }
        }
        for (Room r : tree) {

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

    //Erstellung der Türen
    private char[][] generateDoors(char[][] map, Room level) {
        boolean fertig;
        traversedoors(level);

        for (Room r : doors) {
            int x1 = r.getDoorx();
            int y1 = r.getDoory();




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
                        doorObjects.add(new Door(x1, y1, this.main));
                                    fertig = true;
                    }

                    //Horizontale Wand
                } else {
                    if (map[y1 - 1][x1] == WAND || map[y1 + 1][x1] == WAND) {
                        //Linke Grenze erreicht
                        if (x1 > r.getX1()) {
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
                        doorObjects.add(new Door(x1, y1, this.main));
                        fertig = true;
                    }
                }
            } while (fertig == false);

        }

        doors.clear();
        return map;
    }

    //Hilfsfunktion für Türgenerierung
    private static void traversedoors(Room tree) {
        if (tree.getlchild() != null && tree.getrchild() != null) {
            doors.add(tree.getlchild());
            //doors.add(tree.getrchild());
            traversedoors(tree.getlchild());
            traversedoors(tree.getrchild());

        } else {
        }
    }

    public char[][] getMap() {
        return map;
    }

    public LinkedList getRooms() {
        return rooms;
    }
    public LinkedList getDoors() {
        return doorObjects;
    }
}
