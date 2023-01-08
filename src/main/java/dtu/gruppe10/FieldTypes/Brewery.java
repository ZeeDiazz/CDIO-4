package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public class Brewery extends Property {

    public Brewery(String name, int price, PropertyColor color, int rent0, int rent1) {
        super(name, price, color, rent0, rent1);
    }


    // Skal opdatere lejen ift. hvad terningekastet for spilleren er
    public void updateCurrentRent(int diceSum) {
        this.currentRent = this.rent0 * diceSum;
    }

    // Skal opdatere lejen ift. antallet af øjne på terningekastet
    public void whenLandedOn(Player player, int diceSum) {
        this.updateCurrentRent(diceSum);
        super.whenLandedOn(player);
    }

    public void receiveRent(Player payer, int diceSum) {
        super.receiveRent(payer);
    }
}
