package dtu.gruppe10.gui;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class GUIWindow extends JFrame implements Runnable {
    protected final GUIBoard board;
    protected HashMap<Integer, GUIPlayer> idsToPlayers;
    protected HashMap<Integer, Integer> idsToPositions;

    public GUIWindow(Rectangle bounds, GUIPlayer[] players) {
        super("Matador");

        board = new GUIBoard();

        idsToPlayers = new HashMap<>();
        idsToPositions = new HashMap<>();
        for (GUIPlayer player : players) {
            idsToPlayers.put(player.ID, player);
            idsToPositions.put(player.ID, 0);
        }

        Random rng = new Random();
        for (int i = 0; i < rng.nextInt(100); ++i) {
            int index = rng.nextInt(board.fieldCount);

            int id = rng.nextInt(4) + 1;
            board.newOwner(index, idsToPlayers.get(id));
        }

        // Set the size of the window
        setBounds(bounds);

        // Set default values to various things
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        paint(getGraphics()); // Make sure it's painted
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        board.changePositionAndSize(getCenterOfWindow(), getMaxBoardSize());
        board.draw(g);

        for (int playerId : idsToPlayers.keySet()) {
            board.drawPlayer(g, idsToPlayers.get(playerId), idsToPositions.get(playerId));
        }
    }

    public void setNewPlayerPosition(int playerId, int positionIndex) {
        idsToPositions.put(playerId, positionIndex);
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

    @Override
    public void run() {
        setVisible(true);
        paint(getGraphics());
    }
}
