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
    private final DieCup dieCup;
    private final Board board;
    private PlayerMovement movement;
    private GUIBalances balances;

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
        PlayerMovement nextPos = board.generateForwardMove(game.getCurrentPlayerTurn(),rollSum);

        // get the AIPlayer's current position on the board
        Field landedOnField = board.getFieldAt(nextPos.End);

        // make decision about whether to buy the property if its for sale
        if(landedOnField instanceof PropertyField){
            PropertyField propertyField = (PropertyField) landedOnField;
            if (!propertyField.isOwned()) {
                int price = propertyField.Price;
                if (balances.getPlayerBalance(game.getCurrentPlayer().ID) >= price && rng.nextInt(10)>intelligence){
                    propertyField.setOwnerId(game.getCurrentPlayer().ID);
                    balances.playerLostMoney(game.getCurrentPlayer().ID,price);
                }
            }
            //make decision about building houses and hotels
            if (landedOnField instanceof StreetField) {
                StreetField streetField = (StreetField) landedOnField;
                if(intelligence > rng.nextInt(10) && streetField.ownsAllInSet()) {
                    if(balances.getPlayerBalance(game.getCurrentPlayer().ID) > streetField.getHousePrice()) {
                        streetField.buildOneHouse();
                        balances.playerLostMoney(game.getCurrentPlayer().ID,streetField.getHousePrice());
                    }
                }

            }

        }
    }
}