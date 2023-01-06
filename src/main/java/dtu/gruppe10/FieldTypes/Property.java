package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public class Property extends Field {

    private int price;
    private PropertyColor color;
    private boolean isSizable;          //Om der kan bygges huse på ejendommen. Hvis falsk, så nej, hvis sand, så ja
    private boolean isBought;           //Om ejendommen er købt. Hvis falsk, så nej, hvis sand, så ja
    private Player owner;

    public Property(String name, int price, PropertyColor color, boolean isSizable) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.isSizable = isSizable;
    }



}



