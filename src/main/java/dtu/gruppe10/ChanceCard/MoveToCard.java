package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;

public class MoveToCard extends MoveCard {
    public MoveToCard(int ID, int positionIndex) {
        super(ID, positionIndex);
    }

    public int getPositionIndex(){
        return moveNum;
    }
}

