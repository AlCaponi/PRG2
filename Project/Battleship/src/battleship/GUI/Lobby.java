/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Network.UDPServer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Set;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
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
    private JPanel pnlButtons;
    private List lstGames;
    private JButton btnCreateGame;
    private JButton btnJoinGame;
    private JButton btnRefresh;
    private HashMap<InetAddress,String> gameList;
    private UDPServer broadcastServer;
    private UDPServer responseServer;
    
    public Lobby()
    {
        //Window Settings
        super("Lobby");
        
        //Initialize Components
        InitializeComponents();
        
        // UDP servers
        broadcastServer = new UDPServer(this, true);
        responseServer = new UDPServer(this, false);
        gameList = new HashMap<>();
    }

    private void InitializeComponents() 
    {
        //Window Settings
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Main Panel
        pnlLobby = new JPanel(new GridLayout(1, 1));
        pnlLobby.setBackground(Color.WHITE);  
        this.add(pnlLobby);
        
        //List Games
        String[] listData = { "I will ", "Own you ", "Masafackaaa", "!!!!!!!", "Biatch" };
        lstGames = new List();
        for(String s : listData)
        {
            lstGames.add(s);
        }
        lstGames.setSize(250, 250); 
        pnlLobby.add(lstGames, BorderLayout.WEST);
        
        //ButtonPanel
        pnlButtons = new JPanel();
        pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.Y_AXIS));
        pnlLobby.add(pnlButtons, BorderLayout.EAST);
        
        //Button CreateGame
        btnCreateGame = new JButton("Create Game");
        btnCreateGame.setSize(50, 250);
        btnCreateGame.addActionListener(new ActionListener(){ public void actionPerformed(ActionEvent e){btnCreateGame_clicked(e);}});
        pnlButtons.add(btnCreateGame, BorderLayout.EAST);
        
        //Button JoinGame
        btnJoinGame = new JButton("Join Game");
        btnJoinGame.setSize(50, 250);
        pnlButtons.add(btnJoinGame, BorderLayout.EAST);
        
        // Button Refresh List
        btnRefresh = new JButton("Refresh List using Broadcast");
        btnRefresh.setSize(50, 250);
        btnRefresh.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformBroadcast();
            }
        });
        pnlButtons.add(btnRefresh, BorderLayout.SOUTH);
                
        //Set visibility
        setVisible(true);
        
    }
    
    public void btnCreateGame_clicked(ActionEvent e)
    {
        CreateGameDialog createGameDialog = new CreateGameDialog(this);
    }
    
    public void HandleUDPResponse(InetAddress address, String gameName)
    {
        gameList.put(address, gameName);
        UpdateGameList();
    }
    
    public void UpdateGameList() {
        lstGames.removeAll();
        Set<InetAddress> set = gameList.keySet();
        for(InetAddress adr : set)
        {
            String entry = adr.toString() + " " + gameList.get(adr);
            lstGames.add(entry);
        }
    }
    
    public void StartGameServer(String gameName, int portNb)
    {
        if(broadcastServer.running)
            broadcastServer.stopServer();
        
        broadcastServer.startServer(gameName, portNb);
    }
    
    public void PerformBroadcast()
    {
        if(!responseServer.running) {
            gameList.clear();
            UpdateGameList();
        }
        responseServer.startServerBroadcast(); 
    }
}
