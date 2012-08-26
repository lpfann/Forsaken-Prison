/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Spiel.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ButtonModel;
import javax.swing.JButton;

/**
 *
 * @author lukas
 */
public class Menu extends javax.swing.JPanel implements KeyListener {
MainFrame mainfr;
    public Menu(MainFrame mainfr) {
        initComponents();
        this.mainfr=mainfr;
        addKeyListener(this);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        newGameButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        helpButton = new javax.swing.JButton();
        saveGameButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(51, 51, 51));
        setForeground(new java.awt.Color(255, 255, 255));

        newGameButton.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        newGameButton.setForeground(new java.awt.Color(255, 255, 255));
        newGameButton.setText("Neues Spiel");
        newGameButton.setAutoscrolls(true);
        newGameButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonGroup1.add(newGameButton);
        newGameButton.setContentAreaFilled(false);
        newGameButton.setSelected(true);
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newGameButtonActionPerformed(evt);
            }
        });
        newGameButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newGameButtonKeyPressed(evt);
            }
        });

        loadButton.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        loadButton.setForeground(new java.awt.Color(255, 255, 255));
        loadButton.setText("Laden");
        loadButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonGroup1.add(loadButton);
        loadButton.setContentAreaFilled(false);
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        helpButton.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        helpButton.setForeground(new java.awt.Color(255, 255, 255));
        helpButton.setText("Hilfe");
        helpButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonGroup1.add(helpButton);
        helpButton.setContentAreaFilled(false);
        helpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpButtonActionPerformed(evt);
            }
        });

        saveGameButton.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        saveGameButton.setForeground(new java.awt.Color(255, 255, 255));
        saveGameButton.setText("Speichern");
        saveGameButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonGroup1.add(saveGameButton);
        saveGameButton.setContentAreaFilled(false);
        saveGameButton.setEnabled(false);
        saveGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveGameButtonActionPerformed(evt);
            }
        });

        exitButton.setFont(new java.awt.Font("Georgia", 1, 18)); // NOI18N
        exitButton.setForeground(new java.awt.Color(255, 255, 255));
        exitButton.setText("Exit");
        exitButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        buttonGroup1.add(exitButton);
        exitButton.setContentAreaFilled(false);
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(newGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loadButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(saveGameButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(helpButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exitButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(newGameButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveGameButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(helpButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exitButton)
                .addContainerGap(33, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void newGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newGameButtonActionPerformed
        
        mainfr.openGameFrame();
        mainfr.getModel().resumeGame();


    }//GEN-LAST:event_newGameButtonActionPerformed

    private void newGameButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newGameButtonKeyPressed
        if (evt.getKeyCode()== KeyEvent.VK_ENTER) {
            mainfr.openGameFrame();
            
        }
        
    }//GEN-LAST:event_newGameButtonKeyPressed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
     //mainfr.getGame().load();
        mainfr.openGameFrame();
        
    }//GEN-LAST:event_loadButtonActionPerformed

    private void saveGameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveGameButtonActionPerformed
      //mainfr.getGame().save();
      mainfr.getGamepanel().requestFocus();
    }//GEN-LAST:event_saveGameButtonActionPerformed

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

        private void helpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpButtonActionPerformed
                mainfr.openHelp();
        }//GEN-LAST:event_helpButtonActionPerformed


   public void setNewGameButtonText(String text) {
        this.newGameButton.setText(text);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton exitButton;
    private javax.swing.JButton helpButton;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton newGameButton;
    private javax.swing.JButton saveGameButton;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                mainfr.openGameMenu();
                break;

    }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public JButton getExitButton() {
        return exitButton;
    }

    public JButton getHelpButton() {
        return helpButton;
    }

    public JButton getLoadButton() {
        return loadButton;
    }

    public MainFrame getMainfr() {
        return mainfr;
    }

    public JButton getNewGameButton() {
        return newGameButton;
    }

    public JButton getSaveGameButton() {
        return saveGameButton;
    }


}

