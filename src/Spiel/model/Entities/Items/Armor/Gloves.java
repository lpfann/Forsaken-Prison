/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items.Armor;

/**
 *
 * @author Lukas
 */
public abstract class Gloves extends Armor {
   private final int GLOVEBASEPRICE=20;
   
    public Gloves(String name,int def,double droprate,int lvl){
        super(name,def,droprate);
        setType(Armortype.Gloves);
        setItemlvl(lvl);
        setSubimagey(lvl);
        setSubimagex(getType().getValue());
    }
   @Override
   public int itemPrice(){
      return getItemlvl()*GLOVEBASEPRICE;



   }
      public static class Lederhandschuhe extends Gloves {
           public Lederhandschuhe(){
                   super("Lederhandschuhe", 1,500,1);

           }
      }
      public static class GLederhandschuhe extends Gloves {
           public GLederhandschuhe(){
                   super("Grüne Lederhandschuhe", 2,350,2);

           }
      }
      public static class VLederhandschuhe extends Gloves {
           public VLederhandschuhe(){
                   super("Gepanzerte Handschuhe", 3,200,3);

           }
      }
      public static class BrauneLederhandschuhe extends Gloves {
           public BrauneLederhandschuhe(){
                   super("Braune Lederhandschuhe", 4,100,4);

           }
      }
      public static class GenieteteHandschuhe extends Gloves {
           public GenieteteHandschuhe(){
                   super("Nieten-Handschuhe", 5,50,5);

           }
      }
      public static class EisenplattenHandschuhe extends Gloves {
           public EisenplattenHandschuhe(){
                   super("Eisenplatten-Handschuhe", 7,20,6);

           }
      }
      public static class BlutroteHandschuhe extends Gloves {
           public BlutroteHandschuhe(){
                   super("Blutrote Handschuhe", 9,10,7);

           }
      }

}
