package dtu.gruppe10.board;


import dtu.gruppe10.Player;
import dtu.gruppe10.board.fields.Field;
import dtu.gruppe10.board.fields.JailField;
import dtu.gruppe10.board.fields.PropertyField;

import java.util.ArrayList;
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

    public Field[] getFields() {
        return fields;
    }

    public Board(Field[] fields, Player[] players) {
        this(fields, players, 0);
    }

    public Field getFieldAt(int index) {
        return fields[index];
    }

    public int getPrisonIndex() {
        for (int i = 0; i < FieldCount; ++i) {
            Field currentField = getFieldAt(i);
            if (currentField instanceof JailField) {
                return i;
            }
        }
        return -1;
    }

    public int getPlayerPosition(int playerId) {
        return playerPositions.get(playerId);
    }

    public PlayerMovement generateForwardMove(int playerId, int amount) {
        int startPos = getPlayerPosition(playerId);
        return PlayerMovement.ForwardMove(startPos, (startPos + amount) % FieldCount);
    }

    public PlayerMovement generateForwardMoveToField(int playerId, int endPosition){
        int startPos = getPlayerPosition(playerId);
        return PlayerMovement.ForwardMove(startPos,endPosition);
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

    public PropertyField[] getProperty(int playerId){
        ArrayList<PropertyField> properties = new ArrayList<>();
        for (Field field : fields) {
            if (field instanceof PropertyField) {
                properties.add((PropertyField) field);
            }
        }
        return properties.toArray(new PropertyField[0]);
    }
}
