package dtu.gruppe10;

import junit.framework.TestCase;
import org.junit.Test;

public class PlayerTest extends TestCase {

    @Test
    public void testGetPosition() {

    }
    @Test
    public void testSetPosition() {
    }
    @Test
    public void testGetBalance() {
        // Create a new Player object with a balance of 20
        Player player = new Player("Player 1", 20, 0);

        // Check that the player's balance is 20
        assertEquals(20, player.Account.getBalance());
    }
    @Test
    public void testSetBalance() {
        // Create a new Player object with a balance of 20
        Player player = new Player("Player 1", 20, 0);

        // Set the player's balance to 100
        player.Account.add(100);

        // Check that the player's balance is now 120 (100+20)
        assertEquals(120, player.Account.getBalance());
    }
    @Test
    public void testIsBankrupt() {
        // Create a new Player object with a balance of 1000
        Player player = new Player("Player 1", 1000, 0);

        // Check that the player is not bankrupt (has sufficient funds to pay a fee of 500)
        assertFalse(player.canPay(500));

        // Check that the player is bankrupt (does not have sufficient funds to pay a fee of 1000)
        assertTrue(player.canPay(1000));
    }
    @Test
    public void testHandleRent() {
    }
    @Test
    public void testMovePlayer() {
    }
    @Test
    public void testCheckPassedStartField() {
    }
    @Test
    public void testAddToBalance() {
    }
    @Test
    public void testSubtractFromBalance() {
    }
    @Test
    public void testHasRolledPair() {
    }
    @Test
    public void testSetRolledPair() {
    }
    @Test
    public void testInPrison() {
    }
    @Test
    public void testSetInPrison() {
    }
    @Test
    public void testCheckBankrupt() {
    }
}