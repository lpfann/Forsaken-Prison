/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model;

import Spiel.Controller.Game;
import Spiel.model.Entities.*;
import java.util.ListIterator;
import java.util.Random;

/**
 *
 * @author Lukas
 */
public class Utilites {
      
        public static int randomizer(int min, int max) {
        Random rand = new Random();
        int  randomNum=0;
        if (max <1) {
            return 0;
        } else {
            try {
              randomNum = rand.nextInt(max - min + 1) + min;
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
        return randomNum;
        }
    }
        
        public static Monster nearestEnemy(Player a,MainModel main) {
        int d = 50;
        Monster dmin=null;
        for (NPC e : main.getEntities()) {
            if (e == a)  {
                
            } else {
                if (e instanceof Monster) {
                    if (distance(a, e) < d) {
                        d = distance(a, e);
                        dmin = (Monster)e;
                    }
                    
                }

            }

        }
        return dmin;
    }
        
        
        
        static Chest nearestChest(Player a,MainModel main) {
        int d = 50;
        Chest dmin=null;
        for (NPC e : main.getEntities()) {
            if (e == a)  {
                
            } else {
                if (e instanceof Chest) {
                    if (distance(a, e) < d) {
                        d = distance(a, e);
                        dmin = (Chest)e;
                    }
                    
                }

            }

        }
        return dmin;
    }
      
        public static int distance(NPC a, NPC b) {
        int x1 = a.getX()/a.getFIELDSIZE();
        int x2 = b.getX()/a.getFIELDSIZE();
        int y1 = a.getY()/a.getFIELDSIZE();
        int y2 = b.getY()/a.getFIELDSIZE();
        return (int)Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
       
       
   } 
        public static int distance(NPC a, int x, int y) {
        int x1 = a.getX()/a.getFIELDSIZE();
        int x2 = x;
        int y1 = a.getY()/a.getFIELDSIZE();
        int y2 = y;
        return (int)Math.sqrt(Math.pow((x2-x1), 2)+Math.pow((y2-y1), 2));
       
       
   } 

    
        public static Boolean inthesameRoom(NPC a, NPC b){
        if (a.getRoom() == b.getRoom()) {
            return true;
        } else {
            return false;
        }
    }
}
