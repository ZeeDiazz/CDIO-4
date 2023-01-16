package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;

public class PerHouseMoneyCard extends MoneyCard {
    int amount;
    Player player;

    PerHouseMoneyCard(int ID, int amount) {
        super(ID);
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
        /*int houseMultiplier;
        switch () {
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
    }*/
    }
}