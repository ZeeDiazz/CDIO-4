package dtu.gruppe10;

import dtu.gruppe10.FieldTypes.Field;

import static dtu.gruppe10.Board.generateBoardFields;

public class Game {
    private int currentPlayerIndex;
    private Player[] players;

    private Board board;

    public Game(Player[] players) {
        this.currentPlayerIndex = 0;
        this.players = players;
        this.board = new Board(generateGameFields(),players.length,0);
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
            if (!player.Account.isBankrupt()) {
                remainingPlayers++;
            }
        }
        return remainingPlayers <= 1;
    }
    private Field[] generateGameFields() {
        return generateBoardFields();
    }
}
