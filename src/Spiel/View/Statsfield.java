/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author Gamer
 */
public class Statsfield extends JLabel{

  public  Statsfield(String name) {
      super(name);
      this.setForeground(Color.white);
      this.setFont(new Font("Monospaced",Font.BOLD,20));


  }


}
