/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;
import Spiel.model.MainModel.Richtung;
import Spiel.model.Room;
import Spiel.model.Utilites;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 *
 * @author lukas
 */
public abstract class NPC implements Movable,Serializable{
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
    private double walkspeed=2e8;
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
                 this.x = (int) (Math.ceil(this.x + (movex * main.getDelta() / walkspeed )));
                 if (x >= targetx) {
                     this.x = targetx;
                     movex = 0;
                 }
             }
             if (movex < 0) {
                 this.x = (int) (Math.floor(this.x + (movex * main.getDelta() / walkspeed)));
                 if (x <= targetx) {
                     this.x = targetx;
                     movex = 0;

                 }
             }


               

          } else{}

             if (movey != 0) {
              if (movey > 0) {
                  this.y = (int) (Math.ceil(this.y + (movey * main.getDelta() / walkspeed)));
                  if (y >= targety) {
                      this.y = targety;
                      movey = 0;
                  }
              }
              if (movey < 0) {
                  this.y = (int) (Math.floor(this.y + (movey * main.getDelta() / walkspeed)));
                  if ( y <= targety) {
                      this.y = targety;
                      movey = 0;

                  }
              }



          }
          
     }
          
    
    
    
    
    
        @Override
    public void doLogic(long delta) {
           delay+=main.getDelta()/1e6;

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
    public void setstartposition(int x1, int y1, int w, int h) {
        boolean fertig = false;
        while (!fertig) {
            int xx = Spiel.model.Utilites.randomizer(x1, (x1 + w - 1));
            int yy = Spiel.model.Utilites.randomizer(y1, (y1 + h - 1));
            try {
                    //TODO OutofBound Exception Fixens
                    if (main.map[yy][xx] == ' ' && notinFrontofDoor() && noOtheNpcs()) {
                        this.x = xx * FIELDSIZE;
                        this.y = yy * FIELDSIZE;
                        this.targetx = this.x;
                        this.targety = this.y;

                        main.map[yy][xx] = this.icon;
                        fertig = true;
                    }

                }  catch (Exception ex) {
                ex.printStackTrace();
            }
        }

         
    }

    
    
    private boolean noOtheNpcs(){

        if (findEntitieonMap(x/FIELDSIZE-1, y/FIELDSIZE-1) == null && findEntitieonMap(x/FIELDSIZE, y/FIELDSIZE-1) == null && findEntitieonMap(x/FIELDSIZE-1, y/FIELDSIZE) == null &&
                findEntitieonMap(x/FIELDSIZE+1, y/FIELDSIZE-1) == null && findEntitieonMap(x/FIELDSIZE+1, y/FIELDSIZE) == null && findEntitieonMap(x/FIELDSIZE+1, y/FIELDSIZE+1) == null &&
                findEntitieonMap(x/FIELDSIZE-1, y/FIELDSIZE+1) == null && findEntitieonMap(x/FIELDSIZE, y/FIELDSIZE+1) == null    )
        {
           return true;


        } else {
            return false;
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

            
        }
     return foundroom;   
    }
    

   
    public NPC objectinFront() {
         switch (getOrientierung()) {
            case RIGHT:
                return findEntitieonMap( getX()/FIELDSIZE+1, getY()/FIELDSIZE);
            case LEFT:
                return findEntitieonMap( getX()/FIELDSIZE-1, getY()/FIELDSIZE);
            case UP:
                return findEntitieonMap(getX()/FIELDSIZE, getY()/FIELDSIZE-1);
            case DOWN:
                return findEntitieonMap( getX()/FIELDSIZE, getY()/FIELDSIZE+1);
            default:
                return null;
            
        }
        
        
    }
        private  NPC findEntitieonMap(int x,int y) {
        if (main.getEntities()!=null) {

            for (ListIterator<NPC> it = main.getEntities().listIterator(); it.hasNext();) {
                NPC e = it.next();
                if (e.getX()/ e.getFIELDSIZE() ==x && e.getY()/e.getFIELDSIZE()==y) {
                    return e;
                } else {

                }
            }

        } else {
            return null;
        }
        return null;



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
    if ( findEntitieonMap( (getX()/FIELDSIZE)+1, getY()) instanceof Door) {
        return false;

    }

    if ( findEntitieonMap( (getX()/FIELDSIZE)-1, getY()) instanceof Door) {
        return false;

    }

    if ( findEntitieonMap( getX(), (getY()/FIELDSIZE)+1) instanceof Door) {
        return false;

    }

    if ( findEntitieonMap( getX(), (getY()/FIELDSIZE)-1) instanceof Door) {
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

                if (d.getHp()<1) {

                } else {
                if (schaden <= 0) {
                    System.out.println(d.getName()+" hat den Angriff abgeblockt");
                } else {
                    d.setHp(d.getHp() - schaden);
                    System.out.println(a.getName()+" hat dem "+d.getName()+ " "+ schaden + " Schaden zugefügt");
                    main.effects.add(new Effect(d.getX()/FIELDSIZE, d.getY()/FIELDSIZE, main, String.valueOf(schaden)));
                    d.setHit(true);

                }
            }
           
            }
       
       
       
       
       
   }

    public NPC enemyInFront() {

        for (int i = 0 ;i< getRoom().getEntities().size();i++) {
            NPC e = getRoom().getEntities().get(i);
            if (e.getX()/FIELDSIZE == fieldinFront(1)[0] && e.getY()/FIELDSIZE ==fieldinFront(1)[1] &&( e instanceof Monster || e instanceof Player) ) {
                return e;
            }
        }
        
       return null;
        
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
