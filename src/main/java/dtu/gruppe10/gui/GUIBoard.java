package dtu.gruppe10.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIBoard {
    private static final float outerCirclePercentSize = 1f;
    private static final float innerCirclePercentSize = 0.7f;
    private static final float splitCirclePercentSize = 0.9f;
    private static final float playerPathPercentSize = 0.95f;

    protected static Color boardColor = new Color(114, 24, 24);
    protected static Color fieldBaseColor = new Color(108, 159, 159);

    protected GUICircle outerCircle;
    protected GUICircle innerCircle;
    protected GUICircle splitCircle;
    protected GUICircle playerPathCircle;
    protected int fieldCount;
    protected GUIField[] fields;

    public GUIBoard() {
        // Save variables
        this.fields = new GUIField[40];

        // Start, prison, free parking, go to jail
        this.fields[0] = new GUIField(Color.RED, "Start", "Get dat money", Color.WHITE);
        this.fields[10] = new GUIField(Color.BLACK, fieldBaseColor, "Visiting", Color.WHITE, "I fængsel", Color.BLACK, null);
        this.fields[20] = new GUIField(fieldBaseColor, "Free Parking", "", Color.BLACK, null);
        this.fields[30] = new GUIField(Color.BLACK, fieldBaseColor, "Go to Jail", Color.WHITE, "", Color.BLACK, null);

        // Tax fields
        this.fields[4] = new GUIField(fieldBaseColor, "Tax", "Pay tax ($$$)", Color.BLACK);
        this.fields[38] = new GUIField(fieldBaseColor, "Tax", "Pay tax ($)", Color.BLACK);

        // Draw card fields
        for (int luckyIndex : new int[]{2, 7, 17, 22, 33, 36}) {
            this.fields[luckyIndex] = new GUIField(Color.BLACK, "Chance Card", "", Color.WHITE, null);
        }

        // Ships
        Color shipColor = new Color(220, 34, 224);
        for (int shipIndex : new int[]{5, 15, 25, 35}) {
            this.fields[shipIndex] = new GUIField(shipColor, fieldBaseColor, "Ship", Color.BLACK, "4000 kr", Color.BLACK, null);
        }

        // Drinks
        Color drinkColor = new Color(14, 182, 170);
        this.fields[12] = new GUIField(drinkColor, fieldBaseColor, "Fanta", Color.BLACK, "3000 kr", Color.BLACK, null);
        this.fields[28] = new GUIField(drinkColor, fieldBaseColor, "Coca Cola", Color.BLACK, "3000 kr", Color.BLACK, null);

        // Properties
        int[][] propertyIndexes = {{1, 3}, {6, 8, 9}, {11, 13, 14}, {16, 18, 19}, {21, 23, 24}, {26, 27, 29}, {31, 32, 34}, {37, 39}};
        String[][] propertyNames = {{"Rødovre", "Hvidovre"}, {"Roskilde", "Valby", "Allé"}, {"Frederiksberg", "Bülow", "Gl. Konge"}, {"Bernstoff", "Hellerup", "Strand"}, {"Triangel", "Østerbro", "Grønningen"}, {"Bred", "Kgs. Nytorv", "Øster"}, {"Amager", "Vimmel", "Ny"}, {"Frederiksberg", "Rådhus"}};
        Color[] propertyColors = {new Color(75, 109, 255), new Color(241, 94, 16), new Color(28, 157, 5), Color.GRAY, new Color(196, 34, 34), Color.WHITE, new Color(185, 133, 4), new Color(41, 9, 182)};
        for (int i = 0; i < propertyIndexes.length; ++i) {
            Color propertyColor = propertyColors[i];
            for (int j = 0; j < propertyNames[i].length; ++j) {
                int propertyIndex = propertyIndexes[i][j];
                String name = propertyNames[i][j];
                this.fields[propertyIndex] = new GUIField(propertyColor, fieldBaseColor, name, Color.BLACK, "Price", Color.BLACK);
            }
        }

        this.fieldCount = fields.length;
    }

    public void changePositionAndSize(Point center, int diameter) {
        int boardRadius = diameter / 2;

        this.outerCircle = new GUICircle(center, boardRadius);
        this.innerCircle = outerCircle.getScaledCircle(innerCirclePercentSize);
        this.splitCircle = outerCircle.getScaledCircle(splitCirclePercentSize);
        this.playerPathCircle = outerCircle.getScaledCircle(playerPathPercentSize);
    }

    public void draw(Graphics g) {
        drawBoard(g);
    }

    public void drawBoard(Graphics g) {
        Point center = outerCircle.Center;

        // Draw the base color of every field
        outerCircle.draw(g, fieldBaseColor, true);
        // Draw the board color
        innerCircle.draw(g, boardColor, true);

        Point[] innerPoints = innerCircle.getPoints(fieldCount);
        Point[] outerPoints = outerCircle.getPoints(fieldCount);

        for (int i = 0; i < fieldCount; ++i) {
            Point start = innerPoints[i];
            Point end = outerPoints[i];

            GUIField field = fields[i];

            Point[][] pointsOfField = getPointsOfField(center, i);
            if (field.HasSplit) {
                Polygon bottomPolygon = makePolygonFromOrderedPoints(pointsOfField[0], pointsOfField[2]);
                Polygon topPolygon = makePolygonFromOrderedPoints(pointsOfField[1], pointsOfField[2]);

                paintPolygon(g, bottomPolygon, field.PrimaryColor);
            }
            else {
                Polygon fieldPolygon = makePolygonFromOrderedPoints(pointsOfField[0], pointsOfField[1]);

                paintPolygon(g, fieldPolygon, field.PrimaryColor);
            }
            g.setColor(Color.BLACK);

            g.drawLine(start.x, start.y, end.x, end.y);
            g.setColor(Color.BLACK);
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

    protected Polygon makePolygonFromOrderedPoints(Point[] points1, Point[] points2) {
        Polygon polygon = new Polygon();

        for (Point p : points1) {
            polygon.addPoint(p.x, p.y);
        }
        for (int i = 0; i < points2.length; ++i) {
            Point p = points2[points2.length - i - 1];
            polygon.addPoint(p.x, p.y);
        }

        return polygon;
    }

    protected Point[][] getPointsOfField(Point boardCenter, int fieldIndex) {
        Point[][] points = new Point[3][];
        int[] radiuses = {outerCircle.Radius, innerCircle.Radius};

        // Generate the straight line, at the start
        points[2] = new Point[2];
        points[2][0] = splitCircle.getSinglePoint(fieldIndex, fieldCount);
        points[2][1] = splitCircle.getSinglePoint(fieldIndex + 1, fieldCount);

        int pointsForArc = 10;
        for (int i = 0; i < 2; ++i) {
            points[i] = new Point[pointsForArc];

            int radius = radiuses[i];
            int multiplier = pointsForArc - 1;
            for (int j = 0; j < pointsForArc; ++j) {
                points[i][j] = GUICircle.getPointOnCircle(boardCenter, radius, fieldCount * multiplier, fieldIndex * multiplier + j);
            }
        }

        return points;
    }
}
