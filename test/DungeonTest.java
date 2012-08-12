/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Spiel.Controller.Game;
import Spiel.model.Room;
import Spiel.model.DungeonGenerator;
import Spiel.model.Entities.Door;
import java.util.LinkedList;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author Lukas
 */
public class DungeonTest {
    
    public DungeonTest() {
    }

    char [][] map;

        @Test
        public void testdungeon() {
                boolean pass = true;
                int counter = 0;
                Game game = new Game();
                
                game.pauseThread();

                //mehrmaliger Testlauf
                for (int i = 0; i < 1000; i++) {
                        DungeonGenerator dungeon = new DungeonGenerator(100, 100, game.getMain());
                        map =dungeon.getMap();
                        if (doortester(dungeon.getDoorEntities(), map) == false) {
                                pass = false;
                                counter++;
                                System.out.println(counter);
                                for (int j = 0; j < map[0].length; j++) {
                                        for (int k = 0; k < map.length; k++) {
                                                System.out.print(map[j][k]);
                                        }
                                        System.out.println("");
                                }
                        }
                }

                //eigentlicher Test
                assertTrue(pass);



        }

//      private boolean searchtree(Room r) {
//            if (r.getlchild() != null && r.getrchild() != null) {
//                  int x = r.getDoorx();
//                  int y = r.getDoory();
//                  if (x != 0 && y != 0) {
//                        if ((map[y - 1][x] == '*' && map[y + 1][x] == '*') && (map[y][x - 1] == '*' || map[y][x + 1] == '*')) {
//                              map[y][x]='D';
//                              return false;
//                        } else {
//                              if ((map[y][x - 1] == '*' && map[y][x + 1] == '*') && (map[y - 1][x] == '*' || map[y + 1][x] == '*')) {
//                                      map[y][x]='D';
//                                    return false;
//                              } else {
//                                    return true;
//
//                              }
//                        }
//                  } else {
//                        if (searchtree(r.getlchild()) == false || searchtree(r.getrchild()) == false) {
//                              return false;
//
//                        } else {
//                              return true;
//
//                        }
//                  }
//            }
//
//            return true;
//
//      }
      
      //Prüft jede Tür ob sie begehbar ist
      private boolean doortester(LinkedList<Door> list,char[][] map) {
              boolean allestrue=true;
              for (Door d : list) {
                      int x = d.getX();
                      int y = d.getY();
                          if ((map[y - 1][x] == '*' && map[y + 1][x] == '*') && (map[y][x - 1] == '*' || map[y][x + 1] == '*')) {
                              //Von rechts oder links blockiert    
                              allestrue=false;
                      } else {
                              if ((map[y][x - 1] == '*' && map[y][x + 1] == '*') && (map[y - 1][x] == '*' || map[y + 1][x] == '*')) {
                              //Von oben oder unten blockiert
                                      allestrue=false;
                              } else {
                              }
                      }
                      
                      
                      
                      
              }
              if (allestrue) {
                      return true;
              } else {
                      return false;
              }
              
              
              
              
      }

}

