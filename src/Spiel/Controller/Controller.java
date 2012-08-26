/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.Controller;

import Main.Main;
import Spiel.View.MainFrame;
import Spiel.model.MainModel;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.EventListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lukas
 */
public class Controller implements EventListener{
    private MainModel model;
    private MainFrame view;
    private Keys keylistener;

    public Controller(MainModel model, MainFrame view){
        keylistener = new Keys(this);
        this.model=model;
        this.view=view;
        view.setController(this);

        



    }

public MainModel getMain() {
        return model;
    }

    public void save(){
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream("save.ser");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(model);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    public void load() {
//        pauseThread();
        FileInputStream fileIn;
        try {
            fileIn = new FileInputStream("save.ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            try {
                model = (MainModel) in.readObject();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            in.close();
            model.clearObservers();
            model.addObserver(view.getSpielfeld());
            model.addObserver(view.getStatusbar());

            model.notifyAllObservers();
//            resumeThread();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }




    public MainFrame getMainFr() {
        return view;
    }

    public Keys getKeylistener() {
        return keylistener;
    }





}
