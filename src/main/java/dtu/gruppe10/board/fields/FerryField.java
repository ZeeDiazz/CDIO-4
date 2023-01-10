package dtu.gruppe10.board.fields;

public class FerryField extends PropertyField {
    public FerryField(int id, int price, int[] rentSteps) {
        super(id, FieldType.FERRY, price, rentSteps);
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
