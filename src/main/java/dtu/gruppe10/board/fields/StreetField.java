package dtu.gruppe10.board.fields;

import dtu.gruppe10.App;
import dtu.gruppe10.board.Board;

public class StreetField extends PropertyField {
    public final StreetColor Color;
    public final int PropertiesInSet;
    protected int houseCount;

    protected int housePrice;

    public StreetField(int id, int price,int housePrice, int[] rentSteps, StreetColor color, int propertiesInSet) {
        super(id, FieldType.STREET, price, rentSteps);

        this.Color = color;
        this.houseCount = 0;
        this.PropertiesInSet = propertiesInSet;
        this.housePrice = housePrice;
    }


    public boolean ownsAllInSet() {
        int counter = 0;
        if (counter != this.PropertiesInSet) {
            for (Field street : App.game.Board.getFields()) {
                if (street == this) {
                    counter++;
                } else {
                    if (street instanceof StreetField) {
                        if (((StreetField) street).inSameSet(this) && ((StreetField) street).owner == this.owner) {
                            counter++;
                        }
                    }
                }
            }
        }
        return this.PropertiesInSet == counter;
    }

    public void buildOneHouse() {
        if (ownsAllInSet() && hasEnoughHousesOnOtherFieldsToBuildOneHouse()) {
            this.houseCount++;
        }
    }

    public boolean hasEnoughHousesOnOtherFieldsToBuildOneHouse() {
        int counter = 0;
        int eligible = 0;
        if (counter != this.PropertiesInSet) {
            for (Field street : App.game.Board.getFields()) {
                if (street == this) {
                    counter++;
                } else {
                    if (street instanceof StreetField) {
                        if (((StreetField) street).inSameSet(this) && ((StreetField) street).owner == this.owner) {
                            // Hvis felt 1 har flere huse end felt 2, kan der ikke bygges huse på felt 1
                            if (this.houseCount > ((StreetField) street).houseCount) {
                                return false;
                            }
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



   /* // Til når man kan bygge huse

    public void updateCurrentRent(int numberHouses) {
        if (numberHouses == 1) {
            this.currentRent = this.rent1;
        } else if (numberHouses == 2) {
            this.currentRent = this.rent2;
        } else if (numberHouses == 3) {
            this.currentRent = this.rent3;
        } else if (numberHouses == 4) {
            this.currentRent = this.rent4;
        } else if (numberHouses == 5) {
            this.currentRent = this.rent5;
        }
    }*/


}

