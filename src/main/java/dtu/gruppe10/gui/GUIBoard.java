package dtu.gruppe10.gui;

import java.awt.*;
import java.util.Random;

public class GUIBoard {
    private static final float playerPathPercentSize = 0.95f;
    private static final float splitCirclePercentSize = 0.9f;
    private static final float prisonCirclePercentSize = 0.8f;
    private static final float innerCirclePercentSize = 0.7f;
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
    protected int prisonIndex;
    protected Point prisonPoint;

    public GUIBoard(GUIField[] fields, int prisonIndex) {
        // Save variables
        this.fields = fields;
        this.fieldCount = fields.length;
        this.ownerColor = new Color[this.fieldCount];
        this.houseCount = new int[this.fieldCount];
        this.prisonIndex = prisonIndex;
    }

    public void changePositionAndSize(Point center, int diameter) {
        int boardRadius = diameter / 2;

        this.outerCircle = new GUICircle(center, boardRadius);
        this.innerCircle = outerCircle.getScaledCircle(innerCirclePercentSize);
        this.splitCircle = outerCircle.getScaledCircle(splitCirclePercentSize);
        this.ownedCircle = outerCircle.getScaledCircle(ownedCirclePercentSize);
        this.playerPathCircle = outerCircle.getScaledCircle(playerPathPercentSize);

        GUICircle prisonCircle = outerCircle.getScaledCircle(prisonCirclePercentSize);
        this.prisonPoint = prisonCircle.getSinglePoint(prisonIndex * 2 + 1, 80);
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
            Color fieldColor = field.PrimaryColor == null ? fieldBaseColor : field.PrimaryColor;
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
        Point playerPoint = playerPathCircle.getSinglePoint(positionIndex * 2 + 1, fieldCount * 2);

        drawPlayer(g, player, playerPoint);
    }

    public void drawPlayerInPrison(Graphics g, GUIPlayer player) {
        drawPlayer(g, player, prisonPoint);
    }

    protected void drawPlayer(Graphics g, GUIPlayer player, Point playerPosition) {
        GUICircle playerCircle = new GUICircle(playerPosition, 5);
        playerCircle.draw(g, player.TokenColor, true);
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
