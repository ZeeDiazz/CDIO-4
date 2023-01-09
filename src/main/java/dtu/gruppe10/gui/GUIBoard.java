package dtu.gruppe10.gui;

import java.awt.*;
import java.util.Random;

public class GUIBoard {
    private static final float outerCirclePercentSize = 1f;
    private static final float innerCirclePercentSize = 0.7f;
    private static final float splitCirclePercentSize = 0.9f;
    private static final float playerPathPercentSize = 0.95f;
    private static final float ownedCirclePercentSize = 0.65f;

    protected static Color boardColor = new Color(114, 24, 24);
    protected static Color fieldBaseColor = new Color(108, 159, 159);

    protected GUICircle outerCircle;
    protected GUICircle innerCircle;
    protected GUICircle splitCircle;
    protected GUICircle ownedCircle;
    protected GUICircle playerPathCircle;
    protected int fieldCount;
    protected GUIField[] fields;
    protected Color[] ownerColor;
    protected int[] houseCount;

    public GUIBoard(/*GUIField[] fields */) {
        this.fieldCount = 40;

        // Save variables
        this.fields = new GUIField[this.fieldCount];
        this.ownerColor = new Color[this.fieldCount];
        this.houseCount = new int[this.fieldCount];

        // Start, prison, free parking, go to jail
        this.fields[0] = new GUIField(Color.RED, false, null);
        this.fields[10] = new GUIField(Color.BLACK, true, null);
        this.fields[20] = new GUIField(fieldBaseColor, false, null);
        this.fields[30] = new GUIField(Color.BLACK, true, null);

        // Tax fields
        this.fields[4] = new GUIField(fieldBaseColor, false, null);
        this.fields[38] = new GUIField(fieldBaseColor, false, null);

        // Draw card fields
        for (int luckyIndex : new int[]{2, 7, 17, 22, 33, 36}) {
            this.fields[luckyIndex] = new GUIField(Color.BLACK, false, null);
        }

        // Ships
        Color shipColor = new Color(220, 34, 224);
        for (int shipIndex : new int[]{5, 15, 25, 35}) {
            this.fields[shipIndex] = new GUIField(shipColor, true, null);
        }

        // Drinks
        Color drinkColor = new Color(14, 182, 170);
        this.fields[12] = new GUIField(drinkColor, true, null);
        this.fields[28] = new GUIField(drinkColor, true, null);

        // Properties
        int[][] propertyIndexes = {{1, 3}, {6, 8, 9}, {11, 13, 14}, {16, 18, 19}, {21, 23, 24}, {26, 27, 29}, {31, 32, 34}, {37, 39}};
        Color[] propertyColors = {new Color(75, 109, 255), new Color(241, 94, 16), new Color(28, 157, 5), Color.GRAY, new Color(196, 34, 34), Color.WHITE, new Color(185, 133, 4), new Color(41, 9, 182)};
        for (int i = 0; i < propertyIndexes.length; ++i) {
            Color propertyColor = propertyColors[i];
            for (int j = 0; j < propertyIndexes[i].length; ++j) {
                int propertyIndex = propertyIndexes[i][j];
                this.fields[propertyIndex] = new GUIField(propertyColor, true, null);
            }
        }
    }

    public void changePositionAndSize(Point center, int diameter) {
        int boardRadius = diameter / 2;

        this.outerCircle = new GUICircle(center, boardRadius);
        this.innerCircle = outerCircle.getScaledCircle(innerCirclePercentSize);
        this.splitCircle = outerCircle.getScaledCircle(splitCirclePercentSize);
        this.ownedCircle = outerCircle.getScaledCircle(ownedCirclePercentSize);
        this.playerPathCircle = outerCircle.getScaledCircle(playerPathPercentSize);
    }

    public void draw(Graphics g) {
        // Draw the board color
        innerCircle.draw(g, boardColor, true);

        Point[] innerPoints = innerCircle.getAllPoints(fieldCount * 2);
        Point[] outerPoints = outerCircle.getAllPoints(fieldCount);

        // Draw all the fields
        for (int i = 0; i < fieldCount; ++i) {

            if (ownerColor[i] != null) {
                Point ownedCenter = innerPoints[i * 2 + 1];

                GUICircle ownedCircle = new GUICircle(ownedCenter, innerCircle.Radius / 20);
                ownedCircle.draw(g, ownerColor[i], true);
            }

            // Get the information of the current field
            GUIField field = fields[i];

            // Remember the polygon which has to be colored with the fields color
            Polygon coloredPolygon;
            if (field.IsSplit) {
                // Paint the polygon at the top
                Polygon upperPart = field.getTopPolygon(splitCircle, innerCircle, i, fieldCount);
                paintPolygon(g, upperPart, fieldBaseColor);

                coloredPolygon = field.getBottomPolygon(outerCircle, splitCircle, i, fieldCount);
            }
            else {
                coloredPolygon = field.getFullPolygon(outerCircle, innerCircle, i, fieldCount);
            }

            // Paint the fields' painted part (for some it's all, for others it's only a little part)
            paintPolygon(g, coloredPolygon, field.PrimaryColor);

            g.setColor(Color.BLACK);
            // Draw the lines separating the fields
            Point start = innerPoints[i * 2];
            Point end = outerPoints[i];
            g.drawLine(start.x, start.y, end.x, end.y);
        }

        outerCircle.draw(g, false);
        innerCircle.draw(g, false);
    }

    public void drawPlayer(Graphics g, GUIPlayer player, int positionIndex) {
        Color playerColor = player.TokenColor;

        Point playerPoint = playerPathCircle.getSinglePoint(positionIndex * 2 + 1, fieldCount * 2);

        GUICircle playerCircle = new GUICircle(playerPoint, 5);
        playerCircle.draw(g, playerColor, true);
    }

    public void drawPlayers(Graphics g, GUIPlayer[] players, int[] positionIndexes) {
        for (int i = 0; i < players.length; ++i) {
            drawPlayer(g, players[i], positionIndexes[i]);
        }
    }

    protected void paintPolygon(Graphics g, Polygon polygon, Color color) {
        g.setColor(color);
        g.fillPolygon(polygon);
    }

    public void newOwner(int fieldIndex, GUIPlayer player) {
        ownerColor[fieldIndex] = player.TokenColor;
    }

    public void addHouse(int fieldIndex) {
        houseCount[fieldIndex]++;
    }
}
