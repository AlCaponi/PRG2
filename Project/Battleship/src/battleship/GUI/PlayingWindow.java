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
import static battleship.GUI.eBattleFieldMode.Design;
import static battleship.GUI.eBattleFieldMode.Displaying;
import static battleship.GUI.eBattleFieldMode.Playable;
import battleship.Network.Message;
import battleship.Network.eGameState;
import static battleship.Network.eGameState.abort;
import static battleship.Network.eGameState.lost;
import static battleship.Network.eGameState.won;
import battleship.Network.eMessageType;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import javax.swing.JRadioButton;

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
    JComboBox cmbAvailableShips;
    JTextField chatInput;
    List chatOutput;
    Game game;
    JRadioButton rdbHorizontal;
    JRadioButton rdbVertical;
    ButtonGroup btnGroupOrientation;

    public PlayingWindow(Game game) {
        this.game = game;
        this.game.registerGUI(this);
        setLayout(new BorderLayout());
        setSize(600, 600);
        addWindowListener(new WindowListernerExt());
        buildPanels();
        gameSetup();
        setVisible(true);
        //testEdit();
    }

    private void buildPanels() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new GridLayout(3, 1));
        rigthPanel = new JPanel(new GridLayout(0, 1));
        centerPanel = new JPanel(new GridLayout(0, 2, 5, 15));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
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

        JButton btnResetShips = new JButton("Reset");
        btnResetShips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetShips(e);
            }
        });

        cmbAvailableShips = new JComboBox();
        this.game.setShipToPlace(new Ship(eShipType.aircraftcarrier));
        cmbAvailableShips.addItem(new Ship(eShipType.aircraftcarrier));
        cmbAvailableShips.addItem(new Ship(eShipType.battleship));
        cmbAvailableShips.addItem(new Ship(eShipType.destroyer));
        cmbAvailableShips.addItem(new Ship(eShipType.patrolboat));
        cmbAvailableShips.addItem(new Ship(eShipType.submarine));

        cmbAvailableShips.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cmbAvailableShips_SelectedChanged(e);
            }
        });
        leftPanel.add(cmbAvailableShips);

        rdbHorizontal = new JRadioButton();
        rdbHorizontal.setText("Horizontal");
        rdbHorizontal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rdbHorizontal_checkedChanged(e);
            }
        });

        rdbVertical = new JRadioButton();
        rdbVertical.setText("Vertical");
        rdbVertical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rdbVertical_checkedChanged(e);
            }
        });
        rdbVertical.setSelected(true);

        btnGroupOrientation = new ButtonGroup();
        btnGroupOrientation.add(rdbHorizontal);
        btnGroupOrientation.add(rdbVertical);

        leftPanel.add(rdbHorizontal);
        leftPanel.add(rdbVertical);
        JButton buttonChat = new JButton("Send");
        buttonChat.addActionListener(
                new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.sendChatMessage(chatInput.getText());
                chatInput.setText("");
            }
        });
        chatOutput = new List();
        chatInput = new JTextField();
        oponentGrid = new BattleFieldGrid(game, false);
        centerPanel.add(playerGrid);
        centerPanel.add(oponentGrid);
        //left
        JPanel bottomLeft = new JPanel();
        bottomLeft.setLayout(new BoxLayout(bottomLeft, BoxLayout.Y_AXIS));

        bottomLeft.add(buttonApplyShips);
        bottomLeft.add(btnResetShips);

        JPanel bottomCenter = new JPanel();
        bottomCenter.setLayout(new BoxLayout(bottomCenter, BoxLayout.Y_AXIS));
        bottomCenter.add(chatOutput);
        bottomCenter.add(chatInput);

        JPanel bottomRigth = new JPanel();
        bottomRigth.setLayout(new BoxLayout(bottomRigth, BoxLayout.Y_AXIS));
        bottomRigth.add(buttonChat);

        bottomPanel.add(bottomLeft, BorderLayout.WEST);
        bottomPanel.add(bottomCenter, BorderLayout.CENTER);
        bottomPanel.add(bottomRigth, BorderLayout.EAST);


    }

    private void cmbAvailableShips_SelectedChanged(ActionEvent e) {
        Ship selectedShip = (Ship) cmbAvailableShips.getSelectedItem();
        this.game.setShipToPlace(selectedShip);
        playerGrid.UpdateLayout();
    }

    private void rdbHorizontal_checkedChanged(ActionEvent e) {
        this.game.getShipToPlace().setOrientation(eOrientation.Horizontal);
    }

    private void rdbVertical_checkedChanged(ActionEvent e) {
        this.game.getShipToPlace().setOrientation(eOrientation.Vertical);


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
                ImageIcon winIcon = new ImageIcon(PlayingWindow.class.getResource("win.gif"));
                JOptionPane.showMessageDialog(this.getParent(), "", "congratulation!!!", JOptionPane.INFORMATION_MESSAGE, winIcon);
                break;
            case lost:
                ImageIcon lostIcon = new ImageIcon(PlayingWindow.class.getResource("lost.gif"));
                JOptionPane.showMessageDialog(this.getParent(), "", "LOOSER", JOptionPane.INFORMATION_MESSAGE, lostIcon);
                break;
        }
    }

    @Override
    public void addChatMessage(String message) {
        chatOutput.add(message);
       
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

    @Override
    public void updateLayout() {
        playerGrid.UpdateLayout();
        oponentGrid.UpdateLayout();
        if (cmbAvailableShips.getItemCount() > 0) {
            cmbAvailableShips.removeItem(game.getShipToPlace());
        }
    }

    class WindowListernerExt extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            Message abortMessage = new Message();
            abortMessage.setMessageType(eMessageType.gameState);
            abortMessage.setDataContainer(eGameState.abort);
            // Oponent is null at the moment
            game.getOpenent().sendMessage(abortMessage);
            
            //System.exit(0);
        }                
    }

    public eOrientation getOrientation() {
        if (rdbHorizontal.isSelected()) {
            return eOrientation.Horizontal;
        }
        return eOrientation.Vertical;
    }

    public void resetShips(ActionEvent e) {
        playerGrid = new BattleFieldGrid(game, true);

        playerGrid.UpdateLayout();
    }    
}
