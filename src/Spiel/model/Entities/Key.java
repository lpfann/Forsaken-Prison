/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.MainModel;

/**
 *
 * @author Lukas
 */
public class Key extends NPC implements Usable{
    public Key(int x1,int y1,int w, int h,MainModel main){
     super(0, 0, 'S', main);
     this.setstartposition(x1, y1, w, h);
    }

    @Override
    public void use(Player p) {
        this.setRemovethis(true);
        getMain().getDungeon().getStairs().setLocked(false);
        System.out.println("Du hast den Schlüssel zur Treppe gefunden!");
        getMain().notifyObserver(Observer.sounds.chestopen );
    }

    @Override
    public int getLevel() {
        return 0;
    }
    
}
