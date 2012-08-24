/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;

/**
 *
 * @author Gamer
 */
public class Stairs extends NPC implements Usable{
    private boolean locked=true;

    public Stairs(int x1,int y1,int w, int h,MainModel main){
     super(0, 0, 'S', main);
     this.setstartposition(x1, y1, w, h);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void use(Player p) {
        getMain().changeLevel();
    }
    
}
