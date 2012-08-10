/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Entities.Items.Heiltrank;
import Spiel.model.Entities.Items.Schwert;
import Spiel.model.Entities.Items.Trank;
import Spiel.model.Main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.LinkedList;


public class Truhe extends NPC {
private LinkedList items;
private boolean opened;
      
      public Truhe(int x, int y,char icon,Main main) {
            super(x,y,icon,main);
            this.items=generatecontent();
            setFilename("chest.png");
            imageConstructor();
            this.opened=false;
     }
      
//Zufällige Erstellung von Inhalt für die Truhe 
// TODO Zufallsalgorithmus erstellen
      private LinkedList generatecontent() {
            LinkedList items = new LinkedList();
            items.add(new Heiltrank(Trank.Size.GROß));
            items.add(new Schwert());
            
         return items;  
      }

    public LinkedList getItems() {
        return items;
    }

    public void setItems(LinkedList items) {
        this.items = items;
    }

    
        @Override
    public void drawEntitie(Graphics g,int fieldsize) {
       if (opened) {
            g.drawImage(getImage()[1], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null); 
       } else {
            g.drawImage(getImage()[0], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null);    
           
       }
      
   }

    public void setOpened(boolean opened) {
        this.opened = opened;
    }

    public boolean isOpened() {
        return opened;
    }
    
        
}
