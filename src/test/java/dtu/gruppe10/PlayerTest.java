package dtu.gruppe10;

import junit.framework.TestCase;
import org.junit.Test;

public class PlayerTest extends TestCase {

    @Test
    public void testGetPosition() {
        Player player = new Player("Player 1", 30000, 1);
        player.movePlayer(3, 4);
        assertEquals(7, player.getPosition());

        player.movePlayer(6, 6);
        assertEquals(19, player.getPosition()); // wrapped around to start
    }
    @Test
    public void testSetPosition() {
        Player player = new Player("Player 1", 30000, 1);

        player.setPosition(5);
        assertEquals(5, player.getPosition());

        player.setPosition(39);
        assertEquals(39, player.getPosition());

        player.setPosition(-1);
        assertEquals(-1, player.getPosition()); // invalid position
    }
    @Test
    public void testCanPay() {
        Player player = new Player("Player 1", 15000, 1);

        assertTrue(player.canPay(5000));
        assertFalse(player.canPay(20000));

        player.Account.add(5000);
        assertTrue(player.canPay(20000));
    }
    @Test
    public void testMovePlayer() {
        Player player1 = new Player("Player 1", 30000, 1);
        Player player2 = new Player("Player 2", 30000, 1);

        player1.movePlayer(3, 2);
        assertEquals(5, player1.getPosition());

        player2.movePlayer(1, 6);
        assertEquals(7, player2.getPosition());

        player1.movePlayer(1, 2);
        //fordi (1+2)+ 5 = 8
        assertEquals(8, player1.getPosition());
    }
    @Test
    public void testCheckPassedStartField() {
        Player player = new Player("Player 1", 30000, 1);

        //Lands on Start field
        player.setPosition(1);

        // Starting Balance
        int prevBalance = player.Account.getBalance();

        //Player 1 was on field nr. 35
        player.checkPassedStartField(35);

        int currentBalance = player.Account.getBalance();
        assertTrue(currentBalance == prevBalance + 4000);
    }
    @Test
    public void testHasRolledPair() {
        Player player = new Player("Player 1",30000, 1);

        player.setRolledPair(false);
        assertFalse(player.hasRolledPair());

        player.setRolledPair(true);
        assertTrue(player.hasRolledPair());
    }
    @Test
    public void testInPrison() {
        Player player = new Player("Player 1",30000,1);
        player.setInPrison(false);
        assertFalse(player.inPrison());
        player.setInPrison(true);
        assertTrue(player.inPrison());

    }
    @Test
    public void testSetInPrison() {
    }
    @Test
    public void testPayRent() {
        Player receiver = new Player("Player 1", 35000, 1);
        Player payer = new Player("Player 2", 37000, 2);
        int rent = 1000;

        Player.payRent(receiver, payer, rent);
        assertEquals(36000, receiver.Account.getBalance());
        assertEquals(36000, payer.Account.getBalance());
    }
}