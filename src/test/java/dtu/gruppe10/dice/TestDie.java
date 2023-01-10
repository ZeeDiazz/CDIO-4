package dtu.gruppe10.dice;

public class TestDie extends Die {
    protected int[] rolls;
    protected int currentRollIndex;

    public TestDie(int... rolls) {
        this.rolls = rolls;
        this.currentRollIndex = 0;
    }

    @Override
    public void roll() {
        currentRollIndex++;
    }

    @Override
    public int getFace() {
        return rolls[currentRollIndex];
    }
}
