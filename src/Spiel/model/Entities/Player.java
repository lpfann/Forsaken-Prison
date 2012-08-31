package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.Entities.Items.*;
import Spiel.model.Entities.Items.Armor.Armor;
import Spiel.model.Entities.Items.Armor.Armor.Armortype;
import Spiel.model.MainModel;
import java.awt.Color;
import java.util.LinkedList;

/**
 * Klasse für den Spieler
 * @author Lukas
 */
public final class Player extends NPC implements Attackble {

   private int xp, lvl, mana, smallpotions, mediumpotions, bigpotions, basedamage, maxhp, coins;
   private Armor[] armor = new Armor[5];
   private Waffe weapon;
   private LinkedList<Item> inventar = new LinkedList<>();
   boolean walking;
   private final int[] levelups = {100, 250, 500, 750, 1000, 1300, 1700, 2000, 2500, 3000, 3600, 4500,5500};
   private boolean up, down, left, right;

   /**
    *
    * @param main
    */
   public Player(MainModel main) {
      super(0, 0, 'P', main);
      setName("Held");
      setBasedamage(3);
      setDmg(0);
      updateDmg();
      setMaxhp(100);
      setHp(100);
      setDefence(0);
      lvl = 1;
      setXp(0);
      setstartposition(2, 2, main.getBreite() - 2, main.getHoehe() - 2);
      findRoomLocation();
      getMain().getVisitedRooms().add(getRoom());

   }

   @Override
   public void doLogic(long delta) {
      super.doLogic(delta);

      if (up || down || left || right) {
         setWalking(true);
         getMain().notifyObserver(Observer.sounds.walkingon);
      } else {
         setWalking(false);
         getMain().notifyObserver(Observer.sounds.walkingoff);
      }

      if (up) {
         setOrientierung(MainModel.Richtung.UP);
      }
      if (down) {

         setOrientierung(MainModel.Richtung.DOWN);
      }



      if (left) {
         setOrientierung(MainModel.Richtung.LEFT);
      }
      if (right) {

         setOrientierung(MainModel.Richtung.RIGHT);
      }

      setRoom(findRoomLocation());
   }

   /**
    * Monster vor dem Spieler angreifen
    */
   public void attackmonster() {

      if (enemyInFront() instanceof Attackble) {
         Monster monster = (Monster) enemyInFront();
         double attackspeed = (getWeapon() != null) ? getWeapon().getAttackspeed() : 1;

         if (getDelay() * attackspeed > 500) {
            setAttacking(true);
            attack(monster);

            if (monster.getHp() <= 0) {
               setXp(getXp() + monster.getXp());
               getMain().effects.add(new Effect(getX() / getFIELDSIZE(), getY() / getFIELDSIZE(), getMain(), "+" + monster.getXp() + " XP", Color.YELLOW, 1000));
            }
            setDelay(0);
         }




      }






   }

   /**
    * Objekte die vor dem Spieler sind benutzen. Müssen Usable sein
    */
   public void action() {
      if (objectinFront() instanceof Usable) {
         ((Usable) objectinFront()).use(this);
      }
   }

   //Getter und Setter
   /**
    *
    * @return
    */
   public int getLvl() {
      return lvl;
   }

   /**
    *
    * @param lvl
    */
   public void setLvl(int lvl) {

      this.lvl = lvl;
   }

   /**
    * Stufe des Spielers erhöhen. Kümmert sich um die Statserhöhung
    */
   public void increaseLevel() {
      setLvl(getLvl() + 1);
      getMain().notifyObserver(Observer.sounds.levelup);
      setBasedamage(getBasedamage() + 1);
      updateDmg();
      setMaxhp(maxhp + 10);
      setHp(maxhp);
      getMain().effects.add(new Effect(getX() / getFIELDSIZE(), getY() / getFIELDSIZE(), getMain(), "LEVEL UP!", Color.CYAN, 4000));
      System.out.println("Du bist ein Level aufgestiegen! Dein Schaden und deine max. Lebenspunkte haben sich erhöht!");




   }

   /**
    *
    * @return
    */
   public int getMana() {
      return mana;
   }

   /**
    *
    * @param mana
    */
   public void setMana(int mana) {
      this.mana = mana;
   }

   /**
    *
    * @return
    */
   public int getXp() {
      return xp;
   }

   /**
    *
    * @param xp
    */
   public void setXp(int xp) {
      this.xp = xp;

      if (xp >= levelups[getLvl() - 1]) {
         increaseLevel();
      }

   }

   /**
    *
    * @return Liste des Inventars
    */
   public LinkedList<Item> getInventar() {
      return inventar;
   }

   /**
    *
    * @param inventar
    */
   public void setInventar(LinkedList<Item> inventar) {
      this.inventar = inventar;
   }

   /**
    * Gibt die ausgerüstete Rüstung des Spielers aus
    * @param a Typ der Rüstung die ausgegeben werden soll
    * @return Rüstung des Typs a die der Spieler anhat
    */
   public Armor getArmor(Armortype a) {
      return this.armor[a.getValue()];
   }

   /**
    * Rüstung setzen
    * @param a Typ der Rüstung
    */
   public void setArmor(Armor a) {

      this.armor[a.getType().getValue()] = a;
      setDefence(calcDefence());
   }
/**
 * Zählt die Verteidigungswerte aller Rüstungsgegenstände zusammen
 * @return gesamter Verteidigungswerte
 */
   private int calcDefence() {
      int def = 0;
      for (int i = 0; i < armor.length; i++) {
         if (armor[i] != null) {
            def += armor[i].getDefence();

         }
      }
      return def;
   }

   /**
    *
    * @return Die ausgerüstete Waffe
    */
   public Waffe getWeapon() {

      return weapon;


   }

   /**
    *
    * @param weapon Die Waffe die  ausgerüstet werden soll
    */
   public void setWeapon(Waffe weapon) {
      this.weapon = weapon;
      updateDmg();
   }

   /**
    * Das ausgewählte Item benutzen
    * @param selected Item
    */
   public void useItem(Item selected) {
      selected.useItem();
      getMain().notifyObserver(Observer.transEnum.playerstats);

   }

   /**
    *
    * @return DIe Zahl der großen Tränke
    */
   public int getBigpotions() {
      return bigpotions;
   }

   /**
    *
    * @param bigpotions Die Zahl der großen Tränke
    */
   public void setBigpotions(int bigpotions) {
      this.bigpotions = bigpotions;
   }

   /**
    *
    * @return Die Zahl der mittleren Tränke
    */
   public int getMediumpotions() {
      return mediumpotions;
   }

   /**
    *
    * @param mediumpotions Die Zahl der mittleren Tränke
    */
   public void setMediumpotions(int mediumpotions) {
      this.mediumpotions = mediumpotions;
   }

   /**
    *
    * @return Die Zahl der kleinen Tränke
    */
   public int getSmallpotions() {
      return smallpotions;
   }

   /**
    *
    * @param smallpotions Die Zahl der kleinen Tränke
    */
   public void setSmallpotions(int smallpotions) {
      this.smallpotions = smallpotions;
   }

   /**
    * Item verkaufen
    * @param it Item was verkauft werden soll
    */
   public void sellItem(Item it) {
      this.coins += it.itemPrice();
      getInventar().remove(it);
      getMain().notifyObserver(Observer.transEnum.playerstats);

   }

   /**
    * Heiltrank benutzen
    */
   public void usePotion() {
      if (smallpotions > 0) {
         useItem(new Heiltrank(Trank.Size.KLEIN, this));
         smallpotions--;
      } else if (mediumpotions > 0) {
         useItem(new Heiltrank(Trank.Size.MITTEL, this));
         mediumpotions--;
      } else if (bigpotions > 0) {
         useItem(new Heiltrank(Trank.Size.GROß, this));
         bigpotions--;
      } else {
         System.out.println("Du hast keine Tränke");
      }



   }

   @Override
   public void setHit(boolean t) {
      super.setHit(t);
      getMain().notifyObserver(Observer.sounds.playerhit);
   }

   /**
    *
    * @return der Grundschaden
    */
   public int getBasedamage() {
      return basedamage;
   }

   /**
    *
    * @param basedamage der Grundschaden
    */
   public void setBasedamage(int basedamage) {
      this.basedamage = basedamage;
   }

   @Override
   public void setHp(int hp) {
      if (hp < 1) {
         super.setHp(0);
         getMain().setGameover(true);

      } else {

         if (hp > maxhp) {
            super.setHp(maxhp);

         } else {
            super.setHp(hp);

         }
      }
   }

   @Override
   public void setDmg(int dmg) {
      super.setDmg(dmg);
   }

   /**
    *
    * @return Die obere Grenze der HP
    */
   public int getMaxhp() {
      return maxhp;
   }

   /**
    *
    * @param maxhp Die obere Grenze der HP
    */
   public void setMaxhp(int maxhp) {
      this.maxhp = maxhp;
   }

   /**
    * Zählt Basisschaden und Waffenschaden zusammen
    */
   public void updateDmg() {
      if (getWeapon() != null) {
         setDmg(getWeapon().getDamage() + getBasedamage());

      } else {

         setDmg(getBasedamage());
      }
   }



   /**
    *
    * @return
    */
   public boolean isUp() {
      return up;
   }

   /**
    *
    * @param up
    */
   public void setUp(boolean up) {
      this.up = up;
   }

   /**
    *
    * @return
    */
   public boolean isDown() {
      return down;
   }

   /**
    *
    * @param down
    */
   public void setDown(boolean down) {
      this.down = down;
   }

   /**
    *
    * @return
    */
   public boolean isLeft() {
      return left;
   }

   /**
    *
    * @param left
    */
   public void setLeft(boolean left) {
      this.left = left;
   }

   /**
    *
    * @return
    */
   public boolean isRight() {
      return right;
   }

   /**
    *
    * @param right
    */
   public void setRight(boolean right) {
      this.right = right;
   }

   /**
    *
    * @return  die Anzahl der Münzen
    */
   public int getCoins() {
      return coins;
   }

   /**
    *
    * @param coins  die Anzahl der Münzen
    */
   public void setCoins(int coins) {
      this.coins = coins;
   }
}
