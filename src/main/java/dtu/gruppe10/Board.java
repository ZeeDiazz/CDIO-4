package dtu.gruppe10;


import dtu.gruppe10.fields.Field;

public class Board {
    private int numberOfFields;
    private int[] playerIndexesOnBoard;
    private Field[] fields;
    public final int NUMBER_OF_FIELDS;

    Board(Field[] fields, int numberOfPlayers, int startField){
        this.fields = fields;
        this.numberOfFields = fields.length;
        this.playerIndexesOnBoard = new int[numberOfPlayers];

        for (int i = 0; i < numberOfPlayers; i++){
            this.playerIndexesOnBoard[i] = startField;
        }
        this.NUMBER_OF_FIELDS = fields.length;
    }

    public static Field[] generateBoardFields() {
        Field[] fields = new Field[40];

        for (int i = 0; i < fields.length; i++){

        }
        return fields;
    }
}
