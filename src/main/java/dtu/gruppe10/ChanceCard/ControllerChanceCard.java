package dtu.gruppe10.ChanceCard;


import dtu.gruppe10.Account;
import dtu.gruppe10.Jail;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;

public class ControllerChanceCard {
    private ParentChanceCard[] chanceCards;
    private  static ControllerChanceCard instance;
    public PlayerMovement playerMovement;
    public Board board;
    public Player player;
    public Jail jail;
    public Account account;
    public Inventory inventory;
    // Implement Acccount/balance


    // TODO: -1 på alle positioner
    // TODO: Chancekort med Balance
    public ControllerChanceCard() {
        chanceCards = new ParentChanceCard[]{
                new MoveToCard(29, new int[]{1}),
                new MoveToCard(29, new int[]{1}),
                new MoveToCard(129, new int[]{12}),
                new MoveToCard(229, new int[]{25}),
                new MoveToCard(329, new int[]{33}),
                new MoveToCard(429, new int[]{20}),
                new MoveToCard(529, new int[]{40}),

                new MoveToCard(629, new int[]{6, 16, 26, 36}),
                new MoveToCard(729, new int[]{13,29}),
                new MoveToCard(829, new int[]{16}),

                new GoToJailCard(40),

                new MoveCard(31,3),
                new MoveCard(32,-3),

                new TaxCard(47, 500),
                new TaxCard(47, 500),
                new TaxCard(48, 1000),
                new TaxCard(48, 1000),
                new TaxCard(48, 1000),
                new TaxCard(49, 3000),
                new TaxCard(50, 1000),
                new TaxCard(51, 1000),
                new TaxCard(52, 1000),
                new TaxCard(52, 1000),
                new TaxCard(53, 1000),
                new TaxCard(54, 200),
                new TaxCard(55, 200),
                new TaxCard(56, 1000),
                new TaxCard(57, 300),
                new TaxCard(58, 200),
                new TaxCard(59, 3000),
                new TaxCard(59, 3000),
                new TaxCard(60, 1000),
                new TaxCard(61, 200),
                new TaxCard(62, 1000),
                new TaxCard(63, 200),
                new TaxCard(64, 2000),


                new GetOutOfJailFreeCard(1)
        };
    }


        /*The draw method is responsible for drawing a chance card from the array of chance
        * cards and returning it. The method takes a player_iD as an argument, it shifts all the
        * elements of the array to the left, and moves the first element to the last, so the element
        * that is at the last position of the array is the one that is drawn.*/
        public ParentChanceCard draw(int playerId) {
            ParentChanceCard upper = chanceCards[0];
            for (int i = 0; i < chanceCards.length - 1; i++) {
                chanceCards[i] = chanceCards[i + 1];
            }
            chanceCards[chanceCards.length - 1] = upper;
            int amount = 0;
            int newPos = 0;


            if (upper instanceof MoveToCard) {
                switch (upper.getID()) {
                    case 29: // start
                        newPos = 1;
                        break;
                    case 129: // frederiksberg alle
                        newPos = 12;
                        break;
                    case 229: // grønningen
                        newPos = 25;
                        break;
                    case 329: // hvimmelskaftet
                        newPos = 33;
                        break;
                    case 429: // strandvejen
                        newPos = 20;
                        break;
                    case 529: //rådhuspladsen
                        newPos = 40;
                        break;
                    case 629: // nærmeste færge
                        if(board.getPlayerPosition(playerId)==3 || board.getPlayerPosition(playerId)==37) {
                            newPos = 6;
                        }else if(board.getPlayerPosition(playerId)==8) {
                            newPos = 16;
                        }else if (board.getPlayerPosition(playerId)==18 || board.getPlayerPosition(playerId)== 23) {
                            newPos = 26;
                        }else{ /*(board.getPlayerPosition(player_iD)==34)*/
                            newPos = 36;}
                        break;
                    case 829: // mols-linien
                        newPos = 16;
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid ID value");
                }

                MoveToCard card = ((MoveToCard) upper);
                // hvis det virker at tage et tilfældigt moveToCard hver gang der bliver trukket et moveToCard:
                int destinationIndex = (int)(Math.random() * card.getDestination().length);
                int randomDestination = card.getDestination()[destinationIndex];
                //randomDestination skal så erstatte newPos i næste linje
                if (player !=null){
                PlayerMovement moveAmount = board.generateDirectMove(player.ID, newPos);
                board.performMove(player.ID,moveAmount);
                }


            } else if (upper instanceof  TaxCard) { //add to account
                TaxCard card = ((TaxCard) upper);
                switch (upper.getID()) {

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

            } else if (upper instanceof  TaxCard) { //subtract from account
                TaxCard card = ((TaxCard) upper);
                switch (upper.getID()) {

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
                switch (upper.getID()) {
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
            else if (upper instanceof MoveCard) {
                MoveCard card = ((MoveCard) upper);
                switch (upper.getID()) {
                    case 31:
                        amount = 3;
                        break;
                    case 32:
                        amount = -3;
                        break;
                    default:
                        throw  new IllegalArgumentException("Invalid ID value");
                }
                // tænker her at at card.get_amount burde kunne eleminere switch casen
                int moveToFields = card.get_amount();
                PlayerMovement moveAmount = board.generateForwardMove(player.ID,amount);

                board.performMove(player.ID,moveAmount);

            }
            else if (upper instanceof GetOutOfJailFreeCard){
                MoveCard card = ((MoveCard) upper);

                inventory.addChanceCard(card);

            }
            return upper;
        }
    // Fisher-Yates shuffle algorithm
    public void shuffle() {
        for (int i = 0; i < chanceCards.length; i++) {
            int randomIndex = (int)(Math.random() * chanceCards.length);
            ParentChanceCard temp = chanceCards[i];
            chanceCards[i] = chanceCards[randomIndex];
            chanceCards[randomIndex] = temp;
        }
    }
    public static ControllerChanceCard get()
    {
        if (instance == null) {
            instance = new ControllerChanceCard();
        }
        return instance;
    }



    }
