package dtu.gruppe10;

import junit.framework.TestCase;
import org.junit.Test;

public class PlayerTest extends TestCase {
    @Test
    public void testPayRent() {
        Player receiver = new Player(1, 35000);
        Player payer = new Player(2, 37000);
        int rent = 1000;

        Player.payRent(receiver, payer, rent);
        assertEquals(36000, receiver.Account.getBalance());
        assertEquals(36000, payer.Account.getBalance());
    }
}