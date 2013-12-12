/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.GUI;

import battleship.Engine.eOrientation;
import battleship.Network.eGameState;

/**
 *
 * @author Simon
 */
public interface IGameGUI {
    void updateState(eBattleFieldMode state);
    
    void updateGameState(eGameState state);
    void addChatMessage(String message);
    void updateLayout();
    eOrientation getOrientation();
}
