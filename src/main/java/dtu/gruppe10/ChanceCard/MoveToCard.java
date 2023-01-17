package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;

public class MoveToCard extends MoveCard {
    int positionIndex;
    Player player;
    Board board;

    public MoveToCard(int ID, int positionIndex) {
        super(ID, positionIndex);
        this.positionIndex = positionIndex;

    }
    public int getPositionIndex(){
        return positionIndex;
    }
}

