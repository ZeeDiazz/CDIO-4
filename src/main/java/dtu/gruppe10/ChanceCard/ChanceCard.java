package dtu.gruppe10.ChanceCard;

public abstract class ChanceCard implements Comparable<ChanceCard> {
    public final int ID;


    ChanceCard(int ID){
        this.ID = ID;
    }
    public int getID(){
        return ID;
    }

    @Override
    public int compareTo(ChanceCard o) {
        return this.ID-o.ID;
    }
}

