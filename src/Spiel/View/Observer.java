/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.model.Main;


public interface Observer {

    enum transEnum{
        entities,playerstats,fps,fogofwar
    }

    void update (transEnum enu,Main mm);


    void update (char[][] map);

}
