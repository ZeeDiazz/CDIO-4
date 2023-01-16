package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;

public class MoveCard extends ChanceCard {
    Player player;
    Board board;
    int amount;
    public MoveCard(int ID, int amount) {
        super(ID);
        this.amount = amount;

    }
    public void applyAffect(){
        PlayerMovement moveTo = board.generateDirectMove(player.ID, getMoveAmount());
        board.performMove(player.ID, moveTo);
    }
    public int getMoveAmount (){
        return amount;
    }

}
