package dtu.gruppe10;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GameTest extends TestCase {
    private Game game;
    private Player[] players;

    @Before
    public void startGame(){
        //Create player
        players = new Player[2];
        players[0] = new Player("Player 1",20,1);
        players[1] = new Player("Player 2",30,2);

        //
        game = new Game(players);
    }

    @Test
    public void testGetCurrentPlayer() {
        // The answer should be the first player
        Assert.assertEquals(players[0],game.getCurrentPlayer());
    }

    public void testNextTurn() {
        Assert.assertEquals(players[0],game.getCurrentPlayer());

        game.nextTurn();

        Assert.assertEquals(players[1],game.getCurrentPlayer());
    }

    public void testIsGameOver() {
    }
}