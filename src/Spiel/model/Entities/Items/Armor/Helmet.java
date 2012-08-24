/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items.Armor;

/**
 *
 * @author Lukas
 */
public abstract class Helmet extends Armor {
    public Helmet(String name,int def,double droprate,int lvl){
        super(name,def,droprate);
        setType(Armortype.Helmet);
        setItemlvl(lvl);
        setSubimagey(lvl);
        setSubimagex(getType().getValue());
    }

      public static class Kappe extends Helmet {
           public Kappe(){
                   super("Kappe", 1,500,1);

           }
      }
      public static class LederHelm extends Helmet {
           public LederHelm(){
                   super("Lederhelm", 2,350,2);

           }
      }
      public static class Eisenhelm extends Helmet {
           public Eisenhelm(){
                   super("Eisenhelm", 3,150,3);

           }
      }
      public static class Kriegshelm extends Helmet {
           public Kriegshelm(){
                   super("Kriegshelm", 4,50,4);

           }
      }
      public static class Ritterhelm extends Helmet {
           public Ritterhelm(){
                   super("Ritterhelm", 5,10,5);

           }
      }
}
