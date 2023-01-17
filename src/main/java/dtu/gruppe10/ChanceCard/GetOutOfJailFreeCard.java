package dtu.gruppe10.ChanceCard;

public class GetOutOfJailFreeCard extends JailCard {
    Inventory inventory;
    ChanceCard card;

    public GetOutOfJailFreeCard(int ID) {
        super(ID);
    }


    public void applyEffect() throws Exception {
        inventory.addChanceCard(card);
    }
    public boolean addToInventory(Inventory inventory) throws Exception {
        inventory.addChanceCard(this);
        return true;
    }

}

