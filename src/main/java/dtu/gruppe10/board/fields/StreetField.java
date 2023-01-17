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

    public int getHousePrice() {
        return housePrice;
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
        if (eligibleToBuildHouse()) {
            this.houseCount++;
        }
    }

    // Ellers er denne metode til tjekke om man kan bygge et hus på et felt
    public boolean eligibleToBuildHouse() {
        // Hvis ejerens pengebeholdning skal tages i betragtning
        if (this.houseCount < 5 && this.owner.Account.getBalance() >= housePrice) {
            return hasEnoughHousesOnOtherFieldsToBuildOneHouse() && ownsAllInSet();
        } else {
            if (this.owner.Account.getBalance() >= housePrice * 5) {
                return hasEnoughHousesOnOtherFieldsToBuildOneHouse() && ownsAllInSet();
            }
        }
        return false;

    }

    // Er ikke sikker på om denne klasse skal tilføje penge fra spilleren
    public void sellOneHouse() {
        if (eligibleToSellHouse()) {
            this.houseCount = this.houseCount - 1;
        }
    }

    // Ellers er denne metode til tjekke om man kan sælge et hus på et felt
    public boolean eligibleToSellHouse() {
        return hasEnoughHousesOnOtherFieldsToSellOneHouse() && this.houseCount > 0;
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

    public boolean hasEnoughHousesOnOtherFieldsToSellOneHouse() {
        int counter = 0;
        if (counter != this.PropertiesInSet) {
            for (Field street : App.game.Board.getFields()) {
                if (street == this) {
                    counter++;
                } else {
                    if (street instanceof StreetField) {
                        if (((StreetField) street).inSameSet(this) && ((StreetField) street).owner == this.owner) {
                            // Hvis felt 1 har færre huse end felt 2, kan huset på felt 1 ikke sælges
                            if (this.houseCount < ((StreetField) street).houseCount) {
                                return false;
                            }
                            counter++;
                        }
                    }
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

