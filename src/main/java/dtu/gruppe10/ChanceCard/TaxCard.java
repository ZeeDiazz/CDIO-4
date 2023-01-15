package dtu.gruppe10.ChanceCard;


    public class TaxCard extends ParentChanceCard {
        private int amount;

        TaxCard(int ID, int amount) {
            super(ID);
            this.amount = amount;
        }

        int getAmount() {
            return amount;

        }

    }




