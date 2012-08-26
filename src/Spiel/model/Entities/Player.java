package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.Entities.Items.*;
import Spiel.model.MainModel;
import java.util.LinkedList;
import Spiel.model.Entities.Items.Armor.Armor;
import Spiel.model.Entities.Items.Armor.Armor.Armortype;

public final class Player extends NPC {

    private int xp, lvl, mana,smallpotions,mediumpotions,bigpotions,basedamage,maxhp;

    private Armor[] armor= new Armor[5];
    private Waffe weapon;
    private LinkedList<Item> inventar = new LinkedList<>();
    boolean walking;
    boolean attacking;
    private final int[] levelups= { 100,200,300,400,500,650,850,1100,1300,1500,1800,2100};
    private boolean up,down,left,right;
    private int attackanim=0;




    public Player(MainModel main) {
        super(0, 0, 'P', main);
        setName("Held");
        setBasedamage(3);
        setDmg(0);
        updateDmg();
        setMaxhp(100);
        setHp(100);
        setDefence(0);
        lvl=1;
        setXp(0);
        setstartposition(2, 2, main.getBreite()-2, main.getHoehe()-2);
        findRoomLocation();
        getMain().getVisitedRooms().add(getRoom());

    }

    @Override
    public void doLogic(long delta) {
        super.doLogic(delta);
        attackanim += delta / 1e6;
        if (attackanim > 500) {
            setAttacking(false);
            attackanim=0;
        }

        if (up || down || left || right) {
            setWalking(true);
        } else {
            setWalking(false);
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


    public void attackmonster() {

        if (enemyInFront()!= null) {
            Monster monster = (Monster)enemyInFront();
            double attackspeed= (getWeapon()!=null) ? getWeapon().getAttackspeed() : 1;

            if (getDelay()*attackspeed >500) {
                setAttacking(true);
                attack(monster);

                if (monster.getHp() <= 0) {
                    setXp(getXp() + monster.getXp());
                    getMain().effects.add(new Effect(getX()/getFIELDSIZE(), getY()/getFIELDSIZE(), getMain(), "+"+monster.getXp()+" XP"));
                    }
                setDelay(0);
            }




        }






    }







    public void action() {
        if (objectinFront() instanceof Usable) {
            ((Usable) objectinFront()).use(this);
        }
    }




    //Getter und Setter
    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {

        this.lvl = lvl;
    }
    public void increaseLevel(){
        setLvl(getLvl()+1);
         setBasedamage(getBasedamage()+1);
        updateDmg();
        setMaxhp(maxhp+10);
         System.out.println("Du bist ein Level aufgestiegen! Dein Schaden und deine max. Lebenspunkte haben sich erhöht!");




    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;

            if (xp>=levelups[getLvl()-1]) {
                    increaseLevel();
            }

    }

        public LinkedList<Item> getInventar() {
                return inventar;
        }

        public void setInventar(LinkedList<Item> inventar) {
                this.inventar = inventar;
        }



        public Armor getArmor(Armortype a) {
                return this.armor[a.getValue()];
        }

        public void setArmor(Armor a) {

                this.armor[a.getType().getValue()]= a;
                setDefence(calcDefence());
        }

        private int calcDefence(){
            int def=0;
            for (int i = 0; i < armor.length; i++) {
                if (armor[i]!=null) {
                def+=armor[i].getDefence();

                }
            }
            return def;
        }

        public Waffe getWeapon() {

                return weapon;


        }

        public void setWeapon(Waffe weapon) {
                this.weapon = weapon;
                setDmg(weapon.getDamage());
        }

        public void useItem(Item selected) {
          selected.useItem();
          getMain().notifyObserver(Observer.transEnum.playerstats);

        }

        public int getBigpotions() {
                return bigpotions;
        }

        public void setBigpotions(int bigpotions) {
                this.bigpotions = bigpotions;
        }

        public int getMediumpotions() {
                return mediumpotions;
        }

        public void setMediumpotions(int mediumpotions) {
                this.mediumpotions = mediumpotions;
        }

        public int getSmallpotions() {
                return smallpotions;
        }

        public void setSmallpotions(int smallpotions) {
                this.smallpotions = smallpotions;
        }



        public void usePotion() {
                if (smallpotions>0) {
                        useItem(new Heiltrank(Trank.Size.KLEIN,this));
                        smallpotions--;
                } else if ( mediumpotions>0) {
                        useItem(new Heiltrank(Trank.Size.MITTEL,this));
                        mediumpotions--;
                } else if (bigpotions>0) {
                        useItem(new Heiltrank(Trank.Size.GROß,this));
                        bigpotions--;
                } else {
                        System.out.println("Du hast keine Tränke");
                }



        }

     public int getBasedamage() {
          return basedamage;
     }

     public void setBasedamage(int basedamage) {
          this.basedamage = basedamage;
     }



     @Override
    public void setHp(int hp) {
            if (hp <1) {
                    getMain().setGameover(true);
            }
            if (hp>maxhp) {
             super.setHp(maxhp);

          } else {
             super.setHp(hp);

            }
    }

     @Override
     public void setDmg(int dmg) {
          super.setDmg(dmg);
     }

     public int getMaxhp() {
          return maxhp;
     }

     public void setMaxhp(int maxhp) {
          this.maxhp = maxhp;
     }
     public void  updateDmg(){
          if (getWeapon()!=null) {
          setDmg(getWeapon().getDamage()+getBasedamage());

          } else {

               setDmg(getBasedamage());
          }
     }

     public void debugPrintObjectinFront(){
          NPC n = objectinFront();
          if (n!=null) {
          System.err.println(n.getClass().getSimpleName());

          }
          System.err.println(getMain().map[fieldinFront(1)[1]][fieldinFront(1)[0]]);


     }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

}
