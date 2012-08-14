package Spiel.model;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.View.Observer;
import Spiel.View.Observer.transEnum;
import Spiel.model.Entities.ChestFactory;
import Spiel.model.Entities.MonsterFactory;
import Spiel.model.Entities.NPC;
import Spiel.model.Entities.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
//TODO Spieler spawnt in Türen

/**
 *
 * @author Lukas
 */
public class MainModel implements Subject, Serializable, Cloneable {

        int breite = 200;
        int hoehe = 200;
        long delta = 0;
        long last = 0;
        long fps = 0;
        /**
         * Hauptspielfeld
         */
        public char[][] map;
        private boolean[][] fogofwar= new boolean[hoehe][breite];

        /**
         * Spieler
         */
        public Player player;
        private LinkedList monsters;
        transient private MonsterFactory monstergenerator;
        private LinkedList chests;
        transient private ChestFactory chestgenerator;
        private DungeonGenerator dungeon;

        /**
         * Liste aller erstellten Objekte im Spiel
         */
        public LinkedList<NPC> entities;

        private Stack<Room> visitedRooms= new Stack<>();
        private static ArrayList<Observer> observer = new ArrayList<>();
        private boolean gameover;
        private boolean fogofwarrepaint=true;



        /**
         * Richtung in die ein NPC gerade schaut
         */
        public enum Richtung {
                LEFT,
                RIGHT,
                UP,
                DOWN;
        }
        
        

        /**
         * Konstuktor des MainModels
         */
        public MainModel() {

                init();


        }
        
        
        
        
        private void init(){
        //Dungeonerstellung
                dungeon = new DungeonGenerator(breite, hoehe, this);
                map = dungeon.getMap();
                entities = new LinkedList<>();
                //Türen Erstellung
                entities.addAll(dungeon.getDoors());
                placeAllNPConMap();        

                //Player Erstellung
                player = new Player(this);
                entities.add(player);
                
                //Fog of War
                initFogofwar();
                
                //Monster Erstellung
                monstergenerator = new MonsterFactory(this);
                monsters = monstergenerator.populateDungeon(dungeon.getRooms());
                entities.addAll(monsters);

                //Truhen Erstellung
                chestgenerator = new ChestFactory(this);
                chests = chestgenerator.populateDungeon(dungeon.getRooms());
                entities.addAll(chests);


                //Objekte auf Map verteilen
                
                
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

        /**
         * Ausführung der Spiellogik für alle Objekte und Funktionen. Bei jedem Durchlauf eines Threads
         */
        public void doSpiellogik() {
                //Liste der Objekte die gelöscht werden
                LinkedList<NPC> toberemoved = new LinkedList<>();
                
                //Durchlauf aller Objekte und ausführung der Spiellogik
                for (NPC e : entities) {
                        e.doLogic(delta);
                        
                        //Markieren zum Löschen
                        if (e.isRemovethis()) {
                                toberemoved.add(e);
                        }
                        
                }
                
                //Löschen der "toten" Objekte
                for (NPC e : toberemoved) {
                        
                        //GAME OVER
                        if (e instanceof Player) {
                                this.gameover = true;
                        }
                        
                        entities.remove(e);
                        map[e.getY()][e.getX()] = ' ';
                }
                
                
                //Neuzeichnen des Fog-of-War wenn Flag auf True gesetzt ist
                if (isFogofwarrepaint()) {
                        updateFogofWar();
                        
                }
                
                //Benachrichtigen aller Observer
                notifyAllObservers();
        }

        /**
         *       Bewegen der Objekte auf der Karte        
         * 
         */
        public void moveNPCs() {
                for (NPC e : entities) {
                        if (e instanceof Player) {
                             if (e.getMovex()==0 && e.getMovey()==0) {
                                  ((Player)e).setWalking(false);
                             } else {
                                  
                                  ((Player)e).setWalking(true);
                             }
                     }
                        e.move();

                }

        }
        
        /**
         * Benachrichtigen aller Observer für alle möglichen Daten 
         */
        public void notifyAllObservers() {
                notifyObserver(map);
                notifyObserver(transEnum.entities);
                notifyObserver(transEnum.playerstats);
                notifyObserver(transEnum.fogofwar);

        }

        /**
         * Verteilen der erstellten NPCs auf der Map
         */
        public void placeAllNPConMap() {
                for (NPC e : entities) {
                        map[e.getY()][e.getX()] = e.getIcon();
                }
                notifyObserver(map);
        }

        /**
         * 
         * @param o Observer
         */
        @Override
        public void addObserver(Observer o) {
                observer.add(o);
        }

        /**
         * 
         * @param o Observer
         */
        @Override
        public void removeObserver(Observer o) {
                observer.remove(o);
        }

        /**
         *  Benachrichtigen der Observer über Änderung eines Bestimmten Objekts
         * @param enu Art des zu übergebenden Objekts
         */
        @Override
        public void notifyObserver(transEnum enu) {
                
                //Kopie von main erstellen
                MainModel maincopy = null;
                try {
                        maincopy = (MainModel) this.clone();
                } catch (CloneNotSupportedException ex) {
                        Logger.getLogger(MainModel.class.getName()).log(Level.SEVERE, null, ex);
                }


                
                for (Observer ob : observer) {
                        ob.update(enu, maincopy);
                }
        }

        /**
         * Benachrichtigung der Observer über Änderung der Map
         * @param map Die Spielkarte
         */
        @Override
        public void notifyObserver(char[][] map) {
                //Kopie der Map erstellen zur Weitergabe an Observer
                char[][] mapcopy = new char[map.length][map[0].length];
                for (int x = 0; x < map.length; x++) {
                        System.arraycopy(map[x], 0, mapcopy[x], 0, map[0].length);
                }
                for (Observer ob : observer) {
                        ob.update(mapcopy);

                }

        }

        /**
         * 
         * @return Liste aller NPCs im Spiel
         */
        public LinkedList<NPC> getEntities() {
                return entities;
        }


        /**
         * 
         * @return Ausgabe des Spielers
         */
        public Player getPlayer() {
                return player;
        }
        
        /**
         *     Berechnung von Werten für die Spiellogik und Animation
         */
        public void computeDelta() {
                delta = (System.nanoTime() - last);
                last = System.nanoTime();
                fps = ((long) 1e9 / delta);
                this.notifyObserver(transEnum.fps);
        }

        /**
         * 
         * @return Ausgabe des generierten Dungeons
         */
        public DungeonGenerator getDungeon() {
                return dungeon;
        }

        /**
         * Löschen der Observer
         */
        public void clearObservers() {
                observer.clear();
        }

        /**
         * 
         * @return Ausgabe der Breite des momentanen Spielfelds
         */
        public int getBreite() {
                return breite;
        }

        /**
         * 
         * @param breite Bestimmen der Breite des Spielfelds
         */
        public void setBreite(int breite) {
                this.breite = breite;
        }

        /**
         * 
         * @return Ausgabe der Höhe des Spielfelds
         */
        public int getHoehe() {
                return hoehe;
        }

        /**
         * 
         * @param hoehe Ausgabe der Höhe des Spielfelds
         */
        public void setHoehe(int hoehe) {
                this.hoehe = hoehe;
        }

        /**
         * 
         * @return Ausgabe der Zeit zwischen dem letzten Thread-Durchlauf und dem jetzigen in nanosekunden
         */
        public long getDelta() {
                return delta;
        }




        /**
         * 
         * @return
         */
        public long getFps() {
                return fps;
        }



        /**
         * 
         * @return Zeitpunkt des letzten Thread-Durchlaufs in Nanosekunden
         */
        public long getLast() {
                return last;
        }





        /**
         * 
         * @return Ein Array welches Festlegt wo er FogofWar noch ist, bzw wo der Spieler noch nicht war.
         */
        public boolean[][] getFogofwar() {
                return fogofwar;
        }



        /**
         * 
         * @return Gibt aus ob der Fog-of-War sich geändert hat
         */
        public boolean isFogofwarrepaint() {
                return fogofwarrepaint;
        }

        /**
         * 
         * @param fogofwarrepaint Legt fest ob Fog-of-War neugezeichnet werden muss
         */
        public void setFogofwarrepaint(boolean fogofwarrepaint) {
                this.fogofwarrepaint = fogofwarrepaint;
        }

        /**
         * 
         * @return Gibt den Stack der besuchten Räume aus.
         */
        public Stack<Room> getVisitedRooms() {
                return visitedRooms;
        }

        public void setLast(long last) {
                this.last = last;
        }

}
