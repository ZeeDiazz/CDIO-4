package dtu.gruppe10;

import dtu.gruppe10.FieldTypes.Field;

import java.util.Random;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static Game game;
    public static Board board;
    private static Scanner scan = new Scanner(System.in);
    public static void main( String[] args ) {

        int playerCount = getPlayerCount();

        Player[] players = createPlayer(playerCount);

        game = new Game(players);
        //TODO player movement
        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();
            String playerName = currentPlayer.name;

            System.out.println(String.format("Starting %s's turn (money: %d)", playerName, currentPlayer.getBalance()));

            String roll = scan.nextLine();

            if(roll == "r" ){
            //ROLDICE FOR NOW
            int dice1 = rollDice();
            int dice2 = rollDice();

            currentPlayer.movePlayer(dice1,dice2);
            System.out.println(String.format("%s rolls a %d and a %d", currentPlayer.name, dice1, dice2));

            // Calculate the total movement
            }
            game.nextTurn();

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
