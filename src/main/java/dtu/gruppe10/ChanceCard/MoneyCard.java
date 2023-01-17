package dtu.gruppe10.ChanceCard;

public abstract class MoneyCard extends ChanceCard {
    protected int amount;
    public MoneyCard(int ID, int amount) {
        super(ID);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }
}
