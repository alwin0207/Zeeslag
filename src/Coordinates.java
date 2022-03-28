public class Coordinates {
    Columns column;
    int row;

    public Coordinates(int row, Columns column){
        this.row = row;
        this.column = column;
    }

    public Coordinates(int x, int y){
        this.row = GameTile.getRowReference(y);
        this.column = GameTile.getColumnReference(x);
    }

    public Columns getColumn() {
        return column;
    }

    public int getColumnNumber(){
        return this.column.getColumnNumber();
    }

    public int getRow() {
        return row;
    }

    public int getArraylistIndex(){
        if (this.getRow() <= 10 && this.getRow() >= 1 && this.getColumnNumber() <= 10 && this.getColumnNumber() >= 1){
            return (this.getRow() - 1) * 10 + (this.getColumnNumber() - 1);
        }
        return 0;
    }

    public boolean checkCoordinates(Coordinates myReference){
        if ((myReference.getColumn() == this.column) && (myReference.row == this.row)){
            return true;
        }
        else{
            return false;
        }
    }

    public void setColumn(int x){
        this.column = GameTile.getColumnReference(x);
    }

    public void setRow(int y){
        this.row = GameTile.getRowReference(y);
    }

}
