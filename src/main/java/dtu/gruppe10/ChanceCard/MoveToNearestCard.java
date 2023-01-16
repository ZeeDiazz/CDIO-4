package dtu.gruppe10.ChanceCard;

public class MoveToNearestCard extends MoveCard {
    int posistionIndex;
   public MoveToNearestCard(int ID, int positionIndex){
       super(ID, positionIndex);
   }
   public int getPositionIndex(){
       return posistionIndex;
   }

}
