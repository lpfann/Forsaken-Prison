package Main;

import Spiel.Controller.Controller;
import Spiel.View.MainFrame;
import Spiel.model.MainModel;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Lukas Pfannschmidt
 */
public class Main {



   /**
    * Initalisierung der drei verschiedenen Bereiche
    */
   public Main (){

       MainModel model=new MainModel();
       MainFrame view = new MainFrame(model);
       Controller controller = new Controller(model, view);

}


    /**
    *
    * @param args
    */
   public static void main(String[] args) {
        new Main();
    }






}
