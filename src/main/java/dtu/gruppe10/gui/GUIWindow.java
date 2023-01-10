package dtu.gruppe10.gui;

import dtu.gruppe10.gui.prompts.GUIAnswer;
import dtu.gruppe10.gui.prompts.GUIPrompt;
import dtu.gruppe10.gui.prompts.IntegerPrompt;
import dtu.gruppe10.gui.prompts.PromptErrorHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;

public class GUIWindow extends JFrame implements Runnable {
    public final GUIBoard Board;
    public final GUIBalances Balances;

    protected GUIState currentState;
    protected HashMap<Integer, GUIPlayer> idToPlayer;
    protected HashMap<Integer, Integer> idToPosition;

    protected GUIPrompt<?> currentPrompt;
    protected GUIAnswer<?> currentAnswer;
    protected KeyListener promptKeyListener;
    protected PromptErrorHandler promptErrorHandler;
    protected String errorInPrompt;
    protected int currentGUIPromptId = 0;

    public GUIWindow(Rectangle bounds, GUIPlayer[] players, GUIField[] guiFields) {
        super("Matador");

        Board = new GUIBoard(guiFields, 10);
        Balances = new GUIBalances(players);
        Balances.setBuffer(new Point(5, 2));
        Balances.playerWentBankrupt(1);

        idToPlayer = new HashMap<>();
        idToPosition = new HashMap<>();
        for (GUIPlayer player : players) {
            idToPlayer.put(player.ID, player);
            idToPosition.put(player.ID, 0);
        }

        Random rng = new Random();
        for (int i = 0; i < rng.nextInt(40); ++i) {
            int index = rng.nextInt(Board.fieldCount);

            int id = rng.nextInt(4) + 1;
            Board.newOwner(index, idToPlayer.get(id));
        }

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

        // Set the size of the window
        setBounds(bounds);

        // Set default values to various things
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setState(GUIState currentState) {
        this.currentState = currentState;
        repaint(); // Make sure it's painted
    }

    public GUIState getCurrentState() {
        return currentState;
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

        switch (currentState) {
            case START_GAME -> {
                if (hasPromptCurrently()) {
                    handlePrompt(g);
                }
            }
            case PLAYING -> {
                Board.changePositionAndSize(getCenterOfWindow(), getMaxBoardSize());
                Board.draw(g);

                Random rng = new Random();
                for (int playerId : idToPlayer.keySet()) {
                    GUIPlayer player = idToPlayer.get(playerId);
                    if (rng.nextInt(10) == 0) {
                        Board.drawPlayerInPrison(g, player);
                    }
                    else {
                        Board.drawPlayer(g, player, idToPosition.get(playerId));
                    }
                }

                int windowHeight = getHeight();
                Balances.setFont(getOptimalFontForBalances());
                Balances.draw(g, windowHeight);
            }
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

    public GUIAnswer<Integer> getUserInt(int inclusiveMinimum, int inclusiveMaximum) throws IllegalStateException {
        String promptStr = "Please enter a number between " + inclusiveMinimum + "-" + inclusiveMaximum;

        IntegerPrompt prompt = new IntegerPrompt(promptStr, inclusiveMinimum, inclusiveMaximum);
        GUIAnswer<Integer> answer = new GUIAnswer<>();
        addPrompt(prompt, answer);

        return answer;
    }

    public void setNewPlayerPosition(int playerId, int positionIndex) {
        idToPosition.put(playerId, positionIndex);
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
}
