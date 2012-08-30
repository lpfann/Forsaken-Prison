/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;
import java.util.LinkedList;

/**
 *
 * @author Gamer
 */
public class Stairs extends NPC implements Usable{
    private boolean locked=true;

    public Stairs(int x1,int y1,int w, int h,MainModel main,LinkedList<NPC> list){
     super(0, 0, 'S', main);
     this.setstartpositionWithNPCcheck(x1, y1, w, h, list);
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    @Override
    public void use(Player p) {
        if (locked) {
            System.out.println("Der Weg ist verschlossen.");
        } else {
           getMain().changeLevel();

        }
    }

}
