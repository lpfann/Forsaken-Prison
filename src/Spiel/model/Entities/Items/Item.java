/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

import Spiel.model.Entities.Player;
import java.io.Serializable;


public abstract class Item implements Usable,Serializable,Comparable<Item> {

      private String name;
      private double droprate;
      private Player player;
      private int subimagex;
      private int subimagey;


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

        public int getSubimagex() {
                return subimagex;
        }

        public void setSubimagex(int subimagex) {
                this.subimagex = subimagex;
        }

        public int getSubimagey() {
                return subimagey;
        }

        public void setSubimagey(int subimagey) {
                this.subimagey = subimagey;
        }

     @Override
     public int compareTo(Item o) {
          if (this.getDroprate()<o.getDroprate()) {
               return -1;
          }    
          if (this.getDroprate()>o.getDroprate()) {
               return 1;
          }
          return 0;
     }



}
