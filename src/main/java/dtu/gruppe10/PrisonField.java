package dtu.gruppe10;


import dtu.gruppe10.FieldTypes.Field;

import javax.naming.Name;

public class PrisonField extends Field {
    Name prison;
    private boolean isInPrison;
    private int turnInPrison;
    int maxTurnsInPrison = 3;
    private int turnsInPrison;

    public boolean isInPrison (Player player, Boolean isInPrison) {
        for (int i = 0; i < maxTurnsInPrison; i++) {
            turnInPrison++;
            return true;
        }
        return false;
    }

     public void inPrisonEffect (Player player, Boolean isInPrison){


     }
    public void sendToPrison(Player player, int turnInPrison) {
        this.turnsInPrison = turnInPrison;
        player.setPosition(31); //31 because the jail is there
        player.setInPrison(true);
        turnsInPrison = 0;
    }

    public void landedOn(Player player) {
        sendToPrison(player);
    }


    //TODO tilføj metode til at betale kaution eller bruge løsladerkort, hvis spilleren er i fængsel
    //TODO tilføj metode til at betale kaution, efter en spiller har været i fængsel 3 runder (tvunget til at betale)
    //TODO gør sådan at man ikke kan: bevæge sig og opkræve leje
}
