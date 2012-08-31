/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities.Items;

import Spiel.model.Entities.Player;
import java.io.Serializable;

/**
 * Item Klasse
 * @author Lukas
 */
public abstract class Item implements UsableItem, Serializable, Comparable<Item> {

   private String name;
   private double droprate;
   private int itemlvl;
   private Player player;
   private int subimagex;
   private int subimagey;

   /**
    *
    */
   public Item() {


   }

   /**
    * Funktion zum Ausgeben des Item-Bonus
    * @return item-Bonus
    */
   abstract public String showStat();

   /**
    *
    * @param name
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    *
    * @return
    */
   public String getName() {
      return name;
   }

   /**
    *
    * @return
    */
   public double getDroprate() {
      return droprate;
   }

   /**
    *
    * @param droprate
    */
   public void setDroprate(double droprate) {
      this.droprate = droprate;
   }

   /**
    *
    * @return
    */
   public Player getPlayer() {
      return player;
   }

   /**
    *
    * @param player
    */
   public void setPlayer(Player player) {
      this.player = player;
   }

   /**
    *
    * @return
    */
   public int getSubimagex() {
      return subimagex;
   }

   /**
    *
    * @param subimagex
    */
   public void setSubimagex(int subimagex) {
      this.subimagex = subimagex;
   }

   /**
    *
    * @return
    */
   public int getSubimagey() {
      return subimagey;
   }

   /**
    *
    * @param subimagey
    */
   public void setSubimagey(int subimagey) {
      this.subimagey = subimagey;
   }

   @Override
   public int compareTo(Item o) {
      if (this.getDroprate() < o.getDroprate()) {
         return -1;
      }
      if (this.getDroprate() > o.getDroprate()) {
         return 1;
      }
      return 0;
   }

   /**
    *
    * @return
    */
   public int getItemlvl() {
      return itemlvl;
   }

   /**
    *
    * @param itemlvl
    */
   public void setItemlvl(int itemlvl) {
      this.itemlvl = itemlvl;
   }

}
