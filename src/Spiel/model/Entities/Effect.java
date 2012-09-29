/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;
import java.awt.Color;

/**
 *
 * @author Lukas
 */
public class Effect extends NPC {


    private String content;
    private double deletetimer;
    private Color color;
    private double lifetime;
    private int maxy=20;
    private int ytimer;
    public Effect(int x, int y, MainModel main,String cont,Color c,double lifetime){
        super(x, y, ' ', main);
        this.content=cont;
        this.color=c;
        this.lifetime=lifetime;

    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public void doLogic(long delay){
        deletetimer+=delay/1e6;
        if (ytimer<maxy) {
           setY(getY()-1);
           ytimer++;
       }
        if (deletetimer>lifetime) {
            this.setRemovethis(true);
        }

    }

   public Color getColor() {
      return color;
   }

    @Override
    public int getLevel() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
