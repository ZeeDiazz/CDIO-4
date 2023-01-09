package dtu.gruppe10;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest extends TestCase {
    private Game game;
    private Player[] players;

    @Test
    public void testGetCurrentPlayer() {
        // Create a new object of 3 players
        Player player1 = new Player("Player 1", 20, 0);
        Player player2 = new Player("Player 2", 20, 1);
        Player player3 = new Player("Player 3", 20, 2);
        Player[] players = {player1, player2, player3};
        game = new Game(players);

        // The answer should be the first player
        Assert.assertEquals(players[0],game.getCurrentPlayer());
    }

    public void testNextTurn() {
        // Create a new object of 3 players
        Player player1 = new Player("Player 1", 20, 0);
        Player player2 = new Player("Player 2", 20, 1);
        Player player3 = new Player("Player 3", 20, 2);
        Player[] players = {player1, player2, player3};
        game = new Game(players);

        // Check that the current player is Player 1
        assertEquals(player1, game.getCurrentPlayer());

        // Next Players turn
        game.nextTurn();

        // Check that the current player is now Player 2
        assertEquals(player2, game.getCurrentPlayer());

        // Next players turn
        game.nextTurn();

        // Check that the current player is now Player 3
        assertEquals(player3, game.getCurrentPlayer());

        // Mext Players turn (Player 1 again)
        game.nextTurn();

        // Check that the current player is Player 1 again
        assertEquals(player1, game.getCurrentPlayer());
    }

    public void testIsGameOver() {
        // Create a new object of 3 players
        Player player1 = new Player("player 1", 20, 0);
        Player player2 = new Player("player 2", 20, 1);
        Player player3 = new Player("player 3", 20, 2);
        Player[] players = {player1, player2, player3};
        game = new Game(players);

        // Check that the game is not over yet
        assertFalse(game.isGameOver());

        // Set player 1's balance to 0 (Bankrupt)
        player1.setBalance(0);

        // Checks if the game is over
        assertFalse(game.isGameOver());

        // Set player 2's balance to 0 (Bankrupt)
        player2.setBalance(0);

        // Check that the game is over and end game
        assertTrue(game.isGameOver());
    }
}