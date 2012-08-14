/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.Items.Armor.BeschlageneLederRüstung;
import Spiel.model.Entities.Items.Armor.Eisenrüstung;
import Spiel.model.Entities.Items.Armor.Goldrüstung;
import Spiel.model.Entities.Items.Armor.Kettenhemd;
import Spiel.model.Entities.Items.Armor.Lederwams;
import Spiel.model.Entities.Items.Heiltrank;
import Spiel.model.Entities.Items.Trank;
import Spiel.model.MainModel;
import java.util.LinkedList;


public class Truhe extends NPC {
private LinkedList items;
private boolean opened;
      
      public Truhe(int x, int y,char icon,MainModel main) {
            super(x,y,icon,main);
            this.items=generatecontent();
            setFilename("chest.png");
            this.opened=false;
     }
      
//Zufällige Erstellung von Inhalt für die Truhe 
// TODO Zufallsalgorithmus erstellen
      private LinkedList generatecontent() {
            LinkedList items = new LinkedList();
            items.add(new Heiltrank(Trank.Size.GROß));
            items.add(new Lederwams());
            items.add(new Eisenrüstung());
            items.add(new Goldrüstung());
            items.add(new Kettenhemd());
            items.add(new BeschlageneLederRüstung());
            
         return items;  
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
