/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.monsterpack;

import Spiel.model.Entities.Monster;
import Spiel.model.MainModel;

/**
 *
 * @author Lukas
 */
public class EvilMonk extends Monster {
   public static double spawnrate=0.01;


   public EvilMonk(int x1, int y1, int w, int h, MainModel main) {
      super(0, 0, 40, 6, "Besessener Mönch", 'M', main);
      this.setstartposition(x1, y1, w, h);
      setXp(30);
      setMonsterlvl(4);
   }
}

