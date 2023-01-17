package dtu.gruppe10;

import dtu.gruppe10.ChanceCard.Inventory;

public class Player {
    public final int ID;
    public final Account Account;
    public Inventory jailCards;

    public Player(int id, int startingBalance) {
        this.Account = new Account(startingBalance);
        this.ID = id;
        this.jailCards = new Inventory();
    }

    public static void payRent(Player receiver, Player payer, int rent) {
        receiver.Account.add(rent);
        payer.Account.subtract(rent);
    }
}