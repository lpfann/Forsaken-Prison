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

      Waffe(String name,int dmg) {
            super();
            setName(name);
            this.damage=dmg;
            
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
        
      
}
