package Spiel.Controller;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
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
                                game.getMain().getPlayer().setLeft(true);
                                break;
                        case KeyEvent.VK_RIGHT:
                                game.getMain().getPlayer().setRight(true);
                                break;
                        case KeyEvent.VK_UP:
                                game.getMain().getPlayer().setUp(true);
                                break;
                        case KeyEvent.VK_DOWN:
                                game.getMain().getPlayer().setDown(true);
                                break;
                        case KeyEvent.VK_Q:
                                System.exit(0);
                                break;
                        case KeyEvent.VK_SPACE:
                                game.getMain().getPlayer().attackmonster();
                                break;
                        case KeyEvent.VK_E:
                                game.getMain().getPlayer().action();
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
                                game.getMain().getPlayer().usePotion();
                                break;
                        case KeyEvent.VK_L:
                                game.getMain().getPlayer().debugPrintObjectinFront();
                                break;
                }
        }

    @Override
    public void keyReleased(KeyEvent ke) {
                      switch (ke.getKeyCode()) {
                        case KeyEvent.VK_LEFT:
                                game.getMain().getPlayer().setLeft(false);
                                break;
                        case KeyEvent.VK_RIGHT:
                                game.getMain().getPlayer().setRight(false);
                                break;
                        case KeyEvent.VK_UP:
                                game.getMain().getPlayer().setUp(false);
                                break;
                        case KeyEvent.VK_DOWN:
                                game.getMain().getPlayer().setDown(false);
                                break;
                      }
    }
}
