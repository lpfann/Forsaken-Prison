/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.Items.Armor;
import Spiel.model.Entities.Items.Heiltrank;
import Spiel.model.Entities.Items.Schwert;
import Spiel.model.Entities.Items.Trank;
import Spiel.model.MainModel;
import Spiel.model.Room;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Lukas
 */
public class ChestFactory {
       private LinkedList chestlist;
       private MainModel main;
       private ArrayList items= new ArrayList();
       
      public ChestFactory(MainModel main) {
        this.main = main;
            
            items.add(new Heiltrank(Trank.Size.GROß,null));
            items.add(new Heiltrank(Trank.Size.MITTEL,null));
            items.add(new Heiltrank(Trank.Size.KLEIN,null));
            items.add(new Armor.Lederwams());
            items.add(new Armor.Eisenrüstung());
            items.add(new Armor.Goldrüstung());
            items.add(new Armor.Kettenhemd());
            items.add(new Armor.Lederrüstung());
            items.add(new Armor.BeschlageneLederRüstung());
            items.add(new Schwert.Dolch());
            items.add(new Schwert.Kurzschwert());
            items.add(new Schwert.Flammenschwert());
            items.add(new Schwert.Frostschwert());
            items.add(new Schwert.Grasklinge());
            items.add(new Schwert.Donnerschwert());
            items.add(new Schwert.MagischesSchwert());
            items.add(new Schwert.Zweihänder());
      
      }
      
      
      public LinkedList createchests(int anzahl,int x,int y, int w, int h) {
            LinkedList chests= new LinkedList<>();
            
              for (int i = 0; i < anzahl; i++) {
                
                    Truhe chest= new Truhe(0, 0, 'C',main,items);
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
                if (Spiel.model.Utilites.randomizer(1, 10) < 4) {
                    
                    Truhe chest = new Truhe(0, 0, 'C', main,items);
                    boolean fertig=false;
                   //Verhindert das Truhen direkt vor Türen gesetzt werden und sie so blockieren
                    while(!fertig) {
                    chest.setstartposition(room.getX1() + 2, room.getY1() + 2, room.getBreite() - 3, room.getHoehe() - 3);
                        if (Spiel.model.Utilites.distance(chest, room.getDoorx(),room.getDoory() )>1) {
                                fertig=true;
                        }
                    
                    
                    }
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
