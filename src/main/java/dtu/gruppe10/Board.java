package dtu.gruppe10;


import dtu.gruppe10.fields.Field;

import java.util.HashMap;

public class Board {
    public final int FieldCount;
    protected Field[] fields;
    protected HashMap<Integer, Integer> playerPositions;

    public Board(Field[] fields, Player[] players, int startField){
        this.fields = fields;
        this.FieldCount = fields.length;

        this.playerPositions = new HashMap<>();
        for (Player p : players) {
            playerPositions.put(p.ID, startField);
        }
    }

    public Board(Field[] fields, Player[] players) {
        this(fields, players, 0);
    }

    public Field getFieldAt(int index) {
        return fields[index];
    }

    public int getPlayerPosition(int playerId) {
        return playerPositions.get(playerId);
    }

    public PlayerMovement generateForwardMove(int playerId, int amount) {
        int startPos = getPlayerPosition(playerId);
        return PlayerMovement.ForwardMove(startPos, (startPos + amount) % FieldCount);
    }

    public PlayerMovement generateBackwardMove(int playerId, int amount) {
        int startPos = getPlayerPosition(playerId);
        return PlayerMovement.BackwardMove(startPos, (startPos - amount + FieldCount) % FieldCount);
    }

    public PlayerMovement generateDirectMove(int playerId, int newPos) {
        int startPos = getPlayerPosition(playerId);
        return PlayerMovement.DirectMove(startPos, newPos);
    }

    public int[] getFields(PlayerMovement move, boolean includeStart, boolean includeEnd) {
        return move.getFieldIndexes(FieldCount, includeStart, includeEnd);
    }

    public void performMove(int playerId, PlayerMovement move) {
        playerPositions.put(playerId, move.End);
    }
}
