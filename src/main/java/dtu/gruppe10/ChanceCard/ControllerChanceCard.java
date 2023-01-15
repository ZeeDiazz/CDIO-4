package dtu.gruppe10.ChanceCard;


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
    public Inventory inventory;


    // TODO: -1 på alle positioner
    // TODO: Chancekort med Balance
    private ControllerChanceCard() {
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

                PlayerMovement moveAmount = board.generateDirectMove(player.ID, newPos);
                board.performMove(player.ID,moveAmount);


            }
            else if (upper instanceof GoToJailCard){
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
