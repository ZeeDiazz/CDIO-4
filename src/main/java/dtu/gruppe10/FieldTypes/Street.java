package dtu.gruppe10.FieldTypes;

public class Street extends Ferry {

    private final int rent4;
    private final int rent5;

    public Street(String name, int price, PropertyColor color, int rent0, int rent1, int rent2, int rent3, int rent4, int rent5) {
        super(name, price, color, rent0, rent1, rent2, rent3);
        this.rent4 = rent4;
        this.rent5 = rent5;
    }

}

