package dtu.gruppe10;

import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.fields.Field;

import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    public final Board Board;
    protected ArrayList<Player> players;
    protected int currentTurn;

    public Game(Player[] players, Field[] fields) {
        this.currentTurn = 0;
        this.players = new ArrayList<>();
        this.players.addAll(Arrays.asList(players));
        this.Board = new Board(fields, players);
    }

    public Player getCurrentPlayer() {
        return players.get(currentTurn);
    }

    public Player getPlayerById(int playerId) {
        for (Player p : players) {
            if (p.ID == playerId) {
                return p;
            }
        }
        return null;
    }

    public void removePlayer(int playerId) {
        Player toRemove = getPlayerById(playerId);
        if (toRemove == null) {
            return;
        }

        int playerIndex = 0;
        for (int i = 0; i < players.size(); ++i) {
            if (players.get(i).ID == playerId){
                playerIndex = i;
                break;
            }
        }

        players.remove(playerIndex);
        if (currentTurn <= playerIndex) {
            currentTurn--;
        }
    }

    public Player[] getPlayersLeft() {
        return players.toArray(new Player[0]);
    }

    public void nextTurn() {
        currentTurn = (currentTurn + 1) % players.size();
    }

    public boolean gameIsOver() {
        return players == null || players.size() < 2;
    }

    public int getCurrentPlayerTurn() {return currentTurn;}
}
