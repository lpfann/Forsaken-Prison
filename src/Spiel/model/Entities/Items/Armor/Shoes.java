/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items.Armor;

/**
 *
 * @author Lukas
 */
public abstract class Shoes extends Armor {
   private final int SHOEARMORBASEPRICE = 15;
    public Shoes(String name,int def,double droprate,int lvl){
        super(name,def,droprate);
        setType(Armortype.Shoes);
        setItemlvl(lvl);
        setSubimagey(lvl);
        setSubimagex(getType().getValue());
    }
   @Override
   public int itemPrice(){
      return getItemlvl()*SHOEARMORBASEPRICE;



   }
      public static class Stoffschuhe extends Shoes {
           public Stoffschuhe(){
                   super("Stoffschuhe", 1,500,1);

           }
      }
      public static class WaldläuferSchuhe extends Shoes {
           public WaldläuferSchuhe(){
                   super("Waldläuferschuhe", 2,350,2);

           }
      }
      public static class GepanzerteSandalen extends Shoes {
           public GepanzerteSandalen(){
                   super("Gepanzerte Sandalen", 3,200,3);

           }
      }
      public static class Plattenschuhe extends Shoes {
           public Plattenschuhe(){
                   super("Plattenschuhe", 4,100,4);

           }
      }
      public static class HermesStiefel extends Shoes {
           public HermesStiefel(){
                   super("Hermes Stiefel", 5,50,5);

           }
      }
      public static class SchwarzeStiefel extends Shoes {
           public SchwarzeStiefel(){
                   super("Schwarze Stiefel", 6,20,6);

           }
      }
      public static class BlutroteSchuhe extends Shoes {
           public BlutroteSchuhe(){
                   super("Blutrote Handschuhe", 8,10,7);

           }
      }

}
