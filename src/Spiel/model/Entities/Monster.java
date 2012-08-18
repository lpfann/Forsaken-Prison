/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;
/**
 *
 * @author lpfannschmidt
 */
public abstract class Monster extends NPC {

    
    private int abstand=0;
    private int counter1=0;
    private int counter2=0;
    
    public Monster(int x, int y, int hp, int dmg, String name, char icon, MainModel main) {
        super(x, y, icon, main);
        setHp(hp);
        setDmg(dmg);
        setName(name);
        

    }


    
        @Override
    public void doLogic(long delta) {
            super.doLogic(delta);
            
          
            
        counter1++;
        if (Spiel.model.Utilites.inthesameRoom(this, getMain().player)) {
            if (counter1 >=50) {
                Player pl = getMain().player;
                    if (Spiel.model.Utilites.distance(this, pl)==1 && objectinFront() instanceof Player) {
                            setWalking(false);
                            attack(objectinFront());
                    } else
                                if (Spiel.model.Utilites.distance(this, pl) < 6) {

                                if (pl.getX() < this.getX()) {
                                        setWalking(true);
                                        setOrientierung(MainModel.Richtung.LEFT);
                                } else if (pl.getX() > this.getX()) {
                                        setWalking(true);
                                        setOrientierung(MainModel.Richtung.RIGHT);
                                } else {
                                }
                                if (pl.getY() < this.getY()) {
                                        setWalking(true);
                                        setOrientierung(MainModel.Richtung.UP);
                                } else if (pl.getY() > this.getY()) {
                                        setWalking(true);
                                        setOrientierung(MainModel.Richtung.DOWN);
                                } else {
                                }

                                }
                                counter1=0;
                }

        } else {
        
        if (counter1>=100) {
            int rand2 = Spiel.model.Utilites.randomizer(0, 3);
            switch (rand2) {
                case 0:
                     setOrientierung(MainModel.Richtung.LEFT);
                     break;
                case 1:
                     setOrientierung(MainModel.Richtung.RIGHT);
                    break;
                case 2:
                     setOrientierung(MainModel.Richtung.UP);
                    break;
                case 3:
                     setOrientierung(MainModel.Richtung.DOWN);
                    break;
            }
            counter1=0;

        }



    }
        
        

        
}
           
      
}
