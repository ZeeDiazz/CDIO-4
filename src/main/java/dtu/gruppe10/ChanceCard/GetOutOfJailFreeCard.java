package dtu.gruppe10.ChanceCard;

public class GetOutOfJailFreeCard extends JailCard {
    Inventory inventory;
    ChanceCard card;

    public GetOutOfJailFreeCard(int ID) {
        super(ID);
    }


    public void applyEffect(){
        inventory.addChanceCard(card);
    }

}

