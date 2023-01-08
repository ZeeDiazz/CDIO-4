package dtu.gruppe10.FieldTypes;

public class Brewery extends Property {


    private int diceSum;

    public Brewery(String name, int price, PropertyColor color, int rent0, int rent1) {
        super(name, price, color, rent0, rent1);
        this.currentRent = rent0 * this.diceSum;
    }


    public void setDiceSum(int diceSum) {
        this.diceSum = diceSum;

    }
}
