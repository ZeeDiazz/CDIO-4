package dtu.gruppe10.fields;

import dtu.gruppe10.Player;

public class GoToPrisonField extends Field {
    int turnsInPrison;

    public void goToPrisonField(Player player) {
        player.setPosition(31); //31 because the jail is there
        player.setInPrison(true);
        player.setTurnsInPrison(0);
    }

    public void whenLandedOn(Player player) {
        goToPrisonField(player);
    }

}
