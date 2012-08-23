/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.MainModel;
import Spiel.model.MainModel.Richtung;
import Spiel.model.Room;
import Spiel.model.Utilites;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author lukas
 */
public abstract class NPC implements Drawable,Movable,Serializable{
    private int x;
    private int y;
    private char icon;
    private String name;
    private MainModel main;
    private Room room;
    private int animationCounter=0;
    private int internalCounter=0;
    
    private long delay;
    private long gametick=0;
    private int currentimg;
    private int dmg;
    private int hp;
    private int defence;
    private int movex=0;
    private int movey=0;
    private int targetx=0;
    private int targety;
    private double walkspeed=3e8;
    private boolean walking;
    private final int FIELDSIZE;
    private boolean hit=false;
    private String filename;
    private Richtung orientierung=Richtung.DOWN;
    private boolean removethis=false;


    /**
     * Konstrukor für abstrake Klasse NPC, wird von den Subklassen aufgerufen
     * @param x X-Koord
     * @param y Y-Koord
     * @param icon Char Icon zur Identifizierung auf Map
     * @param main MainModel-Methode wird übergeben
     */
    public NPC(int x, int y, char icon,MainModel main) {
          this.FIELDSIZE=main.getFIELDSIZE();
          this.x=x*FIELDSIZE;
          this.y=y*FIELDSIZE;
          this.targetx=x;
          this.targety=y;
          this.icon=icon;
          this.main=main;
    }
    
     @Override
     public void move() {
          delay+=main.getDelta()/1e6;

          if (walking  && movex==0 && movey==0 && getMain().map[fieldinFront(1)[1]][fieldinFront(1)[0]] == ' ') {
          main.map[targety/FIELDSIZE][targetx/FIELDSIZE] = ' ';       
          
          switch (orientierung) {
               case LEFT:
                    movex=-FIELDSIZE;
                    targetx=x-FIELDSIZE;
                         
                    break;
               case RIGHT:
                    movex=+FIELDSIZE;
                    targetx=x+FIELDSIZE;
          
                    break;
               case UP:
                    movey=-FIELDSIZE;
                    targety=y-FIELDSIZE;
                    break;
               case DOWN:
                    movey=+FIELDSIZE;
                    targety=y+FIELDSIZE;
                    break;
          }
          
          main.map[targety/FIELDSIZE][targetx/FIELDSIZE] = getIcon();       
          }

         if (movex != 0) {
             if (movex > 0) {
                 this.x = (int) (this.x + (movex * main.getDelta() / walkspeed * 2));
                 if (x >= targetx) {
                     this.x = targetx;
                     movex = 0;
                 }
             }
             if (movex < 0) {
                 this.x = (int) (this.x + (movex * main.getDelta() / walkspeed));
                 if (x <= targetx) {
                     this.x = targetx;
                     movex = 0;

                 }
             }


               

          } else{}

             if (movey != 0) {
              if (movey > 0) {
                  this.y = (int) (this.y + (movey * main.getDelta() / walkspeed * 2));
                  if (y >= targety) {
                      this.y = targety;
                      movey = 0;
                  }
              }
              if (movey < 0) {
                  this.y = (int) (this.y + (movey * main.getDelta() / walkspeed));
                  if ( y <= targety) {
                      this.y = targety;
                      movey = 0;

                  }
              }



          }
          
     }
          
    
    
    
    
    
        @Override
    public void doLogic(long delta) {
            if    (hit) {
                    if (internalCounter>9) {
                     internalCounter=0;
                     animationCounter=0;      
                     hit=false;       
                    }
                    if (animationCounter<2) {
                       animationCounter++;     
                    }
            internalCounter++;        
      
            }
             if (gametick ==60) {
                  gametick=0;
             } else {
            gametick++;
             }
    }
            

        public void changeMapforObject(NPC e) {
        main.map[e.getY()/FIELDSIZE][e.getX()/FIELDSIZE] = e.getIcon();

    }
    
    
    //Platzieren einer NPC im Bereich von x bis y
    /**
     * 
     * @param x1 Obere Linke Ecke x-Wert
     * @param y1 Obere Linke Ecke y-Wert
     * @param w Breite des Bereichs wo NPC platziert werden soll
     * @param h Höhe des Bereichs wo NPC platziert werden soll
     */
    public void setstartposition(int x1, int y1,int w, int h) {
          for (int i = 0; i < main.getBreite()*main.getHoehe(); i++) {
          int x=  Spiel.model.Utilites.randomizer(x1,(x1+w));
          int y=  Spiel.model.Utilites.randomizer(y1,(y1+h));
                try {
                if (main.map[y][x]== ' ' && notinFrontofDoor()) {
                      this.x=x*FIELDSIZE;
                      this.y=y*FIELDSIZE;
                      this.targetx=this.x;
                      this.targety=this.y;
                      
                      main.map[y][x]=this.icon;
                      break;
                } else {
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }


    }
    
    
    


 

    public Room findRoomLocation(){
        LinkedList<Room> rooms= this.main.getDungeon().getRooms();
        
        for (Room r : rooms) {
            if (r.getX1()<=this.x/FIELDSIZE && this.x/FIELDSIZE<=r.getX1()+r.getBreite() && r.getY1()<=this.y/FIELDSIZE && this.y/FIELDSIZE <= r.getY1()+r.getHoehe()) {
                this.room=r;
            }
             else {
                
            }
            
        }
     return room;   
    }
        public Room findRoomLocationatXY(int x, int y){
        LinkedList<Room> rooms= this.main.getDungeon().getRooms();
        Room foundroom=null;
        
        for (Room r : rooms) {
            if (r.getX1()<=x && x<=r.getX1()+r.getBreite() && r.getY1()<=y && y <= r.getY1()+r.getHoehe()) {
                foundroom=r;
                return foundroom;
            }
             else {
                
            }
            
        }
     return foundroom;   
    }
    

   
    public NPC objectinFront() {
         switch (getOrientierung()) {
            case RIGHT:
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX()/FIELDSIZE+1, getY()/FIELDSIZE);
            case LEFT:
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX()/FIELDSIZE-1, getY()/FIELDSIZE);
            case UP:
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX()/FIELDSIZE, getY()/FIELDSIZE-1);
            case DOWN:
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX()/FIELDSIZE, getY()/FIELDSIZE+1);
            default:
                return null;
            
        }
        
        
    }
   
public int[] fieldinFront(int n){
        int[] coord= new int[2];
     switch (getOrientierung()) {
            case RIGHT:
                    coord[0]=x/FIELDSIZE+n;
                    coord[1]=y/FIELDSIZE;             
                return coord;
            case LEFT:
                    coord[0]=x/FIELDSIZE-n;
                    coord[1]=y/FIELDSIZE;             
                return coord;
            case UP:
                    coord[0]=x/FIELDSIZE;
                    coord[1]=y/FIELDSIZE-n;             
                return coord;
            case DOWN:
                    coord[0]=x/FIELDSIZE;
                    coord[1]=y/FIELDSIZE+n;             
                return coord;
            default:
                return null;
            
        }   
        
        
        
}
public boolean notinFrontofDoor(){
    if ( Utilites.findEntitieonMap(getMain(), (getX()/FIELDSIZE)+1, getY()) instanceof Door) {
        return false;

    }

    if ( Utilites.findEntitieonMap(getMain(), (getX()/FIELDSIZE)-1, getY()) instanceof Door) {
        return false;

    }

    if ( Utilites.findEntitieonMap(getMain(), getX(), (getY()/FIELDSIZE)+1) instanceof Door) {
        return false;

    }

    if ( Utilites.findEntitieonMap(getMain(), getX(), (getY()/FIELDSIZE)-1) instanceof Door) {
        return false;

    }

    return true;



}



    public void attack(NPC d) {
       NPC a = this;
         
       int dmg = a.getDmg();
       int def = d.getDefence();
       int schaden = dmg - def;
       
            if (Spiel.model.Utilites.distance(a, d) > 3) {
                System.out.println("Ausser Reichweite");
            } else {


                if (schaden <= 0) {
                    System.out.println(d.getName()+" hat den Angriff abgeblockt");
                } else {
                    d.setHp(d.getHp() - schaden);
                    System.out.println(a.getName()+" hat dem "+d.getName()+ " "+ schaden + " Schaden zugefügt");
                    d.setHit(true);
                }
            }
           
       
       
       
       
       
       
   } 
    
    
    
    
   //
   //
   //
   //
   //
   //Getter und Setter:

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    
    public int getDefence() {
        return defence;
    }

    public void setDefence(int defence) {
        this.defence = defence;
    }

    public int getCounter() {
        return animationCounter;
    }

    public void setCounter(int counter) {
        this.animationCounter = counter;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
            if (hp <1) {
                    this.removethis=true;
            }
    }

    public char getIcon() {
        return icon;
    }

    public void setIcon(char icon) {
        this.icon = icon;
    }


    public MainModel getMain() {
        return main;
    }

    public void setMain(MainModel main) {
        this.main = main;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Richtung getOrientierung() {
        return orientierung;
    }

    public void setOrientierung(Richtung orientierung) {
             this.orientierung = orientierung;
              
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getCurrentimg() {
        return currentimg;
    }

    public void setCurrentimg(int currentimg) {
        this.currentimg = currentimg;
    }

    public int getMovex() {
        return movex;
    }

    public void setMovex(int movex) {
        this.movex = movex;
    }

    public int getMovey() {
        return movey;
    }

    public void setMovey(int movey) {
        this.movey = movey;
    }

    public long getAnimation() {
        return gametick;
    }

    public void setAnimation(long animation) {
        this.gametick = animation;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }

    public boolean isHit() {
        return hit;
    }

    public void setHit(boolean hit) {
        this.hit = hit;
        this.animationCounter=0;
    }

    public boolean isRemovethis() {
        return removethis;
    }

    public void setRemovethis(boolean removethis) {
        this.removethis = removethis;
    }

     public boolean isWalking() {
          return walking;
     }

     public void setWalking(boolean walking) {
          this.walking = walking;
     }

     public int getTargetx() {
          return targetx;
     }

     public void setTargetx(int targetx) {
          this.targetx = targetx;
     }

     public int getTargety() {
          return targety;
     }

     public void setTargety(int targety) {
          this.targety = targety;
     }

     public double getWalkspeed() {
          return walkspeed;
     }

     public void setWalkspeed(double walkspeed) {
          this.walkspeed = walkspeed;
     }

     public int getFIELDSIZE() {
          return FIELDSIZE;
     }


}
