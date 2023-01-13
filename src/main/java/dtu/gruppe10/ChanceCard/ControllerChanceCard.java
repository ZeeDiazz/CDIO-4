package dtu.gruppe10.ChanceCard;


import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;

public class ControllerChanceCard {
    private ParentChanceCard[] chanceCards;
    private  static ControllerChanceCard instance;
    public PlayerMovement playerMovement;
    public Board board;
    public Player player;



    private ControllerChanceCard() {
        chanceCards = new ParentChanceCard[]{
                new MoveToCard(29, new int[]{39}),
                new MoveToCard(29, new int[]{29}),
                new MoveToCard(29, new int[]{20}),
                new MoveToCard(29, new int[]{4}),
                new MoveToCard(29, new int[]{12}),
                new MoveToCard(29, new int[]{9}),
                new MoveToCard(29, new int[]{1}),
                new MoveToCard(29, new int[]{5, 15, 25, 35}),

        };
    }


        public ParentChanceCard draw(int player_iD) {
            ParentChanceCard upper = chanceCards[0];
            for (int i = 0; i < chanceCards.length - 1; i++) {
                chanceCards[i] = chanceCards[i + 1];
            }
            chanceCards[chanceCards.length - 1] = upper;

            if (upper instanceof MoveToCard) {
                MoveToCard card = ((MoveToCard) upper);

                int destination = card.getDestination()[0];
                PlayerMovement moveAmount = board.generateDirectMove(player.ID, 2);
                //find ud af hvordan den nye position bliver generaliseret
                //player instance

            }
            return upper;
        }

    }


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

