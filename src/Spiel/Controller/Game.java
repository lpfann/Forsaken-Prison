package Spiel.Controller;

import Spiel.View.Fieldpainter;
import Spiel.View.MainFrame;
import Spiel.model.MainModel;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Lukas Pfannschmidt
 */
public class Game implements Runnable {
    
private Fieldpainter painter;
private static MainModel main;
private MainFrame mainFr;
private Thread thread;
boolean wait=true;
final int MAXFPS=60;
final int GAME_TICK = 1000 / MAXFPS;




public Game (){
        main=new MainModel();
        init();
        painter = new Fieldpainter(main.getBreite(),main.getHoehe(),main.getPlayer(),main.getFIELDSIZE());
        main.addObserver(painter);
        mainFr = new MainFrame(this);
        main.notifyAllObservers();
        thread = new Thread(this);
        thread.start();
}

    private void init(){
        main.setLast(System.nanoTime());
        

        
    }

    public static void main(String[] args) {
        new Game();
    }

   

    public MainModel getMain() {
        return main;
    }
    
    public void save(){
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("save.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(main);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
                                    

        
    }
    
    public void load() {
        pauseThread();
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream("save.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            try {
                main = (MainModel) in.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            in.close();
            main.clearObservers();
            main.addObserver(painter);
            main.addObserver(mainFr.getStatusbar());
            main.notifyAllObservers();
            resumeThread();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @Override
    public void run() {
        
        
        
        while  (true) {

            
                main.computeDelta();
                
                main.doSpiellogik();
                main.moveNPCs();

                painter.repaint();
                synchronized (this) {
                                    
                    while (wait) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                    
                   
            }

            
                if (main.getDelta() / 1e6 < GAME_TICK) {

                    try {
                        Thread.sleep((long) (GAME_TICK - (main.getDelta() / 1e6)));
                        
                    } catch (InterruptedException e) {
                    }
                } else {
                    Thread.yield();
                }
            }
    }
    }

    public MainFrame getMainFr() {
        return mainFr;
    }

    public Fieldpainter getPainter() {
        return painter;
    }
    public synchronized void pauseThread(){
            wait=true;
        
        
    }
    public synchronized void resumeThread(){
            wait=false;
            notifyAll();
        
        
    }
    
}
