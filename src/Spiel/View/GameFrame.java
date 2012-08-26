/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.Controller.Keys;
import Spiel.model.MainModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Lukas
 */
public class GameFrame extends JPanel {
    private final Statspanel statusbar;
    private final Itemwindow itemwindow;
    private Fieldpainter spielfeld;
    private JLayeredPane lpanel;
    private MainModel main;
    private boolean open = false;



    public GameFrame(MainModel main){


        this.main=main;
        spielfeld = new Fieldpainter(main.getBreite(), main.getHoehe(), main.getPlayer(), main.getFIELDSIZE());
        statusbar = new Statspanel();
        itemwindow= new Itemwindow(this);
        main.addObserver(spielfeld);
        main.addObserver(statusbar);
        main.addObserver(itemwindow);
        lpanel= new JLayeredPane();

        setBackground(getParent().getBackground());
        statusbar.setBackground(getParent().getBackground());
        setBounds(0,0,this.getPreferredSize().width,this.getPreferredSize().height);
        setLayout(new BorderLayout(5,5));
        setDoubleBuffered(true);
        itemwindow.setBounds(this.getPreferredSize().width/2-itemwindow.getPreferredSize().width/2,0,itemwindow.getPreferredSize().width,itemwindow.getPreferredSize().height);
        itemwindow.setOpaque(true);


        lpanel.add(this,new Integer(0));
        lpanel.add(itemwindow, new Integer(10));
        


        
        main.addObserver(statusbar);


        add(spielfeld, BorderLayout.NORTH);
        add(statusbar, BorderLayout.CENTER);
        statusbar.setPreferredSize(new Dimension(spielfeld.getPreferredSize().width, statusbar.getPreferredSize().height));
        main.resumeGame();
        
        setVisible(true);
        requestFocus();
    }



       public void openItemWindow() {
                if (!open) {
                        itemwindow.setVisible(true);
                        main.pauseGame();
                        itemwindow.focustoItemList();
                        open = true;
                } else {
                        itemwindow.setVisible(false);
                        main.resumeGame();
                        requestFocus();
                        open = false;
                }




        }

    public MainModel getMain() {
        return main;
    }
       public void insertKeylistener(KeyListener kl){
           this.addKeyListener(kl);



       }

    public Statspanel getStatusbar() {
        return statusbar;
    }

    public Fieldpainter getSpielfeld() {
        return spielfeld;
    }

}
