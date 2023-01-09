package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public class GoToPrisonField extends Field {
    int turnsInPrison;

        public void goToPrisonField(Player player) {
            player.setPosition(31); //31 because the jail is there
            player.setInPrison(true);
            turnsInPrison = 0;
        }
        @Override
        public void landedOn(Player player) {
            goToPrisonField(player);
        }

}
