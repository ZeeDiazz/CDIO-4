package dtu.gruppe10.fields;

import dtu.gruppe10.Player;

public class BreweryField extends PropertyField {
    public BreweryField(int id, int price, int[] rentSteps) {
        super(id, FieldType.BREWERY, price);

        this.rentSteps = rentSteps;
    }

    public int payUtility(int diceSum, int propertiesInSetOwned) {
        return getCurrentRent(propertiesInSetOwned) * diceSum;
    }

    @Override
    public int getCurrentRent(int propertiesInSetOwned) {
        return rentSteps[propertiesInSetOwned - 1];
    }
}
