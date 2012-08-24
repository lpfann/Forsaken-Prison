/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.Items.Heiltrank;
import Spiel.model.Entities.Items.Item;
import Spiel.model.Entities.Items.Trank;
import Spiel.model.MainModel;
import Spiel.model.Utilites;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Chest extends NPC implements Usable {
private LinkedList items;
private boolean opened;
private ArrayList<Item> allitems;
private MainModel main;

      public Chest(int x, int y,char icon,MainModel main,List<Item> allitems) {
            super(x,y,icon,main);
            setFilename("chest.png");
            this.allitems=(ArrayList<Item>) allitems;
            //Nach Seltenheit sortieren
            Collections.sort(allitems);
            this.main=main;
            this.items=generatecontent();
            this.opened=false;
            

            
            
     }
      
//Zufällige Erstellung von Inhalt für die Chest

      private LinkedList generatecontent() {
            int maxitems=0;
            LinkedList loot = new LinkedList();
            //Maximale Anzahl von Items in einer Kiste
            int randmax = Utilites.randomizer(1, 100);
            if (randmax <80) {
                maxitems=1;
           } else if (randmax >=80 && randmax < 98) {
                maxitems=2;
                
           } else if (randmax >=98 && randmax <=100) {
                maxitems=3;
                
           }
            
            //Würfeln vom seltensten bis zum häufigsten Item bis maximale Anzahl erreicht ist
           while (loot.size() < maxitems) {
                for (int i = 0; i < allitems.size(); i++) {

                     Item item = allitems.get(i);
                     if (item.getItemlvl()<=main.getCurrentDungeonLevel()) {
                         double d = item.getDroprate();
                         int rand = Utilites.randomizer(1, 100000);
                         if (rand < d) {
                              loot.add(item);
                              break;

                         }
                        
                    }

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

    @Override
    public void use(Player p) {

            if (isOpened()) {

            } else {
            setOpened(true);
            LinkedList<Item> inhalt = getItems();
                    for (Item item : inhalt) {
                            item.setPlayer(p);
                            if (item instanceof Heiltrank) {
                                 switch (((Trank)item).getSize()){
                                         case KLEIN:
                                                 p.setSmallpotions(p.getSmallpotions()+1);
                                                 break;
                                         case MITTEL:
                                                 p.setMediumpotions(p.getMediumpotions()+1);
                                                 break;
                                         case GROß:
                                                 p.setBigpotions(p.getBigpotions()+1);
                                                 break;
                                 }
                            } else {
                               p.getInventar().add(item);

                            }
                             System.out.println("Du hast: " + item.getName() + " gefunden");

                    }
            setItems(null);
            }

    }
    
        
}
