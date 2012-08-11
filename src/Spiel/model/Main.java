package Spiel.model;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.View.Observer;
import Spiel.View.Observer.transEnum;
import Spiel.model.Entities.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Lukas
 */
public class Main implements Subject, Serializable,Cloneable {

    int breite = 40;
    int hoehe = 20;
    long delta = 0;
    long last = 0;
    long fps = 0;
    public char[][] map;
    public Player player;
    private LinkedList monsters;
    transient private MonsterFactory monstergenerator;
    private LinkedList chests;
    transient private ChestFactory chestgenerator;
    private DungeonGenerator dungeon;
    public LinkedList<NPC> entities;
    public LinkedList<NPC> entcopy;
    private static ArrayList<Observer> observer = new ArrayList<>();

    public enum Richtung {

        LEFT, RIGHT, UP, DOWN;
    }

    public Main() {

        //Dungeonerstellung
        dungeon = new DungeonGenerator(breite, hoehe, this);
        map = dungeon.getMap();

        //Player Erstellung
        entities = new LinkedList<>();
        player = new Player(this);
        entities.add(player);
        //Türen Erstellung
        entities.addAll(dungeon.getDoors());
        //Monster Erstellung
        monstergenerator = new MonsterFactory(this);
        monsters = monstergenerator.populateDungeon(dungeon.getRooms());
        entities.addAll(monsters);

        //Truhen Erstellung
        chestgenerator = new ChestFactory(this);
        chests = chestgenerator.populateDungeon(dungeon.getRooms());
        entities.addAll(chests);


        //Objekte auf Map verteilen
        npcmap();



    }
    
    public void copyEntitie(){
        entcopy = (LinkedList<NPC>) entities.clone();
        
    }
    
    public void doLogic() {
         
        for (ListIterator<NPC> it = entcopy.listIterator(); it.hasNext();) {
            NPC e = it.next();
            e.doLogic(delta);
        }
        
    }

    public void moveNPCs() {
        for (ListIterator<NPC> it = entcopy.listIterator(); it.hasNext();) {
            NPC e = it.next();
            e.move();
        }
        
    }

    public void notifyobs() {
        notifyObserver(map);
        notifyObserver(transEnum.entities);
        notifyObserver(transEnum.playerstats);

    }

    public void npcmap() {
        for (NPC e : entities) {
            map[e.getY()][e.getX()] = e.getIcon();
        }
        notifyObserver(map);
    }

    @Override
    public void addObserver(Observer o) {
        observer.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observer.remove(o);
    }

    @Override
    public void notifyObserver(transEnum enu) {
        
        Main maincopy=null;
        try {
            maincopy = (Main) this.clone();
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        for (Observer ob : observer) {
            ob.update(enu, maincopy);
        }
    }

    @Override
    public void notifyObserver(char[][] map) {

        char[][] mapcopy = new char[map.length][map[0].length];
        for (int x = 0; x < map.length; x++) {
            System.arraycopy(map[x], 0, mapcopy[x], 0, map[0].length);
        }
        for (Observer ob : observer) {
            ob.update(mapcopy);

        }

    }

    public LinkedList<NPC> getEntities() {
        return entities;
    }

    public void setEntities(LinkedList<NPC> entities) {
        this.entities = entities;
    }



    public Player getPlayer() {
        return player;
    }



  




        public void computeDelta() {
        delta=System.nanoTime()-last;
        last= System.nanoTime();
        fps= ((long) 1e9/delta);
        this.notifyObserver(transEnum.fps);
    }

    
    
    public DungeonGenerator getDungeon() {
        return dungeon;
    }

    public void clearObservers() {
        observer.clear();
    }

    public void changeMapforObject(NPC e) {
        map[e.getY()][e.getX()] = e.getIcon();

    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public int getHoehe() {
        return hoehe;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public long getDelta() {
        return delta;
    }

    public void setDelta(long delta) {
        this.delta = delta;
    }

    public long getFps() {
        return fps;
    }

    public void setFps(long fps) {
        this.fps = fps;
    }

    public long getLast() {
        return last;
    }

    public void setLast(long last) {
        this.last = last;
    }

    public LinkedList<NPC> getEntcopy() {
        return entcopy;
    }

    public void setEntcopy(LinkedList<NPC> entcopy) {
        this.entcopy = entcopy;
    }
    
}
