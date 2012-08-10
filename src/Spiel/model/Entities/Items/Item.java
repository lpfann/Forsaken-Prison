/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

import java.io.Serializable;


public class Item implements Serializable {

      private String name;
      private double droprate;


      Item() {
            
        
      }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public double getDroprate() {
        return droprate;
    }

    public void setDroprate(double droprate) {
        this.droprate = droprate;
    }
     


}
