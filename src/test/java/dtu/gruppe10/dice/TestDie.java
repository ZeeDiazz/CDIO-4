package dtu.gruppe10.dice;

public class TestDie extends Die {
    protected int[] rolls;
    protected int currentRollIndex;

    public TestDie(int currentRollIndex, int... rolls) {
        this.currentRollIndex = currentRollIndex;
        this.rolls = rolls;
    }

    public TestDie(int... rolls) {
        this(0, rolls);
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
