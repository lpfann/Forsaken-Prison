package Spiel.View;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
import Spiel.Controller.Game;
import Spiel.View.MainFrame;
import Spiel.model.Main.Richtung;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Gamer
 */
public class Keys implements KeyListener {

    private Game game;

    public Keys(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch (ke.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                //game.getMain().player.movechar(Richtung.LEFT);
                game.getMain().player.setMovex(-1);
                break;
            case KeyEvent.VK_RIGHT:
                //game.getMain().player.movechar(Richtung.RIGHT);
                game.getMain().player.setMovex(+1);
                break;
            case KeyEvent.VK_UP:
                //game.getMain().player.movechar(Richtung.UP);
                game.getMain().player.setMovey(-1);
                break;
            case KeyEvent.VK_DOWN:
//                game.getMain().player.movechar(Richtung.DOWN);
                game.getMain().player.setMovey(+1);
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
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
