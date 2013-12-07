package battleship.Network;

import battleship.Engine.Game;
import java.net.ServerSocket;
import java.net.Socket;

public class Player implements IClient {

	private Socket sender;
	private ServerSocket receiver;
        private Game game;

	public Socket getSender() {
		return this.sender;
	}

	/**
	 * 
	 * @param sender
	 */
	public void setSender(Socket sender) {
		this.sender = sender;
	}

	public ServerSocket getReceiver() {
		return this.receiver;
	}
	/**
	 * 
	 * @param receiver
	 */
	public void setReceiver(ServerSocket receiver) {
		this.receiver = receiver;
	}

    @Override
    public void sendMessage(Message message) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void registerGame(Game game) {
        this.game = game;
        
    }

    private ePlayerState state;
    @Override
    public ePlayerState getState() {
        return state;
    }

    @Override
    public void setState(ePlayerState state) {
        this.state = state;
    }

}