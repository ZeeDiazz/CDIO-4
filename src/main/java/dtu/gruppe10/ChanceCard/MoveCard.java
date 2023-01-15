package dtu.gruppe10.ChanceCard;

public class MoveCard extends ParentChanceCard{
    private int amount;

    MoveCard(int ID, int amount){
        super(ID);
        this.amount = amount;

    }
    int get_amount() {
        return amount;
    }

}
