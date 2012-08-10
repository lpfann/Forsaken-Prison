/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Main;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Gamer
 */
public class Door extends NPC {
    
    private boolean open;
    public Door(int x, int y,Main main){
        super(x, y, 'D', main);
        this.open=false;
        setFilename("door.png");
        imageConstructor();
      
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
    public void drawEntitie(Graphics g,int fieldsize) {
        if (open) {
        g.drawImage(getImage()[1], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null);    
        } else {
        g.drawImage(getImage()[0], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null);
    }
    }
}
