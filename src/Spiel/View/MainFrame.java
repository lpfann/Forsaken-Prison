package Spiel.View;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.Controller.Controller;
import Spiel.model.MainModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;

/**
 *
 * @author Lukas
 */
public class MainFrame extends JFrame {

    private Statspanel statusbar;
    private Menu menu;
    private MainModel model;
    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private JLayeredPane lpanel = new JLayeredPane();
    private GameFrame gameframe;
    private Itemwindow itemwindow;
    private Help helpwindow;
    private boolean open = false;

    
    
    public MainFrame(MainModel model) {
        super("Spiel");
        this.model=model;
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(screensize.width / 4, screensize.height / 4);
        this.setPreferredSize(new Dimension(400,300));
        this.setLayout(new BorderLayout(5, 5));
        menu = new Menu(this);
        this.add(lpanel,BorderLayout.CENTER);
        lpanel.setBounds(0,0,this.getPreferredSize().width,this.getPreferredSize().height);
        menu.setBounds(this.getPreferredSize().width/2-menu.getPreferredSize().width/2,0,menu.getPreferredSize().width,menu.getPreferredSize().height);
        menu.setOpaque(true);
        lpanel.add(menu, new Integer(10));
        helpwindow = new Help(this);
        helpwindow.setBounds(this.getPreferredSize().width / 2 - helpwindow.getPreferredSize().width / 2, 0, helpwindow.getPreferredSize().width, helpwindow.getPreferredSize().height);
        helpwindow.setOpaque(true);
        helpwindow.setVisible(false);
        lpanel.add(helpwindow, new Integer(11));
        pack();
        menu.requestFocus();
        setVisible(true);
    }



    public void openGameFrame() {
        gameframe= new GameFrame(model);
        menu.setVisible(false);
        gameframe.setVisible(true);


    }

    public void openGameMenu() {
        
        if (!open) {
            this.menu.setNewGameButtonText("Weiterspielen");
            
            menu.getSaveGameButton().setEnabled(true);
            menu.setVisible(true);
            model.pauseGame();
            menu.requestFocus();
            open=true;
            
        } else {
            menu.setVisible(false);
            gameframe.requestFocus();
            model.resumeGame();
            open=false;
            
            
            
        }





    }

    public GameFrame getGamepanel() {
        return gameframe;
    }



 
        public void openHelp() {
                helpwindow.setVisible(true);




        }

     public Help getHelpwindow() {
          return helpwindow;
     }

     public Itemwindow getItemwindow() {
          return itemwindow;
     }

     public Menu getMenu() {
          return menu;
     }

    public MainModel getModel() {
        return model;
    }






}