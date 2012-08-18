/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Gamer
 */
public class Door extends NPC {
    
    private boolean open;
    public Door(int x, int y,MainModel main){
        super(x, y, 'D', main);
        this.open=false;
        setFilename("door.png");
      
    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    public void opencloseDoorSwitch(){
        if (open) {
            this.open=false;
            setIcon('D');
        } else {
            this.open=true;
            setIcon(' ');
        }
    }
         @Override
     public void move() {
              
         }

}
