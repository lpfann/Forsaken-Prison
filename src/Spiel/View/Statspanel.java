/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.model.Entities.Player;
import Spiel.model.Main;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;

/**
 *
 * @author Gamer
 */
public class Statspanel extends JPanel implements Observer{
private Player player;
private String health;
    private String mana;
    private String xp;
    private String level;
    private String playername;
    private Main main;
    private Statsfield healthlabel;
    private Statsfield namelabel;
    private Statsfield manalabel;
    private Statsfield xplabel;
    private Statsfield lvllabel;

    public Statspanel() {

    health = "0";
    playername ="0";
    mana = "0";
    xp = "0";
    level = "0";
    
    this.setLayout(new FlowLayout());
    healthlabel = new Statsfield(health);
    namelabel = new Statsfield(playername);
    manalabel = new Statsfield(mana);
    xplabel = new Statsfield(xp);
    lvllabel = new Statsfield(level);


    
    add(healthlabel);
    add(manalabel);
    add(namelabel);
    add(xplabel);
    add(lvllabel);

    this.setPreferredSize(new Dimension(200,80));

    }

    @Override
    public void update(transEnum enu, Main mm) {
        if (enu == transEnum.playerstats) {
            main = mm;
            health = "HP: " + String.valueOf(main.player.getHp());
            playername = "Name: " + String.valueOf(main.player.getName());
            mana = "Mana: " + String.valueOf(main.player.getMana());
            xp = "Erfahrung: " + String.valueOf(main.player.getXp());
            level = "Level: " + String.valueOf(main.player.getLvl());
            healthlabel.setText(health);
            namelabel.setText(playername);
            manalabel.setText(mana);
            xplabel.setText(xp);
            lvllabel.setText(level);
        }
    }

    @Override
    public void update(char[][] map) {
        
    }


}
