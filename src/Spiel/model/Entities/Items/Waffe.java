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

      Waffe(String name) {
            super();
            setName(name);
            
      }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
      
}
