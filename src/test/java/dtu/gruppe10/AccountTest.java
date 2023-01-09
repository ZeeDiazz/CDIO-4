package dtu.gruppe10;

import junit.framework.TestCase;
import org.junit.Test;

public class AccountTest extends TestCase {
    @Test
    public void testGetBalance() {
        Account balance = new Account(30000);
        assertEquals(30000,balance.getBalance());
    }
    @Test
    public void testIsBankrupt() {
        Account balance = new Account(30000);
        assertFalse(balance.isBankrupt());
        balance = new Account(-5);
        assertTrue(balance.isBankrupt());
    }
    @Test
    public void testAdd() {
    }
    @Test
    public void testSubtract() {
    }
}