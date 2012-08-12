/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import Spiel.Controller.Game;
import Spiel.model.Room;
import Spiel.model.DungeonGenerator;
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
        boolean pass=true;
        int counter=0;
        Game game = new Game();
        game.pauseThread();
        
        //mehrmaliger Testlauf
        for (int i = 0; i < 10000; i++) {
            //TODO: Dungeon test läuft seit umstellung einer statischen sache nicht mehr
        map =game.getMain().getDungeon().generate(100, 100);
            if (searchtree(DungeonGenerator.level)==false) {
                pass=false;
                counter++;
            System.out.println(counter);
            }
        }
        
        //eigentlicher Test
        assertTrue(pass);           
        

        
    }

      //Baumdurchlauf, Prüfung auf begehbare Türen -> wenn true Dungeon komplett begehbar
      private boolean searchtree(Room r) {
            if (r.getlchild() != null && r.getrchild() != null) {
                  int x = r.getDoorx();
                  int y = r.getDoory();
                  if (x != 0 && y != 0) {
                        if ((map[y - 1][x] == '*' && map[y + 1][x] == '*') && (map[y][x - 1] == '*' || map[y][x + 1] == '*')) {
                              return false;
                        } else {
                              if ((map[y][x - 1] == '*' && map[y][x + 1] == '*') && (map[y - 1][x] == '*' || map[y + 1][x] == '*')) {
                                    return false;
                              } else {
                                    return true;

                              }
                        }
                  } else {
                        if (searchtree(r.getlchild()) == false || searchtree(r.getrchild()) == false) {
                              return false;

                        } else {
                              return true;

                        }
                  }
            }

            return true;

      }
}
