package dtu.gruppe10.dice;

import junit.framework.TestCase;
import junit.framework.TestResult;
import org.junit.Test;

public class DiceRollTest extends TestCase {
    @Test
    public void testIfDiceRollNumbersIsCorrect(){
        Die die1 = new TestDie(5);
        Die die2 = new TestDie(6);

        DiceRoll roll = new DiceRoll(die1, die2);
        assertEquals(11, roll.Sum);
    }
    @Test
    public void testIfDiceRollNumbersIsFalse() {
        Die die1 = new TestDie(5);
        Die die2 = new TestDie(6);

        DiceRoll roll = new DiceRoll(die1, die2);
        assertTrue(2 != roll.Sum);
    }
    @Test
    public void testIfTheDiceFacesAreTheSame() {
        Die die1 = new TestDie(6);
        Die die2 = new TestDie(6);

        DiceRoll roll = new DiceRoll(die1, die2);
        assertEquals(true, roll.AreSame);
    }
    @Test
    public void testIfTheDiceFacesAreNotTheSame() {
        Die die1 = new TestDie(6);
        Die die2 = new TestDie(2);

        DiceRoll roll = new DiceRoll(die1, die2);
        assertEquals(false, roll.AreSame);
    }
    @Test
    public void testIndexOfDice() {
        Die die1 = new TestDie(6);

        DiceRoll roll = new DiceRoll(die1);
        assert(roll.getValue(6));
    }
}