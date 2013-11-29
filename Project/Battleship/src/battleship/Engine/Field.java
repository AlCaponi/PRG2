package battleship.Engine;

public class Field {

    private Ship ship;
    private eFieldState fieldState;
    private eFieldBattleState battleState;

    public eFieldState getFieldState() {
        return this.fieldState;
    }

    /**
     *
     * @param fieldState
     */
    public void setFieldState(eFieldState fieldState) {
        this.fieldState = fieldState;
    }

    public eFieldBattleState getBattleState() {
        return this.battleState;
    }

    /**
     *
     * @param battleState
     */
    public void setBattleState(eFieldBattleState battleState) {
        this.battleState = battleState;
    }

    /**
     * @return the ship
     */
    public Ship getShip() {
        return ship;
    }

    /**
     * @param ship the ship to set
     */
    public void setShip(Ship ship) {
        this.ship = ship;
    }
}