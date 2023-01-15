package dtu.gruppe10.gui;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class GUIBoard {
    private static final float playerPathPercentSize = 0.95f;
    private static final float splitCirclePercentSize = 0.9f;
    private static final float prisonCirclePercentSize = 0.8f;
    private static final float innerCirclePercentSize = 0.7f;
    private static final float ownedCirclePercentSize = 0.65f;
    private static final float diceCirclePercentSize = 0.4f;
    private static final float playerPercentSize = 0.02f;

    protected static Color boardColor = new Color(114, 24, 24);
    protected static Color fieldBaseColor = new Color(108, 159, 159);

    protected Random rng;

    protected int boardRadius;
    protected GUICircle outerCircle;
    protected GUICircle splitCircle;
    protected GUICircle innerCircle;
    protected GUICircle ownedCircle;
    protected GUICircle dieDrawCircle;
    protected GUICircle playerPathCircle;
    protected int playerRadius;
    protected int fieldCount;
    protected GUIField[] fields;
    protected Color[] ownerColor;
    protected int[] houseCount;
    protected int prisonIndex;
    protected Point prisonPoint;
    protected GUIDie[] dice;
    protected Point[] diceDrawPoints;

    public GUIBoard(GUIField[] fields, int prisonIndex) {
        rng = new Random();
        // Save variables
        this.fields = fields;
        this.fieldCount = fields.length;
        this.ownerColor = new Color[this.fieldCount];
        this.houseCount = new int[this.fieldCount];
        this.prisonIndex = prisonIndex;

        this.dice = new GUIDie[] {new GUIDie(), new GUIDie()};
        this.diceDrawPoints = new Point[2];

        displayFieldData();
    }

    public void changePositionAndSize(Point center, int diameter) {
        boardRadius = diameter / 2;

        this.outerCircle = new GUICircle(center, boardRadius);
        this.innerCircle = outerCircle.getScaledCircle(innerCirclePercentSize);
        this.splitCircle = outerCircle.getScaledCircle(splitCirclePercentSize);
        this.ownedCircle = outerCircle.getScaledCircle(ownedCirclePercentSize);
        this.playerPathCircle = outerCircle.getScaledCircle(playerPathPercentSize);
        this.dieDrawCircle = outerCircle.getScaledCircle(diceCirclePercentSize);

        for (GUIDie die : dice) {
            die.setSize(dieDrawCircle.Diameter / 5);
        }

        this.playerRadius = (int)(boardRadius * playerPercentSize);

        GUICircle prisonCircle = outerCircle.getScaledCircle(prisonCirclePercentSize);
        this.prisonPoint = prisonCircle.getSinglePoint(prisonIndex * 2 + 1, 80);
    }

    public int getRadius() {
        return boardRadius;
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
            paintPolygon(g, coloredPolygon, fieldColor);

            g.setColor(Color.BLACK);
            //display field info
            g.drawString(field.fieldName, outerPoints[i].x, outerPoints[i].y);
            // Draw the price of the field
            g.drawString(Integer.toString(field.fieldPrice), outerPoints[i].x, outerPoints[i].y + 10);


            // Draw the lines separating the fields
            Point start = innerPoints[i * 2];
            Point end = outerPoints[i];
            g.drawLine(start.x, start.y, end.x, end.y);
        }

        outerCircle.draw(g, false);
        innerCircle.draw(g, false);

        for (int i = 0; i < dice.length; ++i) {
            GUIDie die = dice[i];
            Point info = diceDrawPoints[i];

            Image currentFace = die.getCurrentFace();
            if (currentFace == null || info == null) {
                continue;
            }

            Point drawPoint = dieDrawCircle.getScaledCircle((float)info.x / 100).getSinglePoint(info.y, 360);
            g.drawImage(currentFace, drawPoint.x, drawPoint.y, null);
        }
    }

    public void drawPlayerInPrison(Graphics g, GUIPlayer player) {
        drawPlayer(g, player, prisonPoint);
    }

    public void drawPlayer(Graphics g, GUIPlayer player, int positionIndex) {
        Point playerPoint = playerPathCircle.getSinglePoint(positionIndex * 2 + 1, fieldCount * 2);

        drawPlayer(g, player, playerPoint);
    }

    public void drawPlayer(Graphics g, GUIPlayer player, float position) {
        int maxDiscreteSteps = 1000;
        int drawIndex = (int)(position * maxDiscreteSteps + maxDiscreteSteps / 2);

        Point playerPoint = playerPathCircle.getSinglePoint(drawIndex, fieldCount * maxDiscreteSteps);

        drawPlayer(g, player, playerPoint);
    }

    protected void drawPlayer(Graphics g, GUIPlayer player, Point playerPosition) {
        int outlineRadius = playerRadius + (playerRadius / 10) + 1;

        GUICircle playerOutline = new GUICircle(playerPosition, outlineRadius);
        playerOutline.draw(g, Color.BLACK, true);

        GUICircle playerCircle = new GUICircle(playerPosition, playerRadius);
        playerCircle.draw(g, player.TokenColor, true);
    }

    public void diceThrown(int... values) {
        int index;

        for (index = 0; index < values.length; ++index) {
            GUIDie die = dice[index];

            die.setFace(values[index]);

            Point dimensions = new Point(1, 0);
            boolean searchingForPoint = true;
            while (searchingForPoint) {
                int randomRadius = rng.nextInt(80) + 20;
                float radiusPercent = (float)randomRadius / 100;
                int randomAngle = rng.nextInt(359);
                Point drawPoint = dieDrawCircle.getScaledCircle(radiusPercent).getSinglePoint(randomAngle, 360);

                searchingForPoint = false;
                for (int j = 0; j < index; ++j) {
                    if (drawPoint.distance(diceDrawPoints[j]) < (float)dieDrawCircle.Radius / 3) {
                        searchingForPoint = true;
                    }
                }

                if (!searchingForPoint) {
                    dimensions = new Point(randomRadius, randomAngle);
                }
            }

            diceDrawPoints[index] = dimensions;
        }

        // Set all the non-thrown dice to not be shown
        for ( ; index < dice.length; ++index) {
            dice[index].setFace(-1);
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

    private void displayFieldData(){
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/fields.CSV"));
            String textLine;
            //int counter = 0;
            while ((textLine = reader.readLine()) != null){
                String[] texts = textLine.split(",");
                String name = texts[0]; //Because it is the first one in fields.CSV
                int price;
                /*if(texts.length < 3) {
                    price = Integer.valueOf(texts[3]); // it is number four in fields.CSV
                }*/

                for (GUIField field : fields) {
                    if (field.ID == Integer.valueOf(texts[1])) {
                        field.setFieldName(name);
                        if(texts.length > 3) {
                            price = Integer.valueOf(texts[3]);
                            field.setFieldPrice(price);
                        }
                        break;
                    }
                }
                //counter++;
            }
            reader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
