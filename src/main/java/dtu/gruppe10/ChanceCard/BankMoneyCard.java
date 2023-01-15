package dtu.gruppe10.ChanceCard;

public class BankMoneyCard extends MoneyCard {
    private int amount;

    BankMoneyCard(int ID, int amount) {
        super(ID);
        this.amount = amount;
    }

    int getAmount() {
        return amount;

    }

}
