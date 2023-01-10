package dtu.gruppe10.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class GUIWindow extends JFrame implements Runnable {
    public final GUIBoard Board;
    public final GUIBalances Balances;

    protected GUIState currentState;
    protected HashMap<Integer, GUIPlayer> idToPlayer;
    protected HashMap<Integer, Integer> idToPosition;

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

        // Set the size of the window
        setBounds(bounds);

        // Set default values to various things
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setState(GUIState currentState) {
        this.currentState = currentState;
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
        int windowHeight = getHeight();
        int targetPixelHeight = windowHeight / 30;

        double fontSize = 72.0 * targetPixelHeight / Toolkit.getDefaultToolkit().getScreenResolution();
        return getFont().deriveFont((float)fontSize);
    }

    @Override
    public void run() {
        setVisible(true);
        paint(getGraphics());
    }
}
