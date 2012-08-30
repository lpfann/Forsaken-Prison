/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.MainModel;
import Spiel.model.UtilFunctions;
import java.awt.Color;

/**
 *
 * @author Lukas
 */
public class Coin extends NPC implements Usable{

     public Coin(int x1,int y1,MainModel main){
     super(x1, y1, 'Z', main);
    }

    @Override
    public void use(Player p) {
        this.setRemovethis(true);
        getMain().notifyObserver(Observer.sounds.chestopen );
        int rand = UtilFunctions.randomizer(1, 10*getMain().getCurrentDungeonLevel());
        p.setCoins(p.getCoins()+rand);
        getMain().effects.add(new Effect(getX()/getFIELDSIZE(), getY()/getFIELDSIZE(), getMain(), "+"+rand+" Münzen", Color.YELLOW, 1000));
    }

   @Override
   public void doLogic(long delta) {
      if (getMain().getPlayer().getX() == getX() && getMain().getPlayer().getY() == getY() ) {
         use(getMain().getPlayer());
      }




   }

}