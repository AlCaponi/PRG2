/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import battleship.Engine.BattleField;
import battleship.Engine.Game;
import battleship.Engine.IBattleField;
import battleship.GUI.CreateGameDialog;
import battleship.GUI.Lobby;
import battleship.GUI.PlayingWindow;
import battleship.Network.AI;
import battleship.Network.IClient;
import battleship.Network.UDPServer;

/**
 *
 * @author Simon sadflksajdf
 */
public class Main {

    /**
     * @param args the command line arguments
     * Todo: comment all the things!
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // set up for ai
        IClient oponent = new AI();
        Game game = new Game(oponent);
        oponent.registerGame(game);
        
        //Set up game
        PlayingWindow wnd = new PlayingWindow(game);
        Lobby l = new Lobby();
    }
}
