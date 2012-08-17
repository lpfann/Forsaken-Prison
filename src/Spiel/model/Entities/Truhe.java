/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.Items.Item;
import Spiel.model.MainModel;
import Spiel.model.Utilites;
import java.util.ArrayList;
import java.util.Collections;
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
            //Nach Seltenheit sortieren
            Collections.sort(allitems);
            
            this.items=generatecontent();
            this.opened=false;
            

            
            
     }
      
//Zufällige Erstellung von Inhalt für die Truhe 

      private LinkedList generatecontent() {
            LinkedList loot = new LinkedList();
            //Maximale Anzahl von Items in einer Kiste
            int maxitems= Utilites.randomizer(1,3);
            
            //Würfeln vom seltensten bis zum häufigsten Item bis maximale Anzahl erreicht ist
           //while (loot.size() < maxitems) {
                for (int i = 0; i < allitems.size(); i++) {

                     Item item = allitems.get(i);
                     double d = item.getDroprate();
                     int rand = Utilites.randomizer(1, 2000);
                     if (rand < d) {
                          loot.add(item);

                     }


                //}
            
                
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
