package battleship.Engine;


import battleship.Network.IClient;
import battleship.Network.Message;

public class Game {

	private IClient openent;
	private IClient player;

	/**
	 * 
	 * @param message
	 */
	public void handleOponentMessage(Message message) {
		throw new UnsupportedOperationException();
	}

    /**
     * @return the openent
     */
    public IClient getOpenent() {
        return openent;
    }

    /**
     * @return the player
     */
    public IClient getPlayer() {
        return player;
    }

}