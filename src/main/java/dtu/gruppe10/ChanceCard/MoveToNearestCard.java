package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.App;
import dtu.gruppe10.board.fields.FieldType;

import java.util.Arrays;

public class MoveToNearestCard extends MoveCard {
    protected int[] positions;

    public MoveToNearestCard(int ID, int[] positionIndexes) {
        super(ID, 0);

        positions = Arrays.stream(positionIndexes).sorted().toArray();
    }

    public int nearestIndex(int currentIndex) {
        int nearest = positions[positions.length - 1];

        for (int i = positions.length - 1; i >= 0; --i) {
            if (positions[i] >= currentIndex) {
                nearest = positions[i];
            }
        }
        return nearest;
    }
}
