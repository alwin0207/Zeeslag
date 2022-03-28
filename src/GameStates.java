public enum GameStates {
    WAITING(0),
    PLACE_MINESWEEPER(1),
    PLACE_SUBMARINE(2),
    PLACE_CRUISER(3),
    PLACE_BATTLESHIP(4),
    PLACE_CARRIER(5),
    SHOOT(6),
    WIN(7);

    final int gameStateNumber;

    GameStates(int gameStateNumber){
        this.gameStateNumber = gameStateNumber;
    }

    public GameStates getNextGameState(){
        if (this.gameStateNumber == 6){
            return GameStates.WAITING;
        }
        else{
            return GameStates.getGameStateByNumber(this.gameStateNumber + 1);
        }
    }

    private static GameStates getGameStateByNumber(int number){
        for (GameStates state :GameStates.values()){
            if (state.gameStateNumber == number){
                return state;
            }
        }
        return null;
    }

    public GameStates cloneState(){
        return GameStates.getGameStateByNumber(this.gameStateNumber);
    }
}
