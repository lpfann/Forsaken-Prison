package Spiel.model;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.View.Observer;
import Spiel.View.Observer.transEnum;
import Spiel.model.Entities.Effect;
import Spiel.model.Entities.NPC;
import Spiel.model.Entities.Player;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas
 */
public class MainModel implements Subject, Serializable, Cloneable {

    int breite = 100;
    int hoehe = 100;
    long delta = 0;
    long last = 0;
    long fps = 0;
    private final int FIELDSIZE = 40;
    public char[][] map;
    private boolean[][] fogofwar = new boolean[hoehe][breite];
    private Player player;
    private DungeonGenerator dungeon;
    public LinkedList<NPC> entities = new LinkedList<>();
    public LinkedList<Effect> effects = new LinkedList<>();
    private Stack<Room> visitedRooms = new Stack<>();
    private transient ArrayList<Observer> observer = new ArrayList<>();
    private boolean gameover;
    public static boolean fogofwarrepaint = true;
    public static boolean dungeonrepaint = true;
    private int currentDungeonLevel = 1;
    private transient Thread thread;
    boolean wait = true;
    public final int MAXFPS = 60;
    public final int GAME_TICK = 1000 / MAXFPS;



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
         * Konstruktor des MainModels
         */
        public MainModel() {

                init();


        }




        private void init(){
        //Dungeonerstellung
                dungeon = new DungeonGenerator(breite, hoehe, this);
                map = dungeon.getMap();
                dungeon.addEntities();

                entities.addAll(dungeon.getEntitiesinLevel());

                //Player Erstellung
                player = new Player(this);
                entities.add(player);

                //Fog of War
                initFogofwar();
                setLast(System.nanoTime());





        }





 public void changeLevel(){
           currentDungeonLevel++;
           dungeon = new DungeonGenerator(breite, hoehe, this);
           map = dungeon.getMap();
           entities.clear();
           visitedRooms.clear();
           dungeon.addEntities();
           entities.addAll(dungeon.getEntitiesinLevel());
           player.setstartposition(2, 2, breite-2, hoehe-2);
           entities.add(player);
           visitedRooms.add(player.findRoomLocation());
           initFogofwar();
           updateFogofWar();
           this.setFogofwarrepaint(true);
           dungeonrepaint=true;
           notifyAllObservers();



        }



        private void initFogofwar() {
                for (int i = 0; i < fogofwar.length; i++) {
                        for (int j = 0; j < fogofwar[0].length; j++) {
                                fogofwar[i][j]=true;
                        }
                }


        }
    public void updateFogofWar() {
        while ( !visitedRooms.isEmpty()){

            Room r = visitedRooms.pop();
            for (int i = r.getY1(); i <= r.getY1() + r.getHoehe(); i++) {
                for (int j = r.getX1(); j <= r.getX1() + r.getBreite(); j++) {
                    fogofwar[i][j] = false;
                }
            }
        }









    }

        /**
         * Ausführung der Spiellogik für alle Objekte und Funktionen. Bei jedem Durchlauf eines Threads
         */
        public void doSpiellogik() {
            if (!gameover) {
                LinkedList<NPC> toberemoved = new LinkedList<>(); //Liste der Objekte die gelöscht werden
                for (NPC e : entities) { //Durchlauf aller Objekte und ausführung der Spiellogik
                        e.doLogic(delta);
                        //Markieren zum Löschen
                        if (e.isRemovethis()) {
                                toberemoved.add(e);
                        }
                }
                entities.addAll(effects);
                effects.clear();
                for (NPC e : toberemoved) {  //Löschen der "toten" Objekte
                        //GAME OVER
                        if (e instanceof Player) {
                                this.gameover = true;
                        }

                       entities.remove(e);
                       if (e instanceof Effect) {
                       } else {

                           map[e.getY() / FIELDSIZE][e.getX() / FIELDSIZE] = ' ';
                       }
                   }

                //Neuzeichnen des Fog-of-War wenn Flag auf True gesetzt ist
                if (fogofwarrepaint) {
                            updateFogofWar();

                }
                //Benachrichtigen aller Observer
                if (!observer.isEmpty()) {
                    notifyAllObservers();
                }
               }
        }

        /**
         *       Bewegen der Objekte auf der Karte
         *
         */
        public void moveNPCs() {
                for (NPC e : entities) {

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
                        map[e.getY()/FIELDSIZE][e.getX()/FIELDSIZE] = e.getIcon();
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

     public boolean isGameover() {
          return gameover;
     }

     public void setGameover(boolean gameover) {
          this.gameover = gameover;
     }

     public int getFIELDSIZE() {
          return FIELDSIZE;
     }

    public int getCurrentDungeonLevel() {
        return currentDungeonLevel;
    }

    public void setCurrentDungeonLevel(int currentDungeonLevel) {
        this.currentDungeonLevel = currentDungeonLevel;
    }


    public boolean isDungeonrepaint() {
        return dungeonrepaint;
    }


    public void setDungeonrepaint(boolean dungeonrepaint) {
        this.dungeonrepaint = dungeonrepaint;
    }

    public boolean isWait() {
        return wait;
    }


    public Thread getThread() {
        return thread;
    }


    public ArrayList<Observer> getObserver() {
        return observer;
    }

    public void setObserver(ArrayList<Observer> observer) {
        this.observer = observer;
    }

    public void setWait(boolean wait) {
        this.wait = wait;
    }

}
