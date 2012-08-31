/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items.Armor;

/**
 *
 * @author Lukas
 */
public abstract class Shield extends Armor {
   private final int SHIELDARMORBASEPRICE=20;
    public Shield(String name,int def,double droprate,int lvl){
        super(name,def,droprate);
        setType(Armortype.Shield);
        setItemlvl(lvl);
        setSubimagey(lvl);
        setSubimagex(getType().getValue());
    }
   @Override
   public int itemPrice(){
      return getItemlvl()*SHIELDARMORBASEPRICE;



   }
      public static class Holzschild extends Shield {
           public Holzschild(){
                   super("Holzschild", 1,500,1);

           }
      }
      public static class VerstärktesHolzschild extends Shield {
           public VerstärktesHolzschild(){
                   super("Verstärktes Holzschild", 2,350,2);

           }
      }
      public static class Wappenschild extends Shield {
           public Wappenschild(){
                   super("Wappenschild", 3,200,3);

           }
      }
      public static class VerstärktesWappenschild extends Shield {
           public VerstärktesWappenschild(){
                   super("Verstärktes Wappenschild", 4,100,4);

           }
      }
      public static class RundesEisenschild extends Shield {
           public RundesEisenschild(){
                   super("RundesEisenschild", 5,75,5);

           }
      }
      public static class GehärtetesRundesEisenschild extends Shield {
           public GehärtetesRundesEisenschild(){
                   super("Gehärtetes Rundes Eisenschild", 6,50,6);

           }
      }
      public static class EisernesWappenschild extends Shield {
           public EisernesWappenschild(){
                   super("EisernesWappenschild", 7,35,7);

           }
      }
      public static class Plattenschild extends Shield {
           public Plattenschild(){
                   super("Plattenschild", 8,20,8);

           }
      }
      public static class RundesPlattenschild extends Shield {
           public RundesPlattenschild(){
                   super("Rundes Plattenschild", 9,10,9);

           }
      }
      public static class GoldenesSchild extends Shield {
           public GoldenesSchild(){
                   super("Goldenes Schild", 10,2,10);

           }
      }

}
