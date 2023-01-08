package dtu.gruppe10;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    private static Scanner scan = new Scanner(System.in);
    public static void main( String[] args ) {

        int playerCount = getPlayerCount();

        Player[] players = createPlayer(playerCount);

        Game game = new Game(players);


        /*//How many player?
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the number of players (2-6):");
        int playerCount = scan.nextInt();

            // Check that player count is between 2 and 6
            if (playerCount <= 2 || playerCount >= 6) {
                System.out.println("Invalid number of players. Please enter a number between 2 and 6.");
                System.out.println("Enter the number of players (2-6):");
                playerCount = scan.nextInt();
            }

            // Create an array of players
            Player[] players = new Player[playerCount];
            for (int i = 0; i < playerCount; i++) {
                players[i] = new Player("Player " + (i + 1), 20, i); // balance is 20 for now
            }

            // Create a game with the array of players
            Game game = new Game(players);

            while (true){
                int d1 = 2;
                int d2 = 5;
                Player currentPlayer = game.getCurrentPlayer();
                System.out.println(String.format("Starting %s's turn (money: %d)", currentPlayer.name, currentPlayer.getBalance()));

            }*/

    }
    private static int getPlayerCount(){
        while (true){
            System.out.println("Enter the number of players (2-6):");
            try{
                int playerCount = scan.nextInt();
                if (playerCount >= 2 && playerCount <= 6) {
                    return playerCount;
                } else {
                    System.out.println("Enter a number between 2 and 6.");
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
            players[i] = new Player("Player " + (i + 1), 20, i);
        }
        return players;
    }

}
