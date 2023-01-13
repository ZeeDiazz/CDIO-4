package dtu.gruppe10.dice;

import junit.framework.TestCase;
import org.junit.Test;

public class DieCupTest extends TestCase {
    @Test
    public void testHasPair() {
        Die die1 = new SixSidedDie();
        Die die2 = new SixSidedDie();
        DieCup cup = new DieCup(die1,die2);
        cup.roll();
        boolean isHasPair = cup.getResult().AreSame;
        assertTrue(isHasPair == (die1.getFace() == die2.getFace()));

    }
    @Test
    public void testGetSum() {
        Die die1 = new SixSidedDie();
        Die die2 = new SixSidedDie();
        DieCup cup = new DieCup(die1,die2);
        cup.roll();
        int DiceSum = cup.getResult().Sum;
        assertEquals(DiceSum,(die1.getFace() +die2.getFace()));
    }
    @Test
    public void testIfPlayerCanThrowThreePairsInARow(){
        TestDie die1 = new TestDie(4, 3 ,5);
        TestDie die2 = new TestDie(4, 3, 5);
        DieCup cup = new DieCup(die1,die2);
        cup.roll();
        boolean isHasPair = cup.getResult().AreSame;
        assertTrue(isHasPair == (die1.getFace() == die2.getFace()));
        cup.roll();

        boolean isHasPair2 = cup.getResult().AreSame;
        assertTrue(isHasPair2 == (die1.getFace() == die2.getFace()));

        boolean isHasPair3 = cup.getResult().AreSame;
        assertTrue(isHasPair3 == (die1.getFace() == die2.getFace()));
    }
    public void testIfPlayerCanThrowOnPair(){
        TestDie die1 = new TestDie(4, 3, 5);
        TestDie die2 = new TestDie(4, 3, 5);
        DieCup cup = new DieCup(die1,die2);
        cup.roll();
        boolean isHasPair = cup.getResult().AreSame;
        assertTrue(isHasPair == (die1.getFace() == die2.getFace()));
    }
}