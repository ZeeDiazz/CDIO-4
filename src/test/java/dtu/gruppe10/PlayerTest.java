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
        assertEquals(20, player.getBalance());
    }
    @Test
    public void testSetBalance() {
    }
    @Test
    public void testIsBankrupt() {
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