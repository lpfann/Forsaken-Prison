package Spiel.model;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.Serializable;

/**
 *
 * @author Gamer
 */
public class Room implements Serializable {

    private int x1,y1,breite,hoehe,doorx,doory;
    private Room lchild;
    private Room rchild;
    private Room parent;


    public Room(Room lc, Room rc) {
        this.lchild = lc;
        this.rchild = rc;

    }


    private void split(Room room, boolean vert) {
        int nbreite = Spiel.model.Utilites.randomizer(3, breite - 3);
        int nhoehe = Spiel.model.Utilites.randomizer(3, hoehe - 3);

        if (vert) {
            //Verschiebung der Wand um +1 damit Türen nicht verdeckt werden
            if (x1 + nbreite == doorx) {
                lchild = new Room(x1, y1, nbreite, hoehe);
                rchild = new Room(x1 + nbreite + 1, y1, breite - nbreite - 1, hoehe);

            } else {
                lchild = new Room(x1, y1, nbreite, hoehe);
                rchild = new Room(x1 + nbreite, y1, breite - nbreite, hoehe);
            }
            
            int randomdoor= Spiel.model.Utilites.randomizer(1, lchild.hoehe - 1);
            lchild.doorx = lchild.x1 + lchild.breite;
            lchild.doory = lchild.y1 + randomdoor;
            lchild.parent = this;

            
            rchild.doorx = rchild.x1;
            rchild.doory = rchild.y1 + randomdoor;
            rchild.parent = this;
        } else {
            //Verschiebung der Wand um +1 damit Türen nicht verdeckt werden
            if (y1 + nhoehe == doory) {
                lchild = new Room(x1, y1, breite, nhoehe);
                rchild = new Room(x1, y1 + nhoehe + 1, breite, hoehe - nhoehe - 1);

            } else {
                lchild = new Room(x1, y1, breite, nhoehe);
                rchild = new Room(x1, y1 + nhoehe, breite, hoehe - nhoehe);
            }
            int randomdoor= Spiel.model.Utilites.randomizer(1, lchild.breite - 1);
            lchild.doorx = lchild.x1 + randomdoor;
            lchild.doory = lchild.y1 + lchild.hoehe;
            lchild.parent = this;
            rchild.doorx = rchild.x1 + randomdoor;
            rchild.doory = rchild.y1;
            rchild.parent = this;
        }

    }

    public Room(int x1, int y1, int breite, int hoehe) {
        this.x1 = x1;
        this.y1 = y1;
        this.breite = breite;
        this.hoehe = hoehe;


        if (breite > 8 && hoehe > 8) {
            if (breite/hoehe>1) {
            split(this,true);
            } else {
            split(this,false);
                
            }

        } else {

            DungeonGenerator.tree.add(this);
        }
        
    }

    public int getDoorx() {
        return doorx;
    }

    public void setDoorx(int doorx) {
        this.doorx = doorx;
    }

    public int getDoory() {
        return doory;
    }

    public void setDoory(int doory) {
        this.doory = doory;
    }

    public int getX1() {
        return x1;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public int getBreite() {
        return breite;
    }

    public void setBreite(int breite) {
        this.breite = breite;
    }

    public int getHoehe() {
        return hoehe;
    }

    public void setHoehe(int hoehe) {
        this.hoehe = hoehe;
    }

    public int getY1() {
        return y1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setLChild(Room myNode) {
        lchild = myNode;
    }

    public void setRChild(Room myNode) {
        rchild = myNode;
    }

    public Room getlchild() {
        return lchild;
    }

    public Room getrchild() {
        return rchild;
    }
    public Room getotherchild(Room r) {
        if (r==lchild) {
        return rchild;
                       
        } else 
        return lchild;
    }
        public Room getParent() {
        return parent;
    }

    public void setParent(Room parent) {
        this.parent = parent;
    }

}