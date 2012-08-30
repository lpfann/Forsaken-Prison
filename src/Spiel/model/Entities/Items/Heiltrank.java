/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

import Spiel.model.Entities.Effect;
import Spiel.model.Entities.Player;
import java.awt.Color;



/**
 *
 * @author Lukas
 */
public class Heiltrank extends Trank {


      public Heiltrank(Size s,Player p) {
            super("Heiltrank",s);
            setPlayer(p);
      }



    @Override
    public void potionAuswirkung(Player p, int i) {
        p.setHp(p.getHp()+i);
         System.out.println("Trank getrunken. +"+i+" HP");
         getPlayer().getMain().effects.add(new Effect(p.getX()/p.getFIELDSIZE(), p.getY()/p.getFIELDSIZE(), p.getMain(), "+"+i+"HP", Color.GREEN, 1000));
    }




}
