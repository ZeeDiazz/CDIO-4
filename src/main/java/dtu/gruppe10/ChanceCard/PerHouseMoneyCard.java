package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;

public class PerHouseMoneyCard extends MoneyCard {
    int amount;
    int houses=0; // this will be changed to where the house
    PerHouseMoneyCard(int ID, int amount) {
        super(ID);
        this.amount = amount;
    }

    public int getAmount() {
        int houseMultiplier;
        switch (houses) {
            case 0:
                houseMultiplier = amount;
                break;
            case 1:
                houseMultiplier = amount * 2;
                break;
            case 2:
                houseMultiplier = amount * 3;
                break;
            case 3:
                houseMultiplier = amount * 4;
                break;
            case 4:
                houseMultiplier = amount * 5;
                break;
            default:
                houseMultiplier = amount * 5;
                break;
        }
        return houseMultiplier;
        //housecount = getHouseCount();
        //switch (housecount)
    }
}