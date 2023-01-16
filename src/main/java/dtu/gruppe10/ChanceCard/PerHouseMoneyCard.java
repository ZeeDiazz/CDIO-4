package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;

public class PerHouseMoneyCard extends MoneyCard {
    int amount;

    PerHouseMoneyCard(int ID, int amount) {
        super(ID);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
