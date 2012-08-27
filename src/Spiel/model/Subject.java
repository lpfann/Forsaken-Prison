/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model;

import Spiel.View.Observer;
import Spiel.View.Observer.sounds;
import Spiel.View.Observer.transEnum;


public interface Subject {

    void addObserver(Observer o);

    void removeObserver(Observer o);

    void notifyObserver(transEnum enu);

    void notifyObserver(char[][] map);
    
    void notifyObserver(sounds s);
}
