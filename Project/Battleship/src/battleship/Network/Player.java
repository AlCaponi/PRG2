package battleship.Network;

import battleship.Engine.Game;
import battleship.GUI.Lobby;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Player extends Thread implements IClient {
    private boolean running, playing, opponentready, waitforclient;
    private boolean isHit = true;
    private boolean ishost = false;
    private boolean isClient = false;
    private int port = 45678;
    private Thread thread;    
    
    private InetAddress ipAddress;

    private ServerSocket serverSocket;
    private Socket clientSocket;

    private BufferedReader inReader;
    private DataOutputStream outStream;
    private ObjectInputStream objectReader;
    private ObjectOutputStream objectWriter;
    private Object tmp;
    private ReadObject writeObject;
    
    private Socket connectionSocket;

    private Game game;        

    private Lobby lobby;


        public Player()
        {            
            ishost = false;
            start();
        }
	public Socket getSender() {
		return clientSocket;
	}

    public void start() {
        thread = new Thread(this);
        thread.start();        
        running = true;
        playing = false;
        System.out.println("TCP Thread started");
    }

    public boolean isHost() {
        return ishost;
    }

    public boolean opponentReady() {
        return opponentready;
    }

    public boolean host(Lobby alobby) {
        this.lobby = alobby;
        waitforclient = true;
        ishost = true;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        System.out.println("Host");
        playing = true;
        return true;
    }

    public synchronized boolean connect(InetAddress ipadr) {
        ishost = false;
        try {
            clientSocket = new Socket(ipadr, port);
            isClient = true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        /*try {
            
            objectReader = new ObjectInputStream(clientSocket.getInputStream());
            objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());*/
            /*   inReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outStream = new DataOutputStream(clientSocket.getOutputStream());*/
        /*} catch (IOException ex) {
            System.out.println("TCP Thread Exception: "+ex.getMessage());
        }*/
        System.out.println("verbunden mit: "+clientSocket.getInetAddress());
        playing = true;
        return true;
    }

    public InetAddress getInetAddress(){
        return ipAddress;
    }

    public void disconnect() throws IOException {
        running = false;
        if(ishost == true) {
            serverSocket.close();
        } else {
            clientSocket.close();
        }
        ishost = false;
        playing = false;
        opponentready = false;
    }
    
    

    public void run() {
        try {
            while(running) {
                Thread.sleep(10);
                if(playing == true) {
                    if (isClient == true){
                            writeObject = new ReadObject(clientSocket, game);
                            isClient = false;
                            System.out.println("is Client");   
                            running = false;
                    }
                    
                    if(ishost == true) {
                        if(waitforclient == true) {
                            connectionSocket = serverSocket.accept();
                            writeObject = new ReadObject(connectionSocket, game);
                            
                            //Startgame
                            lobby.StartGame(this);

                            /*inReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                            outStream = new DataOutputStream(connectionSocket.getOutputStream());*/
                            System.out.println("Client connection accepted from: "+connectionSocket.getInetAddress());
                            waitforclient = false;
                            running = false;
                        }
 /*                       try {                            
                            tmp = objectReader.readObject();
                        }
                        catch(Exception e){ 
                            System.out.println("Exception: run is host " + e.getMessage());
                        }
                        Message message = (Message)tmp;
                        game.handleOponentMessage(message);*/
                        /*String receiveData = inReader.readLine();
                        process(receiveData);*/
                    /*} else {
                        try {
                            tmp = objectReader.readObject();
                        }
                        catch(Exception e){ 
                            System.out.println("Exception: run is client" + e.getMessage());
                        }
                        Message message = (Message)tmp;
                        game.handleOponentMessage(message);*/
                    }
                }
            }
           // disconnect();
        } catch(InterruptedException | IOException ex) {
            System.out.println("TCPNetwork Exception: "+ex.getMessage());
        }
    }

    /*private void dataRecieved()
    {
        ObjectInputStream objectReader = new ObjectInputStream(connectionSocket.getInputStream());
        Object tmp = objectReader.readObject();
        Message message = (Message)tmp;

        game.handleOponentMessage(message);
    }*/

/*      private void process(String data) {
        if(data == null) {
            return;
        }
        System.out.println("Received: "+data);
        String[] split = data.split(":");
        char c = split[0].charAt(0);
        int aRow, aColumn;
        String chatMessage;
        switch(c) {
            case '1':
                //Recieved Opponent Shot
                System.out.println("Schuss erhalten");
                aRow = Integer.parseInt(split[1]);
                aColumn = Integer.parseInt(split[2]);

                //Check for Miss or Hit and return
                if (isHit) {
                    //Hit return
                    data = "2:"+aRow+":"+aColumn;
                } else {
                    //Miss return
                    data = "3:"+aRow+":"+aColumn;
                }
                sendData(data);
                //Now my Turn
                break;
            case '2':
                //Shot was a hit, mark map
                aRow = Integer.parseInt(split[1]);
                aColumn = Integer.parseInt(split[2]);
                //Mark hit on Map

                break;
            case '3':
                //Shot was a miss, mark map
                aRow = Integer.parseInt(split[1]);
                aColumn = Integer.parseInt(split[2]);
                //Mark miss on Map

                break;
            case '4':
                //opponent ready
                opponentready = true;
                break;
            case '5':
                //Chat message
                chatMessage = split[1];
                System.out.println(chatMessage);                
        }
    }   

    public void sendReady() {
        String data = "4";
        sendData(data);
    }

    public void sendFire(int aRow, int aColumn) {
        String data = "1:"+aRow+":"+aColumn;
        sendData(data);
    }

    public void sendMessage(String aMessage){
        sendData("5:"+aMessage);
    }*/



    /*private void sendData(String senddata) {
        System.out.println("Send TCP Data: "+senddata);
        try {
            outStream.writeBytes(senddata + '\n');
            outStream.flush();
        } catch (IOException ex) {
            System.out.println("TCPNetwork Exception: "+ex.getMessage());
        }
    }*/

    public void close() {
        if(running == true) {
            running = false;
            System.out.println("TCP Thread closed");
        }
    }

    public void setPort(int port) {
        this.port = port; 
    }

    public int getPort() {
        return port;
    }

    /**
     * 
     * @param sender
     */
    public void setSender(Socket sender) {
            //this.sender = sender;
    }

    public ServerSocket getReceiver() {
            //return this.receiver;
        return serverSocket;
    }
    /**
     * 
     * @param receiver
     */
    public void setReceiver(ServerSocket receiver) {
            //this.receiver = receiver; 
    }
        
    @Override
    public void sendMessage(Message message) {
        try {
            if (ishost){
                objectWriter = new ObjectOutputStream(connectionSocket.getOutputStream());
            } else {
                objectWriter = new ObjectOutputStream(clientSocket.getOutputStream());
            }
            objectWriter.writeObject(message);
            objectWriter.flush();            
        }
        catch (Exception e){
            System.out.println("Exception: sendMessage " + e.getMessage());
        }                
    }

    
    
    @Override
    public void registerGame(Game game) {
        this.game = game;
        
    }

    private ePlayerState state;
    
    @Override
    public ePlayerState getPlayerState() {
        return state;
    }

    @Override
    public void setState(ePlayerState state) {
        this.state = state;
    }

}
