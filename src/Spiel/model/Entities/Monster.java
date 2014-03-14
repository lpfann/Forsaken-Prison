/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.MainModel;
import Spiel.model.UtilFunctions;

/**
 *
 * @author lpfannschmidt
 */
public abstract class Monster extends NPC implements Attackble {

   private double attackdelay = 0;
   private double walkdelay = 0;
   private double statmultiplier = 0.2;
   private int xp;
   private int monsterlvl;
   private AStar pathfinder;

   /**
    *
    * @param x
    * @param y
    * @param hp
    * @param dmg
    * @param name
    * @param icon
    * @param main
    */
   public Monster(int x, int y, int hp, int dmg, String name, char icon, MainModel main) {
      super(x, y, icon, main);
      //Monster werden stärker desto Tiefer im Dungeon sie erstellt werden
      monsterlvl = main.getCurrentDungeonLevel();
      setHp((int) (hp + (hp * monsterlvl * statmultiplier)));
      setDmg((int) (dmg + (dmg * monsterlvl * statmultiplier)));
      setName(name);
      pathfinder = new AStar(main);

   }

   @Override
   public void doLogic(long delta) {
      super.doLogic(delta);
      attackdelay += delta / 1e6;
      walkdelay += delta / 1e6;


      Player pl = getMain().getPlayer();

      if (attackdelay > 2200 && Spiel.model.UtilFunctions.distance(this, pl) == 1 && enemyInFront() instanceof Player) {
         setWalking(false);
         setAttacking(true);
         attack(enemyInFront());
         attackdelay = 0;
      } else {
         double rand = (double) UtilFunctions.randomizer(85, 100) / 100;
         if (walkdelay * rand > 700 && Spiel.model.UtilFunctions.distance(this, pl) < 6) {

//            int random = UtilFunctions.randomizer(1, 2);
//            if (pl.getX() < this.getX() && random == 1) {
//               setOrientierung(MainModel.Richtung.LEFT);
//               setWalking(true);
//            } else if (pl.getX() > this.getX()) {
//               setOrientierung(MainModel.Richtung.RIGHT);
//               setWalking(true);
//            }
//            if (pl.getY() < this.getY() && random == 2) {
//               setOrientierung(MainModel.Richtung.UP);
//               setWalking(true);
//            } else if (pl.getY() > this.getY()) {
//               setOrientierung(MainModel.Richtung.DOWN);
//               setWalking(true);
//
//            }
           int[] next = pathfinder.getNextWaypoint(this, pl);
             if (next != null) {
                 int random = UtilFunctions.randomizer(1, 2);
                 if (random == 1) {
                     if (next[0] < this.getX()) {
                         setOrientierung(MainModel.Richtung.LEFT);
                         setWalking(true);
                     } else if (next[0] > this.getX()) {
                         setOrientierung(MainModel.Richtung.RIGHT);
                         setWalking(true);
                     }
                 }  else {

                     if (next[1] < this.getY()) {
                         setOrientierung(MainModel.Richtung.UP);
                         setWalking(true);
                     } else if (next[1] > this.getY()) {
                         setOrientierung(MainModel.Richtung.DOWN);
                         setWalking(true);
                     }
                 }
             }
            walkdelay = 0;
         }





      }




   }

   @Override
   public void move() {
      super.move();
      setWalking(false);

   }

   /**
    *
    * @return
    */
   public int getXp() {
      return xp;
   }

   /**
    *
    * @param xp
    */
   public void setXp(int xp) {
      this.xp = xp;
   }

   @Override
   public void setHit(boolean t) {
      super.setHit(t);
      getMain().notifyObserver(Observer.sounds.enemyhit);
   }

   /**
    *
    * @return
    */
   public int getMonsterlvl() {
      return monsterlvl;
   }

   /**
    *
    * @param monsterlvl
    */
   public void setMonsterlvl(int monsterlvl) {
      this.monsterlvl = monsterlvl;
   }

    @Override
    public int getLevel() {
        return getMonsterlvl();
    }
   



}
