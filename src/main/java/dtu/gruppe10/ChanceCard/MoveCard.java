package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;

public abstract class MoveCard extends ChanceCard {
    protected int moveNum;
    public MoveCard(int ID, int moveNum) {
        super(ID);

        this.moveNum = moveNum;

    }
}
