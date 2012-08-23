/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;

/**
 *
 * @author Gamer
 */
public class Stairs extends NPC {
    public Stairs(int x1,int y1,int w, int h,MainModel main){
     super(0, 0, 'S', main);
     this.setstartposition(x1, y1, w, h);
    } 
}
