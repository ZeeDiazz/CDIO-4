package dtu.gruppe10;

import dtu.gruppe10.board.fields.Field;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest extends TestCase {
    private Game game;
    private Player[] players;

    @Before
    public void initialize() {
        players = new Player[3];
        // Create a new object of 3 players
        players[0] = new Player(0, 20);
        players[1] = new Player(1, 20);
        players[2] = new Player(2, 20);

        game = new Game(players, new Field[40]); // TODO
    }

    @Test
    public void testGetCurrentPlayer() {
        // The answer should be the first player
        Assert.assertEquals(players[0], game.getCurrentPlayer());
    }

    public void testNextTurn() {
        // Check that the current player is Player 1
        assertEquals(players[0], game.getCurrentPlayer());

        // Next Players turn
        game.nextTurn();

        // Check that the current player is now Player 2
        assertEquals(players[1], game.getCurrentPlayer());

        // Next players turn
        game.nextTurn();

        // Check that the current player is now Player 3
        assertEquals(players[2], game.getCurrentPlayer());

        // Next Players turn (Player 1 again)
        game.nextTurn();

        // Check that the current player is Player 1 again
        assertEquals(players[0], game.getCurrentPlayer());
    }

    public void testIsGameOver() {
        // Check that the game is not over yet
        assertFalse(game.gameIsOver());

        // Set player 1's balance to 0 (Bankrupt)
        players[0].Account.subtract(21);

        // Checks if the game is over
        assertFalse(game.gameIsOver());

        // Set player 2's balance to 0 (Bankrupt)
        players[1].Account.subtract(21);


        // Check that the game is over and end game
        assertTrue(game.gameIsOver());
    }
}