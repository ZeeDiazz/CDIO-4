package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Game;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;

public class OtherPlayersMoneyCard extends MoneyCard {

    OtherPlayersMoneyCard(int ID, int amount) {
        super(ID, amount);
    }

    public int calculateReceivingAmount(int playerCount) {
        return this.amount * playerCount;
    }
}
