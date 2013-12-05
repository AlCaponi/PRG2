/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship.Engine;

/**
 *
 * @author Simon
 */
public class Coordinates {

    public Coordinates() {
    }
        public Coordinates(int x,int y) {
            setX(x);
            setY(y);
    }
    private int x;
    private int y;

    public final int getX() {
        return this.x;
    }

    /**
     *
     * @param x
     */
    public final void setX(int x) {
        if (x < 0) {
            return;
        }
        this.x = x;
    }

    public final int getY() {
        return this.y;
    }

    /**
     *
     * @param y
     */
    public final void setY(int y) {
        if (y < 0) {
            return;
        }
        this.y = y;
    }
}
