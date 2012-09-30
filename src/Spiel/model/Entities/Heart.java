/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.MainModel;
import java.awt.Color;

/**
 *
 * @author Lukas
 */
public class Heart extends NPC implements Usable{

     public Heart(int x1,int y1,MainModel main){
     super(x1, y1, 'H', main);

    }

    @Override
    public void use(Player p) {
        this.setRemovethis(true);
        //getMain().notifyObserver(Observer.sounds.chestopen );
        p.setHp(p.getHp()+10);
        getMain().effects.add(new Effect(getX()/getFIELDSIZE(), getY()/getFIELDSIZE(), getMain(), "+10HP", Color.GREEN, 1000));
    }

   @Override
   public void doLogic(long delta) {
      if (getMain().getPlayer().getX() == getX() && getMain().getPlayer().getY() == getY() ) {
         use(getMain().getPlayer());
      }




   }

    @Override
    public int getLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
