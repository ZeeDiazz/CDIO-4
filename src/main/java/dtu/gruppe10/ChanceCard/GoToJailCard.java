package dtu.gruppe10.ChanceCard;

import dtu.gruppe10.Player;

public class GoToJailCard extends JailCard {
    public GoToJailCard(int ID) {
        super(ID);
    }

    public boolean playerGetsJailed(){
        return true;
    }
}
