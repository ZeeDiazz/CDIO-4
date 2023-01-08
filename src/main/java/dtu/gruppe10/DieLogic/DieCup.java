package dtu.gruppe10.DieLogic;

//Taget fra CDIO 3
public class DieCup {
        public final Die Die1;
        public final Die Die2;

        public DieCup(Die die1, Die die2) {
            this.Die1 = die1;
            this.Die2 = die2;
        }

        public void roll() {
            Die1.roll();
            Die2.roll();
        }

        public boolean hasPair() {
            return Die1.getFace() == Die2.getFace();
        }

        public int getSum() {
            return Die1.getFace() + Die2.getFace();
        }
}
