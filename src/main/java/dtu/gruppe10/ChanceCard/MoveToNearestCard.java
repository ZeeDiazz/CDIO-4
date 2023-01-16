package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.App;
import dtu.gruppe10.board.fields.FieldType;

public class MoveToNearestCard extends MoveCard {
    int posistionIndex;

    public MoveToNearestCard(int ID, int positionIndex) {
        super(ID, positionIndex);
    }

    public int moveToNearestFerry(int currentIndex) {


        for (int i = 0; !(i == currentIndex - 1); i++) {
            if (App.game.Board.getFieldAt(i).Type == FieldType.FERRY) {
                return i;
            }

            if (i == App.game.Board.FieldCount) {
                i = 0;
            }
        }

        App.game.Board.getFieldAt(currentIndex);

        return -1;

    }


}
