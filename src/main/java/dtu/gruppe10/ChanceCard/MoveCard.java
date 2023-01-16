package dtu.gruppe10.ChanceCard;

public class MoveCard extends ChanceCard {
    int amount;
    public MoveCard(int ID, int amount) {
        super(ID);
        this.amount = amount;

    }
    public int getMoveAmount (){
        return amount;
    }

}
