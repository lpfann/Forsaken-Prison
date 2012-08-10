package Spiel.View;



import Spiel.Controller.Game;
import Spiel.View.Observer.transEnum;
import Spiel.model.Entities.Door;
import Spiel.model.Entities.NPC;
import Spiel.model.Entities.Player;
import Spiel.model.Main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fieldpainter extends JPanel implements Observer {

    private Image offscreenImage;
    private Graphics offscreenGraph;
    private BufferedImage ground;
    private BufferedImage wall;
    private int flag;
    private int viewportx;
    private int viewporty;
    private int fps;
    private Player player;
    private char[][] map;
    private LinkedList<NPC> entities= new LinkedList();
    public static final int FIELDSIZE=30;
    public static int VIEWPORTWIDTH=800;
    public static int VIEWPORTHEIGTH=600;
    public Fieldpainter(int breite, int hoehe,Player p) {
        flag=-1;

        this.setPreferredSize(new Dimension(FIELDSIZE*breite+10, FIELDSIZE*hoehe));
        this.setBackground(Color.BLACK);
        try {
            //liest ein bild ein
            ground = ImageIO.read(getClass().getResource("/resources/groundDun.png"));
            wall = ImageIO.read(getClass().getResource("/resources/HBlockDun.png"));


        } catch (IllegalArgumentException | IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error", 0);
        }

}

    public void setFlag(int flag) {
        this.flag = flag;
    }


    @Override
    protected void paintComponent(Graphics g) {
        
        super.paintComponent(g);
        //Dungeon Zeichnen
        paintDungeon(g);



        //Teil der jedes mal gezeichnet wird
        //

        //Zeichnen der Entities
        if (!entities.isEmpty()) {
        LinkedList<NPC> entcopy = (LinkedList<NPC>) entities.clone();
            for (ListIterator<NPC> it = entcopy.listIterator() ; it.hasNext();) {
                    NPC e = it.next();
                        try {
                            e.drawEntitie(g,FIELDSIZE);
                        } catch ( ConcurrentModificationException i) {
                            i.printStackTrace();
                        }

                
            }
        }
        
        
        //FPS
        g.setColor(Color.white);
        g.setFont(new Font("Monospaced", Font.BOLD, 20));
        g.drawString("FPS: " + fps, 20, 15);
        
    }
    
    private void paintDungeon(Graphics g) {
            if (flag == -1) {
            flag = 0;
            //erstellt ein leeres bild.
            offscreenImage = createImage(map[0].length * FIELDSIZE, map.length * FIELDSIZE);
            offscreenGraph = offscreenImage.getGraphics();
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    if (map[y][x] == '*') {
                        offscreenGraph.drawImage(wall, x * FIELDSIZE, y * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                    } else {
                        offscreenGraph.drawImage(ground, x * FIELDSIZE, y * FIELDSIZE, FIELDSIZE, FIELDSIZE, this);
                    }
                }
            }
        }
        g.drawImage(offscreenImage, 0, 0, map[0].length * FIELDSIZE, map.length * FIELDSIZE, this);
        
    }
    

    @Override
    public void update(transEnum enu, Main mm) {
          if (enu==transEnum.entities) {
               entities=mm.getEntities();
          } else if (enu==transEnum.fps) {
              this.fps=(int)mm.getFps();
          } else if (enu==transEnum.playerstats) {
              this.player=mm.getPlayer();
              
          }
    }


    @Override
    public void update(char[][] map) {
        this.map=map;

    }





}
