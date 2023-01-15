package dtu.gruppe10.ChanceCard;


    public class PayToBank extends ParentChanceCard {
        private int amount;

        PayToBank(int ID, int amount) {
            super(ID);
            this.amount = amount;
        }

        int getAmount() {
            return amount;

        }

    }




