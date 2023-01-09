package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public abstract class Prison extends Field {
    private boolean[] playersInPrison;
    private int maxTurnsInPrison = 3;

    public Prison (int playerAmount){

        this.playersInPrison = new boolean[playerAmount];

    }
    public void addPlayerToPrison(int inmate){
        this.playersInPrison[inmate] = true;
    }
    public void releasePlayerFromPrison(int inmate){
        this.playersInPrison[inmate] = false;
    }

    public boolean isPlayersInPrison(int inmate) {
        return this.playersInPrison[inmate];
    }

    public void inPrisonEffect (Player player){
        if(player.inPrison()){

            player.increaseTurnsInPrison();

            if (player.getTurnsInPrison()>maxTurnsInPrison){
                //betal kaution
                player.getsOutofPrisonByBail();

            }else{
                //do nothing
            }
        }
    }
}

