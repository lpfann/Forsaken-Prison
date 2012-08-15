/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.Items.Armor.Eisenrüstung;
import Spiel.model.Entities.Items.Armor.Goldrüstung;
import Spiel.model.Entities.Items.Armor.Kettenhemd;
import Spiel.model.Entities.Items.Armor.Lederwams;
import Spiel.model.Entities.Items.Heiltrank;
import Spiel.model.Entities.Items.Item;
import Spiel.model.Entities.Items.Schwert.Dolch;
import Spiel.model.Entities.Items.Schwert.Donnerschwert;
import Spiel.model.Entities.Items.Schwert.Flammenschwert;
import Spiel.model.Entities.Items.Schwert.Frostschwert;
import Spiel.model.Entities.Items.Schwert.Grasklinge;
import Spiel.model.Entities.Items.Schwert.Kurzschwert;
import Spiel.model.Entities.Items.Schwert.MagischesSchwert;
import Spiel.model.Entities.Items.Schwert.Zweihänder;
import Spiel.model.Entities.Items.Trank;
import Spiel.model.MainModel;
import Spiel.model.Utilites;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Truhe extends NPC {
private LinkedList items;
private boolean opened;
private ArrayList<Item> allitems;      

      public Truhe(int x, int y,char icon,MainModel main,List<Item> allitems) {
            super(x,y,icon,main);
            setFilename("chest.png");
            this.allitems=(ArrayList<Item>) allitems;
            this.items=generatecontent();
            this.opened=false;
            

            
            
     }
      
//Zufällige Erstellung von Inhalt für die Truhe 

      private LinkedList generatecontent() {
            LinkedList loot = new LinkedList();
            int maxitems= Utilites.randomizer(1,10);
            for (int i = 0; i < allitems.size(); i++) {
                //int randomindex= Utilites.randomizer(1,allitems.size())-1;
                Item item = allitems.get(i);
                double d =item.getDroprate();
                int rand = Utilites.randomizer(1, 1000);
                 if (rand < d) {
                      loot.add(item);
                 
            
            }
            
                
           }

            
         return loot;  
      }

    public LinkedList getItems() {
        return items;
    }

    public void setItems(LinkedList items) {
        this.items = items;
    }

    

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }
    
        
}
