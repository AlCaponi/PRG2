package battleship.Network;

import battleship.Engine.BattleField;
import battleship.Engine.Coordinates;
import battleship.Engine.Game;
import battleship.Engine.IBattleField;
import battleship.Engine.Ship;
import battleship.Engine.eOrientation;
import battleship.Engine.eShipType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AI implements IClient {

    Game game;
    IBattleField field;
    HashSet<Coordinates> alreadyHit;
    private Coordinates lastAttack;
    boolean lastAttackResult = false;
    AttackStrategie strategie;
    private ePlayerState state;

    public AI() {
        alreadyHit = new HashSet<>();
        field = new BattleField(10, 10);

    }

    /**
     * Because AI doesn't use any network protocoll, send message means you
     * "receive" the message
     *
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
                //interpret attakResult
                lastAttackResult = attackResult.getDataContainer();
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
            try {
                AITurn();
            } catch (CloneNotSupportedException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
            }
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
            eOrientation orient = toPlace.getStartPoint().getX() % 2 == 0 ? eOrientation.Horizontal : eOrientation.Vertical;
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

    private void AITurn() throws CloneNotSupportedException {

        Coordinates toHit = new Coordinates();
        do {
            // first time hit any ship
            if (strategie == null && lastAttackResult == true) {

                strategie = new AttackStrategie(lastAttack);
                toHit = strategie.getNextAttack(lastAttackResult);
            } // go on with strategie ;)
            else if (strategie != null) {
                toHit = strategie.getNextAttack(lastAttackResult);
                // to do chech if allready in hit fields
                if (toHit == null) {
                    strategie = null;
                    toHit = getRandomCoordinates();
                }
            } else {
                toHit = getRandomCoordinates();
            }

        } while (alreadyHit.contains(toHit));
        alreadyHit.add(toHit);
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
    public ePlayerState getPlayerState() {
        return state;
    }

    @Override
    public void setState(ePlayerState state) {
        this.state = state;
    }

    class AttackStrategie {

        private int direction;
        private Coordinates origin;
        private Coordinates lasthit;

        public AttackStrategie(Coordinates origin) {
            this.origin = origin;
            lasthit = new Coordinates(origin.getX(), origin.getY());
            // rigth
            direction = 0;
        }

        public Coordinates getNextAttack(boolean lastDidHit) throws CloneNotSupportedException {
            Coordinates nextToHit = new Coordinates();

            //next direction
            if (lastDidHit == false) {
                // reset
                lasthit = new Coordinates(origin.getX(), origin.getY());
                direction++;

            }
            
            do {
                nextToHit = getNextCoordinates();
                if(direction != -1&& nextToHit == null)
                {
                 direction++;
                // reset
                lasthit = new Coordinates(origin.getX(), origin.getY());
                }
            }
            while(nextToHit == null && direction != -1);
            

            return nextToHit;
        }

        private Coordinates getNextCoordinates() throws CloneNotSupportedException {

            try
            {
            
            Coordinates lastHitCopy =  lasthit.clone();
            switch (direction) {
                case 0:
                    if (lasthit.getX() == 9) {
                        return null;
                    }
                    lastHitCopy.setX(lasthit.getX() + 1);
                    lastHitCopy.setY(lasthit.getY());
                    break;
                // Left
                case 1:
                    if (lasthit.getX() == 0) {
                        return null;
                    }
                    lastHitCopy.setX(lasthit.getX() - 1);
                    lastHitCopy.setY(lasthit.getY());
                    break;
                //Top
                case 2:
                    if (lasthit.getY() == 0) {
                        return null;
                    }
                    lastHitCopy.setX(lasthit.getX());
                    lastHitCopy.setY(lasthit.getY() - 1);
                    break;
                //Down
                case 3:
                    if (lasthit.getY() == 9) {
                        return null;
                    }
                    lastHitCopy.setX(lasthit.getX());
                    lastHitCopy.setY(lasthit.getY() + 1);
                    break;
                default:
                    direction =-1;
                    return null;

            }
            lasthit = lastHitCopy;
            return lastHitCopy;
            }
            catch(CloneNotSupportedException asdf)
            {
                return null;
            }
            
        }
    }
}