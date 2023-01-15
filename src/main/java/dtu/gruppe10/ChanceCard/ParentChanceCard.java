package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;

public class ParentChanceCard implements Comparable<ParentChanceCard> {
    private int ID;


    ParentChanceCard(int ID){
        this.ID = ID;

    }
    public int getID() {
        return ID;
    }

    @Override
    public int compareTo(ParentChanceCard o) {
        return this.ID-o.ID;
    }
}

