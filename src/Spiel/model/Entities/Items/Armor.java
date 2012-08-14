/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

/**
 *
 * @author Gamer
 */
public abstract class Armor extends Item {
        private int defence;
      public Armor(String name,int def,int x,int y){
              super();
              this.defence=def;
              setSubimagex(x);
              setSubimagey(y);
              setName(name);
              
              
      }

        public int getDefence() {
                return defence;
        }

        public void setDefence(int defence) {
                this.defence = defence;
        }

        @Override
        public void useItem() {
                       if (getPlayer().getArmor()!=null) {
                                getPlayer().getInventar().add(getPlayer().getArmor());
                        }
                        getPlayer().setArmor(this);
                        getPlayer().getInventar().remove(this);
        }

        
      public static class Lederwams extends Armor {
           public Lederwams(){
                   super("Lederwams", 1,0,1);
                   
           }   
      }  
      public static class Eisenrüstung extends Armor {
           public Eisenrüstung(){
                   super("Eisenrüstung", 5,1,1);
                   
           }   
      }  
      public static class Goldrüstung extends Armor {
           public Goldrüstung(){
                   super("Goldrüstung", 6,2,1);
                   
           }   
      }  
      public static class Lederrüstung extends Armor {
           public Lederrüstung(){
                   super("Lederrüstung", 2,3,1);
                   
           }   
      }
      
      public static class BeschlageneLederRüstung extends Armor {
           public BeschlageneLederRüstung(){
                   super("Beschlagene Leder Rüstung", 3,5,1);
                   
           }   
      }  
      public static class Kettenhemd extends Armor {
           public Kettenhemd(){
                   super("Kettenhemd", 4,4,1);
                   
           }   
      }  
}
