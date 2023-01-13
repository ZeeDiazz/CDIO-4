package dtu.gruppe10;


import java.util.ArrayList;

public class ChanceCard {
    private ArrayList<ChanceCardAction[]> actions;

    public ChanceCard() {
        this.actions = new ArrayList<>();
    }
    public void addAction(ChanceCardAction action) {
        this.actions.add(new ChanceCardAction[]{action});
    }


    //Could use a interface and than make a class for everyChanceCard...

}
