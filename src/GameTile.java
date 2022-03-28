import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class GameTile {
    ArrayList<Shape> tileContent = new ArrayList<>();
    int ownerField;
    int fieldState = 0;
    boolean hovered = false;
    Coordinates fieldLocation;
    Ellipse2D circle;
    Rectangle crossA;
    Rectangle crossB;
    Ellipse2D enemyCircle;
    Rectangle enemyCrossA;
    Rectangle enemyCrossB;
    Rectangle tile;
    Rectangle enemyTile;
    static int offsetX = 10;
    static int offsetY = 10;
    static int fieldSize = 50;
    static int offsetFrameBar = 32;

    public GameTile(int player, Coordinates fieldLocation){
        this.ownerField = player;
        this.fieldLocation = fieldLocation;
        this.updateShapes();
    }

    public void setFieldState(int state){
        this.fieldState = state;
        this.updateShapes();
    }

    public int getFieldState(){
        return this.fieldState;
    }

    public boolean getHovered(){
        return this.hovered;
    }

    private void updateShapes(){
        this.crossA = new Rectangle(offsetX + fieldLocation.getColumnNumber() * fieldSize + 5, offsetY + fieldLocation.getRow() * fieldSize +20, fieldSize - 10, 10);
        this.crossB = new Rectangle(offsetX + fieldLocation.getColumnNumber() * fieldSize + 20, offsetY + fieldLocation.getRow() * fieldSize +5, 10, fieldSize - 10);
        this.circle = new Ellipse2D.Double(offsetX + fieldLocation.getColumnNumber() * fieldSize + 10, offsetY + fieldLocation.getRow() * fieldSize + 10, 30, 30);
        this.enemyCrossA = new Rectangle(offsetX + fieldLocation.getColumnNumber() * fieldSize + 5 + 600, offsetY + fieldLocation.getRow() * fieldSize +20, fieldSize - 10, 10);
        this.enemyCrossB = new Rectangle(offsetX + fieldLocation.getColumnNumber() * fieldSize + 20 + 600, offsetY + fieldLocation.getRow() * fieldSize +5, 10, fieldSize - 10);
        this.enemyCircle = new Ellipse2D.Double(offsetX + fieldLocation.getColumnNumber() * fieldSize + 10 + 600, offsetY + fieldLocation.getRow() * fieldSize + 10, 30, 30);
        this.tile = new Rectangle(offsetX + fieldLocation.getColumnNumber() * fieldSize, offsetY + fieldLocation.getRow() * fieldSize, fieldSize, fieldSize);
        this.enemyTile = new Rectangle(offsetX + fieldLocation.getColumnNumber() * fieldSize + 600, offsetY + fieldLocation.getRow() * fieldSize, fieldSize, fieldSize);
    }

    public static void setDimensions(int offSetX, int offSetY){
        offsetX = offSetX;
        offsetY = offSetY;
    }

    public Rectangle getCrossB(boolean isShipScreen){
        if(isShipScreen){return crossB;}
        else{return enemyCrossB;}
    }

    public Rectangle getCrossA(boolean isShipScreen){
        if(isShipScreen){return crossA;}
        else{return enemyCrossA;}
    }

    public Ellipse2D getCircle(boolean isShipScreen){
        if(isShipScreen){return circle;}
        else{return enemyCircle;}
    }

    public Rectangle getTile(boolean isShipScreen){
        if(isShipScreen){return tile;}
        else{return enemyTile;}
    }

    public static int getRowReference(int y){
        int relativeY = y - offsetY;
//        if (relativeY >= 600){
//            relativeY -= 600;
//        }
        int correctedRelativeY = relativeY - offsetFrameBar;// + fieldSize;
        int row = (correctedRelativeY - ((correctedRelativeY) % fieldSize))/ fieldSize; // this is always getting us whole numbers.

        if (row >= 11){
            return 10;
        }
        else if (row <= 0){
            return 1;
        }
        else{
            return row;
        }
    }

    public static Columns getColumnReference(int x){
        int relativeX = x - offsetY;
        if (relativeX >= 600){
            relativeX -= 600;
        }
        int correctedRelativeX = relativeX;// + fieldSize;
        int columnNumber = (correctedRelativeX - ((correctedRelativeX) % fieldSize))/ fieldSize; // this is always getting us whole numbers.

        if (columnNumber >= 11){
            return Columns.getColumn(10);
        }
        else if (columnNumber <= 0){
            return Columns.getColumn(1);
        }
        else{
            return Columns.getColumn(columnNumber);
        }
    }

    public void setHovered(boolean hovered){
        this.hovered = hovered;
    }

}
