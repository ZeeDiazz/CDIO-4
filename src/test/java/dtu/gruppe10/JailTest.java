package dtu.gruppe10;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class JailTest extends TestCase {
    @Test
    public void testIfPlayerCanGetPutInJail() {
        Jail jail = new Jail(1000,3);
        Player player1 = new Player(1, 30000);
        Player player2 = new Player(1, 30000);

        jail.addPlayer(player1);
        assertTrue(jail.playerIsJailed(player1)); //Is player 1 in jail
        assertFalse(jail.playerIsJailed(player2)); //Is player 2 in jail


    }

    @Test
    public void testIfPlayerCanGetReleasedFromJail() {
        Jail jail = new Jail(1000, 3);
        Player player1 = new Player(1, 30000);
        Player player2 = new Player(1, 30000);
        jail.addPlayer(player1);
        jail.addPlayer(player2);
        jail.releasePlayer(player2);
        assertTrue(jail.playerIsJailed(player1)); //Is player 1 in jail?
        assertFalse(jail.playerIsJailed(player2));//Is player 2 not in jail?

    }

    @Test
    public void testIfPlayerCanSurveTurnsInJail() {
        Jail jail = new Jail(1000, 3);
        Player player1 = new Player(1, 30000);
        Player player2 = new Player(2, 30000);
        jail.addPlayer(player1);
        jail.addPlayer(player2);
        jail.playerServedTurn(player1); //Player1 serves one turn in jail
        assertEquals(jail.turnsServed(player1), 1); // Player1 has had 1 turn in jail?
        assertEquals(jail.turnsServed(player2), 0); // Player2 has had 0 turns?

    }

    @Test
    public void testIfPlayerCanBeInJailForMoreThanThreeRounds() {
        Jail jail = new Jail(1000, 3);
        Player player1 = new Player(1, 30000);
        Player player2 = new Player(2, 30000);
        jail.addPlayer(player1);
        jail.addPlayer(player2);
        jail.playerServedTurn(player1);
        jail.playerServedTurn(player1);
        jail.playerServedTurn(player1);
        jail.playerServedTurn(player1);
        assertTrue(jail.playerHasToGetOut(player1));

    }
}
