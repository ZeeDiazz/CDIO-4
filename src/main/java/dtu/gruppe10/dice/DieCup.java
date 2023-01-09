package dtu.gruppe10.dice;

public class DieCup {
    protected Die[] dice;
    protected DiceRoll lastRoll;

    public DieCup(Die... dice) {
        this.dice = dice;
    }

    public void roll() {
        for (Die d : dice) {
            d.roll();
        }

        lastRoll = new DiceRoll(dice);
    }

    public DiceRoll getResult() {
        return lastRoll;
    }
}
