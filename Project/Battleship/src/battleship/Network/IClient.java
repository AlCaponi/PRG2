package battleship.Network;

import battleship.Engine.Game;

public interface IClient {

	/**
	 * 
	 * @param message
	 */
	void sendMessage(Message message);

	/**
	 * 
	 * @param game
	 */
	void registerGame(Game game);

}