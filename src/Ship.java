import java.util.ArrayList;

public class Ship {
    ShipType shipType;
    int size;
    ArrayList<Coordinates> shipCoordinates;
    boolean[] shotCoordinates;

    public Ship(ShipType shipType, Coordinates start, boolean horizontal){
        this.shipCoordinates = shipType.getCoordinates(start, horizontal);
        this.shipType = shipType;
        this.size = shipType.shipSize;
        this.shotCoordinates = new boolean[shipType.shipSize];
    }

    public Ship(ShipType shipType, ArrayList<Coordinates> shipCoordinates){
        this.shipCoordinates = shipCoordinates;
        this.shipType = shipType;
        this.size = shipType.shipSize;
        this.shotCoordinates = new boolean[shipType.shipSize];
    }

    public boolean isHit(Coordinates shotLocation){
        for(Coordinates point: shipCoordinates){
            if (shotLocation.checkCoordinates(point)){
                shotCoordinates[shipCoordinates.indexOf(point)] = true;
                return true;
            }
        }
        return false;
    }

    public boolean destroyed(){
        for(boolean location : shotCoordinates){
            if(!location){
                return false;
            }
        }
        return true;
    }

    public boolean overLaps(ArrayList<Coordinates> shipB){
        for(Coordinates square : shipCoordinates){
            for(Coordinates shipBSquare : shipB){
                if(square.checkCoordinates(shipBSquare)){
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Coordinates> getShipCoordinates() {
        return shipCoordinates;
    }
}
