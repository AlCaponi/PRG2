/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Network;

import battleship.Engine.Game;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author bmoor
 */
public class ReadObject extends Thread {
    Thread thread;    
    private ObjectInputStream objectReader;
    private Socket socket;
    private Object tmp;
    private Game game;
    private boolean running = true;

    public ReadObject(Socket socket, Game game) {
        this.socket = socket;
        this.game = game;
        thread = new Thread(this);
        thread.start();
    }
            
    @Override
    public void run(){                
        try {
            while(running){
            objectReader = new ObjectInputStream(socket.getInputStream());
            tmp = objectReader.readObject();
            if (tmp != null && tmp instanceof Message){
                this.game.handleOponentMessage((Message)tmp);
            }
            }
        } catch(Exception e) {
            System.out.println("TCPNetwork Exception: WriteObject"+e.getMessage());
        }
    }
   
}
