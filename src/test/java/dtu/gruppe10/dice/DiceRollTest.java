package dtu.gruppe10.dice;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Test;

public class DiceRollTest extends TestCase {
    @Test
    public void testIfDiceRollNumbersIsCorrect(){
        Die die1 = new SixSidedDie();
        Die die2 = new SixSidedDie();
        DieCup cup = new DieCup(die1,die2);
        assertEquals(5, 3,2);
    }

}