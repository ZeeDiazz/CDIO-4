package dtu.gruppe10;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.dice.DiceRoll;
import dtu.gruppe10.dice.DieCup;
import dtu.gruppe10.dice.SixSidedDie;

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

        int playerCount = getPlayerCount();

        Player[] players = createPlayer(playerCount);

        DieCup cup = new DieCup(new SixSidedDie(), new SixSidedDie());

        game = new Game(players);

        while (!game.isGameOver()) {

            Player currentPlayer = game.getCurrentPlayer();
            String playerName = currentPlayer.name;

            System.out.println(String.format("Starting %s's turn (money: %d)", playerName, currentPlayer.Account.getBalance()));

            String roll = scan.nextLine();

            if(roll.equals("r") || roll.equals("roll")){
                cup.roll();
                DiceRoll rollResult = cup.getResult();
                //ROLDICE FOR NOW
                currentPlayer.movePlayer(rollResult.getValue(0), rollResult.getValue(1));
                System.out.println(String.format("%s rolls a %d and a %d", currentPlayer.name, rollResult.getValue(0), rollResult.getValue(1)));

                if (rollResult.AreSame) {
                    currentPlayer.setRolledPair(true);
                } else {
                    currentPlayer.setRolledPair(false);
                }

                if (currentPlayer.hasRolledPair()) {
                    System.out.println(String.format("%s rolled a pair! You get another turn :) ", currentPlayer.name));
                } else {
                    game.nextTurn();
                }
            } else if(!roll.equals("r") || !roll.equals("roll")){
                System.out.println("roll Again");
            }
            // Calculate the total movement

            //ikke færdigt, skal kigges på
            /*if(currentPlayer.getBalance() >= 0){
            return;
            }*/



            if(game.isGameOver()){
                return;
            }

        }
    }
    private static int rollDice() {
        Random rand = new Random();
        return rand.nextInt(6) + 1;
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
    private static Player[] createPlayer(int playerCount){
        Player[] players = new Player[playerCount];
        for(int i = 0; i < playerCount; i++){
            players[i] = new Player("Player " + (i + 1), 20, i); //balance for now
        }
        return players;
    }
}
