package Spiel.model;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.View.Observer;
import Spiel.View.Observer.transEnum;
import Spiel.model.Entities.*;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.io.Serializable;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
//TODO Truhen spawnen vor türen
//TODO Spieler spawnt in Türen

/**
 *
 * @author Lukas
 */
public class Main implements Subject, Serializable, Cloneable {

        int breite = 40;
        int hoehe = 20;
        long delta = 0;
        long last = 0;
        long fps = 0;
        public char[][] map;
        private boolean[][] fogofwar= new boolean[hoehe][breite];
        public Player player;
        private LinkedList monsters;
        transient private MonsterFactory monstergenerator;
        private LinkedList chests;
        transient private ChestFactory chestgenerator;
        private DungeonGenerator dungeon;
        public LinkedList<NPC> entities;
        public LinkedList<NPC> entcopy;
        private Stack<Room> visitedRooms= new Stack<>();
        private static ArrayList<Observer> observer = new ArrayList<>();
        private boolean gameover;
        private boolean fogofwarrepaint=true;



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
                
                //Fog of War
                initFogofwar();
                
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
        private void initFogofwar() {
                for (int i = 0; i < fogofwar.length; i++) {
                        for (int j = 0; j < fogofwar[0].length; j++) {
                                fogofwar[i][j]=true;
                        }
                }
  
                
        }
        private void updateFogofWar(){
               Room r = visitedRooms.pop();
                
                for (int i = r.getY1(); i <= r.getY1()+r.getHoehe(); i++) {
                        for (int j = r.getX1(); j <= r.getX1()+r.getBreite(); j++) {
                                fogofwar[i][j]=false;
                        }
                }
                this.fogofwarrepaint=false;
                
                
        }

        public void copyEntitie() {
                entcopy = (LinkedList<NPC>) entities.clone();

        }

        public void doLogic() {
                LinkedList<NPC> toberemoved = new LinkedList<>();
                for (NPC e : entities) {
                        e.doLogic(delta);
                        if (e.isRemovethis()) {
                                toberemoved.add(e);
                        }
                }
                for (NPC e : toberemoved) {
                        if (e instanceof Player) {
                                this.gameover = true;
                        }
                        entities.remove(e);
                        map[e.getY()][e.getX()] = ' ';
                }
                if (isFogofwarrepaint()) {
                        updateFogofWar();
                        
                }
                
                
                notifyObserver(transEnum.entities);
                notifyObserver(map);
                notifyObserver(transEnum.playerstats);
                notifyObserver(transEnum.fogofwar);
        }

        public void moveNPCs() {
                for (NPC e : entities) {
                        e.move();

                }

        }

        public void notifyobs() {
                notifyObserver(map);
                notifyObserver(transEnum.entities);
                notifyObserver(transEnum.playerstats);
                notifyObserver(transEnum.fogofwar);

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

                Main maincopy = null;
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
                delta = (System.nanoTime() - last);
                last = System.nanoTime();
                fps = ((long) 1e9 / delta);
                this.notifyObserver(transEnum.fps);
        }

        public DungeonGenerator getDungeon() {
                return dungeon;
        }

        public void clearObservers() {
                observer.clear();
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

        public boolean[][] getFogofwar() {
                return fogofwar;
        }

        public void setFogofwar(boolean[][] fogofwar) {
                this.fogofwar = fogofwar;
        }

        public boolean isFogofwarrepaint() {
                return fogofwarrepaint;
        }

        public void setFogofwarrepaint(boolean fogofwarrepaint) {
                this.fogofwarrepaint = fogofwarrepaint;
        }

        public Stack<Room> getVisitedRooms() {
                return visitedRooms;
        }

        public void setVisitedRooms(Stack<Room> visitedRooms) {
                this.visitedRooms = visitedRooms;
        }
        
}
