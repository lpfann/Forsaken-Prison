package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.Entities.Items.*;
import Spiel.model.Entities.Items.Armor.Lederwams;
import Spiel.model.Entities.Items.Schwert.Zweihänder;
import Spiel.model.MainModel;
import java.util.LinkedList;

public final class Player extends NPC {

    private int xp, lvl, mana,smallpotions,mediumpotions,bigpotions;
    private Item armor;
    private Waffe weapon;
    private LinkedList<Item> inventar = new LinkedList<>();
    boolean walking;


    public Player(MainModel main) {
        super(0, 0, 'P', main);
        setName("Held");
        setDmg(5);
        setHp(100);
        setLvl(1);
        setMana(10);
        setXp(0);
        setstartposition(1, 1, main.getBreite()-2, main.getHoehe()-2);
        findRoomLocation();
        getMain().getVisitedRooms().add(getRoom());
        setWeapon(new Zweihänder());
        getWeapon().setPlayer(this);
        setArmor(new Lederwams());
        getArmor().setPlayer(this);
        setFilename("player.png");
    }

    
    
    public void attackmonster() {
        if (objectinFront() instanceof Monster) {
            NPC monster = objectinFront();

            attack(monster);
            if (monster.getHp() <= 0) {
                setXp(getXp() + 10);

            }


        }






    }

    

    public void openChest() {
        if (objectinFront() instanceof Truhe) {
            
            Truhe chest = (Truhe) objectinFront();
            if (chest.isOpened()) {
                
            } else {
            chest.setOpened(true);
            LinkedList<Item> inhalt = chest.getItems();
                    for (Item item : inhalt) {
                            item.setPlayer(this);
                            if (item instanceof Heiltrank) {
                                 switch (((Trank)item).getSize()){
                                         case KLEIN:
                                                 smallpotions++;
                                                 break;
                                         case MITTEL:
                                                 mediumpotions++;
                                                 break;
                                         case GROß:
                                                 bigpotions++;
                                                 break;
                                 } 
                            } else {
                               inventar.add(item);     
                                    
                            }
                             System.out.println("Du hast: " + item.getName() + " gefunden");
                            
                    }
            chest.setItems(null);
            }


        }




    }

    public void openDoor() {
        if (objectinFront() instanceof Door) {
          Door door = (Door)objectinFront();
          door.opencloseDoorSwitch();
          changeMapforObject(door);
          getMain().getVisitedRooms().add(findRoomLocationatXY(fieldinFront(2)[0],fieldinFront(2)[1]));
          getMain().setFogofwarrepaint(true);
          
        }
        
       
    }


    public void action() {
        if (objectinFront() instanceof Door) {
            openDoor();
            
            } else if (objectinFront() instanceof Truhe) {
                openChest();
            } else {
                
            }
        
    }
    
    
    
    
    //Getter und Setter
    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
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
            if (xp>100) {
                    setLvl(getLvl()+1);       
            }
                    
    }

        public LinkedList<Item> getInventar() {
                return inventar;
        }

        public void setInventar(LinkedList<Item> inventar) {
                this.inventar = inventar;
        }



        public Item getArmor() {
                return armor;
        }

        public void setArmor(Armor armor) {
                this.armor = armor;
                setDefence(armor.getDefence());
        }

        public Item getWeapon() {
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

     public boolean isWalking() {
          return walking;
     }

     public void setWalking(boolean walking) {
          this.walking = walking;
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


        
        
}
