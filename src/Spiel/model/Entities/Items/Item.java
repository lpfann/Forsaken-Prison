/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

import Spiel.model.Entities.Player;
import java.io.Serializable;


public abstract class Item implements Usable,Serializable {

      private String name;
      private double droprate;
      private Player player;


      Item() {
          this.name="";
          this.droprate=0;
        
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

        public Player getPlayer() {
                return player;
        }

        public void setPlayer(Player player) {
                this.player = player;
        }


     


}
