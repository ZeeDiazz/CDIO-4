package dtu.gruppe10.DieLogic;
import java.util.Random;
//Taget fra CDIO 3

public class SixSidedDie extends Die {
        private final Random rng;

        public SixSidedDie() {
            rng = new Random();
            roll();
        }
        public void roll() {
            face = rng.nextInt(6) + 1;
        }
}
