/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.monsterpack;

import Spiel.model.Entities.Monster;
import Spiel.model.MainModel;
import java.awt.image.BufferedImage;

/**
 *
 * @author Gamer
 */



public class Troll extends Monster{
   
    
    public Troll(int x1,int y1,int w, int h,MainModel main){
        super(0, 0, 15, 2, "Troll", 'T', main);
        this.setstartposition(x1, y1, w, h);
        setXp(5);
        setFilename("troll.png");
        
    }

    
    
}
