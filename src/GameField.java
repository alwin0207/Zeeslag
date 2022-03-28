import java.util.ArrayList;

public class GameField {
    int[][] field;
    ArrayList<GameTile> tileField = new ArrayList<>();
    int player;

    Ship minesweeper;
    Ship cruiser;
    Ship submarine;
    Ship battleship;
    Ship carrier;

    ArrayList<Ship> placedShips;
    ArrayList<Coordinates> lastHover;

    public GameField(int player){
        this.player = player;
        this.placedShips = new ArrayList<>();
        this.lastHover = new ArrayList<>();
        this.field = new int[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                this.tileField.add(new GameTile(player, new Coordinates(i+1, Columns.getColumn(j+1))));
            }
        }
    }

    public void updateFieldPlacement(){
        for(Ship ship: this.placedShips){
            for(Coordinates square : ship.getShipCoordinates()){
                int xAxis = square.getColumn().getColumnNumber();
                int yAxis = square.getRow();
                this.field[yAxis - 1][xAxis - 1] = 1; // 1 = has ship. Beware, in game rows start at 1 but in the 2d array needs a 0.
                this.setTileState(square, 1);
            }
        }
    }

    public void updateHover(Coordinates hoverLocation, boolean horizontal, ShipType shipType){
        for (Coordinates square: this.lastHover){
            this.setHoveredTiles(square, false);
        }
//        lastHover.clear();
        ArrayList<Coordinates> shipPreview = shipType.getCoordinates(hoverLocation, horizontal);
        if (shipPreview != null){
            this.lastHover = shipType.getCoordinates(hoverLocation,horizontal);
        }
        for (Coordinates square: this.lastHover){
            this.setHoveredTiles(square, true);
        }
    }

    public void clearHover(){
        for (Coordinates square: this.lastHover){
            this.setHoveredTiles(square, false);
        }
        lastHover.clear();
    }

    public boolean checkLoss(){
        for(Ship ship: placedShips){
            if(!ship.destroyed()){
                return false;
            }
        }
        return true;
    }

    public void updateHover(Coordinates location){
        for (Coordinates mySquare : this.lastHover){
            this.setHoveredTiles(mySquare, false);
        }
        this.lastHover.clear();
        this.lastHover.add(location);
        for (Coordinates square : this.lastHover){
            this.setHoveredTiles(square, true);
        }
    }

    public boolean shoot(Coordinates shotLocation){
        for(Ship ship : this.placedShips){
            if(ship.isHit(shotLocation)){
                if (ship.destroyed()){
                    for (Coordinates shipPart: ship.shipCoordinates){
                        this.setTileState(shipPart, 3);
                    }
                }
                else {
                    this.setTileState(shotLocation, 2);
                }
                String hitMessage = (ship.destroyed()) ? "You destroyed a ship":"You hit a Ship";
                System.out.println(hitMessage);
                return true;
            }
        }
        setTileState(shotLocation, 4);
        return false;
    }


//    public void placeShips(){
//        boolean shipPlaced;
//        for (int i = 0; i < 5; i++) {
//            do{
//                Coordinates inputCo = new Coordinates(1, Columns.A);
//                boolean horizontal = true;
//                shipPlaced = placeShipSwitch(i, inputCo, horizontal);
//            }
//            while(!shipPlaced);
//        }
//    }
//
//    private boolean placeShipSwitch(int ship, Coordinates input, boolean horizontal){
//        switch(ship){
//            case 0:
//                return placeBattleship(input, horizontal);
//            case 1:
//                return placeCarrier(input, horizontal);
//            case 2:
//                return placeCruiser(input, horizontal);
//            case 3:
//                return placeSubmarine(input, horizontal);
//            case 4:
//                return placeMineSweeper(input, horizontal);
//            default:
//                return false;
//        }
//    }

    public boolean placeMineSweeper(Coordinates start, boolean horizontal){
        ArrayList<Coordinates> placement = ShipType.MINESWEEPER.getCoordinates(start, horizontal);
        if(placement != null && !overlapsWithPlacedShips(placement)){
            this.minesweeper = new Ship(ShipType.MINESWEEPER, placement);
            placedShips.add(this.minesweeper);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean placeCruiser(Coordinates start, boolean horizontal){
        ArrayList<Coordinates> placement = ShipType.CRUISER.getCoordinates(start, horizontal);
        if(placement != null && !overlapsWithPlacedShips(placement)){
            this.cruiser = new Ship(ShipType.CRUISER, placement);
            placedShips.add(this.cruiser);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean placeSubmarine(Coordinates start, boolean horizontal){
        ArrayList<Coordinates> placement = ShipType.SUBMARINE.getCoordinates(start, horizontal);
        if(placement != null && !overlapsWithPlacedShips(placement)){
            this.submarine = new Ship(ShipType.SUBMARINE, placement);
            placedShips.add(this.submarine);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean placeBattleship(Coordinates start, boolean horizontal){
        ArrayList<Coordinates> placement = ShipType.BATTLESHIP.getCoordinates(start, horizontal);
        if(placement != null && !overlapsWithPlacedShips(placement)){
            this.battleship = new Ship(ShipType.BATTLESHIP, placement);
            placedShips.add(this.battleship);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean placeCarrier(Coordinates start, boolean horizontal){
        ArrayList<Coordinates> placement = ShipType.CARRIER.getCoordinates(start, horizontal);
        if(placement != null && !overlapsWithPlacedShips(placement)){
            this.carrier = new Ship(ShipType.CARRIER, placement);
            placedShips.add(this.carrier);
            return true;
        }
        else{
            return false;
        }
    }

    private boolean overlapsWithPlacedShips(ArrayList<Coordinates> placement){
        for(Ship shipReference: this.placedShips){
            if(shipReference.overLaps(placement)){
                return true;
            }
        }
        return false;
    }

    private void setTileState(Coordinates coordinates, int state){
        this.tileField.get(coordinates.getArraylistIndex()).setFieldState(state);
    }

    private void setHoveredTiles(Coordinates coordinates, boolean hovered){
        this.tileField.get(coordinates.getArraylistIndex()).setHovered(hovered);
    }

    public ArrayList<GameTile> getTileField() {
        return tileField;
    }
}
