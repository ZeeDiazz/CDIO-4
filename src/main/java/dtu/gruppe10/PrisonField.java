package dtu.gruppe10;


import dtu.gruppe10.FieldTypes.Field;

import javax.naming.Name;

public abstract class PrisonField extends Field {
    Name prison;
    private boolean isInPrison;
    private int turnsInPrison = 0;
    private int maxTurnsInPrison = 3;

    public boolean isInPrison (Player player, boolean isInPrison) {
        // her tæller jeg antal ture i fængsel og så længe
        for (int i = 0; i < maxTurnsInPrison; i++) {
            turnsInPrison++;

            setInPrison(true);
        }
        // her vil jeg gerne tvinge player til at betale kaution
        //current.playerSubtract(xxx)
        // efter at spilleren har betalt kaution skal deres inPrison status = false
        setInPrison(false);
        return false;
    }

     public void inPrisonEffect (Player player){
        while (player.inPrison()){
            player.movePlayer(0,0);
        }

    }

    public int getTurnsInPrison() {
        return turnsInPrison;
    }

    public void setTurnsInPrison(int turnsInPrison) {
        this.turnsInPrison = turnsInPrison;
    }

    public boolean isInPrison() {
        return isInPrison;
    }

    public void setInPrison(boolean inPrison) {
        isInPrison = inPrison;
    }

    //TODO tilføj metode til at betale kaution eller bruge løsladerkort, hvis spilleren er i fængsel
    //TODO tilføj metode til at betale kaution, efter en spiller har været i fængsel 3 runder (tvunget til at betale)
    //TODO gør sådan at man ikke kan: bevæge sig og opkræve leje
}
