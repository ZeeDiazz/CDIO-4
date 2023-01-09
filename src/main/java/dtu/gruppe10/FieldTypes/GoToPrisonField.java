package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;
import dtu.gruppe10.PrisonField;

public class GoToPrisonField extends PrisonField {

        public void goToPrisonField(Player player) {
            player.setPosition(31); //31 because the jail is there
            player.setInPrison(true);
            setTurnsInPrison(0);

        }
        public void landedOn(Player player) {
            goToPrisonField(player);
        }

}
