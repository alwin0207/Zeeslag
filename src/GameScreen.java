import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends Component {
    //todo: Zorgen dat er nog een omrekenpunt komt naar de data. Dat je kan kijken of er een schip zit. Kan door verwijzing naar gamefield te maken.
    GameField playerA = new GameField(1);
    GameField playerB = new GameField(2);
    GameField player;
    GameField enemyPlayer;
    ArrayList<GameTile> boardStatePlayerA = playerA.getTileField();
    ArrayList<GameTile> boardStatePlayerB = playerB.getTileField();
    int currentPlayer = 1;
    private int mouseX = 0;
    private int mouseY = 0;
    private Coordinates mouseCoordinates = new Coordinates(0,0);
    private boolean horizontal = true;
    int actionState = 0; // todo: Zorgen dat hier betekenis achter komt met een uitleg van de gamestates. 1 = placingShip, 2 = readyToShoot,
    private GameStates gameState = GameStates.PLACE_MINESWEEPER;
    private GameStates nextGameState = GameStates.WAITING;
    boolean waitingScreen = false;

    public GameScreen(){
        this.player = playerA;
        this.enemyPlayer = playerB;
    }


    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        if (currentPlayer == 1){
            g2d.setPaint(Color.BLUE);
            g2d.fillRect(0,0,2000, 2000);
        }
        else{
            g2d.setPaint(Color.MAGENTA);
            g2d.fillRect(0,0,2000, 2000);
        }
        tileFiller(g2d, this.boardStatePlayerA);
        tileFiller(g2d, this.boardStatePlayerB);
        if(this.gameState == GameStates.WIN){
            g2d.setPaint(Color.LIGHT_GRAY);
            g2d.fill(new Rectangle(400, 140, 500, 100));
            g2d.setPaint(Color.RED);
            String winner = "Player " + currentPlayer + " won the game!";
            g2d.setFont(new Font("Serif", Font.BOLD, 40));
            g2d.drawString(winner, 450, 200);
        }
        if(this.waitingScreen == true){
            g2d.setPaint(Color.LIGHT_GRAY);
            g2d.fill(new Rectangle(0, 0, 2000, 2000));
        }
    }
    private void tileFiller(Graphics2D g2d, ArrayList<GameTile> boardState){
        for(GameTile tile : boardState){

            boolean owned = (tile.ownerField == currentPlayer);
            // todo: Add a Color getter to GameTile so setpaint can be dependend on ownerfiel == currentPlayer. This way i can remove the second switch statement.

            if(tile.getHovered()){
                g2d.setPaint(Color.GRAY);
                g2d.fill(tile.getTile(owned));
            }
            else{
                g2d.setPaint(Color.WHITE);
                g2d.fill(tile.getTile(owned));
            }

            if(owned){ g2d.setPaint(Color.BLACK); }
            else{ g2d.setPaint(Color.RED); }
            g2d.draw(tile.getTile(owned));

            switch(tile.getFieldState()){
                case(1):
                    if(owned){
                        g2d.setPaint(Color.BLACK);
                        g2d.fill(tile.getCircle(true));
                    }
                    break;
                case(2):
                    g2d.setPaint(Color.BLACK);
                    g2d.fill(tile.getCircle(owned));
                    g2d.setPaint(Color.RED);
                    g2d.fill(tile.getCrossA(owned));
                    g2d.fill(tile.getCrossB(owned));
                    break;
                case(3):
                    g2d.setPaint(Color.RED);
                    g2d.fill(tile.getCircle(owned));
                    g2d.setPaint(Color.RED);
                    g2d.fill(tile.getCrossA(owned));
                    g2d.fill(tile.getCrossB(owned));
                    break;
                case(4):
                    g2d.setPaint(Color.BLACK);
                    g2d.fill(tile.getCrossA(owned));
                    g2d.fill(tile.getCrossB(owned));
                    break;
            }


        }
    }

    public void action(int x, int y){
        System.out.println("X: " + x );
        System.out.println("Y: " + y );
        if (currentPlayer == 1){
            this.player = playerA;
            this.enemyPlayer = playerB;
        }
        else{
            this.player = playerB;
            this.enemyPlayer = playerA;
        }

        switch(this.gameState.gameStateNumber){
            case(0):

                this.waitingScreen = false;
                this.nextGameState();
                break;
            case(1):
                if(player.placeMineSweeper(new Coordinates(x,y), horizontal)){
                    player.updateFieldPlacement();
                    this.repaint();
                    this.nextGameState();
                }
                break;
            case(2):
                if(player.placeSubmarine(new Coordinates(x,y), horizontal)){
                    player.updateFieldPlacement();
                    this.repaint();
                    this.nextGameState();
                }
                break;
            case(3):
                if(player.placeCruiser(new Coordinates(x,y), horizontal)){
                    player.updateFieldPlacement();
                    this.repaint();
                    this.nextGameState();
                }
                break;
            case(4):
                if(player.placeBattleship(new Coordinates(x,y), horizontal)){
                    player.updateFieldPlacement();
                    this.repaint();
                    this.nextGameState();
                }
                break;
            case(5):
                if(player.placeCarrier(new Coordinates(x,y), horizontal)){
                    player.updateFieldPlacement();
                    this.repaint();
                    player.clearHover();
                    this.nextGameState();
                }
                break;
            case(6):
                enemyPlayer.shoot(new Coordinates(x,y));
                if (enemyPlayer.checkLoss()){
                    winMessage();
                    this.gameState = GameStates.WIN;
                    this.repaint();
                }
                else{
                    this.repaint();
                    this.nextGameState();
//                    this.currentPlayer = this.currentPlayer % 2 + 1; // weird switch between 1 & 2;

                }
                break;
            default:
                break;
        }
        System.out.println(this.gameState);
    }

    private void winMessage(){
        System.out.printf("Player %d won the game!", this.currentPlayer);
    }

    public void hoverUpdate(){
        if (currentPlayer == 1){
            this.player = playerA;
            this.enemyPlayer = playerB;
        }
        else{
            this.player = playerB;
            this.enemyPlayer = playerA;
        }
        switch(this.gameState.gameStateNumber){
            case(0):
                this.waitingScreen = true;
                this.repaint();
                break;
            case(1):
                player.updateHover(mouseCoordinates, this.horizontal, ShipType.MINESWEEPER);
                this.repaint();
                break;
            case(2):
                player.updateHover(mouseCoordinates, this.horizontal, ShipType.SUBMARINE);
                this.repaint();
                break;
            case(3):
                player.updateHover(mouseCoordinates, this.horizontal, ShipType.CRUISER);
                this.repaint();
                break;
            case(4):
                player.updateHover(mouseCoordinates, this.horizontal, ShipType.BATTLESHIP);
                this.repaint();
                break;
            case(5):
                player.updateHover(mouseCoordinates, this.horizontal, ShipType.CARRIER);
                this.repaint();
                break;
            case(6):
                enemyPlayer.updateHover(mouseCoordinates);
                player.clearHover();
                this.repaint();
                break;
            default:
                break;
        }
    }

    public void startPlacement(){
        this.gameState = GameStates.PLACE_MINESWEEPER;
    }

    private void nextGameState(){
        if(this.gameState.gameStateNumber == GameStates.SHOOT.gameStateNumber){
            this.nextGameState = GameStates.SHOOT;
            this.gameState = GameStates.WAITING;
        }
        else if(this.gameState.gameStateNumber == GameStates.PLACE_CARRIER.gameStateNumber){
            if(this.currentPlayer == 1){
                this.nextGameState = GameStates.PLACE_MINESWEEPER;
            }
            else{
                this.nextGameState = GameStates.SHOOT;
            }
            this.gameState = GameStates.WAITING;
        }
        else if (this.gameState.gameStateNumber == GameStates.WAITING.gameStateNumber){
            this.gameState = this.nextGameState;
            this.currentPlayer = this.currentPlayer % 2 + 1;
        }
        else{
            this.gameState = this.gameState.getNextGameState();
        }
    }

    public void setMouseX(int mouseX) {
//        this.mouseCoordinates.setColumn(mouseX);
        this.mouseX = mouseX;
    }

    public void setMouseY(int mouseY) {
//        this.mouseCoordinates.setRow(mouseY);
        this.mouseY = mouseY;
    }

    public void updateMouseCoordinates(){
        this.mouseCoordinates = new Coordinates(mouseX, mouseY);
    }

    public void toggleHorizontal() {
        this.horizontal = !this.horizontal;
    }
}
