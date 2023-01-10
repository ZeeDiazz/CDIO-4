package dtu.gruppe10.board.fields;

public class StreetField extends PropertyField {
    public final StreetColor Color;
    public final int PropertiesInSet;
    protected int houseCount;

    public StreetField(int id, int price, int[] rentSteps, StreetColor color, int propertiesInSet) {
        super(id, FieldType.STREET, price, rentSteps);

        this.Color = color;
        this.houseCount = 0;
        this.PropertiesInSet = propertiesInSet;
    }

    @Override
    public int getCurrentRent(int propertiesInSetOwned) {
        if (isMortgaged) {
            return 0;
        }

        int rent = rentSteps[houseCount];

        boolean doubleRent = (houseCount == 0) && (propertiesInSetOwned == PropertiesInSet);
        if (doubleRent) {
            rent *= 2;
        }

        return rent;
    }
}

