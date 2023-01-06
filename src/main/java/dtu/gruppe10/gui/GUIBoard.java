package dtu.gruppe10.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class GUIBoard extends JFrame implements Runnable {
    private static final float outerCirclePercentSize = 1f;
    private static final float innerCirclePercentSize = 0.7f;
    private static final float splitCirclePercentSize = 0.9f;
    private static final float playerPathPercentSize = 0.95f;

    protected static Color boardColor = new Color(114, 24, 24);
    protected static Color fieldBaseColor = new Color(108, 159, 159);

    protected GUICircle currentBoardCircle;
    protected GUICircle currentInnerCircle;
    protected GUICircle currentSplitCircle;
    protected GUICircle currentPlayerPathCircle;
    protected int fieldCount;
    protected GUIField[] fields;
    protected ArrayList<Integer> playerIds;
    protected HashMap<Integer, Color> idsToColors;
    protected HashMap<Integer, Integer> idsToPositions;

    public GUIBoard(Rectangle bounds, GUIPlayer[] players) {
        super("Matador");

        // Save variables
        this.playerIds = new ArrayList<>();
        this.idsToColors = new HashMap<>();
        this.idsToPositions = new HashMap<>();

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

        for (GUIPlayer player : players) {
            playerIds.add(player.ID);
            idsToColors.put(player.ID, player.TokenColor);
            idsToPositions.put(player.ID, 0);
        }

        // Set the size of the window
        setBounds(bounds);

        // Set default values to various things
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setType(Type.NORMAL);
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        paint(getGraphics()); // Make sure it's painted
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        drawBoard(g, getCenterOfWindow(), getMaxBoardSize());

        for (int playerId : playerIds) {
            drawPlayer(g, playerId);
        }
    }

    public void setNewPlayerPosition(int playerId, int newPos) {
        idsToPositions.put(playerId, newPos);

        Graphics g = getGraphics();
        if (g != null) {
            paint(g);
        }
    }

    public void drawBoard(Graphics g, Point center, int diameter) {
        int currentBoardRadius = (int)(diameter * outerCirclePercentSize) / 2;
        int innerCircleRadius = (int)(currentBoardRadius * innerCirclePercentSize);
        int splitCircleRadius = (int)(currentBoardRadius * splitCirclePercentSize);
        int pathCircleRadius = (int)(currentBoardRadius * playerPathPercentSize);

        currentBoardCircle = new GUICircle(center, currentBoardRadius);
        currentInnerCircle = new GUICircle(center, innerCircleRadius);
        currentSplitCircle = new GUICircle(center, splitCircleRadius);
        currentPlayerPathCircle = new GUICircle(center, pathCircleRadius);

        currentBoardCircle.draw(g, boardColor, true);

        Point[] innerPoints = currentInnerCircle.getPoints(fieldCount);
        Point[] outerPoints = currentBoardCircle.getPoints(fieldCount);

        for (int i = 0; i < fieldCount; ++i) {
            Point start = innerPoints[i];
            Point end = outerPoints[i];

            GUIField field = fields[i];

            Point[][] pointsOfField = getPointsOfField(center, i);
            if (field.HasSplit) {
                Polygon bottomPolygon = makePolygonFromOrderedPoints(pointsOfField[0], pointsOfField[2]);
                Polygon topPolygon = makePolygonFromOrderedPoints(pointsOfField[1], pointsOfField[2]);

                paintPolygon(g, topPolygon, field.SecondaryColor);
                paintPolygon(g, bottomPolygon, field.PrimaryColor);
            }
            else {
                Polygon fieldPolygon = makePolygonFromOrderedPoints(pointsOfField[0], pointsOfField[1]);

                paintPolygon(g, fieldPolygon, field.PrimaryColor);
            }
            g.setColor(Color.BLACK);

            g.drawLine(start.x, start.y, end.x, end.y);

            Point splitMid = currentSplitCircle.getSinglePoint(i * 2 + 1, fieldCount * 2);

            g.setColor(field.PrimaryTextColor);
            g.drawString(field.PrimaryText, splitMid.x, splitMid.y);
            g.setColor(Color.BLACK);
        }

        currentBoardCircle.draw(g, false);
        currentInnerCircle.draw(g, false);
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

    protected void drawPlayer(Graphics g, int playerId) {
        int playerPosition = idsToPositions.get(playerId);
        Color playerColor = idsToColors.get(playerId);

        Point playerPoint = currentPlayerPathCircle.getSinglePoint(playerPosition * 2 + 1, fieldCount * 2);

        GUICircle playerCircle = new GUICircle(playerPoint, 5);
        playerCircle.draw(g, playerColor, true);
    }

    protected Point getPointOnCircle(Point center, int radius, int totalPointCount, int pointIndex) {
        double pointDegree = Math.toRadians(360) / totalPointCount * pointIndex;
        int x = -(int)(radius * Math.sin(pointDegree)) + center.x;
        int y = (int)(radius * Math.cos(pointDegree)) + center.y;
        return new Point(x, y);
    }

    protected Point[][] getPointsOfField(Point boardCenter, int fieldIndex) {
        Point[][] points = new Point[3][];
        int[] radiuses = {currentBoardCircle.Radius, currentInnerCircle.Radius};

        // Generate the straight line, at the start
        points[2] = new Point[2];
        points[2][0] = currentSplitCircle.getSinglePoint(fieldIndex, fieldCount);
        points[2][1] = currentSplitCircle.getSinglePoint(fieldIndex + 1, fieldCount);

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
