/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

/**
 *
 * @author Lukas
 */
public class Waffe extends Item {
      
private int damage;
private double attackspeed;

      Waffe(String name,int dmg) {
            super();
            setName(name);
            this.damage=dmg;
            
      }
      @Override
      public String showStat(){
        return Integer.toString(this.damage);

      }
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

        @Override
        public void useItem() {
                       if (getPlayer().getWeapon()!=null) {
                                getPlayer().getInventar().add(getPlayer().getWeapon());
                        }
                        getPlayer().setWeapon(this);
                        getPlayer().getInventar().remove(this);
        }

    public double getAttackspeed() {
        return attackspeed;
    }

    public void setAttackspeed(double attackspeed) {
        this.attackspeed = attackspeed;
    }
        
      
}
