package battleship.Engine;

import java.util.ArrayList;

public class BattleField implements IBattleField {
    
    private int width = 10;
    private int heigth = 10;
    private ArrayList<Ship> ships;
    private Field[][] fields;
    
    public BattleField(int width, int heigth) {
        if (width > 5) {
            this.width = width;
        }
        if (heigth > 5) {
            this.width = heigth;
        }
        
        ships = new ArrayList<>();
        
        initFields();
        
    }
    
    private void initFields() {
        fields = new Field[getWidth()][getHeigth()];
        for (int i = 0; i < getWidth(); i++) {
            for (int j = 0; j < getHeigth(); j++) {
                Field field = new Field();
                fields[i][j] = field;
            }
        }
        
    }
    
    @Override
    public boolean setShip(Ship shipToPlace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public boolean hitField(int x, int y) {
        
        Field tolook = getFields()[x][y];
        if (tolook.getFieldState() == eFieldState.Empty) {
            tolook.setBattleState(eFieldBattleState.Missed);
            return false;
        } else {
            tolook.setBattleState(eFieldBattleState.Hit);
            return true;
        }
    }
    
    @Override
    public double getHitCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void applyShipPositions() {
        buildFinalFieldList();
    }
    
    private void buildFinalFieldList() {
        // set field values foreach ship which was placed
        for (Ship s : ships) {
            for (int i = 0; i < s.getSize(); i++) {
                if (s.getOrientation() == eOrientation.Horizontal) {
                    getFields()[s.getX() + i][s.getY()].setShip(s);
                } else {
                    getFields()[s.getX()][s.getY() + i].setShip(s);
                }
            }
        }
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return the heigth
     */
    public int getHeigth() {
        return heigth;
    }

    /**
     * @return the fields
     */
    public Field[][] getFields() {
        return fields;
    }
}