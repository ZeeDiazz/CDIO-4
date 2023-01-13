package dtu.gruppe10.ChanceCard;

public class MoveToCard extends ParentChanceCard{

    private int[] destination;
    MoveToCard(int ID, int[] destination) {
        super(ID);
        this.destination = destination;
    }

    int[] getDestination() {
        return destination;
    }

    }

