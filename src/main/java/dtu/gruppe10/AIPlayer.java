package dtu.gruppe10;

import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;
import dtu.gruppe10.board.fields.Field;
import dtu.gruppe10.board.fields.PropertyField;
import dtu.gruppe10.dice.DiceRoll;
import dtu.gruppe10.dice.DieCup;
import dtu.gruppe10.dice.SixSidedDie;

import java.util.Random;

import static dtu.gruppe10.App.game;

public class AIPlayer extends Player{
    private int intelligence;
    private final Random rng;
    private final DieCup dieCup;
    private final Board board;
    private PlayerMovement movement;

    public AIPlayer(int id, int startingBalance, int intelligence, Board board) {
        super(id, startingBalance);
        this.intelligence = intelligence;
        this.rng = new Random();
        this.dieCup = new DieCup(new SixSidedDie(),new SixSidedDie());
        this.board = board;
    }

    //Make it owns decision:
    public void takeTurn() {
        // roll the dice
        dieCup.roll();
        DiceRoll roll = dieCup.getResult();
        int rollSum = roll.Sum;

        // move to new position on the board
        movement.getMoveAmount(rollSum);

        // get the AIPlayer's current position on the board
        Field landedOnField = board.getFieldAt(/*newPosition*/2);

        // make decision about whether to buy the property if its for sale


        //make decision about building houses and hotels

    }


}
