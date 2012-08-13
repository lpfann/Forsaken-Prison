/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.monsterpack;

import Spiel.model.Entities.Monster;
import Spiel.model.MainModel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Gamer
 */



public class Ork extends Monster{
   
    
    public Ork(int x1,int y1,int w, int h,MainModel main){
        super(0, 0, 20, 1, "Ork", 'O',main);
        this.setstartposition(x1, y1, w, h);
        setFilename("ork.png");
    }

   

               
           
    
}
