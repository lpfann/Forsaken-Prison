/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model;

import Spiel.model.Entities.Monster;
import Spiel.model.Entities.NPC;

/**
 *
 * @author Gamer
 */
public class Kampf {

    
   public static void attack(NPC a,NPC d) {
       int dmg = a.getDmg();
       int def = d.getArmor();
       int schaden = dmg - def;
       
            if (Spiel.model.Utilites.distance(a, d) > 3) {
                System.out.println("Kein Monster in der Nähe");
            } else {


                if (schaden < 0) {
                    System.out.println("Kein Schaden");
                } else {
                    d.setHp(d.getHp() - schaden);
                    System.out.println("Er hat dem Gegner " + schaden + " Schaden zugefügt");
                    ((Monster)d).setHit(true);
                }
            }
           
       
       
       
       
       
       
   } 
    
    

}
