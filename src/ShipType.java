import java.util.ArrayList;

public enum ShipType {
    MINESWEEPER(2),
    SUBMARINE(3),
    CRUISER(3),
    BATTLESHIP(4),
    CARRIER(5);

    final int shipSize;

    ShipType(int shipSize){
        this.shipSize = shipSize;
    }

    public ArrayList<Coordinates> getCoordinates(Coordinates start, boolean horizontal){
        ArrayList<Coordinates> returnList = new ArrayList<Coordinates>();
        if(horizontal){
            if(start.getColumn().getColumnNumber() + this.shipSize - 1 > 10){
                return null;
            }
            for (int i = 0; i < this.shipSize; i++) {
                int columnNumber = start.getColumn().getColumnNumber() + i;
                returnList.add(new Coordinates(start.getRow(), Columns.getColumn(columnNumber)));
            }
        }
        else{
            if(start.getRow() + this.shipSize - 1 > 10){
                return null;
            }
            for(int i = 0; i< this.shipSize; i++){
                returnList.add(new Coordinates(start.getRow() + i, start.getColumn()));
            }
        }
        return returnList;
    }
}
