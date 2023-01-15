package dtu.gruppe10.ChanceCard;

public class MoveToCard extends ParentChanceCard{

    private int[] newPos;
    MoveToCard(int ID, int[] newPos) {
        super(ID);
        this.newPos = newPos;
    }

    int[] getDestination() {
        return  newPos;
    }

    }

