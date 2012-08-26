package Spiel.Controller;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Main.Main;
import Spiel.View.MainFrame;
import Spiel.model.MainModel.Richtung;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Gamer
 */
public class Keys implements KeyListener {

    private Controller game;

    public Keys(Controller game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
        public void keyPressed(KeyEvent ke) {
                switch (ke.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                                game.getMain().player.setLeft(true);
                                break;
                        case KeyEvent.VK_RIGHT:
                                game.getMain().player.setRight(true);
                                break;
                        case KeyEvent.VK_UP:
                                game.getMain().player.setUp(true);
                                break;
                        case KeyEvent.VK_DOWN:
                                game.getMain().player.setDown(true);
                                break;
                        case KeyEvent.VK_Q:
                                System.exit(0);
                                break;
                        case KeyEvent.VK_SPACE:
                                game.getMain().player.attackmonster();
                                break;
                        case KeyEvent.VK_E:
                                game.getMain().player.action();
                                break;
                        case KeyEvent.VK_F5:
                                game.save();
                                break;
                        case KeyEvent.VK_F9:
                                game.load();
                                break;
                        case KeyEvent.VK_ESCAPE:
                                game.getMainFr().openGameMenu();
                                break;
                        case KeyEvent.VK_I:
                                game.getMainFr().openItemWindow();
                                break;
                        case KeyEvent.VK_R:
                                game.getMain().player.usePotion();
                                break;
                        case KeyEvent.VK_L:
                                game.getMain().player.debugPrintObjectinFront();
                                break;
                }
        }

    @Override
    public void keyReleased(KeyEvent ke) {
                      switch (ke.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                                game.getMain().player.setLeft(false);
                                break;
                        case KeyEvent.VK_RIGHT:
                                game.getMain().player.setRight(false);
                                break;
                        case KeyEvent.VK_UP:
                                game.getMain().player.setUp(false);
                                break;
                        case KeyEvent.VK_DOWN:
                                game.getMain().player.setDown(false);
                                break;   
                      }
    }
}
