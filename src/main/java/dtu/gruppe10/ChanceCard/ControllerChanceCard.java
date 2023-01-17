package dtu.gruppe10.ChanceCard;


import dtu.gruppe10.Player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ControllerChanceCard {
    private ChanceCard[] arrayOfCards;


    private Queue<ChanceCard> chanceCards;
    private static ControllerChanceCard instance;

    // TODO: -1 på alle positioner
    // TODO: Chancekort med Balance
    public ControllerChanceCard() {
        this.chanceCards = new LinkedList<>();

        arrayOfCards = new ChanceCard[]{
                // BetalingsKort:
                new PerHouseMoneyCard(1, 500, 2000), // 500 pr hus 2000kr pr hotel
                new PerHouseMoneyCard(2, 800, 2300), // 800 kr pr hus, 2300 kr pr hotel

                new BankMoneyCard(3, -1000), // Betal 1000 kroner i bøde
                new BankMoneyCard(4, -300), // Betal for vognvask og smøring kr 300
                new BankMoneyCard(5, -200), // Betal kr 200 for levering af 2 kasser øl
                new BankMoneyCard(6, -3000), // Betal 3000 for reparation af deres vogn
                new BankMoneyCard(7, -3000), // Betal 3000 for reparation af deres vogn
                new BankMoneyCard(8, -1000), // De har købt 4 nye dæk til Deres vogn, betal kr 1000
                new BankMoneyCard(9, -200), // parkeringsbøde 200kr
                new BankMoneyCard(10, -1000), // bilforskikring 1000
                new BankMoneyCard(11, -200),// 200kr told
                new BankMoneyCard(12, -2000), // 2000kr tandlæge
                new BankMoneyCard(13, 500), // modtag 500kr
                new BankMoneyCard(14, 500),// modtag 500kr
                new BankMoneyCard(15, 1000), // modtag 1000kr
                new BankMoneyCard(16, 1000), // modtag 1000kr
                new BankMoneyCard(17, 1000), // modtag 1000kr
                new BankMoneyCard(18, 200), // modtag 200
                new BankMoneyCard(19, 40000), // modtag 40.000 hvis netWorth<15.000kr

                new OtherPlayersMoneyCard(20, 200, 200), // 200 fra andre spillere
                new OtherPlayersMoneyCard(21, 500, 500), // modtag 500 fra hver spiller

                // BevægelsesKort:
                new MoveToCard(29, 1), // start
                new MoveToCard(30, 1), // start

                new MoveCard(31, 3),// tre felter frem
                new MoveCard(32, -3),// tre felter tilbage
                new MoveCard(33, -3),// tre felter tilbage

                new MoveToCard(34, 12), // frederiksberg alle
                new MoveToNearestCard(35, 1), // færge
                new MoveToNearestCard(36, 1), // færge

                new MoveToCard(37, 16),// mols-linjien
                new MoveToCard(38, 33),// hvimmeskaftet
                new MoveToCard(39, 25), // grønningen
                new MoveToNearestCard(40, 1), // færge
                new MoveToCard(41, 20), // strandvejen
                new MoveToCard(42, 40), // rådhudspladsen
                new GetOutOfJailFreeCard(43), // ud af fængsel
                new GoToJailCard(44), // fængsel
                new GoToJailCard(45) // fængsel


        };
    }


    public Queue addToQueue() {
        Random rand = new Random();

        ArrayList list = new ArrayList<ChanceCard>();

        for (ChanceCard card : this.arrayOfCards) {
            list.add(card);
        }

        int i = 0;
        while (list.size() > 0) {


            int randomInt = rand.nextInt(arrayOfCards.length - i);
            int helper = randomInt;

            this.chanceCards.add((ChanceCard) list.get(helper));
            list.remove(helper);
            i = i + 1;
        }

        return this.chanceCards;

    }

    public ChanceCard draw() {


        ChanceCard kort = chanceCards.remove();
        chanceCards.add(kort);


        Random rand = new Random();


        int drawIndex = rand.nextInt(arrayOfCards.length);

        ChanceCard drawnCard = arrayOfCards[drawIndex];
        // move the drawn card to the last position in the array
        for (int i = drawIndex; i < arrayOfCards.length - 1; i++) {
            arrayOfCards[i] = arrayOfCards[i + 1];
        }
        arrayOfCards[arrayOfCards.length - 1] = null;
        return drawnCard;
    }

    public void activateChanceCard(Player player) {
        ChanceCard chanceCard = draw();

        if (chanceCard instanceof PerHouseMoneyCard) {
            ((PerHouseMoneyCard) chanceCard).getAmount(player);

        } else if (chanceCard instanceof BankMoneyCard) {
            ((BankMoneyCard) chanceCard).getAmount();

        } else if (chanceCard instanceof OtherPlayersMoneyCard) {
            ((OtherPlayersMoneyCard) chanceCard).calculateReceivingAmount();
            ((OtherPlayersMoneyCard) chanceCard).calculatePayingAmount();

        } else if (chanceCard instanceof MoveToCard) {
            ((MoveToCard) chanceCard).getPositionIndex();

        } else if (chanceCard instanceof MoveCard) {
            ((MoveCard) chanceCard).getMoveAmount();

        } else if (chanceCard instanceof MoveToNearestCard) {
            ((MoveToNearestCard) chanceCard).getMoveAmount();

        } else if (chanceCard instanceof GetOutOfJailFreeCard) {


        } else if (chanceCard instanceof GoToJailCard) {
            ((GoToJailCard) chanceCard).playerGetsJailed();

        }

    }

    public ChanceCard drawFirstCard() {
        //get the first card
        ChanceCard drawnCard = arrayOfCards[0];
        // move all the other cards one position forward
        for (int i = 0; i < arrayOfCards.length - 1; i++) {
            arrayOfCards[i] = arrayOfCards[i + 1];
        }
        arrayOfCards[arrayOfCards.length - 1] = null;
        return drawnCard;
    }

    // Fisher-Yates shuffle algorithm
    public void bland(ChanceCard[] chanceCards) {
        for (int i = 0; i < chanceCards.length; i++) {
            int randomIndex = (int) (Math.random() * chanceCards.length);
            ChanceCard temp = chanceCards[i];
            chanceCards[i] = chanceCards[randomIndex];
            chanceCards[randomIndex] = temp;
        }
    }

    public static ControllerChanceCard get() {
        if (instance == null) {
            instance = new ControllerChanceCard();
        }
        return instance;
    }



    /*The draw method is responsible for drawing a chance card from the array of chance
     * cards and returning it. The method takes a player_iD as an argument, it shifts all the
     * elements of the array to the left, and moves the first element to the last, so the element
     * that is at the last position of the array is the one that is drawn.*/


       /* public ChanceCard draw() {
            ChanceCard upper = chanceCards[0];
            int info = 0;
            for (int i = 0; i < chanceCards.length - 1; i++) {
                chanceCards[i] = chanceCards[i + 1];
            }
            chanceCards[chanceCards.length - 1] = upper;
            int amount = 0;
            int newPos = 0;


            if (upper instanceof MoveToCard) {
                MoveToCard card = ((MoveToCard) upper);

                }
            else if (upper instanceof MoveCard) {
                MoveCard card = ((MoveCard) upper);
                return card.getID();
            }
            else if (upper instanceof GetOutOfJailFreeCard){
                GetOutOfJailFreeCard card = ((GetOutOfJailFreeCard) upper);


                //inventory.addChanceCard(card);

            }



            else if (upper instanceof BankMoneyCard) { //add to account
                BankMoneyCard card = ((BankMoneyCard) upper);
                switch (upper.ID) {

                    case 47: //klasselotteriet
                        amount = 500;
                        break;

                    case 48: //aktieudbytte
                        amount = 1000;
                        break;

                    case 49: //kvartals skat
                        amount = 3000;
                        break;

                    case 50: //tipning
                        amount = 1000;
                        break;

                    case 51: //gageforhøjelse
                        amount = 1000;
                        break;

                    case 52: //præmieobligation
                        amount = 1000;
                        break;

                    case 53: //gamle møbler på aktion
                        amount = 1000;
                        break;

                    case 54: //Værdien af egen avl fra nyttehaven
                        amount = 200;
                        break;

                    case 55: //fødsesldag
                        amount = 200;
                        break;
                }
                account.add(amount);

            } else if (upper instanceof BankMoneyCard) { //subtract from account
                BankMoneyCard card = ((BankMoneyCard) upper);
                switch (upper.ID) {

                    case 56: //"fuld stop" bøde
                        amount = 1000;
                        break;

                    case 57: //vognvask og smøring
                        amount = 300;
                        break;

                    case 58: //levering af 2 kasser øl
                        amount = 200;
                        break;

                    case 59: //reparation af deres vogn
                        amount = 3000;
                        break;

                    case 60: //4 nye dæk
                        amount = 1000;
                        break;

                    case 61: //parkeringsbøde
                        amount = 200;
                        break;

                    case 62: //bilforsikring
                        amount = 1000;
                        break;

                    case 63: //betaling af smør i told
                        amount = 200;
                        break;

                    case 64: //Tandlægeregning
                        amount = 2000;
                        break;
                }
                account.subtract(amount);

            } else if (upper instanceof GoToJailCard){
                GoToJailCard card = ((GoToJailCard) upper);
                switch (upper.ID) {
                    case 40: // jail
                        newPos = 31;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid ID value");
                }
                PlayerMovement goToJail = board.generateDirectMove(player.ID, newPos);
                board.performMove(player.ID,goToJail);
                jail.addPlayer(player);

            }


            return upper;
        }*/


}
