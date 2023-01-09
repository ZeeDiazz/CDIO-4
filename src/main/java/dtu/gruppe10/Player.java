package dtu.gruppe10;

import dtu.gruppe10.Players.PlayerMovement;

public class Player {
    private static final int NUM_SPACES = 40;
    protected String name;
    public final int ID;
    public final Account Account;
    private boolean rolledPair;
    private int position;
    private boolean InPrison;
    private int turnsInPrison;
    private PlayerMovement movement;

    public Player(String name, int startingBalance, int ID) {
        this.name = name;
        this.Account = new Account(startingBalance);
        this.ID = ID;
        this.position = 0;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean canPay(int fee) {
        return this.Account.getBalance() >= fee;
    }

    public void movePlayer(int dice1, int dice2) {
        int spacesToMove = dice1 + dice2;
        int currentPosition = this.getPosition();
        int prevPosition = this.position;
        int newPosition = (currentPosition + spacesToMove) % NUM_SPACES;
        this.setPosition(newPosition);
        this.checkPassedStartField(prevPosition);
    }
    public void increaseTurnsInPrison(){
        this.turnsInPrison++;
    }
    public void getsOutofPrisonByBail(){
        this.Account.subtract(50);

    }
    public void checkPassedStartField(int prevPosition) {
        if (this.position < prevPosition) {
            this.Account.add(4000);
        }
    }

    public boolean hasRolledPair() {
        return this.rolledPair;
    }

    public void setRolledPair(boolean rolledPair) {
        this.rolledPair = rolledPair;
    }

    public boolean inPrison() {
        return InPrison;
    }

    public void setInPrison(boolean inPrison) {
        InPrison = inPrison;
    }

    public static void payRent(Player receiver, Player payer, int rent) {
        receiver.Account.add(rent);
        payer.Account.subtract(rent);
    }
}