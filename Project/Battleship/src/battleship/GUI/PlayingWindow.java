/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Engine.Coordinates;
import battleship.Engine.Game;
import battleship.Engine.Ship;
import battleship.Engine.eOrientation;
import battleship.Engine.eShipType;
import battleship.Network.Message;
import battleship.Network.eGameState;
import battleship.Network.eMessageType;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Simon
 */
public class PlayingWindow extends JFrame implements IGameGUI {

    JPanel leftPanel;
    JPanel rigthPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    BattleFieldGrid playerGrid;
    BattleFieldGrid oponentGrid;
    JTextField chatInput;
    JList chatOutput;
    
    Game game;

    public PlayingWindow(Game game) {
        this.game = game;
        this.game.registerGUI(this);
        setLayout(new BorderLayout());
        setSize(600, 600);

        buildPanels();
        gameSetup();
        setVisible(true);
        testEdit();
    }

    private void buildPanels() {
        leftPanel = new JPanel(new GridLayout(0, 1));
        rigthPanel = new JPanel(new GridLayout(0, 1));
        centerPanel = new JPanel(new GridLayout(0, 2, 5, 15));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        add(leftPanel, BorderLayout.WEST);
        add(rigthPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void gameSetup() {
        playerGrid = new BattleFieldGrid(game, true);
        JButton buttonApplyShips = new JButton("Apply ship settings");
        buttonApplyShips.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JButton buttonApplyShips = (JButton) e.getSource();
                buttonApplyShips.setVisible(false);

                // to do check if all ships are placed
                // player is ready
                game.sendReadyRequest();

            }
        });
        JButton buttonChat = new JButton("Send");
        buttonChat.addActionListener(
                new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                game.sendChatMessage(chatInput.getText());
                chatInput.setText("");  
                
            }
        }
                );
        chatOutput = new JList();
        chatInput = new JTextField();
        oponentGrid = new BattleFieldGrid(game, false);
        centerPanel.add(playerGrid);
        centerPanel.add(oponentGrid);
        bottomPanel.add(buttonApplyShips);
        bottomPanel.add(chatOutput);
        bottomPanel.add(chatInput);
        bottomPanel.add(buttonChat);

    }

    /**
     * this mehtod is used to set the mode of the gui
     *
     * @param state
     */
    @Override
    public void updateState(eBattleFieldMode state) {

        switch (state) {
            case Design:
                playerGrid.setMode(eBattleFieldMode.Playable);
                oponentGrid.setMode(eBattleFieldMode.Displaying);
                break;
            // this means its players turn
            case Playable:

                playerGrid.setMode(eBattleFieldMode.Displaying);
                oponentGrid.setMode(eBattleFieldMode.Playable);
                break;
            // this means player has to wait
            case Displaying:

                playerGrid.setMode(eBattleFieldMode.Displaying);
                oponentGrid.setMode(eBattleFieldMode.Displaying);
                break;
        }
        // update gui
        playerGrid.UpdateLayout();
        oponentGrid.UpdateLayout();
    }

    /**
     *
     * @param state
     */
    @Override
    public void updateGameState(eGameState state) {
        switch (state) {
            case abort:
                JOptionPane.showMessageDialog(this.getParent(), "Oponent left the current game");
                break;
            case won:
                JOptionPane.showMessageDialog(this.getParent(), "Well done");
                break;
            case lost:
                //ImageIcon  yoda = new ImageIcon ("lost.jpg");                
                //,JOptionPane.ERROR_MESSAGE,(Icon)yoda
                JOptionPane.showMessageDialog(this.getParent(), "Noch viel zu lernen du hast junger Padavan!");
                break;
        }


    }

    @Override
    public void addChatMessage(String message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void testEdit() {
        Ship s = new Ship(eShipType.aircraftcarrier);
        s.setStartPoint(new Coordinates(0, 0));
        s.setOrientation(eOrientation.Vertical);

        Ship s2 = new Ship(eShipType.battleship);
        s2.setStartPoint(new Coordinates(5, 4));
        s2.setOrientation(eOrientation.Vertical);

        Ship s3 = new Ship(eShipType.destroyer);
        s3.setStartPoint(new Coordinates(6, 2));
        s3.setOrientation(eOrientation.Vertical);

        Ship s4 = new Ship(eShipType.patrolboat);
        s4.setStartPoint(new Coordinates(3, 6));
        s4.setOrientation(eOrientation.Vertical);

        Ship s5 = new Ship(eShipType.submarine);
        s5.setStartPoint(new Coordinates(5, 8));
        s5.setOrientation(eOrientation.Horizontal);
        game.setShipToPlace(s);
        game.placeCurrentShip();
        game.setShipToPlace(s2);
        game.placeCurrentShip();
        game.setShipToPlace(s3);
        game.placeCurrentShip();
        game.setShipToPlace(s4);
        game.placeCurrentShip();
        game.setShipToPlace(s5);
        game.placeCurrentShip();


        playerGrid.UpdateLayout();
    }

    class WindowListerner extends WindowAdapter {

        @Override
        public void windowClosed(WindowEvent e) {
            Message abortMessage = new Message();
            abortMessage.setMessageType(eMessageType.gameState);
            abortMessage.setDataContainer(eGameState.abort);
            // Oponent is null at the moment
            //game.getOpenent().sendMessage(abortMessage);
            System.exit(0);
        }
    }
}
