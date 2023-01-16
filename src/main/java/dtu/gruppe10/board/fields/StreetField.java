package dtu.gruppe10.board.fields;

import dtu.gruppe10.App;

public class StreetField extends PropertyField {
    public final StreetColor Color;
    public final int PropertiesInSet;
    protected int houseCount;

    protected int housePrice;

    public StreetField(int id, int price, int housePrice, int[] rentSteps, StreetColor color, int propertiesInSet) {
        super(id, FieldType.STREET, price, rentSteps);

        this.Color = color;
        this.houseCount = 0;
        this.PropertiesInSet = propertiesInSet;
        this.housePrice = housePrice;
    }

    public int getHouseCount() {
        return houseCount;
    }

    public boolean ownsAllInSet() {
        int counter = 0;
        for (Field field : App.game.Board.getFields()) {
            if (inSameSet(field) && ((StreetField) field).getOwner().equals(this.owner)) {
                counter++;
            }
        }
        return this.PropertiesInSet == counter;
    }

    // Er ikke sikker på om denne klasse skal trække penge fra spilleren
    public void buildOneHouse() {
        if (ownsAllInSet() && hasEnoughHousesOnOtherFieldsToBuildOneHouse()) {
            //this.owner.Account.subtract(this.housePrice);
            this.houseCount++;
        }
    }

    // Ellers er denne metode til tjekke om man kan bygge et hus på et felt
    public boolean eligibleToBuildHouse() {
        // Hvis ejerens pengebeholdning skal tages i betragtning
        if (this.owner.Account.getBalance() > housePrice) {
            return hasEnoughHousesOnOtherFieldsToBuildOneHouse() && ownsAllInSet();
        }

        return false;
    }


    public boolean hasEnoughHousesOnOtherFieldsToBuildOneHouse() {
        if (!ownsAllInSet()) {
            return false;
        }

        for (Field field : App.game.Board.getFields()) {
            if (inSameSet(field)) {
                if (this.houseCount > ((StreetField) field).houseCount) {
                    return false;
                }
            }
        }
        return true;
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

    @Override
    public boolean inSameSet(PropertyField propertyField) {
        if (propertyField instanceof StreetField streetField) {
            return this.Color == streetField.Color;
        }
        return false;
    }
}

