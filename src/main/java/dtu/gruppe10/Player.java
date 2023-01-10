package dtu.gruppe10;

import dtu.gruppe10.board.PlayerMovement;

public class Player {
    public final int ID;
    public final Account Account;
    protected String name;

    public Player(int id, String name, int startingBalance) {
        this.name = name;
        this.Account = new Account(startingBalance);
        this.ID = id;
    }

    public static void payRent(Player receiver, Player payer, int rent) {
        receiver.Account.add(rent);
        payer.Account.subtract(rent);
    }
}