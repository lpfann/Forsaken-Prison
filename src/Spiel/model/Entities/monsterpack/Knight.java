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
public class Knight extends Monster{
         public Knight(int x1,int y1,int w, int h,MainModel main){
        super(0, 0, 40, 5, "Ritter", 'O',main);
        this.setstartposition(x1, y1, w, h);
        setXp(20);
        setFilename("knight.png");
    }
     
     
     
}
