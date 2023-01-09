package dtu.gruppe10;

public class Account {
    protected int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public boolean isBankrupt() {
        return balance < 0;
    }

    public void add(int amount) {
        if (amount < 0) {
            System.out.println("Error: Cannot add negative amount to balance.");
            return;
        }
        this.balance += amount;
    }

    public void subtract(int amount) {
        if (amount < 0) {
            System.out.println("Error: Cannot subtract negative amount from balance.");
            return;
        }
        this.balance -= amount;
    }
}
