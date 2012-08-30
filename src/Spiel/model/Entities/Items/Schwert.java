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

       public Schwert(String name,int dmg,int x,int y,double droprate) {
       super(name,dmg);
       setSubimagex(x);
       setSubimagey(y);
       setDroprate(droprate);
       setAttackspeed(1);

       }

        public static class Dolch extends Schwert {

                public Dolch() {
                        super("Dolch", 1, 7, 7,500);
                        setAttackspeed(1.1);
                }
        }
        public static class Kurzschwert extends Schwert {

                public Kurzschwert() {
                        super("Kurzschwert", 2, 0, 7,350);
                }
        }
        public static class Flammenschwert extends Schwert {

                public Flammenschwert() {
                        super("Flammenschwert", 3, 1, 7,110);
                }
        }
        public static class Frostschwert extends Schwert {

                public Frostschwert() {
                        super("Frostschwert", 4, 2, 7,110);
                }
        }
        public static class Grasklinge extends Schwert {

                public Grasklinge() {
                        super("Grasklinge", 4, 3, 7,100);
                }
        }
        public static class Donnerschwert extends Schwert {

                public Donnerschwert() {
                        super("Donnerschwert", 5, 4, 7,60);
                }
        }
        public static class MagischesSchwert extends Schwert {

                public MagischesSchwert() {
                        super("Magisches Schwert", 5, 5, 7,50);
                }
        }
        public static class Zweihänder extends Schwert {

                public Zweihänder() {
                        super("Zweihänder", 8, 6, 7,10);
                        setAttackspeed(0.9);
                }
        }

}
