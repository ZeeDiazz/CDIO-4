package dtu.gruppe10;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App {
    public static Game game;
    private static Scanner scan = new Scanner(System.in);
    public static void main( String[] args ) {

        int playerCount = getPlayerCount();

        Player[] players = createPlayer(playerCount);

        game = new Game(players);
        //TODO player movement
        while (!game.isGameOver()) {
            Player currentPlayer = game.getCurrentPlayer();

            System.out.println(String.format("Starting %s's turn (money: %d)", currentPlayer.name, currentPlayer.getBalance()));

            game.nextTurn();
        }
    }
    private static int getPlayerCount(){
        while (true){
            System.out.println("Enter the number of players (2-6):");
            try{
                int playerCount = scan.nextInt();
                if (playerCount >= 2 && playerCount <= 6) {
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
