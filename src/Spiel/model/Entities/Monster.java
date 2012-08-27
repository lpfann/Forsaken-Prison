/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;
import Spiel.model.Utilites;
/**
 *
 * @author lpfannschmidt
 */
public abstract class Monster extends NPC {


    private double attackdelay=0;
    private double walkdelay=0;
    private double statmultiplier=0.2;
    private int xp;

    public Monster(int x, int y, int hp, int dmg, String name, char icon, MainModel main) {
        super(x, y, icon, main);
        setHp((int)(hp+(hp*main.getCurrentDungeonLevel()*statmultiplier)));
        setDmg((int)(dmg+(dmg*main.getCurrentDungeonLevel()*statmultiplier)));
        setName(name);


    }



        @Override
    public void doLogic(long delta) {
            super.doLogic(delta);
            attackdelay+=getDelay()/1e3;
            walkdelay+=getDelay()/1e3;


           if (Spiel.model.Utilites.inthesameRoom(this, getMain().getPlayer())) {
                 Player pl = getMain().getPlayer();

                 if (attackdelay > 2500 &&  Spiel.model.Utilites.distance(this, pl) == 1 && objectinFront() instanceof Player) {
                    setWalking(false);
                    attack(objectinFront());
                    attackdelay=0;
                 } else {
                 double rand= (double)Utilites.randomizer(70, 100)/100;
                 if (walkdelay*rand>900 && Spiel.model.Utilites.distance(this, pl) < 6) {

                    if (pl.getX() < this.getX()) {
                       setOrientierung(MainModel.Richtung.LEFT);
                       setWalking(true);
                    } else if (pl.getX() > this.getX()) {
                       setOrientierung(MainModel.Richtung.RIGHT);
                       setWalking(true);

                       if (pl.getY() < this.getY()) {
                          setOrientierung(MainModel.Richtung.UP);
                          setWalking(true);
                       } else if (pl.getY() > this.getY()) {
                          setOrientierung(MainModel.Richtung.DOWN);
                          setWalking(true);

                       }
                    }
                    walkdelay=0;
                 }
                 }

//        if (counter1>=100) {
//            int rand2 = Spiel.model.Utilites.randomizer(0, 3);
//            switch (rand2) {
//                case 0:
//                     setOrientierung(MainModel.Richtung.LEFT);
//                     break;
//                case 1:
//                     setOrientierung(MainModel.Richtung.RIGHT);
//                    break;
//                case 2:
//                     setOrientierung(MainModel.Richtung.UP);
//                    break;
//                case 3:
//                     setOrientierung(MainModel.Richtung.DOWN);
//                    break;
//            }
//            counter1=0;
//
//        }


    }




}
        @Override
     public void move() {
            super.move();
            setWalking(false);

     }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

}
