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
            setPlayerPosition(p.ID, startField);
        }
    }

    public Board(Field[] fields, Player[] players) {
        this(fields, players, 0);
    }

    public Field getFieldAt(int index) {
        return fields[index];
    }

    public void movePlayerForward(int playerId, int amount) {

    }

    public void setPlayerPosition(int playerId, int fieldIndex) {
        this.playerPositions.put(playerId, fieldIndex);
    }
}
