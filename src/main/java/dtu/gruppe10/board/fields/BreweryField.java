package dtu.gruppe10.board.fields;

public class BreweryField extends PropertyField {
    public BreweryField(int id, int price, int[] rentSteps) {
        super(id, FieldType.BREWERY, price, rentSteps);
    }

    public int utilityPrice(int diceSum, int propertiesInSetOwned) {
        return getCurrentRent(propertiesInSetOwned) * diceSum;
    }

    @Override
    public int getCurrentRent(int propertiesInSetOwned) {
        if (isMortgaged) {
            return 0;
        }

        return rentSteps[propertiesInSetOwned - 1];
    }

    @Override
    public boolean inSameSet(PropertyField propertyField) {
        return this.Type == propertyField.Type;
    }
}
