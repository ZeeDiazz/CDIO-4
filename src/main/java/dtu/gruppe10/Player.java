package dtu.gruppe10;

import dtu.gruppe10.Players.PlayerMovement;

public class Player {
    private static final int NUM_SPACES = 40;
    protected String name;
    public Account balance;
    private boolean eliminated;
    private boolean rolledPair;
    private int position;
    private boolean InPrison;
    private int ID;
    private boolean isBankrupt;
    private PlayerMovement movement;

    public Player(String name, int balance, int ID) {
        this.name = name;
        this.balance = new Account(balance);
        this.ID = ID;
        this.position = 0;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
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

    public void movePlayer(int dice1, int dice2) {
        int spacesToMove = dice1 + dice2;
        int currentPosition = this.getPosition();
        int prevPosition = this.position;
        int newPosition = (currentPosition + spacesToMove) % NUM_SPACES;
        this.setPosition(newPosition);
        this.checkPassedStartField(prevPosition);
    }

    public void checkPassedStartField(int prevPosition) {
        if (this.position < prevPosition) {
            this.balance.setBalance(4000);
        }
    }

    //Not sure
    public void addToBalance(int amount) {
        this.balance.add(amount);
    }

    public void subtractFromBalance(int amount) {
        this.balance.subtract(amount);
    }


    public boolean isEliminated() {
        return eliminated;
    }
    public boolean hasRolledPair() {
        return this.rolledPair;
    }

    public void setRolledPair(boolean rolledPair) {
        this.rolledPair = rolledPair;
    }

    public boolean InPrison() {
        return InPrison;
    }
    public void setInPrison(boolean inPrison) {
        InPrison = inPrison;
    }
}
