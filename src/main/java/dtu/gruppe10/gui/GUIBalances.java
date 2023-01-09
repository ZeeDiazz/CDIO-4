package dtu.gruppe10.gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIBalances {
    protected static Color bankruptColor = new Color(100, 100, 100);

    protected final int playerCount;
    protected HashMap<Integer, GUIPlayer> idToPlayer;
    protected HashMap<Integer, Integer> idToBalance;
    protected ArrayList<String> bankruptPlayers;
    protected Point drawPosition;
    protected Font font;
    protected int spaceBetweenLines;
    protected int xBuffer;
    protected int yBuffer;

    public GUIBalances(GUIPlayer[] players) {
        this.playerCount = players.length;

        idToBalance = new HashMap<>();
        idToPlayer = new HashMap<>();
        bankruptPlayers = new ArrayList<>();
        spaceBetweenLines = 0;

        for (GUIPlayer player : players) {
            idToBalance.put(player.ID, 0);
            idToPlayer.put(player.ID, player);
        }
    }

    public void setBuffer(Point buffer) {
        this.xBuffer = buffer.x;
        this.yBuffer = buffer.y;
    }

    public void setFont(Font newFont) {
        font = newFont;
    }

    public void setSpaceBetweenLines(int spaceBetweenLines) {
        this.spaceBetweenLines = spaceBetweenLines;
    }

    public void draw(Graphics g, int windowHeight) {
        Font prevFont = g.getFont();
        g.setFont(this.font);

        int fontHeight = g.getFontMetrics().getHeight();
        int xTextOffset = fontHeight + 3;
        int circleRadius = fontHeight / 2;

        int startY = windowHeight - yBuffer;
        startY -= playerCount * (fontHeight + spaceBetweenLines);
        drawPosition = new Point(xBuffer, startY);

        drawPosition.translate(circleRadius + 1, 0);

        String[] text = new String[playerCount];
        Color[] colors = new Color[playerCount];
        int playerIndex = 0;

        // Save all the playing players to the text and color arrays
        for (int playerId : idToBalance.keySet()) {
            GUIPlayer player = idToPlayer.get(playerId);
            text[playerIndex] = player.Name + ": $" + idToBalance.get(playerId);
            colors[playerIndex] = player.TokenColor;

            ++playerIndex;
        }
        // Save all the bankrupt players to the text and color arrays
        for (String bankruptName : bankruptPlayers) {
            text[playerIndex] = bankruptName;
            colors[playerIndex] = bankruptColor;

            ++playerIndex;
        }

        // Draw all the players and their info
        for (int i = 0; i < text.length; ++i) {
            drawPosition.translate(0, -(circleRadius / 2));
            GUICircle playerCircle = new GUICircle(drawPosition, circleRadius);
            playerCircle.draw(g, colors[i], true);
            System.out.println("Draw pos: " + drawPosition);

            drawPosition.translate(xTextOffset, circleRadius / 2);
            g.setColor(Color.BLACK);
            g.drawString(text[i], drawPosition.x, drawPosition.y);

            drawPosition.translate(-xTextOffset, spaceBetweenLines + fontHeight);
        }

        g.setFont(prevFont);
    }

    public void playerMadeMoney(int playerId, int amount) {
        playerChangeInMoney(playerId, amount);
    }

    public void playerLostMoney(int playerId, int amount) {
        playerChangeInMoney(playerId, -amount);
    }

    protected void playerChangeInMoney(int playerId, int amount) {
        int playerMoney = idToBalance.get(playerId);
        playerMoney += amount;
        idToBalance.put(playerId, playerMoney);
    }

    public void playerWentBankrupt(int playerId) {
        GUIPlayer bankruptPlayer = idToPlayer.get(playerId);
        idToPlayer.remove(playerId);
        idToBalance.remove(playerId);

        bankruptPlayers.add(bankruptPlayer.Name);
    }
}
