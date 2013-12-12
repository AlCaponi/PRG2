package battleship.Network;

import battleship.GUI.Lobby;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.Timer;

/**
 *
 * @author HP
 */
public class UDPServer implements Runnable {
    public static int udpServerPortNb = 4444;
    public static int responseWaitTime = 3000;
    public static String initCode = "Battleship";
    public boolean running;
    public String gameName;
    public int tcpPortNb;
    public Lobby lobbyPtr;
    private Timer responseListenTimer;
    private DatagramSocket socket;
    private Thread thread;
    
    public UDPServer(Lobby lobbyPtr, boolean broadcastServer) {
        this.lobbyPtr = lobbyPtr;
        try {
            if(broadcastServer)
                socket = new DatagramSocket(udpServerPortNb);
            else
                socket = new DatagramSocket();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
            
    }
    
    public void startServer(String gameName, int portNb) {
        // start as broadcast listener
        if(running)
            return;
        
        running = true;
        this.gameName = gameName;
        this.tcpPortNb = portNb;
        start(); 
    }
    
    public void startServerBroadcast() {
        // send broadcast and start as response listener
        if(running) 
            return;
        
        running = true;
        byte[] buffer = initCode.getBytes();
        try {
            start();
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, broadcastAddress, udpServerPortNb);
            socket.send(packet);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        responseListenTimer = new Timer(responseWaitTime, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopServer();
                
            }
        });
        responseListenTimer.setRepeats(false);
        responseListenTimer.start();
    }
    
    public void stopServer() {
        running = false;
    }
    
    public void start() 
    { 
        if (thread == null) { 
            thread = new Thread(this); 
            thread.start(); 
        } 
    }
    
    @Override
    public void run() {
        try {
            while (running) {
                DatagramPacket packet = new DatagramPacket(new byte[256], 256);
                socket.receive(packet);
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] data = packet.getData();
                String infoString = new String(data);
                if(infoString.startsWith(initCode)) {
                    // return Ready
                    String dataStr = gameName;
                    data = dataStr.getBytes();
                    packet = new DatagramPacket(data, data.length, address, port);
                    socket.send(packet);
                    System.out.println(address + " asked for available games");
                }
                else {
               
                    lobbyPtr.HandleUDPResponse(address, infoString);
                    System.out.println(infoString + " found");
                }
            }
            socket.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
