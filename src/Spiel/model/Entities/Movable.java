/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

/**
 * Interface für alle Beweglichen Objekte
 * @author Lukas
 */
public interface Movable {

public void doLogic(long delta);

public void move();

}
