package dtu.gruppe10.FieldTypes;

import dtu.gruppe10.Player;

public class Property extends Field {
    // Klasse der dækker over ejendomme som man kan bygge huse på, de fire færger og de to bryggeri felter

    protected final int price;
    protected final PropertyColor color;
    protected boolean isBought;           //Om ejendommen er købt. Hvis falsk, så nej, hvis sand, så ja
    protected Player owner;

    protected int numberOfHouses;

    protected int[] rent;

    protected int currentRent;


    public Property(String name, int price, PropertyColor color, int rent0, int rent1) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.rent = new int[2];
        this.rent[0] = rent0;
        this.rent[1] = rent1;
        this.currentRent = rent0;
    }


    public int getCurrentRent() {
        return currentRent;
    }

    public Player getOwner() {
        return owner;
    }


    // Metode der sørger håndterer opkrævning af leje
    // Denne metode tager den betalende spiller, og referer til metoden handleRent i
    // spiller klassen.

    public void receiveRent(Player payer) {
        if (this.landedOn && owner != null && this.owner != payer) {
            Player.payRent(this.owner, payer, currentRent);
        }
    }

    /* Til når man kan bygge huse

        public void updateCurrentRent(int numberHouses) {
            if (numberHouses == 1) {
                this.currentRent = this.rent1;
            } else if (numberHouses == 2) {
                this.currentRent = this.rent2;
            } else if (numberHouses == 3) {
                this.currentRent = this.rent3;
            } else if (numberHouses == 4) {
                this.currentRent = this.rent4;
            } else if (numberHouses == 5) {
                this.currentRent = this.rent5;
            }
        }
    */


    // Metode der gør at en spiller kan købe en ejendom. Man kan kun købe en ejendom når man lander
    // på ejendommen, dvs. når attributten landedOn er true.
    // Hvis ejendommen er købt er købt, stoppes metoden.
    // Man kan også kun købe, hvis man har nok penge til at købe for.

    public void buyProperty(Player buyer) {
        if (this.landedOn) {
            if (this.isBought) {
                return;
            }
            if (buyer.canPay(this.price)) {
                this.isBought = true;
                buyer.Account.subtract(this.price);
                this.owner = buyer;
            }
        }
    }

    //Håndterer det at betale penge og opkrævning af leje
    //Kan kun købe og opkræve leje via denne metode, da det er den eneste metode, der sætter
    //attributten landedOn til at være true. Metoden gør derfor også landedOn falsk igen til sidst i metoden.
    @Override
    public void whenLandedOn(Player player) {
        this.landedOn = true;
        if (this.owner == null) {
            this.buyProperty(player);
        } else {
            this.receiveRent(player);
        }
        this.landedOn = false;
    }
}
