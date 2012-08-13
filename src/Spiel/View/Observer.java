/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.model.MainModel;


public interface Observer {

    enum transEnum{
        entities,playerstats,fps,fogofwar
    }

    void update (transEnum enu,MainModel mm);


    void update (char[][] map);

}
