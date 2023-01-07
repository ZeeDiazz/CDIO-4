package dtu.gruppe10;

public class Account {
    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }


    public void setBalance(int amount) {
        this.balance = this.balance + amount;
    }

    /**
     * @return Returns true, if player can pay their fees
     */
    public boolean isBankrupt(Player player, int fees) {
        return player.getBalance() - fees < 0;
    }

    // Metode der håndterer opkrævning af leje
    // Hvis den betalende spiller ikke bliver bankerot af den leje, der skal betales
    // køres koden. Spillernes balance bliver også opdateret i metoden
    public void handleRent(Player reciever, Player payer, int rent) {
        if (!(payer.isBankrupt(rent))) {
            reciever.setBalance(rent);
            payer.setBalance(-rent);
        }
    }


}
