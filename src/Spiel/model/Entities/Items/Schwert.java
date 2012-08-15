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

       }

        public static class Dolch extends Schwert {

                public Dolch() {
                        super("Dolch", 1, 7, 7,340);
                }
        }
        public static class Kurzschwert extends Schwert {

                public Kurzschwert() {
                        super("Kurzschwert", 2, 0, 7,280);
                }
        }
        public static class Flammenschwert extends Schwert {

                public Flammenschwert() {
                        super("Flammenschwert", 3, 1, 7,240);
                }
        }
        public static class Frostschwert extends Schwert {

                public Frostschwert() {
                        super("Frostschwert", 4, 2, 7,200);
                }
        }
        public static class Grasklinge extends Schwert {

                public Grasklinge() {
                        super("Grasklinge", 4, 3, 7,175);
                }
        }
        public static class Donnerschwert extends Schwert {

                public Donnerschwert() {
                        super("Donnerschwert", 5, 4, 7,125);
                }
        }
        public static class MagischesSchwert extends Schwert {

                public MagischesSchwert() {
                        super("Magisches Schwert", 5, 5, 7,100);
                }
        }
        public static class Zweihänder extends Schwert {

                public Zweihänder() {
                        super("Zweihänder", 8, 6, 7,10);
                }
        }

}
