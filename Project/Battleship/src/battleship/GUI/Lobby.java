/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Engine.Game;
import battleship.Network.AI;
import battleship.Network.GameInfo;
import battleship.Network.IClient;
import battleship.Network.Player;
import battleship.Network.UDPServer;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
    private ArrayList<GameInfo> gameList;
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
        gameList = new ArrayList<>();
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
        lstGames = new List();
        lstGames.setSize(250, 250);
        pnlLobby.add(lstGames, BorderLayout.WEST);
        
        //ButtonPanel
        pnlButtons = new JPanel();
        pnlButtons.setLayout(new BoxLayout(pnlButtons, BoxLayout.Y_AXIS));
        pnlLobby.add(pnlButtons, BorderLayout.EAST);
        
        //Button CreateGame
        btnCreateGame = new JButton("Create Game");
        btnCreateGame.setSize(50, 250);
        btnCreateGame.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent e){
                String gameName = JOptionPane.showInputDialog("New Game Name:");
                StartGameServer(gameName);
            }
        });
        pnlButtons.add(btnCreateGame, BorderLayout.EAST);
        
        //Button JoinGame
        btnJoinGame = new JButton("Join Game");
        btnJoinGame.setSize(50, 250);
        btnJoinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = lstGames.getSelectedIndex();
                if(index<0)
                    return;
                try {
                    JoinGame(gameList.get(index).getAddress());
                } catch(Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        pnlButtons.add(btnJoinGame, BorderLayout.EAST);
        
        // Button Join Game Through IP
        btnJoinGame = new JButton("Join Game Through IP");
        btnJoinGame.setSize(50, 250);
        btnJoinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = JOptionPane.showInputDialog("Enter remote IP:");
                try {
                    InetAddress adr = InetAddress.getByName(ip);
                    JoinGame(adr);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });
        pnlButtons.add(btnJoinGame, BorderLayout.EAST);
        
        // Button Start Game with AI
        btnJoinGame = new JButton("Start Game with AI");
        btnJoinGame.setSize(50, 250);
        btnJoinGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                IClient oponent = new AI();
                StartGame(oponent);
            }
        });
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
    
    public void HandleUDPResponse(InetAddress address, String gameName)
    {
        gameList.add(new GameInfo(address, gameName));
        UpdateGameList();
    }
    
    public void UpdateGameList() {
        lstGames.removeAll();
        for(GameInfo i:gameList) {
           lstGames.add(i.toString());
        }
        
    }
    
    public void StartGameServer(String gameName)
    {
        if(broadcastServer.running)
            broadcastServer.stopServer();
        
        broadcastServer.startServer(gameName);
    }
    
    public void PerformBroadcast()
    {
        if(!responseServer.running) {
            gameList.clear();
            UpdateGameList();
        }
        responseServer.startServerBroadcast(); 
    }
    
    public void JoinGame(InetAddress adr)
    {
        Player player = new Player();
        player.connect(adr);
        StartGame(player);
    }
    
    public void StartGame(IClient oponent)
    {
        Game game = new Game(oponent);
        oponent.registerGame(game);
        PlayingWindow playingWindow = new PlayingWindow(game);
    }
}
