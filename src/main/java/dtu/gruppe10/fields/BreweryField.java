package dtu.gruppe10.fields;

public class BreweryField extends PropertyField {
    public BreweryField(int id, int price, int[] rentSteps) {
        super(id, FieldType.BREWERY, price, rentSteps);
    }

    public int utilityPrice(int diceSum, int propertiesInSetOwned) {
        return getCurrentRent(propertiesInSetOwned) * diceSum;
    }

    @Override
    public int getCurrentRent(int propertiesInSetOwned) {
        return rentSteps[propertiesInSetOwned - 1];
    }
}
