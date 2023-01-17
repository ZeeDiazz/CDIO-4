package dtu.gruppe10.ChanceCard;


import java.util.*;

public class ControllerChanceCard {
    private ChanceCard[] arrayOfCards;
    public Queue<ChanceCard> chanceCards;
    private static ControllerChanceCard instance;

    // TODO: -1 på alle positioner
    // TODO: Chancekort med Balance
    public ControllerChanceCard() {
        arrayOfCards = new ChanceCard[]{
                // BetalingsKort:
                new PerHouseMoneyCard(0, 500, 2000), // 500 pr hus 2000kr pr hotel
                new PerHouseMoneyCard(1, 800, 2300), // 800 kr pr hus, 2300 kr pr hotel

                new BankMoneyCard(2, -1000), // Betal 1000 kroner i bøde
                new BankMoneyCard(3, -300), // Betal for vognvask og smøring kr 300
                new BankMoneyCard(4, -200), // Betal kr 200 for levering af 2 kasser øl
                new BankMoneyCard(5, -3000), // Betal 3000 for reparation af deres vogn
                new BankMoneyCard(6, -3000), // Betal 3000 for reparation af deres vogn
                new BankMoneyCard(7, -1000), // De har købt 4 nye dæk til Deres vogn, betal kr 1000
                new BankMoneyCard(8, -200), // parkeringsbøde 200kr
                new BankMoneyCard(9, -1000), // bilforskikring 1000
                new BankMoneyCard(10, -200),// 200kr told
                new BankMoneyCard(11, -2000), // 2000kr tandlæge
                new BankMoneyCard(12, 500), // modtag 500kr
                new BankMoneyCard(13, 500),// modtag 500kr
                new BankMoneyCard(14, 1000), // modtag 1000kr
                new BankMoneyCard(15, 1000), // modtag 1000kr
                new BankMoneyCard(16, 1000), // modtag 1000kr
                new BankMoneyCard(17, 200), // modtag 200
                // new BankMoneyCard(24, 40000), // modtag 40.000 hvis netWorth<15.000kr

                new OtherPlayersMoneyCard(25, 200), // 200 fra andre spillere
                new OtherPlayersMoneyCard(27, 500), // modtag 500 fra hver spiller

                // BevægelsesKort:
                new MoveToCard(28, 0), // start
                new MoveToCard(39, 0), // start
                new MoveToCard(40, 19), // strandvejen
                new MoveToCard(41, 39), // rådhudspladsen
                new MoveToCard(33, 11), // frederiksberg alle
                new MoveToCard(36, 15),// mols-linjien
                new MoveToCard(38, 32),// hvimmeskaftet
                new MoveToCard(37, 24), // grønningen


                new MoveByCard(30, 3),// tre felter frem
                new MoveByCard(31, -3),// tre felter tilbage
                new MoveByCard(32, -3),// tre felter tilbage


                new MoveToNearestCard(34, new int[] {5, 15, 25, 35}), // færge
                new MoveToNearestCard(35, new int[] {5, 15, 25, 35}), // færge
                new MoveToNearestCard(39, new int[] {5, 15, 25, 35}), // færge

                // new GetOutOfJailFreeCard(42), // ud af fængsel

                new GoToJailCard(43), // fængsel
                new GoToJailCard(44) // fængsel
        };
        this.chanceCards = new LinkedList<>();

        Random rand = new Random();
        ArrayList<ChanceCard> list = new ArrayList<>(Arrays.asList(this.arrayOfCards));

        while (list.size() > 0) {
            int randomIndex = rand.nextInt(list.size());

            this.chanceCards.add(list.get(randomIndex));
            list.remove(randomIndex);
        }
    }

    public ChanceCard draw() {
        ChanceCard card = chanceCards.remove();
        chanceCards.add(card);
        return card;
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
