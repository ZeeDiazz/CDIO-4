package dtu.gruppe10;


import dtu.gruppe10.FieldTypes.Field;

import javax.naming.Name;

public class PrisonField extends Field {
    Name prison;
    private boolean isInPrison;
    private int turnInPrison;

    public void isInPrison (Player player,Boolean isInPrison){
     }
     public void inPrisonEffect (Player player, Boolean isInPrison){


     }



    //TODO tilføj metode til at betale kaution eller bruge løsladerkort, hvis spilleren er i fængsel
    //TODO tilføj metode til at betale kaution, efter en spiller har været i fængsel 3 runder (tvunget til at betale)
    //TODO gør sådan at man ikke kan: bevæge sig og opkræve leje
}