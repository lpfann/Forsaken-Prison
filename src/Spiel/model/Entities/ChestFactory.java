/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;
import Spiel.model.Room;
import java.util.LinkedList;

/**
 *
 * @author Lukas
 */
public class ChestFactory {
       private LinkedList chestlist;
       private MainModel main;
       
      public ChestFactory(MainModel main) {
        this.main = main;
           
      
      }
      
      
      public LinkedList createchests(int anzahl,int x,int y, int w, int h) {
            LinkedList chests= new LinkedList<>();
            
              for (int i = 0; i < anzahl; i++) {
                
                    Truhe chest= new Truhe(0, 0, 'C',main);
                    chest.setstartposition(x, y, w, h);
                    
                    chests.add(chest);
                      
                  }
                    
                    
              
              return chests;
      }
      
    public LinkedList populateDungeon(LinkedList<Room> rooms) {
        LinkedList chests = new LinkedList<>();

        for (Room room : rooms) {
            int size = room.getBreite() * room.getHoehe();
            int anzahl = (int) (size * 0.03);

            for (int i = 0; i < anzahl; i++) {
                //  Chance das Truhe gespawnt wird.
                if (Spiel.model.Utilites.randomizer(1, 10) < 6) {
                    
                    Truhe chest = new Truhe(0, 0, 'C', main);
                    chest.setstartposition(room.getX1() + 2, room.getY1() + 2, room.getBreite() - 3, room.getHoehe() - 3);

                    chests.add(chest);
                }
            }
        }
        return chests;
    }

      public LinkedList getChestlist() {
            return chestlist;
      }

      public void setChestlist(LinkedList chestlist) {
            this.chestlist = chestlist;
      }     
}
