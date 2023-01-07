package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public class Property extends Field {
    // Klasse der dækker over ejendomme som man kan bygge huse på, de fire færger og de to bryggeri felter

    private int price;
    private PropertyColor color;
    private boolean isBought;           //Om ejendommen er købt. Hvis falsk, så nej, hvis sand, så ja
    public Player owner;

    private int numberOfHouses;
    private int rent0;
    private int rent1;
    private int rent2;
    private int rent3;
    private int rent4;
    private int rent5;
    private int currentRent;

    // Constructor for de to 'brewery' felter
    public Property(String name, int price, PropertyColor color, int rent0, int rent1) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.rent0 = rent0;
        this.rent1 = rent1;
        this.currentRent = rent0;
    }

    // Constructor til de fire 'ferry' felter;
    public Property(String name, int price, PropertyColor color, int rent0, int rent1, int rent2, int rent3) {
        this(name, price, color, rent0, rent1);
        this.rent2 = rent2;
        this.rent3 = rent3;
        this.currentRent = rent0;
    }


    // Constructor til felter hvor man kan bygge huse og hoteller på;
    public Property(String name, int price, PropertyColor color, int rent0, int rent1, int rent2, int rent3, int rent4, int rent5) {
        this(name, price, color, rent0, rent1, rent2, rent3);
        this.rent4 = rent4;
        this.rent5 = rent5;
        this.currentRent = rent0;
    }

    public int getCurrentRent() {
        return currentRent;
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
    public void recieveRent(Player payer) {
        if (this.landedOn == true) {
            if (!(this.owner.equals(null)) || this.owner == payer) {
                this.owner.handleRent(payer, currentRent);
            }
        }
    }

    public void whenLandedOn(Player player) {
        this.landedOn = true;
        if (this.owner == null) {
            this.buyProperty(player);
        } else {
            this.recieveRent(player);
        }

        this.landedOn = false;
    }

    public void buyProperty(Player buyer) {
        if (this.landedOn == true) {
            if (this.isBought == true) {
                return;
            }
            if (buyer.getBalance() >= this.price) {
                this.isBought = true;
                this.owner = buyer;
                this.owner.setBalance(-this.price);
            }
        }
    }

}



