package dtu.gruppe10;

public class Player {
    private String name;
    private Account balance;
    private int ID;
    private boolean isBankrupt;

    public Player(String name, Account balance, int ID) {
        this.name = name;
        this.balance = balance;
        this.ID = ID;
    }

    public int getBalance() {
        return this.balance.getBalance();
    }
}
