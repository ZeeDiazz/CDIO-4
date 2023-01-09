package dtu.gruppe10.fields;

public class FerryField extends PropertyField {
    public FerryField(int id, int price, int[] rentSteps) {
        super(id, FieldType.FERRY, price, rentSteps);
    }

    @Override
    public int getCurrentRent(int propertiesInSetOwned) {
        return rentSteps[propertiesInSetOwned - 1];
    }
}
