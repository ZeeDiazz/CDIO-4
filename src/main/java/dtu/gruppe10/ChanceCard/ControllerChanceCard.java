package dtu.gruppe10.ChanceCard;


import dtu.gruppe10.Account;
import dtu.gruppe10.Jail;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;

import java.util.Random;

public class ControllerChanceCard {
    private ChanceCard[] chanceCards;
    private  static ControllerChanceCard instance;
    public PlayerMovement playerMovement;
    public Board board;
    public Player player;
    public Jail jail;
    public Account account;
    public Inventory inventory;
    public int Info;

    // TODO: -1 på alle positioner
    // TODO: Chancekort med Balance
    public ControllerChanceCard() {
        chanceCards = new ChanceCard[]{

                new MoveToCard(29, 1), // start
                new MoveToCard(29, 1), // start
                new MoveToCard(129, 12), // frederiksberg alle
                new MoveToCard(229, 25), // grønningen
                new MoveToCard(329, 33), // hvimmelskaftet
                new MoveToCard(429, 20), // strandvejen
                new MoveToCard(529, 40), // rådhudspladsen
/*
                new MoveToCard(629, 6, 16, 26, 36), // færge
                new MoveToCard(729, 13,29),
                new MoveToCard(829, 16),// mols-linjien*/


                new GoToJailCard(40),

                new MoveCard(31,3),
                new MoveCard(32,-3),

                new BankMoneyCard(47, 500),
                new BankMoneyCard(47, 500),
                new BankMoneyCard(48, 1000),
                new BankMoneyCard(48, 1000),
                new BankMoneyCard(48, 1000),
                new BankMoneyCard(49, 3000),
                new BankMoneyCard(50, 1000),
                new BankMoneyCard(51, 1000),
                new BankMoneyCard(52, 1000),
                new BankMoneyCard(52, 1000),
                new BankMoneyCard(53, 1000),
                new BankMoneyCard(54, 200),
                new BankMoneyCard(55, 200),
                new BankMoneyCard(56, 1000),
                new BankMoneyCard(57, 300),
                new BankMoneyCard(58, 200),
                new BankMoneyCard(59, 3000),
                new BankMoneyCard(59, 3000),
                new BankMoneyCard(60, 1000),
                new BankMoneyCard(61, 200),
                new BankMoneyCard(62, 1000),
                new BankMoneyCard(63, 200),
                new BankMoneyCard(64, 2000),


                new GetOutOfJailFreeCard(1)
        };
    }

        /*The draw method is responsible for drawing a chance card from the array of chance
        * cards and returning it. The method takes a player_iD as an argument, it shifts all the
        * elements of the array to the left, and moves the first element to the last, so the element
        * that is at the last position of the array is the one that is drawn.*/


        /*public ChanceCard draw() {
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
                info = card.getPositionIndex();
                }
            else if (upper instanceof MoveCard) {
                MoveCard card = ((MoveCard) upper);
                info = card.getMoveAmount();
            }
            else if (upper instanceof GetOutOfJailFreeCard){
                MoveCard card = ((MoveCard) upper);

                inventory.addChanceCard(card);

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


    // Fisher-Yates shuffle algorithm
    public void bland(ChanceCard[] chanceCards) {
        for (int i = 0; i < chanceCards.length; i++) {
            int randomIndex = (int)(Math.random() * chanceCards.length);
            ChanceCard temp = chanceCards[i];
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
