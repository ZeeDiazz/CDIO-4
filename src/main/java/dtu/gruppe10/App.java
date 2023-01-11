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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static Game game;
    protected static boolean wantsToPayBail;
    protected static boolean moveHacks;
    protected static int moveHackAmount;
    protected static boolean moveHackDouble;

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

        Color[] playerColors = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.CYAN, Color.MAGENTA};

        int startBalance = 30000;
        for (int i = 0; i < playerCount; ++i) {
            GUIAnswer<String> playerNameAnswer = window.getUserString("Player " + (i+1) + " enter your name", 1, 15);
            window.repaint();

            while (!playerNameAnswer.hasAnswer()) {
                trySleep(10);
            }

            String playerName = playerNameAnswer.getAnswer();
            players[i] = new Player(i+1, startBalance);

            window.addPlayer(new GUIPlayer(i+1, playerName, playerColors[i]));
        }

        window.setState(GUIState.PLAYING);

        moveHacks = false;
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'j') {
                    System.out.println("Player chose to pay bail");
                    wantsToPayBail = true;
                }
                if (e.getKeyChar() == 'm') {
                    moveHacks = !moveHacks;

                    if (moveHacks) {
                        System.out.println("Move hacks enabled");
                    }
                    else {
                        System.out.println("Move hacks disabled");
                        moveHackAmount = 0;
                        moveHackDouble = false;
                    }
                }
                if (moveHacks) {
                    if (Character.isDigit(e.getKeyChar())) {
                        moveHackAmount = Character.getNumericValue(e.getKeyChar());
                    }
                    else if (e.getKeyChar() == 'd') {
                        moveHackDouble = true;
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        for (int i = 0; i < playerCount; ++i) {
            window.updatePlayerBalance(i+1, startBalance);
        }

        DieCup cup = new DieCup(new SixSidedDie(), new SixSidedDie());
        Jail jail = new Jail(1000,3);

        ArrayOfFields fieldReader = new ArrayOfFields();
        try {
            fieldReader.readFieldData();
        }
        catch (IOException e) {
            System.out.println("Frick");
        }
        game = new Game(players, fieldReader.getFields());

        while (!game.gameIsOver()) {
            wantsToPayBail = false;

            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("\nPlayer " + currentPlayer.ID + " is starting their turn");

            window.hasToRoll();
            window.repaint();
            while (!window.hasPressedRoll()) {
                trySleep(10);
                if (wantsToPayBail && jail.playerIsJailed(currentPlayer)) {
                    currentPlayer.Account.subtract(jail.BailPrice);
                    window.updatePlayerBalance(currentPlayer.ID, -jail.BailPrice);
                    jail.releasePlayer(currentPlayer);
                    window.setPlayerFreeFromJail(currentPlayer.ID);
                    window.repaint();
                }
            }

            cup.roll();
            DiceRoll roll = cup.getResult();

            if (jail.playerIsJailed(currentPlayer)) {
                boolean release = false;

                if (roll.AreSame || moveHacks && moveHackDouble) {
                    System.out.println("Player rolled out of prison");
                    System.out.println(roll.getValue(0) + " " + roll.getValue(1));
                    release = true;
                }
                else if (jail.playerHasToGetOut(currentPlayer)) {
                    System.out.println("Player paid bail");
                    currentPlayer.Account.subtract(jail.BailPrice);
                    window.updatePlayerBalance(currentPlayer.ID, -jail.BailPrice);
                    release = true;
                }

                if (!release) {
                    jail.playerServedTurn(currentPlayer);
                    System.out.println("Player is spending their turn number " + jail.turnsServed(currentPlayer) + " in jail");
                    game.nextTurn();
                    continue;
                }

                System.out.println("Player " + currentPlayer.ID + " was released from Jail");
                jail.releasePlayer(currentPlayer);
                window.setPlayerFreeFromJail(currentPlayer.ID);
            }

            PlayerMovement move = game.Board.generateForwardMove(currentPlayer.ID, roll.Sum);
            if (moveHacks && moveHackAmount != 0) {
                move = game.Board.generateForwardMove(currentPlayer.ID, moveHackAmount);
            }

            Field endField = game.Board.getFieldAt(move.End);
            if (endField instanceof GoToJailField) {
                System.out.println("Player landed on 'Go To Jail'");
                jail.addPlayer(currentPlayer);
                jail.playerServedTurn(currentPlayer);

                move = game.Board.generateDirectMove(currentPlayer.ID, game.Board.getPrisonIndex());
                endField = game.Board.getFieldAt(move.End);

                window.setPlayerInJail(currentPlayer.ID);
            }

            game.Board.performMove(currentPlayer.ID, move);

            window.setNewPlayerPosition(currentPlayer.ID, move.End);
            window.repaint();

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

            if (move.PassedStart) {
                currentPlayer.Account.add(4000);
                window.updatePlayerBalance(currentPlayer.ID, 4000);
            }

            if (currentPlayer.Account.isBankrupt()) {
                game.removePlayer(currentPlayer.ID);
                window.playerWentBankrupt(currentPlayer.ID);
                game.nextTurn();
                continue;
            }

            if (!moveHacks && !roll.AreSame) {
                game.nextTurn();
            }
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
