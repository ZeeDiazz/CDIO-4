package dtu.gruppe10;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class JailTest extends TestCase {
    @Test
    public void testAddPlayer() {
        Jail jail = new Jail(3);
        Player player1 = new Player(1,30000);
        Player player2 = new Player(1,30000);

        jail.addPlayer(player1);
        assertTrue(jail.playerIsJailed(player1)); //Is player 1 in jail
        assertFalse(jail.playerIsJailed(player2)); //Is player 2 in jail
        assertEquals(0, jail.turnsServed(player1));


    }

    @Test
    public void testReleasePlayer() {


    }

    @Test
    public void testPlayerIsJailed() {



    }

    @Test
    public void testTurnsServed() {

    }

    @Test
    public void testPlayerHasToGetOut() {
    }

    @Test
    public void testPlayerServedTurn() {
    }
}