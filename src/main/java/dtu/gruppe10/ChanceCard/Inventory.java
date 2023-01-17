package dtu.gruppe10.ChanceCard;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<ChanceCard> inventory;

    public Inventory(){
        this.inventory = new ArrayList<>();
    }
    public void addChanceCard(ChanceCard card) throws Exception {
        int count = 0;
        for (ChanceCard c : inventory) {
            if (c.getClass() == card.getClass()) {
                count++;
            }
        }
        if (count < 2) {
            this.inventory.add(card);
        } else {
            throw new Exception("Cannot add more than 2 cards of the same type to inventory");
        }
    }
    //For each iteration of the loop, card is assigned the value of next element in the inventory ArrayList
    public boolean hasGetOutOfJailFreeCard(){
        for (ChanceCard card : inventory){
            if (card instanceof GetOutOfJailFreeCard){
                return true;
            }
        }
        return  false;
    }
    public void useGetOutOfJailFreeCard(){
        for (int i = 0; i < inventory.size(); i++){
            if (inventory.get(i) instanceof GetOutOfJailFreeCard){
                inventory.remove(i);
                        break;
            }
        }
    }
    public ArrayList<ChanceCard> getInventory(){
        return inventory;
    }
}
