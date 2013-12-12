package battleship.Engine;

import battleship.GUI.IGameGUI;
import battleship.GUI.eBattleFieldMode;
import battleship.Network.AI;
import battleship.Network.IClient;
import battleship.Network.Message;
import battleship.Network.MessageFactory;
import battleship.Network.eGameState;
import battleship.Network.eMessageType;
import battleship.Network.ePlayerState;

public class Game {

    // used for sending messagiges
    private IClient oponent;
    // used for placing ships
    private IBattleField playerGrid;
    // just used for displaying and pressing buttons
    private IBattleField openentGrid;
    // used for storing latest coordinates of the attack
    private Coordinates lastAttack;
    // used to temp store the ship which should be placed on a battlefield
    private Ship toPlace;
    // reference to gui for updating state
    private IGameGUI gui;
    private boolean playerIsReady = false;
    private boolean gameStarted = false;

    public Game(IClient openent) {
        this.oponent = openent;

        setupGame();
    }

    private void setupGame() {
        playerGrid = new BattleField(10, 10);
        openentGrid = new BattleField(10, 10);
    }

    /**
     *
     * @param message
     */
    public void handleOponentMessage(Message message) {


        switch (message.getMessageType()) {
            case attack:
                // check player grid and send result
                Message<Coordinates> attackMessage = message;
                boolean hit = getPlayerGrid().hitField(attackMessage.getDataContainer().getX(), attackMessage.getDataContainer().getY());
                sendAttackResult(hit);
                if (getPlayerGrid().getHitCount() == 100) {
                    oponent.sendMessage(new MessageFactory<eGameState>().createMessage(eMessageType.gameState, eGameState.won));
                    if(gui != null)
                    {
                        gui.updateGameState(eGameState.lost);
                    }
                }
                break;
            case attackResult:
                // Display on player grid
                Message<Boolean> attackResult = message;
                openentGrid.setFieldState(attackResult.getDataContainer(), lastAttack.getX(), lastAttack.getY());
                // switch turns
                sendPlayerState(ePlayerState.TurnSwitch);
                break;
            case gameState:
                // display result
                Message<eGameState> gameStateMessage = message;
                if(gui != null)
                {
                    gui.updateGameState(gameStateMessage.getDataContainer());
                }
                break;
            case playerState:
                // start game or change turn
                Message<ePlayerState> playerStateMessage = message;
                if (playerStateMessage.getDataContainer() == ePlayerState.Ready) {
                    oponent.setState(ePlayerState.Ready);
                    startGame();

                } else if (playerStateMessage.getDataContainer() == ePlayerState.TurnSwitch) {
                    oponent.setState(ePlayerState.Waiting);
                    if (gui != null) {
                        gui.updateState(eBattleFieldMode.Playable);
                    }
                }
                break;
            case chat:
                // display in chat :)

                break;
        }
    }

    /**
     * @return the openent
     */
    public IClient getOpenent() {
        return oponent;
    }

    /**
     * @return the playerGrid
     */
    public IBattleField getPlayerGrid() {
        return playerGrid;
    }

    /**
     * @return the openentGrid
     */
    public IBattleField getOpenentGrid() {
        return openentGrid;
    }

    public void sendAttackRequest(int x, int y) {
        //set displaying
        if (gui != null) {
            gui.updateState(eBattleFieldMode.Displaying);
        }
        Message<Coordinates> attack = new Message<>();
        attack.setMessageType(eMessageType.attack);
        attack.setDataContainer(new Coordinates(x, y));
        lastAttack = attack.getDataContainer();
        oponent.sendMessage(attack);

    }

    public void sendReadyRequest() {
        playerIsReady = true;
        Message<ePlayerState> ready = new Message<>();
        ready.setMessageType(eMessageType.playerState);
        ready.setDataContainer(ePlayerState.Ready);
        oponent.sendMessage(ready);
        // cant start game wait till openent is ready
        if (startGame() == false) {
            if (gui != null) {
                gui.updateState(eBattleFieldMode.Displaying);
            }
        }

    }

    public void sendAttackResult(boolean result) {
        if (gui != null) {
            gui.updateState(eBattleFieldMode.Displaying);
        }
        Message<Boolean> attackResult = new Message<>();
        attackResult.setMessageType(eMessageType.attackResult);
        attackResult.setDataContainer(result);
        oponent.sendMessage(attackResult);

    }

    public void sendChatMessage(String message) {
        Message<String> ready = new Message<>();
        ready.setMessageType(eMessageType.chat);
        ready.setDataContainer(message);
        oponent.sendMessage(ready);
        // add message to gui
        if (gui != null) {
            gui.addChatMessage(message);
        }
    }

    public void sendPlayerState(ePlayerState state) {
        Message<ePlayerState> ready = new Message<>();
        ready.setMessageType(eMessageType.playerState);
        ready.setDataContainer(state);
        oponent.sendMessage(ready);
    }

    /**
     * if both players are ready this mehtod starts the real interaction
     *
     * @return
     */
    private boolean startGame() {
        //To do Decide who is starting
        // in case of ai openent I'm starting
        if (playerIsReady && oponent.getPlayerState() == ePlayerState.Ready) {
            if (oponent instanceof AI) {
                if (gui != null) {
                    gui.updateState(eBattleFieldMode.Playable);
                    gameStarted = true;
                    return true;
                }
            } else {
                // to check if this is the host else send a switch turn
            }
        }
        return false;
    }

    public void setShipToPlace(Ship toPlace) {
        this.toPlace = toPlace;
    }

    public boolean placeCurrentShip() {
        return playerGrid.setShip(toPlace);
    }

    public void registerGUI(IGameGUI gui) {
        this.gui = gui;
    }
    
    public void updateLayout()
    {
        this.gui.updateLayout();
    }
    
    public Ship getShipToPlace()
    {
        return this.toPlace;
    }
    
    public eOrientation getSelectedOrientation()
    {
        return this.gui.getOrientation();
    }
}
