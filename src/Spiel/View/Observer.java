/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.model.MainModel;


public interface Observer {

    enum transEnum{
        entities,playerstats,fps,fogofwar,gameover
    }
    enum sounds{
       enemyhit,playerhit,enemydead,dooropen,levelup,chestopen,walkingon,walkingoff

    }

    void update (transEnum enu,MainModel mm);


    void update (char[][] map);
    void update (sounds s, long delta);

}
