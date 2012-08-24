/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items.Armor;

import Spiel.model.Entities.Items.Item;

/**
 *
 * @author Gamer
 */
public abstract class Armor extends Item {

    private int defence;

    public enum Armortype {

        Helmet(0), BodyArmor(1), Gloves(2), Shield(3), Shoes(4);
        private int value;

        private Armortype(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    private Armortype type;
      public Armor(String name,int def,double droprate){
              super();
              this.defence=def;
              setName(name);
              setDroprate(droprate);

              
              
      }
      @Override
      public String showStat(){
        return Integer.toString(this.defence);

      }

        public int getDefence() {
                return defence;
        }

        public void setDefence(int defence) {
                this.defence = defence;
        }

    public Armortype getType() {
        return type;
    }

    public void setType(Armortype type) {
        this.type = type;
    }
        

        @Override
        public void useItem() {
                       if (getPlayer().getArmor(type) !=null) {
                                getPlayer().getInventar().add(getPlayer().getArmor(type));
                        }
                        getPlayer().setArmor(this);
                        getPlayer().getInventar().remove(this);
        }

        

}
