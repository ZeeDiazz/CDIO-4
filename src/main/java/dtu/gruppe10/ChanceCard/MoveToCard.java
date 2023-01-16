package dtu.gruppe10.ChanceCard;

public class MoveToCard extends MoveCard {
    int positionIndex;

    public MoveToCard(int ID, int positionIndex) {
        super(ID, positionIndex);
        this.positionIndex = positionIndex;

    }
    public int getPositionIndex(){
        return positionIndex;
    }
}

