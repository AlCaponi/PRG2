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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Simon
 */
public class PlayingWindow extends JFrame {

    JPanel leftPanel;
    JPanel rigthPanel;
    JPanel centerPanel;
    JPanel bottomPanel;
    BattleFieldGrid playerGrid;
    BattleFieldGrid oponentGrid;
    Game game;

    public PlayingWindow(Game game) {
        this.game = game;
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
        centerPanel = new JPanel(new GridLayout(0, 2,5,15));
        bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.Y_AXIS));
        add(leftPanel, BorderLayout.WEST);
        add(rigthPanel, BorderLayout.EAST);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void gameSetup() {
        playerGrid = new BattleFieldGrid(game,true);
        JButton buttonApplyShips = new JButton("Apply ship settings");
        buttonApplyShips.addActionListener(
                new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        JButton buttonApplyShips = (JButton)e.getSource();
                        buttonApplyShips.setVisible(false);
                        playerGrid.setMode(eBattleFieldMode.Displaying);
                        oponentGrid.setMode(eBattleFieldMode.Playable);
                        
                        Message ready = new Message();
                        ready.setMessageType(eMessageType.playerState);
                        ready.setDataContainer("I'm ready");
                        //Openent is not set at the moment
                        //game.getOpenent().sendMessage(ready);
                        
                    }
                }
                );
        oponentGrid = new BattleFieldGrid(game,false);
        centerPanel.add(playerGrid);
        centerPanel.add(oponentGrid);
        bottomPanel.add(buttonApplyShips);
        
    }
    
    private void testEdit()
    {
        Ship s = new Ship(eShipType.aircraftcarrier);
        s.setStartPoint(new Coordinates(0,4));
        s.setOrientation(eOrientation.Vertical);
        
        Ship s2 = new Ship(eShipType.aircraftcarrier);
        s2.setStartPoint(new Coordinates(5,4));
        s2.setOrientation(eOrientation.Vertical);
        playerGrid.field.setShip(s);
        playerGrid.field.setShip(s2);
        playerGrid.UpdateLayout();
    }
    
    class WindowListerner extends WindowAdapter
    {
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
