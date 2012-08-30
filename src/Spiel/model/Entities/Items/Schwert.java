/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

/**
 *
 * @author Lukas
 */
public abstract class Schwert extends Waffe {

       public Schwert(String name,int dmg,int x,int y,double droprate,int itemlvl) {
       super(name,dmg);
       setSubimagex(x);
       setSubimagey(y);
       setDroprate(droprate);
       setAttackspeed(1);
       setItemlvl(itemlvl);
       }

        public static class Dolch extends Schwert {

                public Dolch() {
                        super("Dolch", 1, 7, 7,500,1);
                        setAttackspeed(1.1);
                }
        }
        public static class Kurzschwert extends Schwert {

                public Kurzschwert() {
                        super("Kurzschwert", 2, 0, 7,350,1);
                }
        }
        public static class Flammenschwert extends Schwert {

                public Flammenschwert() {
                        super("Flammenschwert", 3, 1, 7,110,2);
                }
        }
        public static class Frostschwert extends Schwert {

                public Frostschwert() {
                        super("Frostschwert", 4, 2, 7,110,3);
                }
        }
        public static class Grasklinge extends Schwert {

                public Grasklinge() {
                        super("Grasklinge", 5, 3, 7,100,4);
                }
        }
        public static class Donnerschwert extends Schwert {

                public Donnerschwert() {
                        super("Donnerschwert", 6, 4, 7,60,5);
                }
        }
        public static class MagischesSchwert extends Schwert {

                public MagischesSchwert() {
                        super("Magisches Schwert", 7, 5, 7,50,6);
                }
        }
        public static class Zweihänder extends Schwert {

                public Zweihänder() {
                        super("Zweihänder", 8, 6, 7,10,7);
                }
        }

}
