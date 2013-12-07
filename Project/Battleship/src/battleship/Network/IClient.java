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
        
        /**
         * uset to get the state of the oponent
         * @return 
         */
        ePlayerState getState();
        
        /**
         *  used to store the state of the oponent
         * @param state 
         */
        void setState(ePlayerState state);

}