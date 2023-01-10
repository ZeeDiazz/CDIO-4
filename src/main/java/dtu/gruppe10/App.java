package dtu.gruppe10;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;
import dtu.gruppe10.board.fields.Field;
import dtu.gruppe10.dice.DiceRoll;
import dtu.gruppe10.dice.DieCup;
import dtu.gruppe10.dice.SixSidedDie;
import dtu.gruppe10.gui.GUIPlayer;
import dtu.gruppe10.gui.GUIState;
import dtu.gruppe10.gui.GUITest;
import dtu.gruppe10.gui.GUIWindow;
import dtu.gruppe10.gui.prompts.GUIAnswer;

import java.awt.*;
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
            System.out.println("No answer yet");
        }

        int playerCount = playerCountAnswer.getAnswer();
        Player[] players = new Player[playerCount];
        GUIPlayer[] guiPlayers = new GUIPlayer[playerCount];

        Color[] playerColors = {new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)};

        for (int i = 0; i < playerCount; ++i) {
            GUIAnswer<String> playerNameAnswer = window.getUserString("Player " + (i+1) + " enter your name", 1, 15);
            window.repaint();

            while (true) {
                if (playerNameAnswer.hasAnswer()) {
                    break;
                }
                System.out.println("No answer yet");
            }

            String playerName = playerNameAnswer.getAnswer();
            players[i] = new Player(i+1, 30000);
            guiPlayers[i] = new GUIPlayer(i+1, playerName, playerColors[i]);

            window.addPlayer(new GUIPlayer(i+1, playerName, playerColors[i]));
        }

        window.setState(GUIState.PLAYING);
        for (int i = 0; i < playerCount; ++i) {
            window.updatePlayerBalance(i+1, 30000);
        }

        DieCup cup = new DieCup(new SixSidedDie(), new SixSidedDie());
        game = new Game(players, new Field[40]);

        while (!game.gameIsOver()) {
            Player currentPlayer = game.getCurrentPlayer();

            cup.roll();
            DiceRoll roll = cup.getResult();

            PlayerMovement move = game.Board.generateForwardMove(currentPlayer.ID, roll.Sum);
            game.Board.performMove(currentPlayer.ID, move);

            window.setNewPlayerPosition(currentPlayer.ID, move.End);
            window.repaint();

            if (move.PassedStart) {
                currentPlayer.Account.add(4000);
                window.updatePlayerBalance(currentPlayer.ID, 4000);
            }

            if (!roll.AreSame) {
                game.nextTurn();
            }

            for (int x = 0; x < 1_000_000; ++x) {
                System.out.println();
            }
        }
    }

    private static int getPlayerCount(){
        while (true){
            System.out.println("Enter the number of players (2-6):");
            try{
                int playerCount = scan.nextInt();
                if (playerCount > 1 && playerCount < 7) {
                    scan.nextLine();
                    return playerCount;
                } else {
                    System.out.println("Enter a number between 2 and 6");
                }
            } catch (Exception e) {
                System.out.println("Enter a valid number");
                scan.next();
            }
        }
    }
}
