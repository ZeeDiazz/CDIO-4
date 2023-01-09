package dtu.gruppe10.dice;

public class DiceRoll {
    public final int Sum;
    public final boolean AreSame;
    protected int[] values;

    public DiceRoll(Die... dice) {
        int firstFace = dice[0].getFace();
        int sum = firstFace;

        boolean areAllSame = true;

        this.values = new int[dice.length];
        this.values[0] = firstFace;
        for (int i = 1; i < dice.length; ++i) {
            Die die = dice[i];

            int dieFace = die.getFace();

            sum += dieFace;
            if (firstFace != dieFace) {
                areAllSame = false;
            }
            this.values[i] = dieFace;
        }

        this.Sum = sum;
        this.AreSame = areAllSame;
    }

    public int getValue(int index) {
        return values[index];
    }
}
