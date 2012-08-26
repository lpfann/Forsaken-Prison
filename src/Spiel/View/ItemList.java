/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import Spiel.model.Entities.Items.Item;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Gamer
 */
public class ItemList extends JList implements KeyListener {
private MainFrame gameframe;
private Item selected;

        public ItemList(MainFrame gameframe) {
                super();
                this.gameframe=gameframe;
                setLayoutOrientation(JList.HORIZONTAL_WRAP);
                setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                setVisibleRowCount(-1);
                setCellRenderer(new ItemListRenderer());
                ListModel listmodel = new DefaultListModel();
                addKeyListener(this);
                requestFocus();

        }

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                        case KeyEvent.VK_I:
                                gameframe.openItemWindow();
                                break;
                        case KeyEvent.VK_E:

                                selected=(Item)this.getSelectedValue();
                                if (selected!=null) {
                                gameframe.getModel().getPlayer().useItem(selected);

                                }
                                break;
                        case KeyEvent.VK_R:
                                gameframe.getModel().getPlayer().usePotion();
                                break;







    }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
}
