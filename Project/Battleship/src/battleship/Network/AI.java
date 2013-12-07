package battleship.Network;

import battleship.Engine.BattleField;
import battleship.Engine.Coordinates;
import battleship.Engine.Game;
import battleship.Engine.IBattleField;
import battleship.Engine.Ship;
import battleship.Engine.eOrientation;
import battleship.Engine.eShipType;
import java.util.ArrayList;
import java.util.Random;

public class AI implements IClient {

    Game game;
    IBattleField field;
    ArrayList<Coordinates> alreadyHit;
    private Coordinates lastAttack;
    private ePlayerState state;

    public AI() {
        alreadyHit = new ArrayList<>();
        field = new BattleField(10, 10);

    }

    /**
     * Because AI doesn't use any network protocoll, send message means you "receive" the message
     * @param message 
     */
    @Override
    public void sendMessage(Message message) {

        //handles the messages passed form the main game
        switch (message.getMessageType()) {
            case attack:
                // check player grid and send result
                Message<Coordinates> attackMessage = message;
                boolean hit = field.hitField(attackMessage.getDataContainer().getX(), attackMessage.getDataContainer().getY());
                //check hit count
                // won ?
                game.handleOponentMessage(new MessageFactory<Boolean>().createMessage(eMessageType.attackResult, hit));
                if (field.getHitCount() == 100) {
                    game.handleOponentMessage(new MessageFactory<eGameState>().createMessage(eMessageType.gameState, eGameState.won));
                }
                break;
            case attackResult:
                // Display on player grid
                Message<Boolean> attackResult = message;
                //field.setFieldState(attackResult.getDataContainer(), lastAttack.getX(), lastAttack.getY());
                // switch turns
                game.handleOponentMessage(new MessageFactory<ePlayerState>().createMessage(eMessageType.playerState, ePlayerState.TurnSwitch));
                break;
            case gameState:
                // display result
                Message<eGameState> gameState = message;
                break;
            case playerState:
                // start game or change turn
                Message<ePlayerState> playerStateMessage = message;
                if (playerStateMessage.getDataContainer() == ePlayerState.Ready) {
                    // Player can always start
                    // just wait for first input
                    //game.handleOponentMessage(new MessageFactory<ePlayerState>().createMessage(eMessageType.playerState, ePlayerState.TurnSwitch));
                } else if (playerStateMessage.getDataContainer() == ePlayerState.TurnSwitch) {
                    AITurn();
                }
                break;
            case chat:
                // display in chat :)

                break;
        }

    }

    @Override
    public void registerGame(Game game) {
        this.game = game;
        placeShips();
    }

    private void placeShips() {
        ArrayList<Ship> shipsToPlace = new ArrayList<>();
        shipsToPlace.add(new Ship(eShipType.aircraftcarrier));
        shipsToPlace.add(new Ship(eShipType.battleship));
        shipsToPlace.add(new Ship(eShipType.destroyer));
        shipsToPlace.add(new Ship(eShipType.patrolboat));
        shipsToPlace.add(new Ship(eShipType.submarine));


        while (shipsToPlace.size() > 0) {

            Ship toPlace = shipsToPlace.get(0);
            toPlace.setStartPoint(getRandomCoordinates());
            eOrientation orient = toPlace.getStartPoint().getX() % 2 == 0? eOrientation.Horizontal:eOrientation.Vertical;
            toPlace.setOrientation(orient);

            boolean canPlace = true;
            //try place
            // TO DO
            canPlace = field.setShip(toPlace);

            if (canPlace) {
                shipsToPlace.remove(toPlace);
            }
        }
        // when all ships are placed AI is ready
        Message<ePlayerState> ready = new MessageFactory<ePlayerState>().createMessage(eMessageType.playerState, ePlayerState.Ready);
        game.handleOponentMessage(ready);
    }

    private void AITurn() {

        Coordinates toHit = getRandomCoordinates();
        while (alreadyHit.contains(toHit)) {
            toHit = getRandomCoordinates();
        }
        // to do
        // hit field with coordinates

        lastAttack = toHit;

        Message<Coordinates> hitMessage = new MessageFactory<Coordinates>().createMessage(eMessageType.attack, toHit);
        // game
        game.handleOponentMessage(hitMessage);

    }

    public Coordinates getRandomCoordinates() {
        Random generator = new Random();
        int startX = generator.nextInt(10);
        int startY = generator.nextInt(10);
        Coordinates coord = new Coordinates(startX, startY);
        return coord;

    }

    @Override
    public ePlayerState getState() {
        return state;
    }

    @Override
    public void setState(ePlayerState state) {
        this.state = state;
    }
}