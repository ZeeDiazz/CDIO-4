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

}
