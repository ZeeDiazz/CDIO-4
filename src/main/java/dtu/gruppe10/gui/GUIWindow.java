package dtu.gruppe10.gui;

import dtu.gruppe10.AIPlayer;
import dtu.gruppe10.ChanceCard.ChanceCard;
import dtu.gruppe10.Game;
import dtu.gruppe10.Player;
import dtu.gruppe10.board.Board;
import dtu.gruppe10.board.PlayerMovement;
import dtu.gruppe10.board.fields.ArrayOfFields;
import dtu.gruppe10.gui.prompts.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static dtu.gruppe10.App.game;

public class GUIWindow extends JFrame implements Runnable {
    public final GUIBoard Board;
    public Board board;
    private Game game;
    protected GUIBalances balances;

    protected GUIState currentState;
    protected HashMap<Integer, GUIPlayer> idToPlayer;
    protected HashMap<Integer, Float> idToPosition;
    protected HashMap<Integer, Boolean> idToJailedStatus;

    protected ArrayList<Integer> bankruptIds;

    protected GUIPrompt<?> currentPrompt;
    protected GUIAnswer<?> currentAnswer;
    protected KeyListener promptKeyListener;
    protected PromptErrorHandler promptErrorHandler;
    protected String errorInPrompt;
    protected int currentGUIPromptId = 0;
    private GUIChanceCard chanceCardPanel;

    protected boolean needToRoll;
    protected Rectangle rollButton;

    public GUIWindow(Rectangle bounds, GUIField[] guiFields) {
        super("Matador");

        Board = new GUIBoard(guiFields, 10);
        idToPlayer = new HashMap<>();
        idToPosition = new HashMap<>();
        idToJailedStatus = new HashMap<>();
        bankruptIds  = new ArrayList<>();

        promptKeyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                repaint();
                if (e.getKeyChar() == '\n') {
                    enterPressedInPromptMode();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        promptErrorHandler = new PromptErrorHandler() {
            @Override
            public void lastInputNotAccepted(String reason) {
                errorInPrompt(reason);
            }

            @Override
            public void lastAnswerNotAccepted(String reason) {
                errorInPrompt(reason);
            }
        };

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isVisible() && currentState == GUIState.PLAYING && Board.innerCircle.contains(e.getPoint())) {
                    needToRoll = false;
                    repaint();
                }
                //if the player is playing
                if (currentState == GUIState.PLAYING) {
                    for (int i = 0; i < Board.fields.length; i++) {
                        //if the field is clicked
                        if (Board.fields[i].getFullPolygon(Board.outerCircle, Board.innerCircle, i, Board.fields.length).contains(e.getPoint())) {
                            // Display Field information
                            displayFieldInfo(Board.fields[i]);
                        }
                    }
                }
                if (isVisible() && currentState == GUIState.PLAYING){
                    createSellButton();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        // Set the size of the window
        setBounds(bounds);

        // Set default values to various things
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Choose Player type:
        //choosePlayerType();
    }

    public void setState(GUIState currentState) {
        this.currentState = currentState;
        repaint(); // Make sure it's painted

        if (currentState == GUIState.PLAYING) {
            balances = new GUIBalances(this.idToPlayer.values().toArray(new GUIPlayer[0]));
            balances.setBuffer(new Point(5, 2));
        }
    }

    public GUIState getCurrentState() {
        return currentState;
    }

    public void addPlayer(GUIPlayer player) {
        idToPlayer.put(player.ID, player);
        idToPosition.put(player.ID, 0f);
        idToJailedStatus.put(player.ID, false);
    }

    public void addPlayers(GUIPlayer[] players) {
        for (GUIPlayer player : players) {
            addPlayer(player);
        }
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        paint(getGraphics()); // Make sure it's painted
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        if (this.currentState == null) {
            return;
        }

        Point windowCenter = getCenterOfWindow();
        Point drawPoint;

        switch (currentState) {
            case START_GAME -> {
                if (hasPromptCurrently()) {
                    handlePrompt(g);
                }
            }
            case PLAYING -> {
                Board.changePositionAndSize(windowCenter, getMaxBoardSize());
                Board.draw(g);

                GUIPlayer[] players = new GUIPlayer[idToPlayer.size()];
                boolean[] inJail = new boolean[players.length];
                float[] positions = new float[players.length];

                Integer[] playerIds = idToPlayer.keySet().toArray(new Integer[0]);
                for (int i = 0; i < players.length; ++i) {
                    int playerId = playerIds[i];

                    players[i] = idToPlayer.get(playerId);
                    inJail[i] = idToJailedStatus.get(playerId);
                    positions[i] = idToPosition.get(playerId);
                }

                Board.drawPlayers(g, players, positions, inJail);

                int windowHeight = getHeight();
                balances.setFont(getOptimalFontForBalances());
                balances.draw(g, windowHeight);

                if (needToRoll) {
                    // Draw text saying player has to click on the board to roll
                    GUICircle boardCircle = Board.outerCircle;
                    drawPoint = boardCircle.Center.getLocation();

                    drawPoint.translate(0, (int)(boardCircle.Radius * 1.1f));
                    String rollPrompt = "Press middle of board to roll";
                    Font prevFont = g.getFont();
                    Font rollFont = getFontByWindowHeight(30);
                    g.setFont(rollFont);

                    FontMetrics metrics = g.getFontMetrics();
                    int leftShift = -(metrics.stringWidth(rollPrompt) / 2);
                    drawPoint.translate(leftShift, -(metrics.getHeight() / 2));

                    g.setColor(Color.BLACK);
                    g.drawString(rollPrompt, drawPoint.x, drawPoint.y);

                    g.setFont(prevFont);
                }
            }
            case GAME_OVER -> {
                Font gameOverFont = getFontByWindowHeight(4);
                g.setFont(gameOverFont);

                FontMetrics metrics = g.getFontMetrics();
                int leftShift = metrics.stringWidth("Game Over") / 2;

                drawPoint = windowCenter.getLocation();
                drawPoint.translate(-leftShift, 0);
                g.drawString("Game Over", drawPoint.x, drawPoint.y);
                drawPoint.translate(leftShift, 0);

                Font winnerFont = gameOverFont.deriveFont(gameOverFont.getSize() * 0.6f);
                g.setFont(winnerFont);
                metrics = g.getFontMetrics();

                GUIPlayer winner = idToPlayer.values().toArray(new GUIPlayer[0])[0];
                String winnerString = winner.Name + " has won!";
                leftShift = metrics.stringWidth(winnerString) / 2;

                drawPoint.translate(-leftShift, getFontMetrics(gameOverFont).getHeight() * 2);
                g.drawString(winnerString, drawPoint.x, drawPoint.y);
            }
        }

        if (bankruptIds.size() > 0) {
            for (int playerId : bankruptIds) {
                this.idToPlayer.remove(playerId);
                this.idToPosition.remove(playerId);

                this.balances.playerWentBankrupt(playerId);
            }
            bankruptIds.clear();
        }
    }

    public void enterPressedInPromptMode() {
        if (currentPrompt.answerAcceptable()) {
            currentAnswer.setAnswer(currentPrompt.CurrentAnswer);
            removeCurrentPrompt();
        }
    }

    public void errorInPrompt(String reason) {
        errorInPrompt = reason;
    }

    public void propertyBought(int playerId, int fieldIndex) {
        Board.newOwner(fieldIndex, idToPlayer.get(playerId));
    }

    public boolean hasPromptCurrently() {
        return this.currentPrompt != null;
    }

    protected void addPrompt(GUIPrompt<?> prompt, GUIAnswer<?> answer) throws IllegalStateException {
        if (hasPromptCurrently()) {
            throw new IllegalStateException("Cannot run more than one prompt at a time");
        }

        currentPrompt = prompt;
        currentAnswer = answer;

        currentPrompt.addErrorHandler(promptErrorHandler);

        addKeyListener(currentPrompt.getKeyListener());
        addKeyListener(promptKeyListener);

        currentGUIPromptId++;
    }

    public void removeCurrentPrompt() {
        removeKeyListener(this.currentPrompt.getKeyListener());
        removeKeyListener(promptKeyListener);

        this.currentPrompt = null;
        this.currentAnswer = null;
    }

    public void handlePrompt(Graphics g) {
        Color prevColor = g.getColor();
        Font prevFont = g.getFont();
        Font queryFont = getOptimalFontForQuery();

        boolean error = errorInPrompt != null;

        if (error) {
            g.setColor(Color.RED);
        }

        g.setFont(queryFont);

        FontMetrics metrics = g.getFontMetrics();
        int promptWidth = metrics.stringWidth(currentPrompt.Prompt);
        Point drawPoint = getCenterOfWindow();
        drawPoint.translate(-(promptWidth / 2), -(metrics.getHeight() / 2));
        g.drawString(currentPrompt.Prompt, drawPoint.x, drawPoint.y);
        drawPoint.translate(promptWidth / 2, 5 + metrics.getHeight());

        if (currentPrompt.CurrentAnswer != null) {
            int answerWidth = metrics.stringWidth(currentPrompt.CurrentAnswer.toString());
            drawPoint.translate(-(answerWidth / 2), 0);
            g.drawString(currentPrompt.CurrentAnswer.toString(), drawPoint.x, drawPoint.y);
            drawPoint.translate(answerWidth / 2, 0);
        }

        drawPoint.translate(0, metrics.getHeight() * 2);
        if (error) {
            Font errorFont = queryFont.deriveFont(queryFont.getSize() * 0.7f);
            g.setFont(errorFont);

            metrics = getFontMetrics(errorFont);

            int errorWidth = metrics.stringWidth(errorInPrompt);
            drawPoint.translate(-(errorWidth / 2), 0);
            g.drawString(errorInPrompt, drawPoint.x, drawPoint.y);
            drawPoint.translate(errorWidth / 2, 0);

            errorInPrompt = null;
        }

        g.setFont(prevFont);
        g.setColor(prevColor);
    }

    public GUIAnswer<Integer> getUserInt(String prompt, int inclusiveMinimum, int inclusiveMaximum) throws IllegalStateException {
        prompt = prompt.replace("{0}", inclusiveMinimum + "").replace("{1}", inclusiveMaximum + "");

        IntegerPrompt promptObj = new IntegerPrompt(prompt, inclusiveMinimum, inclusiveMaximum);
        GUIAnswer<Integer> answer = new GUIAnswer<>();
        addPrompt(promptObj, answer);

        return answer;
    }

    public GUIAnswer<String> getUserString(String prompt, int inclusiveMinimum, int inclusiveMaximum) throws IllegalStateException {
        prompt = prompt.replace("{0}", inclusiveMinimum + "").replace("{1}", inclusiveMaximum + "");

        StringPrompt promptObj = new StringPrompt(prompt, inclusiveMinimum, inclusiveMaximum, new char[0]);
        GUIAnswer<String> answer = new GUIAnswer<>();
        addPrompt(promptObj, answer);

        return answer;
    }

    public boolean hasPressedRoll() {
        return !needToRoll;
    }

    public void hasToRoll() {
        needToRoll = true;
    }

    public void setNewPlayerPosition(int playerId, float positionIndex) {
        idToPosition.put(playerId, positionIndex);
    }

    public void movePlayer(int playerId, PlayerMovement move, int stepsPerField, int fieldMilliTime) {
        int[] fieldIndexes = move.getFieldIndexes(40, true, false);
        float moveAmount = 1f / stepsPerField;
        int waitTime = fieldMilliTime / stepsPerField;

        for (int fieldIndex : fieldIndexes) {
            for (int j = 0; j < stepsPerField; ++j) {
                setNewPlayerPosition(playerId, fieldIndex + moveAmount * j);

                try {
                    Thread.sleep(waitTime);
                }
                catch (InterruptedException ignored) { }
                repaint();
            }
            setNewPlayerPosition(playerId, fieldIndex);
            repaint();
        }

        setNewPlayerPosition(playerId, move.End);
    }

    public void playerWentBankrupt(int playerId) {
        this.bankruptIds.add(playerId);
    }

    public void updatePlayerBalance(int playerId, int changeAmount) {
        this.balances.playerChangeInMoney(playerId, changeAmount);
    }

    public void setPlayerInJail(int playerId) {
        idToJailedStatus.put(playerId, true);
    }

    public void setPlayerFreeFromJail(int playerId) {
        idToJailedStatus.put(playerId, false);
    }

    protected Point getCenterOfWindow() {
        Rectangle window = getBounds();
        return new Point(window.width / 2, window.height / 2);
    }

    protected int getMaxBoardSize() {
        int buffer = 100;

        Rectangle window = getBounds();
        int maxRadius = Math.min(window.width, window.height) - buffer;

        return Math.max(maxRadius, 0);
    }

    protected Font getOptimalFontForBalances() {
        return getFontByWindowHeight(30); // Get font fitting for 1/30 of window height
    }

    protected Font getOptimalFontForQuery() {
        return getFontByWindowHeight(10); // Get font fitting for 1/4 of window height
    }

    protected Font getFontByWindowHeight(int divisorToHeight) {
        int targetPixelHeight = getHeight() / divisorToHeight;
        return getFontByPixelHeight(targetPixelHeight);
    }

    protected Font getFontByPixelHeight(int targetPixelHeight) {
        double fontSize = 72.0 * targetPixelHeight / Toolkit.getDefaultToolkit().getScreenResolution();
        return getFont().deriveFont((float)fontSize);
    }

    @Override
    public void run() {
        setVisible(true);
        paint(getGraphics());
    }
    private void displayFieldInfo(GUIField field) {
        StringBuilder messageBuilder = new StringBuilder();

        switch (field.ID) {
            case 0 -> messageBuilder.append("Pass to get $4000");
            case 1 -> messageBuilder.append("For when you break the law");
            case 2 -> messageBuilder.append("Go straight to jail");
            case 5 -> messageBuilder.append("When landed on, draw a chance card");
            case 6, 7 -> {
                messageBuilder.append("Pay $");
                messageBuilder.append(field.fieldPrice);
                messageBuilder.append(" in taxes");
            }
            case 9 -> messageBuilder.append("Have a rest");
            default -> {
                messageBuilder.append("Price: ");
                messageBuilder.append(field.fieldPrice);
            }
        }

        JOptionPane.showMessageDialog(this, messageBuilder.toString(), field.fieldName, JOptionPane.INFORMATION_MESSAGE);
    }
    private void createSellButton() {
            setVisible(true);
            JButton sellButton = new JButton("Sell");
            sellButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GUISellProperty sellProperty = new GUISellProperty(idToPlayer.values().toArray(new GUIPlayer[1]), balances, game,board);
                    sellProperty.setVisible(true);
                    sellButton.setPreferredSize(new Dimension(10,10));
                }
            });
            add(sellButton, BorderLayout.NORTH);
    }
    public void displayChanceCard(ChanceCard card) {
        chanceCardPanel.updateCard(card);
        chanceCardPanel.setVisible(true);
    }
    //Choose AI or other Players
    /*private void choosePlayerType() {
        for (int i = 0; i < game.getPlayersLeft().length; i++) {
            int playerType = JOptionPane.showOptionDialog(null, "Please select player type for Player " + (i+1), "Player Type", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Human", "AI" }, "Human");
            if (playerType == 0) {
                //Create "Human" players
                GUIPlayer players = new GUIPlayer(i, "Player " + (i+1), Color.cyan);
            } else if (playerType == 1) {
                //Create AI players and choose their Intelligence
                int intelligence = 7;
                AIPlayer players = new AIPlayer(i, "AI Player " + (i+1),30000, intelligence, game.Board);
            }
        }
    }*/
}
