/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.monsterpack;

import Spiel.model.Entities.Monster;
import Spiel.model.MainModel;

/**
 *
 * @author Gamer
 */



public class Ork extends Monster{
   public static double spawnrate=0.07;


    public Ork(int x1,int y1,int w, int h,MainModel main){
        super(0, 0, 25, 5, "Ork", 'O',main);
        this.setstartposition(x1, y1, w, h);
        setXp(15);
    }






}
