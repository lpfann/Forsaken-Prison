package Spiel.View;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.Controller.Game;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 *
 * @author Lukas
 */
public class MainFrame extends JFrame {

    private Fieldpainter spielfeld;
    private Statspanel statusbar;
    private Menu menu;
    private Game game;
    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private JLayeredPane lpanel = new JLayeredPane();
    private JPanel gamepanel = new JPanel();
    private Itemwindow itemwindow;
    private boolean open = false;

    
    
    public MainFrame(Game game) {
        super("Spiel");
        this.game = game;
        statusbar = new Statspanel();
        menu = new Menu(this);
        itemwindow= new Itemwindow(game);
        game.getMain().addObserver(itemwindow);
        
        //this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(screensize.width / 4, screensize.height / 4);
        this.setPreferredSize(new Dimension(game.getPainter().getPreferredSize().width,game.getPainter().getPreferredSize().height+statusbar.getPreferredSize().height));
        this.setLayout(new BorderLayout(5, 5));
        gamepanel.setBackground(menu.getBackground());
        statusbar.setBackground(menu.getBackground());
        this.add(lpanel,BorderLayout.CENTER);
        lpanel.setBounds(0,0,this.getPreferredSize().width,this.getPreferredSize().height);
        gamepanel.setBounds(0,0,this.getPreferredSize().width,this.getPreferredSize().height);
        gamepanel.setLayout(new BorderLayout(5,5));
        gamepanel.setDoubleBuffered(true);
        menu.setBounds(this.getPreferredSize().width/2-menu.getPreferredSize().width/2,0,menu.getPreferredSize().width,menu.getPreferredSize().height);
        itemwindow.setBounds(this.getPreferredSize().width/2-itemwindow.getPreferredSize().width/2,0,itemwindow.getPreferredSize().width,itemwindow.getPreferredSize().height);
        
        itemwindow.setOpaque(true);
        menu.setOpaque(true);
        lpanel.add(gamepanel,new Integer(0));
        lpanel.add(menu, new Integer(10));
        lpanel.add(itemwindow, new Integer(10));
        pack();
        menu.requestFocus();
        setVisible(true);
    }

    public Observer getStatusbar() {
        return (Observer) statusbar;
    }

    public void openGameFrame() {
        Fieldpainter fieldPanel = game.getPainter();


        menu.setVisible(false);
        spielfeld = fieldPanel;
        game.getMain().addObserver(statusbar);

        gamepanel.addKeyListener(new Keys(game));

        gamepanel.add(spielfeld, BorderLayout.NORTH);
        gamepanel.add(statusbar, BorderLayout.CENTER);
        statusbar.setPreferredSize(new Dimension(spielfeld.getPreferredSize().width, statusbar.getPreferredSize().height));
        game.resumeThread();
        pack();
        gamepanel.setVisible(true);
        gamepanel.requestFocus();

    }

    public void openGameMenu() {
        
        if (!open) {
            this.menu.setNewGameButtonText("Weiterspielen");
            
            menu.getSaveGameButton().setEnabled(true);
            menu.setVisible(true);
            game.pauseThread();
            menu.requestFocus();
            open=true;
            
        } else {
            menu.setVisible(false);
            gamepanel.requestFocus();
            game.resumeThread();
            open=false;
            
            
            
        }





    }

    public JPanel getGamepanel() {
        return gamepanel;
    }

    public Game getGame() {
        return game;
    }

void openItemWindow() {
        if (!open) {
            itemwindow.setVisible(true);
            game.pauseThread();
            itemwindow.focustoItemList();
            open=true;
        } else {
                itemwindow.setVisible(false);
                game.resumeThread();
                gamepanel.requestFocus();
                open=false;
        }
    
    
    
    
}




}