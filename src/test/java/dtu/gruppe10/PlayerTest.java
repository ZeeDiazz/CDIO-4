package dtu.gruppe10;

import junit.framework.TestCase;

public class PlayerTest extends TestCase {

    public void testGetPosition() {
        Player player = new Player("Player 1", 10000, 1);
        player.movePlayer(3, 4);
        assertEquals(7, player.getPosition());

        player.movePlayer(6, 6);
        assertEquals(19, player.getPosition()); // wrapped around to start
    }

    public void testSetPosition() {
        Player player = new Player("Player 1", 10000, 1);

        player.setPosition(5);
        assertEquals(5, player.getPosition());

        player.setPosition(39);
        assertEquals(39, player.getPosition());

        player.setPosition(-1);
        assertEquals(-1, player.getPosition()); // invalid position
    }

    public void testCanPay() {
    }

    public void testMovePlayer() {
    }

    public void testCheckPassedStartField() {
    }

    public void testHasRolledPair() {
    }

    public void testSetRolledPair() {
    }

    public void testInPrison() {
    }

    public void testSetInPrison() {
    }

    public void testPayRent() {
        Player receiver = new Player("Player 1", 5000, 1);
        Player payer = new Player("Player 2", 7000, 2);
        int rent = 1000;

        Player.payRent(receiver, payer, rent);
        assertEquals(6000, receiver.Account.getBalance());
        assertEquals(6000, payer.Account.getBalance());
    }
}