/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.model.Entities;

import Spiel.model.MainModel;

/**
 *
 * @author Lukas
 */
public class Effect extends NPC {


    private String content;
    private double deletetimer;
    public Effect(int x, int y, MainModel main,String cont){
        super(x, y, ' ', main);
        this.content=cont;
        
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
        setY(getY()-1);
        if (deletetimer>300) {
            this.setRemovethis(true);
        }
        
    }
    
}
