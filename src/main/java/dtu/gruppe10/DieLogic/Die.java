package dtu.gruppe10.DieLogic;

//Taget fra CDIO 3
public abstract class Die {
        protected int face;

        public abstract void roll();

        public int getFace() {
            return face;
        }

        @Override
        public String toString() {
            return "" + getFace();
        }
}
