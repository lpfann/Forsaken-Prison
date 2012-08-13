/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.Main;
/**
 *
 * @author lpfannschmidt
 */
public abstract class Monster extends NPC {

    
    private int abstand=0;
    private int counter1=0;
    private int counter2=0;
    
    public Monster(int x, int y, int hp, int dmg, String name, char icon, Main main) {
        super(x, y, icon, main);
        setHp(hp);
        setDmg(dmg);
        setName(name);
        

    }

//    @Override
//    public void drawEntitie(Graphics g, int fieldsize) {
//        if (hit) {
//            setCounter(getCounter()+1);
//            g.drawImage(getImage()[1], getX() * fieldsize, getY() * fieldsize, fieldsize, fieldsize, null);
//            if (getCounter() > 20) {
//                hit = false;
//                setCounter(0);
//            }
//        } else {
//            g.drawImage(getImage()[0], getX() * fieldsize, getY() * fieldsize, fieldsize, fieldsize, null);
//
//        }
//
//
//    }
    
        @Override
    public void doLogic(long delta) {
            super.doLogic(delta);
            
          
            
        counter1++;
        if (Spiel.model.Utilites.inthesameRoom(this, getMain().player)) {
            if (counter1 >=50) {
                Player pl = getMain().player;
                    if (Spiel.model.Utilites.distance(this, pl)==1 && objectinFront() instanceof Player) {
                            attack(objectinFront());
                    } else
                                if (Spiel.model.Utilites.distance(this, pl) < 6) {

                                if (pl.getX() < this.getX()) {
                                        setMovex(-1);
                                } else if (pl.getX() > this.getX()) {
                                        setMovex(+1);
                                } else {
                                }
                                if (pl.getY() < this.getY()) {
                                        setMovey(-1);
                                } else if (pl.getY() > this.getY()) {
                                        setMovey(+1);
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
                    setMovex(1);
                    break;
                case 1:
                    setMovex(-1);
                    break;
                case 2:
                    setMovey(1);
                    break;
                case 3:
                    setMovey(-1);
                    break;
            }
            counter1=0;

        }



    }
        
        

        
}
           
      
}
