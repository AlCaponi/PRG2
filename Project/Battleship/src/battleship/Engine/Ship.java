package battleship.Engine;

import java.util.ArrayList;

public class Ship {

    private int size;
    private eOrientation orientation;
    private eShipType type;
    private Coordinates startPoint;
    private ArrayList<Field> fields;

    public Ship(eShipType type) {
        this.type = type;
        switch (type) {
            case aircraftcarrier:
                size = 5;
                break;
            case battleship:
                size = 4;
            case destroyer:
                size = 3;
                break;
            case submarine:
                size = 3;
                break;
            case patrolboat:
                size = 2;
                break;
            default:
                size = 0;
        }
        fields = new ArrayList<>();
    }

    public int getSize() {
        return this.size;
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

    /**
     * @return the startPoint
     */
    public Coordinates getStartPoint() {
        return startPoint;
    }

    /**
     * @param startPoint the startPoint to set
     */
    public void setStartPoint(Coordinates startPoint) {
        this.startPoint = startPoint;
    }

    /**
     * @return the type
     */
    public eShipType getType() {
        return type;
    }

    @Override
    public int hashCode() {
        // just use the type so that you can have only one type 
        // in a hash set
        return type.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ship other = (Ship) obj;

        // just use the type so that you can have only one type 
        // in a hash set
        if (this.type != other.type) {
            return false;
        }

        //        if (this.orientation != other.orientation) {
//            return false;
//        }
//        if (!Objects.equals(this.startPoint, other.startPoint)) {
//            return false;
//        }
        return true;
    }

    public boolean Cross(Ship other) {
        boolean cross = false;
        ArrayList<Coordinates> myCord = getCoordinates();
        ArrayList<Coordinates> otherCord = other.getCoordinates();
        // wenn meine coordinaten irgend eine koordinate des anderen schiffes beinhalten kreuzen sie sich
        for (Coordinates c : otherCord) {
            if (myCord.contains(c)) {
                cross = true;
                break;
            }
        }
        return cross;
    }

    public ArrayList<Coordinates> getCoordinates() {
        ArrayList<Coordinates> coordinates = new ArrayList<>();
        for (int i = 0; i < getSize(); i++) {
            if (getOrientation() == eOrientation.Horizontal) {
                coordinates.add(new Coordinates(startPoint.getX() + i, startPoint.getY()));

            } else {
                coordinates.add(new Coordinates(startPoint.getX(), startPoint.getY() + i));
            }
        }
        return coordinates;
    }
    
        public void setFieldReference(Field field)
    {
        getFields().add(field);
    }

    /**
     * @return the fields
     */
    public ArrayList<Field> getFields() {
        return fields;
    }
}