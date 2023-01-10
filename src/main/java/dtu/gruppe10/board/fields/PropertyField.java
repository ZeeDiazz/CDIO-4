package dtu.gruppe10.board.fields;

import dtu.gruppe10.Player;

public abstract class PropertyField extends Field {
    public final int Price;
    protected Player owner;
    protected boolean isMortgaged;
    protected int[] rentSteps;

    public PropertyField(int id, FieldType type, int price, int[] rentSteps) {
        super(id, type);

        this.Price = price;
        this.owner = null;
        this.isMortgaged = false;
        this.rentSteps = rentSteps;
    }

    public abstract int getCurrentRent(int propertiesInSetOwned);

    public Player getOwner() {
        return owner;
    }

    public boolean isOwned() {
        return getOwner() != null;
    }

    public void newOwner(Player newOwner) {
        this.owner = newOwner;
    }

    public void mortgage() {
        isMortgaged = true;
    }

    public void unMortgage() {
        isMortgaged = false;
    }

    public abstract boolean inSameSet(PropertyField propertyField);
    public boolean inSameSet(Field field) {
        if (field instanceof PropertyField propertyField) {
            return inSameSet(propertyField);
        }
        return false;
    }

    public boolean inSetWith(PropertyField propertyField) {
        if (propertyField.equals(this)) {
            return false;
        }
        return inSameSet(propertyField);
    }

    public boolean inSetWith(Field field) {
        if (field instanceof PropertyField propertyField) {
            return inSetWith(propertyField);
        }
        return false;
    }

    /* Til når man kan bygge huse

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
        }
    */
}
