package dtu.gruppe10.ChanceCard;

import java.util.ArrayList;

public class Inventory {
    private ArrayList<ParentChanceCard> inventory;

    public Inventory(){
        this.inventory = new ArrayList<>();
    }
    public void addChanceCard(ParentChanceCard card) {
        this.inventory.add(card);
    }
    //For each iteration of the loop, card is assigned the value of next element in the inventory ArrayList
    public boolean hasGetOutOfJailFreeCard(){
        for (ParentChanceCard card : inventory){
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
    public ArrayList<ParentChanceCard> getInventory(){
        return inventory;
    }
}