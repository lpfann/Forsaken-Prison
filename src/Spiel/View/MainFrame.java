package Spiel.View;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.Controller.Game;
import Spiel.model.Keys;
import java.awt.BorderLayout;
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
    private boolean open = false;

    
    
    public MainFrame(Game game) {
        super("Spiel");
        this.game = game;
        statusbar = new Statspanel();
        menu = new Menu(this);
        
        //this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(screensize.width / 4, screensize.height / 4);
        this.setPreferredSize(new Dimension(game.getPainter().getPreferredSize().width,game.getPainter().getPreferredSize().height+statusbar.getPreferredSize().height));
        this.setLayout(new BorderLayout(5, 5));
        
        
        this.add(lpanel,BorderLayout.CENTER);
        lpanel.setBounds(0,0,this.getPreferredSize().width,this.getPreferredSize().height);
        gamepanel.setBounds(0,0,this.getPreferredSize().width,this.getPreferredSize().height);
        //panel.setBounds(0,0,600,400);
        gamepanel.setLayout(new BorderLayout(5,5));
        
        menu.setBounds(this.getPreferredSize().width/2-menu.getPreferredSize().width/2,0,menu.getPreferredSize().width,menu.getPreferredSize().height);
       
        menu.setOpaque(true);
        lpanel.add(gamepanel,new Integer(0));
        lpanel.add(menu, new Integer(10));
        
        pack();
        this.requestFocus();
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
        
        game.getMain().notifyobs();
        pack();
        gamepanel.setVisible(true);
        gamepanel.requestFocus();

    }

    public void openGameMenu() {
        
        if (!open) {
            this.menu.setNewGameButtonText("Continue");
            
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
    
    
    
    
}
