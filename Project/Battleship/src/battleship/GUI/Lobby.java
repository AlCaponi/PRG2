/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

/**
 *
 * @author Angelo
 */
public class Lobby extends JFrame
{
    private JPanel pnlLobby;
    private JList lstGames;
    
    public Lobby()
    {
        //Window Settings
        super("Lobby");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Initialize Components
        InitializeComponents();
        setVisible(true);
    }

    private void InitializeComponents() 
    {
        pnlLobby = new JPanel();
        pnlLobby.setBackground(Color.WHITE);
        this.add(pnlLobby);
        
        
        lstGames = new JList();
        lstGames.setSize(250, 300);
        lstGames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstGames.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        lstGames.setVisibleRowCount(-1);
        
                
        this.add(lstGames);
    }

}
