package dtu.gruppe10;


import dtu.gruppe10.FieldTypes.Field;

import javax.naming.Name;

public abstract class PrisonField extends Field {
    Name prison;
    private boolean isInPrison;
    private int turnsInPrison = 0;
    private int maxTurnsInPrison = 3;


    public boolean isInPrison (Player player, boolean isInPrison) {
        if (player.inPrison() == true) {
return true;
        }
        else return false;
    }
     public void inPrisonEffect (Player player){
        if(player.inPrison()){

            player.increaseTurnsInPrison();

            if (player.getTurnsInPrison()>maxTurnsInPrison){
                //betal kaution
                player.getsOutofPrisonByBail();
            }else{
                //do nothing
                System.out.println(getTurnsInPrison());
            }
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
