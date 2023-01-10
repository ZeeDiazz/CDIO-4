package dtu.gruppe10;

public class Player {
    public final int ID;
    public final Account Account;

    public Player(int id, int startingBalance) {
        this.Account = new Account(startingBalance);
        this.ID = id;
    }

    public static void payRent(Player receiver, Player payer, int rent) {
        receiver.Account.add(rent);
        payer.Account.subtract(rent);
    }
}