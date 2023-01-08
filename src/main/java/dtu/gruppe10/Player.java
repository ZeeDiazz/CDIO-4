package dtu.gruppe10;

public class Player {
    private static final int NUM_SPACES = 40;
    protected String name;
    private Account balance;
    private int position;
    private int ID;
    private boolean isBankrupt;

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

    public void move(int dice1, int dice2) {
        int spacesToMove = dice1 + dice2;
        // Find the player's current position on the board
        int currentPosition = this.getPosition();
        // Calculate the player's new position
        int newPosition = (currentPosition + spacesToMove) % NUM_SPACES;
        // Set the player's position to the new position
        this.setPosition(newPosition);
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
        int newPosition = (currentPosition + spacesToMove) % NUM_SPACES;
        this.setPosition(newPosition);
    }

}
