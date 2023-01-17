package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Game;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;

public class OtherPlayersMoneyCard extends MoneyCard {
    int reacievingPlayerAmount;
    int payingPlayersAmount;
    Game game;
    OtherPlayersMoneyCard(int ID, int reacievingPlayerAmount, int payingPlayersAmount) {
        super(ID);
        this.reacievingPlayerAmount = reacievingPlayerAmount;
        this.payingPlayersAmount = payingPlayersAmount;
    }

    public int calculateReceivingAmount(){
        int totalPlayers = game.getPlayersLeft().length;
        int amountOfPayingPlayers = totalPlayers -1;

        return reacievingPlayerAmount * amountOfPayingPlayers;
    }
    public int calculatePayingAmount(){
        return payingPlayersAmount;
    }

}
