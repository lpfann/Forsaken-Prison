package Spiel.View;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.Controller.Controller;
import Spiel.model.MainModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
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
    private MainModel model;
    private Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
    private JLayeredPane lpanel = new JLayeredPane();
    private JPanel gamepanel = new JPanel();
    private Itemwindow itemwindow;
    private Help helpwindow;
    private boolean open = false;
    private Controller controller;
    private Sounds sounds;
   private Story storywindow;



    public MainFrame(MainModel model) {
        super("Forsaken Prison");
        this.model = model;


        spielfeld = new Fieldpainter(model.getBreite(), model.getHoehe(), model.getPlayer(), model.getFIELDSIZE());
        statusbar = new Statspanel();

        menu = new Menu(this);
        itemwindow= new Itemwindow(this);
        helpwindow = new Help(this);
        sounds= new Sounds();
        storywindow= new Story(this);
        model.addObserver(itemwindow);
        model.addObserver(sounds);

        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocation(screensize.width / 4, screensize.height / 4);
        this.setPreferredSize(new Dimension(spielfeld.getPreferredSize().width,spielfeld.getPreferredSize().height+statusbar.getPreferredSize().height));
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
        helpwindow.setBounds(this.getPreferredSize().width/2-helpwindow.getPreferredSize().width/2,0,helpwindow.getPreferredSize().width,helpwindow.getPreferredSize().height);
        storywindow.setBounds(this.getPreferredSize().width/2-storywindow.getPreferredSize().width/2,0,storywindow.getPreferredSize().width,storywindow.getPreferredSize().height);

        itemwindow.setOpaque(true);
        storywindow.setOpaque(true);
        menu.setOpaque(true);
        helpwindow.setOpaque(true);
        helpwindow.setVisible(false);
        lpanel.add(gamepanel,new Integer(0));
        lpanel.add(menu, new Integer(10));
        lpanel.add(itemwindow, new Integer(10));
        lpanel.add(helpwindow,new Integer(11));
        lpanel.add(storywindow, new Integer(11));
        pack();
        menu.requestFocus();
        setVisible(true);
    }

    public Statspanel getStatusbar() {
        return statusbar;
    }

    public void openGameFrame() {



        menu.setVisible(false);

        model.addObserver(statusbar);
        model.addObserver(spielfeld);

        gamepanel.addKeyListener(controller.getKeylistener());

        gamepanel.add(spielfeld, BorderLayout.NORTH);
        gamepanel.add(statusbar, BorderLayout.CENTER);
        statusbar.setPreferredSize(new Dimension(spielfeld.getPreferredSize().width, statusbar.getPreferredSize().height));
        controller.resumeGame();
        pack();
        gamepanel.setVisible(true);
        gamepanel.requestFocus();

    }

    public void openGameMenu() {

        if (!open) {
            this.menu.setNewGameButtonText("Weiterspielen");

            menu.getSaveGameButton().setEnabled(true);
            menu.setVisible(true);
            controller.pauseGame();
            menu.requestFocus();
            open=true;

        } else {
            menu.setVisible(false);
            gamepanel.requestFocus();
            controller.resumeGame();
            open=false;



        }





    }

    public JPanel getGamepanel() {
        return gamepanel;
    }


        public void openItemWindow() {
                if (!open) {
                        itemwindow.setVisible(true);
                        controller.pauseGame();
                        itemwindow.focustoItemList();
                        open = true;
                } else {
                        itemwindow.setVisible(false);
                        controller.resumeGame();
                        gamepanel.requestFocus();
                        open = false;
                }




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

     public Fieldpainter getSpielfeld() {
          return spielfeld;
     }

    public MainModel getModel() {
        return model;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void setModel(MainModel model) {
        this.model = model;
    }


    void openStoryMenu(){
       storywindow.setVisible(true);




}

   public Sounds getSounds() {
      return sounds;
   }

}