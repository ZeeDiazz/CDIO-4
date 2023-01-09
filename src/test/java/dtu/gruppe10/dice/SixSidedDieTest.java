package dtu.gruppe10.dice;

import junit.framework.TestCase;
import org.junit.Test;

public class SixSidedDieTest extends TestCase {
    @Test
    public void testRoll() {
        SixSidedDie die = new SixSidedDie();
        die.roll();
        int dieNum = die.getFace();
        assertTrue(dieNum > 0 && dieNum <= 6);

    }
}