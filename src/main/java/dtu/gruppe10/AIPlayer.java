package dtu.gruppe10;

import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;
import dtu.gruppe10.board.fields.Field;
import dtu.gruppe10.board.fields.PropertyField;
import dtu.gruppe10.board.fields.StreetField;
import dtu.gruppe10.dice.DiceRoll;
import dtu.gruppe10.dice.DieCup;
import dtu.gruppe10.dice.SixSidedDie;
import dtu.gruppe10.gui.GUIBalances;

import java.util.Random;

import static dtu.gruppe10.App.game;

public class AIPlayer extends Player{
    private int intelligence;
    private final Random rng;

    public AIPlayer(int id, int startingBalance, int intelligence) {
        super(id, startingBalance);
        this.intelligence = intelligence;
        this.rng = new Random();
    }

    public boolean shouldBuyProperty() {
        return rng.nextInt(10) < intelligence;
    }

    public boolean shouldBuildHouse() {
        return rng.nextInt(10) < intelligence;
    }
}
