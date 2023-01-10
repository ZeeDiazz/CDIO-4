package dtu.gruppe10;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;
import dtu.gruppe10.board.fields.*;
import dtu.gruppe10.dice.DiceRoll;
import dtu.gruppe10.dice.DieCup;
import dtu.gruppe10.dice.SixSidedDie;
import dtu.gruppe10.gui.GUIPlayer;
import dtu.gruppe10.gui.GUIState;
import dtu.gruppe10.gui.GUITest;
import dtu.gruppe10.gui.GUIWindow;
import dtu.gruppe10.gui.prompts.GUIAnswer;

import java.awt.*;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static Game game;
    SixSidedDie d1 = new SixSidedDie();
    public static Board board;
    private static Scanner scan = new Scanner(System.in);
  
    public static void main( String[] args ) {
        GUIWindow window = new GUIWindow(new Rectangle(100, 100, 1000, 500), GUITest.generateFields());

        Thread uiThread = new Thread(window, "uiThread");

        window.setState(GUIState.START_GAME);
        uiThread.start();

        GUIAnswer<Integer> playerCountAnswer = window.getUserInt("Enter the number of players (between {0}-{1})", 2, 6);
        while (true) {
            if (playerCountAnswer.hasAnswer()) {
                break;
            }
            trySleep(10);
        }

        int playerCount = playerCountAnswer.getAnswer();
        Player[] players = new Player[playerCount];

        Color t = new Color(255, 0, 0);
        Color[] playerColors = {Color.RED, Color.GREEN, Color.YELLOW, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)};

        int startBalance = 10000;
        for (int i = 0; i < playerCount; ++i) {
            GUIAnswer<String> playerNameAnswer = window.getUserString("Player " + (i+1) + " enter your name", 1, 15);
            window.repaint();

            while (true) {
                if (playerNameAnswer.hasAnswer()) {
                    break;
                }
                trySleep(10);
            }

            String playerName = playerNameAnswer.getAnswer();
            players[i] = new Player(i+1, startBalance);

            window.addPlayer(new GUIPlayer(i+1, playerName, playerColors[i]));
        }

        window.setState(GUIState.PLAYING);
        for (int i = 0; i < playerCount; ++i) {
            window.updatePlayerBalance(i+1, startBalance);
        }

        DieCup cup = new DieCup(new SixSidedDie(), new SixSidedDie());

        ArrayOfFields fieldReader = new ArrayOfFields();
        try {

            fieldReader.readFieldData();
        }
        catch (IOException e) {
            System.out.println("Frick");
        }
        game = new Game(players, fieldReader.getFields());

        while (!game.gameIsOver()) {
            Player currentPlayer = game.getCurrentPlayer();

            cup.roll();
            DiceRoll roll = cup.getResult();

            PlayerMovement move = game.Board.generateForwardMove(currentPlayer.ID, roll.Sum);
            game.Board.performMove(currentPlayer.ID, move);

            window.setNewPlayerPosition(currentPlayer.ID, move.End);
            window.repaint();

            Field endField = game.Board.getFieldAt(move.End);
            if (endField instanceof PropertyField propertyField) {
                if (propertyField.isOwned()) {
                    // Pay rent
                    int propertiesInSetOwned = 1;
                    for (int i = 0; i < game.Board.FieldCount; ++i) {
                        Field field = game.Board.getFieldAt(i);
                        if (propertyField.inSetWith(field) && propertyField.getOwner().equals(((PropertyField)field).getOwner())) {
                            propertiesInSetOwned++;
                        }
                    }

                    int toPay;
                    if (propertyField instanceof BreweryField breweryField) {
                        toPay = breweryField.utilityPrice(roll.Sum, propertiesInSetOwned);
                    }
                    else {
                        toPay = propertyField.getCurrentRent(propertiesInSetOwned);
                    }

                    System.out.println(currentPlayer.ID + " has paid " + toPay + " in rent to " + propertyField.getOwner().ID);
                    System.out.println("Property: " + propertyField.ID);
                    Player.payRent(propertyField.getOwner(), currentPlayer, toPay);
                }
                else {
                    // Buy property
                    currentPlayer.Account.subtract(propertyField.Price);
                    window.updatePlayerBalance(currentPlayer.ID, -propertyField.Price);

                    propertyField.newOwner(currentPlayer);
                    window.propertyBought(currentPlayer.ID, move.End);
                    System.out.println(currentPlayer.ID + " has bought a property");
                }
            }
            else if (endField instanceof GoToJailField) {
                // Send to jail
            }

            if (move.PassedStart) {
                currentPlayer.Account.add(4000);
                window.updatePlayerBalance(currentPlayer.ID, 4000);
            }

            if (currentPlayer.Account.isBankrupt()) {
                game.removePlayer(currentPlayer.ID);
                window.playerWentBankrupt(currentPlayer.ID);
            }

            if (!roll.AreSame) {
                game.nextTurn();
            }

            trySleep(1000);
        }

        window.setState(GUIState.GAME_OVER);
    }

    private static void trySleep(long millis) {
        try {
            Thread.sleep(millis);
        }
        catch (InterruptedException ignored) {}
    }
}
