package Main;

import Spiel.Controller.Controller;
import Spiel.View.Fieldpainter;
import Spiel.View.MainFrame;
import Spiel.model.MainModel;
import java.awt.event.KeyListener;
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
public class Main {
    


public Main (){

       MainModel model=new MainModel();
       MainFrame view = new MainFrame(model);
       Controller controller = new Controller(model, view);

}


    public static void main(String[] args) {
        new Main();
    }



   

    
}
