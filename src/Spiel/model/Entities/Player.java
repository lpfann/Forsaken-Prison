package Spiel.model.Entities;

import Spiel.model.Entities.Items.Item;
import Spiel.model.Main;
import java.util.LinkedList;

public final class Player extends NPC {

    private int xp, lvl, mana;
    private LinkedList inventar = new LinkedList<>();


    public Player(Main main) {
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
        setFilename("player.png");
    }
    
//    @Override
//    public void drawEntitie(Graphics g,int fieldsize) {
//        switch (getOrientierung())    {
//            case DOWN:
//                    g.drawImage(getImage()[0], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null);    
//                    break;
//            case LEFT:
//                    g.drawImage(getImage()[1], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null);    
//                    break;
//            case UP:
//                    g.drawImage(getImage()[2], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null);    
//                    break;
//            case RIGHT:
//                    g.drawImage(getImage()[3], getX()*fieldsize, getY()*fieldsize,fieldsize,fieldsize, null);    
//                    break;
//            
//        }
//    }
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
            this.inventar.addAll(inhalt);
            for (Item e : inhalt) {
                System.out.println("Du hast: " + e.getName() + " gefunden");
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
    }

    public LinkedList getInventar() {
        return inventar;
    }

    public void setInventar(LinkedList inventar) {
        this.inventar = inventar;
    }

   
    
}
