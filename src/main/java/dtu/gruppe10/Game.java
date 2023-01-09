package dtu.gruppe10;

public class Game {
    private int currentPlayerIndex;
    private Player[] players;

    private Board board;

    public Game(Player[] players) {
        this.currentPlayerIndex = 0;
        this.players = players;
        this.board = new Board(players.length);
    }

    public Player getCurrentPlayer() {
        return players[currentPlayerIndex];
    }
    public void nextTurn() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
    }
    public boolean isGameOver() {
        if (players == null || players.length == 0) {
            return false;
        }
        int remainingPlayers = 0;
        for (Player player : players) {
            if (!player.CheckBankrupt()) { //if the player is bankrupt
                remainingPlayers++;
            }
        }
        return remainingPlayers <= 1;
    }
}
