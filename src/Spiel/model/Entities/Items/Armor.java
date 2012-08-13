/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

/**
 *
 * @author Gamer
 */
public class Armor extends Item {
        private int defence;
      public Armor(String name,int def){
              super();
              this.defence=def;
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

}
