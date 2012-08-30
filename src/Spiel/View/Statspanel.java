/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.model.Entities.Player;
import Spiel.model.MainModel;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

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
    private String damage;
    private String defence;

    private MainModel main;
    private Statsfield healthlabel;
    private Statsfield namelabel;
    private Statsfield manalabel;
    private Statsfield xplabel;
    private Statsfield lvllabel;
    private Statsfield damagelabel;
    private Statsfield defencelabel;
    private JTextPane console;
    public static ImageIcon heart;
    public static ImageIcon tome;
    public static ImageIcon dagger;
    public static ImageIcon shield;
    public static ImageIcon lvlicon;


//TODO Statspanel vereinfachen mit for schleife etc...
    public Statspanel() {
            try {
            heart =     new ImageIcon(ImageIO.read(getClass().getResource("/resources/heart.png")));
            tome =     new ImageIcon(ImageIO.read(getClass().getResource("/resources/tome.png")));
            dagger =     new ImageIcon(ImageIO.read(getClass().getResource("/resources/dagger.png")));
            shield =     new ImageIcon(ImageIO.read(getClass().getResource("/resources/shield.png")));
            lvlicon =     new ImageIcon(ImageIO.read(getClass().getResource("/resources/lvl.png")));


        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }
    health = "0";
    playername ="0";
    mana = "0";
    xp = "0";
    level = "0";
    damage="0";
    defence="0";


    this.setLayout(new BorderLayout(5,5));
    JPanel stats = new JPanel();
    stats.setLayout(new FlowLayout(FlowLayout.CENTER));
    stats.setBackground(Color.black);
    stats.setPreferredSize(new Dimension(200,40));
    healthlabel = new Statsfield(health);
    namelabel = new Statsfield(playername);
    //manalabel = new Statsfield(mana);
    xplabel = new Statsfield(xp);
    lvllabel = new Statsfield(level);
    damagelabel = new Statsfield(damage);
    defencelabel = new Statsfield(defence);

    console= new JTextPane();
    console.setPreferredSize(new Dimension(200,50));
    console.setEditable(false);
    console.setFocusable(false);
    console.setAutoscrolls(true);
    console.setBackground(Color.black);
    console.setForeground(Color.white);
    JScrollPane consolescroller= new JScrollPane(console);
    consolescroller.setPreferredSize(new Dimension(200,50));
    consolescroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    consolescroller.setFocusable(false);
    redirectSystemStreams();

    healthlabel.setIcon(heart);
    xplabel.setIcon(tome);
    damagelabel.setIcon(dagger);
    defencelabel.setIcon(shield);
    lvllabel.setIcon(lvlicon);
    stats.add(healthlabel);
    //stats.add(manalabel);
    stats.add(healthlabel);
    //stats.add(namelabel);
    stats.add(xplabel);
    stats.add(lvllabel);
    stats.add(damagelabel);
    stats.add(defencelabel);
    add(stats,BorderLayout.CENTER);
    add(consolescroller,BorderLayout.NORTH);

    this.setPreferredSize(new Dimension(200,160));

    }

  private void updateTextPane(final String text) {
  SwingUtilities.invokeLater(new Runnable() {
    public void run() {
      Document doc = console.getDocument();
      try {
        doc.insertString(doc.getLength(), text, null);
      } catch (BadLocationException e) {
        throw new RuntimeException(e);
      }
      console.setCaretPosition(doc.getLength() - 1);
    }
  });
}

private void redirectSystemStreams() {
  OutputStream out = new OutputStream() {
    @Override
    public void write(final int b) throws IOException {
      updateTextPane(String.valueOf((char) b));
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
      updateTextPane(new String(b, off, len));
    }

    @Override
    public void write(byte[] b) throws IOException {
      write(b, 0, b.length);
    }
  };

  System.setOut(new PrintStream(out, true));
  //System.setErr(new PrintStream(out, true));
}



    @Override
    public void update(transEnum enu, MainModel mm) {
        if (enu == transEnum.playerstats) {
            main = mm;
            health = "HP: " + String.valueOf(main.getPlayer().getHp());
            playername = "Name: " + String.valueOf(main.getPlayer().getName());
            //mana = "Mana: " + String.valueOf(main.player.getMana());
            xp = "XP: " + String.valueOf(main.getPlayer().getXp());
            level = ": " + String.valueOf(main.getPlayer().getLvl());
            damage = "DMG: " + String.valueOf(main.getPlayer().getDmg());
            defence = "DEF: " + String.valueOf(main.getPlayer().getDefence());

            healthlabel.setText(health);
            namelabel.setText(playername);
            //manalabel.setText(mana);
            xplabel.setText(xp);
            lvllabel.setText(level);
            defencelabel.setText(defence);
            damagelabel.setText(damage);
        }
    }

    @Override
    public void update(char[][] map) {

    }

   @Override
   public void update(sounds s, long delta) {
   }


}
