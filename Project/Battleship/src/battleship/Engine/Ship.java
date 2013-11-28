package battleship.Engine;

public class Ship {

	private int size;
	private eOrientation orientation;
	private int x;
	private int y;

	public int getSize() {
		return this.size;
	}

	/**
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		this.size = size;
	}

	public eOrientation getOrientation() {
		return this.orientation;
	}

	/**
	 * 
	 * @param orientation
	 */
	public void setOrientation(eOrientation orientation) {
		this.orientation = orientation;
	}

	public int getX() {
		return this.x;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return this.y;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}

}