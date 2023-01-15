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



    private ControllerChanceCard() {
        chanceCards = new ParentChanceCard[]{
                new MoveToCard(29, new int[]{39}),
                new MoveToCard(129, new int[]{29}),
                new MoveToCard(229, new int[]{20}),
                new MoveToCard(329, new int[]{4}),
                new MoveToCard(429, new int[]{12}),
                new MoveToCard(529, new int[]{9}),
                new MoveToCard(629, new int[]{1}),
                new MoveToCard(729, new int[]{5, 15, 25, 35}),
                new GoToJailCard(40),
                new MoveCard(31,3),
                new MoveCard(32,-3)

        };
    }


        /*The draw method is responsible for drawing a chance card from the array of chance
        * cards and returning it. The method takes a player_iD as an argument, it shifts all the
        * elements of the array to the left, and moves the first element to the last, so the element
        * that is at the last position of the array is the one that is drawn.*/
        public ParentChanceCard draw(int player_iD) {
            ParentChanceCard upper = chanceCards[0];
            for (int i = 0; i < chanceCards.length - 1; i++) {
                chanceCards[i] = chanceCards[i + 1];
            }
            chanceCards[chanceCards.length - 1] = upper;
            int destination = 0;
            int moveToField = 0;

            if (upper instanceof MoveToCard) {
                switch (upper.getID()) {
                    case 29: // start
                        destination = 1;
                        break;
                    case 129: // frederiksberg alle
                        destination = 12;
                        break;
                    case 229: // grønningen
                        destination = 20;
                        break;
                    case 329: // hvimmelskaftet
                        destination = 33;
                        break;
                    case 429: // strandvejen
                        destination = 20;
                        break;
                    case 529: //rådhuspladsen
                        destination = 40;
                        break;
                    case 629: // nærmeste færge
                        if(board.getPlayerPosition(player_iD)==3 || board.getPlayerPosition(player_iD)==37) {
                            destination = 6;
                        }else if(board.getPlayerPosition(player_iD)==8) {
                            destination = 16;
                        }else if (board.getPlayerPosition(player_iD)==18 || board.getPlayerPosition(player_iD)== 23) {
                            destination = 26;
                        }else{ /*(board.getPlayerPosition(player_iD)==34)*/
                            destination = 36;}
                        break;
                    case 729: // nærmeste ræderi
                        if (board.getPlayerPosition(player_iD) == 18 || board.getPlayerPosition(player_iD) == 23 ){
                            destination = 29;/* Coke*/}
                        else{
                            destination = 13;// Squash
                            
                        }

                        break;
                    default:
                        throw new IllegalArgumentException("Invalid ID value");
                }

                MoveToCard card = ((MoveToCard) upper);
                // hvis det virker at tage et tilfældigt moveToCard hver gang der bliver trukket et moveToCard
                int destinationIndex = (int)(Math.random() * card.getDestination().length);
                int randomDestination = card.getDestination()[destinationIndex];
                //randomDestination skal så erstatte desination i næste linje

                PlayerMovement moveAmount = board.generateDirectMove(player.ID, destination);
                board.performMove(player.ID,moveAmount);


            }
            else if (upper instanceof GoToJailCard){
                GoToJailCard card = ((GoToJailCard) upper);

                //jail.addPlayer(Player player.ID);

            } else if (upper instanceof MoveCard) {
                MoveCard card = ((MoveCard) upper);
                switch (upper.getID()) {
                    case 31:
                        moveToField = 3;
                        break;
                    case 32:
                        moveToField = -3;
                        break;
                }
                // tænker her at at card.get_amount burde kunne eleminere switch casen
                int moveToFields = card.get_amount();
                PlayerMovement moveAmount = board.generateForwardMove(player_iD,moveToField);

                board.performMove(player_iD,moveAmount);




            }
            return upper;
        }
    public static ControllerChanceCard get()
    {
        if (instance == null) {
            instance = new ControllerChanceCard();
        }
        return instance;
    }

    }
    //int destination = card.getDestination()[0];
//PlayerMovement moveAmount = board.generateDirectMove(player.ID, destination);
////også bruger
//board.prefromMove(player.ID,moveAmount);


    /*

        private String name;
        private String description;
        private Action action;

        public ChanceCard(String name, String description, Action action) {
            this.name = name;
            this.description = description;
            this.action = action;
        }

        public void takeAction(Player player) {
            action.perform(player);
        }
    }

    interface Action {
        void perform(Player player);
    }

    /*
    private ArrayList<ChanceCardAction[]> actions;

    public ChanceCard() {
        this.actions = new ArrayList<>();
    }
    public void addAction(ChanceCardAction action) {
        this.actions.add(new ChanceCardAction[]{action});
    }
    public ArrayList<ChanceCardAction[]> getActions() {
        return actions;
    }

    public void takeActions(Player player, Board board){
        for (ChanceCardAction[] actionList : actions) {
            for (ChanceCardAction action : actionList) {
                action.perform(player, board);
            }
        }
    }



    //Could use a interface and than make a class for everyChanceCard...
    interface ChanceKortAction{
        void perform(Player player, Board board);
    }*/

