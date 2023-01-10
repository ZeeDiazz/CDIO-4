package dtu.gruppe10;


public class ChanceCard {
    private String text;

    ChanceCard(String text){
        this.text = text;
    }

    //Could use a interface and than make a class for everyChanceCard...
    public void GoToPrisonAction(Player player) {
        // player.setPosition(31);
        // player.setInPrison(true);
    }

}
