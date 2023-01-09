package dtu.gruppe10.DieLogic;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

public class DieCupTest extends TestCase {
    @Test
    public void testHasPair() {
        Die die1 = new SixSidedDie();
        Die die2 = new SixSidedDie();
        DieCup cup = new DieCup(die1,die2);
        cup.roll();
        boolean isHasPair = cup.hasPair();
        assertTrue(isHasPair == (die1.getFace() == die2.getFace()));

    }
    @Test
    public void testGetSum() {
        Die die1 = new SixSidedDie();
        Die die2 = new SixSidedDie();
        DieCup cup = new DieCup(die1,die2);
        cup.roll();
        int DiceSum = cup.getSum();
        assertEquals(DiceSum,(die1.getFace() +die2.getFace()));
    }
}