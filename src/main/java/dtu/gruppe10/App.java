package dtu.gruppe10;

import dtu.gruppe10.ChanceCard.*;
import dtu.gruppe10.board.MovementType;
import dtu.gruppe10.board.PlayerMovement;
import dtu.gruppe10.board.fields.*;
import dtu.gruppe10.dice.DiceRoll;
import dtu.gruppe10.dice.DieCup;
import dtu.gruppe10.dice.SixSidedDie;
import dtu.gruppe10.gui.*;
import dtu.gruppe10.gui.prompts.GUIAnswer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static Game game;
    protected static boolean wantsToPayBail;
    protected static boolean moveHacks;
    protected static int moveHackAmount;
    protected static boolean moveHackDouble;

    public static Player[] players;

    protected static Jail jail;
    private static GUIWindow window;

    public static void main(String[] args) throws Exception {
        window = new GUIWindow(new Rectangle(100, 100, 1000, 500), GUITest.generateFields());

        Thread uiThread = new Thread(window, "uiThread");

        window.setState(GUIState.START_GAME);
        uiThread.start();

        GUIAnswer<Integer> playerCountAnswer = window.getUserInt("Enter the number of players (between {0}-{1})", 2, 6);
        while (!playerCountAnswer.hasAnswer()) {
            trySleep(10);
        }

        int playerCount = playerCountAnswer.getAnswer();
        players = new Player[playerCount];

        Color[] playerColors = {Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.CYAN, Color.MAGENTA};

        int startBalance = 30000;
        int cpuNum = 1;
        for (int i = 0; i < playerCount; ++i) {
            players[i] = makePlayer(i, startBalance);

            String playerName;
            if (!(players[i] instanceof AIPlayer)) {
                GUIAnswer<String> playerNameAnswer = window.getUserString("Player " + (i + 1) + " enter your name", 1, 15);
                window.repaint();

                while (!playerNameAnswer.hasAnswer()) {
                    trySleep(10);
                }

                playerName = playerNameAnswer.getAnswer();
            } else {
                playerName = "CPU " + cpuNum;
                cpuNum++;
            }

            window.addPlayer(new GUIPlayer(i, playerName, playerColors[i]));
        }

        window.setState(GUIState.PLAYING);

        moveHacks = false;
        window.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                switch (e.getKeyChar()) {
                    case 'j' -> {
                        System.out.println("Player chose to pay bail");
                        wantsToPayBail = true;
                    }
                    case 'm' -> {
                        moveHacks = !moveHacks;

                        if (moveHacks) {
                            System.out.println("Move hacks enabled");
                        } else {
                            System.out.println("Move hacks disabled");
                            moveHackAmount = 0;
                            moveHackDouble = false;
                        }
                    }
                }

                if (moveHacks) {
                    if (Character.isDigit(e.getKeyChar())) {
                        moveHackAmount = Character.getNumericValue(e.getKeyChar());
                    } else if (e.getKeyChar() == 'd') {
                        moveHackDouble = true;
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        for (int i = 0; i < playerCount; ++i) {
            window.updatePlayerBalance(i, startBalance);
        }

        DieCup cup = new DieCup(new SixSidedDie(), new SixSidedDie());
        jail = new Jail(1000, 3);

        ArrayOfFields fieldReader = new ArrayOfFields();
        try {
            fieldReader.readFieldData();
        } catch (IOException e) {
            System.out.println("Frick");
        }
        game = new Game(players, fieldReader.getFields());

        int turnCount = 0;
        while (!game.gameIsOver()) {
            wantsToPayBail = false;

            Player currentPlayer = game.getCurrentPlayer();
            System.out.println("\nPlayer " + currentPlayer.ID + " is starting their turn");
            turnCount++;

            if (jail.playerIsJailed(currentPlayer)) {
                if (currentPlayer instanceof AIPlayer ai) {
                    wantsToPayBail = ai.wantsToBuyProperty();
                }
                else {
                    wantsToPayBail = JOptionPane.showConfirmDialog(null, "Do you want to pay bail? (" + jail.BailPrice + ")", "Question", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                }
            }

            if (wantsToPayBail) {
                updatePlayerBalance(window, currentPlayer, -jail.BailPrice);

                if (currentPlayer.Account.isBankrupt()) {
                    for (int i = 0; i < game.Board.FieldCount; ++i) {
                        Field field = game.Board.getFieldAt(i);
                        if (!(field instanceof PropertyField property)) {
                            continue;
                        }

                        setPropertyOwner(window, i, property, null);
                    }
                    newBankruptcy(window, game, currentPlayer);
                    continue;
                }

                freeFromJail(window, jail, currentPlayer);
            }

            if (!(currentPlayer instanceof AIPlayer)) {
                window.hasToRoll();
                window.repaint();

                while (!window.hasPressedRoll()) {
                    trySleep(10);
                }
            }

            cup.roll();
            DiceRoll roll = cup.getResult();
            window.Board.diceThrown(roll.getValue(0), roll.getValue(1));
            window.repaint();
            trySleep(1000);

            window.Board.diceThrown(-1, -1);
            trySleep(1000);

            if (turnCount == 3 && (roll.AreSame || moveHackDouble)) {
                System.out.println("Player was sent to jail for three consecutive doubles");
                JOptionPane.showMessageDialog(window, "You were sent to jail for three consecutive doubles", "Unfortunate", JOptionPane.INFORMATION_MESSAGE);
                setInJail(window, jail, currentPlayer);
                PlayerMovement move = game.Board.generateDirectMove(currentPlayer.ID, game.Board.getPrisonIndex());
                game.Board.performMove(currentPlayer.ID, move);


                turnCount = 0;
                game.nextTurn();
                continue;
            }

            if (jail.playerIsJailed(currentPlayer)) {
                boolean release = false;

                if (roll.AreSame || moveHacks && moveHackDouble) {
                    System.out.println("Player rolled out of prison");
                    JOptionPane.showMessageDialog(null, "You rolled doubles, and was let out of prison", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    release = true;
                } else if (jail.playerHasToGetOut(currentPlayer)) {
                    System.out.println("Player paid bail");

                    JOptionPane.showMessageDialog(null, "You had to pay bail", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    updatePlayerBalance(window, currentPlayer, -jail.BailPrice);

                    if (currentPlayer.Account.isBankrupt()) {
                        for (int i = 0; i < game.Board.FieldCount; ++i) {
                            Field field = game.Board.getFieldAt(i);
                            if (!(field instanceof PropertyField property)) {
                                continue;
                            }

                            setPropertyOwner(window, i, property, null);
                        }
                        newBankruptcy(window, game, currentPlayer);
                        continue;
                    }
                    release = true;
                }

                if (!release) {
                    jail.playerServedTurn(currentPlayer);
                    System.out.println("Player is spending their turn number " + jail.turnsServed(currentPlayer) + " in jail");
                    game.nextTurn();
                    turnCount = 0;
                    continue;
                }

                freeFromJail(window, jail, currentPlayer);
            }

            PlayerMovement move = game.Board.generateForwardMove(currentPlayer.ID, roll.Sum);
            if (moveHacks && moveHackAmount != 0) {
                move = game.Board.generateForwardMove(currentPlayer.ID, moveHackAmount);
            }

            Field endField = game.Board.getFieldAt(move.End);

            movePlayer(window, currentPlayer, move);


            while (endField instanceof ChanceField chanceField) {
                ChanceCard card = chanceField.draw();

                window.displayChanceCard(card);
                System.out.println("Drew card: " + card.ID);

                if (card instanceof MoneyCard moneyCard) {
                    int amount = moneyCard.getAmount();
                    if (card instanceof BankMoneyCard) {
                        updatePlayerBalance(window, currentPlayer, amount);
                    }
                    else if (card instanceof OtherPlayersMoneyCard otherPlayersMoneyCard) {
                        int receiveAmount = otherPlayersMoneyCard.calculateReceivingAmount(game.players.size() - 1);
                        for (Player p : game.players) {
                            if (p.equals(currentPlayer)) {
                                continue;
                            }
                            updatePlayerBalance(window, p, -amount, false);

                            if (p.Account.isBankrupt()) {
                                for (int i = 0; i < game.Board.FieldCount; ++i) {
                                    Field field = game.Board.getFieldAt(i);
                                    if (!(field instanceof PropertyField property)) {
                                        continue;
                                    }

                                    setPropertyOwner(window, i, property, currentPlayer);
                                }
                                newBankruptcy(window, game, p);
                            }
                        }
                        updatePlayerBalance(window, currentPlayer, receiveAmount);
                    }
                    else if (card instanceof PerHouseMoneyCard perHouseMoneyCard) {
                        int payAmount = perHouseMoneyCard.getPayAmount(currentPlayer, game.Board);
                        updatePlayerBalance(window, currentPlayer, -payAmount);

                        if (currentPlayer.Account.isBankrupt()) {
                            for (int i = 0; i < game.Board.FieldCount; ++i) {
                                Field field = game.Board.getFieldAt(i);
                                if (!(field instanceof PropertyField property)) {
                                    continue;
                                }

                                setPropertyOwner(window, i, property, null);
                            }
                            newBankruptcy(window, game, currentPlayer);
                            continue;
                        }
                    }
                    break;
                } else if (card instanceof JailCard) {
                    if (card instanceof GoToJailCard) {
                        setInJail(window, jail, currentPlayer);
                        move = game.Board.generateDirectMove(currentPlayer.ID, game.Board.getPrisonIndex());
                        game.Board.performMove(currentPlayer.ID, move);

                        game.nextTurn();
                        continue;
                    }
                    else if (card instanceof GetOutOfJailFreeCard) {
                        // TODO
                    }
                    break;
                } else if (card instanceof MoveCard) {
                    if (card instanceof MoveByCard moveByCard) {
                        if (moveByCard.getAmount() < 0) {
                            move = game.Board.generateBackwardMove(currentPlayer.ID, -1*moveByCard.getAmount());
                        }
                        else {
                            move = game.Board.generateForwardMove(currentPlayer.ID, moveByCard.getAmount());
                        }
                    }
                    else if (card instanceof MoveToCard moveToCard) {
                        move = game.Board.generateForwardMoveToField(currentPlayer.ID, moveToCard.getPositionIndex());
                    }
                    else if (card instanceof MoveToNearestCard moveToNearestCard) {
                        move = game.Board.generateDirectMove(currentPlayer.ID, moveToNearestCard.nearestIndex(game.Board.getPlayerPosition(currentPlayer.ID)));
                    }
                    movePlayer(window, currentPlayer, move);

                    if (move.PassedStart && move.Type != MovementType.BACKWARD) {
                        updatePlayerBalance(window, currentPlayer, 4000);
                    }
                    endField = game.Board.getFieldAt(move.End);
                } else {
                    // ???
                }
            }

            if (endField instanceof GoToJailField) {
                System.out.println("Player landed on 'Go To Jail'");
                JOptionPane.showMessageDialog(null, "You were caught speeding, and sent straight to jail", "Alert", JOptionPane.INFORMATION_MESSAGE);

                move = game.Board.generateDirectMove(currentPlayer.ID, game.Board.getPrisonIndex());
                endField = game.Board.getFieldAt(move.End);

                game.Board.performMove(currentPlayer.ID, move);
                setInJail(window, jail, currentPlayer);
            }

            if (endField instanceof PropertyField propertyField) {
                if (propertyField.isOwned()) {
                    // Pay rent
                    int propertiesInSetOwned = 1;
                    for (int i = 0; i < game.Board.FieldCount; ++i) {
                        Field field = game.Board.getFieldAt(i);
                        if (propertyField.inSetWith(field) && propertyField.getOwner().equals(((PropertyField) field).getOwner())) {
                            propertiesInSetOwned++;
                        }
                    }

                    int toPay;
                    if (propertyField instanceof BreweryField breweryField) {
                        toPay = breweryField.utilityPrice(roll.Sum, propertiesInSetOwned);
                    } else {
                        toPay = propertyField.getCurrentRent(propertiesInSetOwned);
                    }

                    if (!(currentPlayer instanceof AIPlayer)) {
                        JOptionPane.showConfirmDialog(null, "Player " + (currentPlayer.ID + 1) + "you have landed on " + (propertyField.getOwner().ID + 1) + " property " + propertyField.Type.name() + " and have to pay " + toPay, "Pay rent", 0);
                    }
                    payRent(window, currentPlayer, propertyField, toPay);
                } else {
                    boolean wantsToBuy;
                    if (currentPlayer instanceof AIPlayer ai) {
                        // Buy property AI
                        wantsToBuy = ai.wantsToBuyProperty();
                    } else {
                        // Buy property
                        System.out.println("Player " + currentPlayer.ID + ", do you want to buy " + propertyField.ID + " for " + propertyField.Price + "? (Enter 1 for Yes, 0 for No)");
                        wantsToBuy = JOptionPane.showConfirmDialog(null, "Player " + (currentPlayer.ID + 1) + ", do you want to buy " + propertyField.Type.name() + " for " + propertyField.Price + "?", "Buy property", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
                    }
                    if (wantsToBuy) {
                        buyProperty(window, currentPlayer, propertyField, move.End);

                        if (currentPlayer.Account.isBankrupt()) {
                            for (int i = 0; i < game.Board.FieldCount; ++i) {
                                Field field = game.Board.getFieldAt(i);
                                if (!(field instanceof PropertyField property)) {
                                    continue;
                                }

                                setPropertyOwner(window, i, property, null);
                            }
                            newBankruptcy(window, game, currentPlayer);
                        }
                    }
                }
            }

            if (move.PassedStart) {
                updatePlayerBalance(window, currentPlayer, 4000);
            }

            if (currentPlayer.Account.isBankrupt()) {
                newBankruptcy(window, game, currentPlayer);

                game.nextTurn();
                turnCount = 0;
                continue;
            }

            if (!moveHacks && !roll.AreSame) {
                game.nextTurn();
                turnCount = 0;
            }
        }

        window.setState(GUIState.GAME_OVER);
    }

    private static void setPropertyOwner(GUIWindow window, int index, PropertyField propertyField, Player player) {
        propertyField.newOwner(player);

        if (player == null) {
            window.propertyBought(-1, index);
        }
        else {
            window.propertyBought(player.ID, index);
        }
    }

    private static void trySleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ignored) {
        }
    }

    private static void movePlayer(GUIWindow window, Player player, PlayerMovement move) {
        game.Board.performMove(player.ID, move);

        window.movePlayer(player.ID, move, 5, 1000);
        // window.setNewPlayerPosition(player.ID, move.End);

        window.repaint();
    }

    private static void payRent(GUIWindow window, Player player, PropertyField propertyField, int amount) {
        Player owner = propertyField.getOwner();


        if (jail.inmates.containsKey(owner)) {
            System.out.println("Rent was not paid since " + owner.ID + " is in jail.");
        } else {
            Player.payRent(owner, player, amount);

            System.out.println("Player " + player.ID + " paid " + amount + " in rent to " + owner.ID + " (property: " + propertyField.ID + ")");

            updatePlayerBalance(window, owner, amount, false);
            updatePlayerBalance(window, player, -amount, false);

            window.repaint();
        }
    }

    private static void buyProperty(GUIWindow window, Player player, PropertyField propertyField, int index) {
        System.out.println(player.ID + " has bought a property");

        setPropertyOwner(window, index, propertyField, player);

        updatePlayerBalance(window, player, -propertyField.Price);
    }

    private static void updatePlayerBalance(GUIWindow window, Player player, int amount) {
        updatePlayerBalance(window, player, amount, true);
    }

    private static void updatePlayerBalance(GUIWindow window, Player player, int amount, boolean repaint) {
        window.updatePlayerBalance(player.ID, amount);

        if (amount < 0) {
            player.Account.subtract(-amount);
        } else {
            player.Account.add(amount);
        }

        if (repaint) {
            window.repaint();
        }
    }

    private static void newBankruptcy(GUIWindow window, Game game, Player player) {
        System.out.println("Player " + player.ID + " went bankrupt");

        game.removePlayer(player.ID);
        window.playerWentBankrupt(player.ID);

        window.repaint();
    }

    private static void setInJail(GUIWindow window, Jail jail, Player player) {
        System.out.println("Player " + player.ID + " was jailed");

        jail.addPlayer(player);
        jail.playerServedTurn(player);
        window.setPlayerInJail(player.ID);

        window.repaint();
    }

    private static void freeFromJail(GUIWindow window, Jail jail, Player player) {
        System.out.println("Player " + player.ID + " was released from Jail");

        jail.releasePlayer(player);
        window.setPlayerFreeFromJail(player.ID);

        window.repaint();
    }

    private static Player makePlayer(int playerId, int startingBalance) {
        Player player;
        int playerType = JOptionPane.showOptionDialog(null, "Please select player type for Player " + (playerId + 1), "Player Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Human", "AI"}, "Human");
        if (playerType == 0) {
            //Create "Human" players
            player = new Player(playerId, startingBalance);
        } else /* if (playerType == 1) */ {
            //Create AI players and choose their Intelligence
            int intelligence = -1;
            while (intelligence < 1 || intelligence > 10) {
                try {
                    intelligence = Integer.parseInt(JOptionPane.showInputDialog("Enter AI intelligence level (1-10):"));
                }
                catch (NumberFormatException ignored) { }
            }
            player = new AIPlayer(playerId, startingBalance, intelligence);
        }
        return player;
    }

    public static void changeInHouses(int fieldIndex, int amount) {
        Field field = game.Board.getFieldAt(fieldIndex);
        if (!(field instanceof StreetField street)) {
            return;
        }
        if (!game.getCurrentPlayer().equals(street.getOwner())) {
            JOptionPane.showMessageDialog(null, "You cannot build on properties you don't own", "Error", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (amount == 1) {
            if (street.eligibleToBuildHouse()) {
                int ans = JOptionPane.showConfirmDialog(null, "Do you want to build a house on this property?", "Check", JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.NO_OPTION) {
                    return;
                }

                street.buildOneHouse();
                window.Board.addHouse(fieldIndex);
                updatePlayerBalance(window, street.getOwner(), -street.getHousePrice());
            }
            else {
                if (street.getHouseCount() == 5) {
                    JOptionPane.showMessageDialog(null, "Cannot build more than a hotel", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else if (street.getOwner().Account.balance < street.getHousePrice()) {
                    JOptionPane.showMessageDialog(null, "Too poor", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "You have to build houses evenly across the properties in this set", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        else {
            if (street.eligibleToSellHouse()) {
                int ans = JOptionPane.showConfirmDialog(null, "Do you want to demolish a house on this property?", "Check", JOptionPane.YES_NO_OPTION);
                if (ans == JOptionPane.NO_OPTION) {
                    return;
                }

                street.sellOneHouse();
                window.Board.removeHouse(fieldIndex);
                updatePlayerBalance(window, street.getOwner(), street.getHousePrice() / 2);
            }
            else {
                if (street.getHouseCount() == 0) {
                    JOptionPane.showMessageDialog(null, "Cannot demolish, because there are no more houses", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(null, "The houses evenly across the properties in this set", "Error", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
}
