package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public class Property extends Field {
    // Klasse der dækker over ejendomme som man kan bygge huse på, de fire færger og de to bryggeri felter

    private int price;
    private PropertyColor color;
    private boolean isBought;           //Om ejendommen er købt. Hvis falsk, så nej, hvis sand, så ja
    private Player owner;

    private int numberOfhouses;
    private int rent0;
    private int rent1;
    private int rent2;
    private int rent3;
    private int rent4;
    private int rent5;
    private int requiredRent;

    // Contructor for de to 'brewery' felter
    public Property(String name, int price, PropertyColor color, int rent0, int rent1) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.rent0 = rent0;
        this.rent1 = rent1;
        this.requiredRent = rent0;
    }

    // Contructor til de fire 'ferry' felter;
    public Property(String name, int price, PropertyColor color, int rent0, int rent1, int rent2, int rent3) {
        this(name, price, color, rent0, rent1);
        this.rent2 = rent2;
        this.rent3 = rent3;
        this.requiredRent = rent0;
    }


    // Contructor til felter hvor man kan bygge huse og hoteller på;
    public Property(String name, int price, PropertyColor color, int rent0, int rent1, int rent2, int rent3, int rent4, int rent5) {
        this(name, price, color, rent0, rent1, rent2, rent3);
        this.rent4 = rent4;
        this.rent5 = rent5;
        this.requiredRent = rent0;
    }


    public void updateCurrentRent(int numberOfhouses) {
        if (numberOfhouses == 1) {
            this.requiredRent = this.rent1;
        } else if (numberOfhouses == 1) {
            this.requiredRent = this.rent3;
        } else if (numberOfhouses == 1) {
            this.requiredRent = this.rent4;
        } else if (numberOfhouses == 1) {
            this.requiredRent = this.rent5;
        }


    }


}



