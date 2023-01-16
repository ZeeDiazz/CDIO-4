package dtu.gruppe10;

import java.util.Random;

public class AIPlayer extends Player{
    private int intelligence;
    private final Random rng;

    public AIPlayer(int id, int startingBalance, int intelligence) {
        super(id, startingBalance);
        this.intelligence = intelligence;
        this.rng = new Random();
    }

    public boolean wantsToBuyProperty() {
        return rng.nextInt(10) < intelligence;
    }

    public boolean shouldBuildHouse() {
        return rng.nextInt(10) < intelligence;
    }
}
