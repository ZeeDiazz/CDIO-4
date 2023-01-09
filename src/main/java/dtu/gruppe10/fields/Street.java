package dtu.gruppe10.fields;

public class Street extends Property {
    public Street(String name, int price, StreetColor color, int rent0, int rent1, int rent2, int rent3, int rent4, int rent5) {
        super(name, price, color, rent0, rent1);
        this.rent = new int[6];
        this.rent[2] = rent2;
        this.rent[3] = rent3;
        this.rent[4] = rent4;
        this.rent[5] = rent5;
    }
}

