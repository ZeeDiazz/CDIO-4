package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;

abstract class ParentChanceCard {
    private int ID;


    ParentChanceCard(int ID){
        this.ID = ID;

    }
    public int getID() {
        return ID;
    }
    /*private String name;
    private String description;

    public ParentChanceCard(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void takeAction(Player player, Board board);*/
}

