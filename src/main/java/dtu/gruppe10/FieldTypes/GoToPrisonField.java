package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;
import dtu.gruppe10.PrisonField;

public class GoToPrisonField extends Field {
    private boolean isInPrison;
    private int turnInPrison;
    public void goToPrison(Player player, PrisonField prisonField){

        // mangler en setField i player klassen (setLocation)
        //player.setField(prisonField);

        //player.setInPrison(true);

    }
    public void sendToPrison(Player player) {
        player.setPosition(31); //31 because the jail is there
        player.setInPrison(true);
    }
    @Override
    public void landedOn(Player player) {
        sendToPrison(player);
    }
}
