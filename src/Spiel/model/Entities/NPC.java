/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.Main;
import Spiel.model.Main.Richtung;
import Spiel.model.Room;
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
    private Main main;
    private Room room;
    private int animationCounter=0;
    private int internalCounter=0;
    
    private long delay;
    private long gametick=0;
    private int currentimg;
    private int dmg;
    private int hp;
    private int armor;
    private int movex=0;
    private int movey=0;
    private boolean hit=false;
    private String filename;
    private Richtung orientierung=Richtung.DOWN;
    private boolean removethis=false;


    /**
     * Konstrukor für abstrake Klasse NPC, wird von den Subklassen aufgerufen
     * @param x X-Koord
     * @param y Y-Koord
     * @param icon Char Icon zur Identifizierung auf Map
     * @param main Main-Methode wird übergeben
     */
    public NPC(int x, int y, char icon,Main main) {
          this.x=x;
          this.y=y;
          this.icon=icon;
          this.main=main;
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
    }
            
    @Override
    public void move() {

        if (movex != 0) {
            if (movex>0) {
                moveRight();
                this.movex--;
            } else {
                moveLeft();
                this.movex++;
            }
            
        }
        if (movey != 0) {
            if (movey>0) {
                moveDown();
                this.movey--;
            } else {
                moveUp();
                this.movey++;
            }
        }
        changeMapforObject(this);
      }
    
        public void changeMapforObject(NPC e) {
        main.map[e.getY()][e.getX()] = e.getIcon();

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
          int x=  Spiel.model.Utilites.randomizer(x1,x1+w);
          int y=  Spiel.model.Utilites.randomizer(y1,y1+h);
                try {
                if (main.map[y][x]== ' ') {
                      this.x=x;
                      this.y=y;
                      main.map[y][x]=this.icon;
                      break;
                } else {
                      
                }
                } catch (ArrayIndexOutOfBoundsException ex) {
                    System.out.println("stop");
                }
          }
                
          
    }
    
    
    


    public void moveLeft() {
        if (main.map[y][x - 1] == ' ') {
            main.map[y][x] = ' ';
            this.x += movex;
            this.orientierung= Richtung.LEFT;
        findRoomLocation();
        } else {
          this.orientierung= Richtung.LEFT;  
        }
    }

    public void moveRight() {
        if (main.map[y][x + 1] == ' ') {
            main.map[y][x] = ' ';
            this.x += movex;
            this.orientierung= Richtung.RIGHT;
        findRoomLocation();
        } else {
          this.orientierung= Richtung.RIGHT;  
        }
    }

    public void moveUp() {
        if (main.map[y - 1][x] == ' ') {
            main.map[y][x] = ' ';
            this.y += movey;
            this.orientierung= Richtung.UP;
        findRoomLocation();
        } else {
          this.orientierung= Richtung.UP;  
        }
    }

    public void moveDown() {
        if (main.map[y + 1][x] == ' ') {
            main.map[y][x] = ' ';
            this.y += movey;
            this.orientierung= Richtung.DOWN;
            findRoomLocation();
        } else {
          this.orientierung= Richtung.DOWN;  
        }
    }
        

    public Room findRoomLocation(){
        LinkedList<Room> rooms= this.main.getDungeon().getRooms();
        
        for (Room r : rooms) {
            if (r.getX1()<=this.x && this.x<=r.getX1()+r.getBreite() && r.getY1()<=this.y && this.y <= r.getY1()+r.getHoehe()) {
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
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX()+1, getY());
            case LEFT:
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX()-1, getY());
            case UP:
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX(), getY()-1);
            case DOWN:
                return Spiel.model.Utilites.findEntitieonMap(getMain(), getX(), getY()+1);
            default:
                return null;
            
        }
        
        
    }
   
public int[] fieldinFront(int n){
        int[] coord= new int[2];
     switch (getOrientierung()) {
            case RIGHT:
                    coord[0]=x+n;
                    coord[1]=y;             
                return coord;
            case LEFT:
                    coord[0]=x-n;
                    coord[1]=y;             
                return coord;
            case UP:
                    coord[0]=x;
                    coord[1]=y-n;             
                return coord;
            case DOWN:
                    coord[0]=x;
                    coord[1]=y+n;             
                return coord;
            default:
                return null;
            
        }   
        
        
        
}



    public void attack(NPC d) {
       NPC a = this;
       int dmg = a.getDmg();
       int def = d.getArmor();
       int schaden = dmg - def;
       
            if (Spiel.model.Utilites.distance(a, d) > 3) {
                System.out.println("Ausser Reichweite");
            } else {


                if (schaden < 0) {
                    System.out.println("Kein Schaden");
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
    
    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
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


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
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


}
