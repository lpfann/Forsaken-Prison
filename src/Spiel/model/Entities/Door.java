/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.View.Observer;
import Spiel.model.MainModel;
import Spiel.model.Room;

/**
 *
 * @author Gamer
 */
public class Door extends NPC implements Usable {

    private boolean open;
    public Door(int x, int y,MainModel main){
        super(x, y, 'D', main);
        this.open=false;

    }

    public boolean getOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
    public void opencloseDoorSwitch(){
        if (open) {
            this.open=false;
            setIcon('D');
        } else {
            this.open=true;
            setIcon(' ');
        }
        getMain().notifyObserver(Observer.sounds.dooropen);
    }
         @Override
     public void move() {

         }

    @Override
    public void use(Player p) {

          opencloseDoorSwitch();
          changeMapforObject();
          Room r = findRoomLocationatXY(p.fieldinFront(2)[0],p.fieldinFront(2)[1]);

          p.getMain().getVisitedRooms().add(r);
          MainModel.fogofwarrepaint=true;
          p.getMain().updateFogofWar();

    }

    @Override
    public int getLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
