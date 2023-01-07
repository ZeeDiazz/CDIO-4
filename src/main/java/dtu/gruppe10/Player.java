package dtu.gruppe10;

public class Player {
    private String name;
    private Account balance;
    private int ID;
    private boolean isBankrupt;

    public Player(String name, int balance, int ID) {
        this.name = name;
        this.balance = new Account(balance);
        this.ID = ID;
    }

    public int getBalance() {
        return this.balance.getBalance();
    }

    public void setBalance(int balance) {
        this.balance.setBalance(balance);
    }

    public boolean isBankrupt(int fees) {
        if (this.balance.isBankrupt(this, fees)) {
            this.isBankrupt = true;
            return true;
        }
        return false;
    }

    public void handleRent(Player payer, int fees) {
        this.balance.handleRent(this, payer, fees);
    }

}
