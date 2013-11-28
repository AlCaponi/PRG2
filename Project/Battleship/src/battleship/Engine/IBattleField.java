package battleship.Engine;


public interface IBattleField {

	/**
	 * 
	 * @param shipToPlace
	 */
	boolean setShip(Ship shipToPlace);

	/**
	 * 
	 * @param x
	 * @param y
	 */
	boolean hitField(int x, int y);

	double getHitCount();

	void applyShipPositions();

}