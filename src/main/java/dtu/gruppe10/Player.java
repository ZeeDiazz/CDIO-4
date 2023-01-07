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

    //Setter for balancen. Bruger metoden setBalance fra Account-klassen
    public void setBalance(int balance) {
        this.balance.setBalance(balance);
    }

    // Metode der afgør om en spiller er bankerot/kan betale sin afgift
    // Bruger metode isBankrupt fra Account-klassen
    public boolean isBankrupt(int fees) {
        if (this.balance.isBankrupt(this, fees)) {
            this.isBankrupt = true;
            return true;
        }
        return false;
    }


    // Metode der håndterer opkrævning af leje
    // Bruger metoden handleRent fra Account-klassen
    public void handleRent(Player payer, int fees) {
        this.balance.handleRent(this, payer, fees);
    }

}
