package dtu.gruppe10.FieldTypes;

public class Ferry extends Property {


    protected final int rent2;
    protected final int rent3;


    public Ferry(String name, int price, PropertyColor color, int rent0, int rent1, int rent2, int rent3) {
        super(name, price, color, rent0, rent1);
        this.rent2 = rent2;
        this.rent3 = rent3;
    }



}
