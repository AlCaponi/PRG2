package battleship.Engine;

public interface IBattleField {

    /**
     *
     * @param shipToPlace
     */
    boolean setShip(Ship shipToPlace);

    /**
     * Request erhaltent
     *
     * @param x
     * @param y
     */
    boolean hitField(int x, int y);

    /**
     * Response von gegener anzeigen
     *
     * @param x
     * @param y
     */
    void setFieldState(boolean hit, int x, int y);

    /**
     * gets porcent of the totally hit ships
     *
     * @return
     */
    int getHitCount();

    int getWidth();

    int getHeigth();

    Field[][] getFields();

    void applyShipPositions();
}