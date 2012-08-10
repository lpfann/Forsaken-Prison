/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

import Spiel.model.Entities.Player;

/**
 *
 * @author Gamer
 */
public class Manatrank extends Trank {
    
    
    
    public Manatrank(Size size) {
        super("Manatrank",size);
        
        
        
    }

    @Override
    public void potionAuswirkung(Player p, int i) {
        p.setMana(p.getMana()+i);
    }
    
}
