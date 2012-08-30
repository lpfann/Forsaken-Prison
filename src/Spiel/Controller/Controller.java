/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.Controller;

import Main.Main;
import Spiel.View.MainFrame;
import Spiel.View.Observer;
import Spiel.model.MainModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas
 */
public class Controller implements EventListener,Runnable{
    private MainModel model;
    private MainFrame view;
    private Keys keylistener;
    private Thread thread;

    /**
    *
    * @param model Die Spiellogik
    * @param view Die Ausgabe
    */
   public Controller(MainModel model, MainFrame view){
        keylistener = new Keys(this);
        this.model=model;
        this.view=view;
        view.setController(this);
        thread = new Thread(this);
        thread.start();





    }

   /**
    *
    * @return Main-Model
    */
   public MainModel getMain() {
        return model;
    }

    /**
    * Speichern des Models durch Serialiserung
    */
   public void save(){
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("save.ser");
          try (ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
             out.writeObject(model);
          }
        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    /**
    * Laden des Models aus Speicherstand.
    */
   public void load() {
        pauseGame();
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream("save.ser");
          try (ObjectInputStream in = new ObjectInputStream(fileIn)) {
             try {
                model = (MainModel) in.readObject();
             } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
             }
          }
            MainModel.dungeonrepaint=true;
            MainModel.fogofwarrepaint=true;
            model.setObserver(new ArrayList<Observer>());
            view.setModel(model);
            model.addObserver(view.getSpielfeld());
            model.addObserver(view.getStatusbar());
            model.addObserver(view.getItemwindow());
            model.addObserver(view.getSounds());


            model.notifyAllObservers();

            resumeGame();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    /**
    *
    * @return Gibt die View zurück
    */
   public MainFrame getMainFr() {
        return view;
    }

    /**
    *
    * @return gibt den Keylistener zurück
    */
   public Keys getKeylistener() {
        return keylistener;
    }



    /**
    * Run-Methode der GameLoop
    * Führt Spiellogik aus. Kann pausiert werden.
    */
 @Override
    public void run() {



        while  (true) {



                model.computeDelta();
                model.doSpiellogik();
                model.moveNPCs();


                synchronized (this) {

                    while (model.isWait()) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }


            }


                if (model.getDelta() / 1e6 < model.GAME_TICK) {

                    try {
                        Thread.sleep((long) (model.GAME_TICK - (model.getDelta() / 1e6)));

                    } catch (InterruptedException e) {
                    }
                } else {
                    Thread.yield();
                }
            }
    }
    }


    /**
    * Gameloop pausieren
    */
   public synchronized void pauseGame() {
        model.setWait(true);


    }

    /**
    * Gameloop weiterlaufen lassen
    */
   public synchronized void resumeGame() {
        model.setWait(false);
        notifyAll();



    }
}
