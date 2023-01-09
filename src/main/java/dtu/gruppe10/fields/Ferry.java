package dtu.gruppe10.fields;

public class Ferry extends Property {
    public Ferry(String name, int price, StreetColor color, int rent0, int rent1, int rent2, int rent3) {
        super(name, price, color, rent0, rent1);
        this.rent = new int[4];
        this.rent[2] = rent2;
        this.rent[3] = rent3;
    }
}
