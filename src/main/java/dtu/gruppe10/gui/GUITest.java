package dtu.gruppe10.gui;

import dtu.gruppe10.gui.prompts.GUIAnswer;

import java.awt.*;
import java.util.Random;

public class GUITest {
    public static void main(String[] args) {
        GUIPlayer[] players = new GUIPlayer[4];
        players[0] = new GUIPlayer(1, "Zaid", Color.green);
        players[1] = new GUIPlayer(2, "Zach", Color.blue);
        players[2] = new GUIPlayer(3, "Felix", Color.magenta);
        players[3] = new GUIPlayer(4, "Zahedullah", Color.red);

        GUIWindow window = new GUIWindow(new Rectangle(100, 100, 300, 300), players, generateFields());
        window.setNewPlayerPosition(1, 2);
        window.setNewPlayerPosition(2, 4);

        Thread uiThread = new Thread(window, "uiThread");
        uiThread.start();

        window.setState(GUIState.START_GAME);
        GUIAnswer<Integer> playerCountAnswer = window.getUserInt(2, 6);

        while (!playerCountAnswer.hasAnswer()) {
            System.out.println("No answer yet");
        }

        System.out.println("There are " + playerCountAnswer.getAnswer() + " players playing");
        window.setState(GUIState.PLAYING);

        Random rng = new Random();
        int[] positions = new int[4];
        while (true) {
            window.setNewPlayerPosition(1, positions[0]);
            positions[0] = (positions[0] + rng.nextInt(11) + 2) % 40;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                continue;
            }
            window.repaint();

            window.setNewPlayerPosition(2, positions[1]);
            positions[1] = (positions[1] + rng.nextInt(11) + 2) % 40;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                continue;
            }
            window.repaint();

            window.setNewPlayerPosition(3, positions[2]);
            positions[2] = (positions[2] + rng.nextInt(11) + 2) % 40;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                continue;
            }
            window.repaint();

            window.setNewPlayerPosition(4, positions[3]);
            positions[3] = (positions[3] + rng.nextInt(11) + 2) % 40;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                continue;
            }
            window.repaint();
        }
    }

    // TODO make it a method reading from a file, elsewhere
    protected static GUIField[] generateFields() {
        GUIField[] fields = new GUIField[40];

        // Start, prison, free parking, go to jail
        fields[0] = new GUIField(0, Color.RED, false, null);
        fields[10] = new GUIField(10, Color.BLACK, true, null);
        fields[20] = new GUIField(20, null, false, null);
        fields[30] = new GUIField(30, Color.BLACK, true, null);

        // Tax fields
        fields[4] = new GUIField(4, null, false, null);
        fields[38] = new GUIField(38, null, false, null);

        // Draw card fields
        for (int luckyIndex : new int[]{2, 7, 17, 22, 33, 36}) {
            fields[luckyIndex] = new GUIField(luckyIndex, Color.BLACK, false, null);
        }

        // Ships
        Color shipColor = new Color(220, 34, 224);
        for (int shipIndex : new int[]{5, 15, 25, 35}) {
            fields[shipIndex] = new GUIField(shipIndex, shipColor, true, null);
        }

        // Drinks
        Color drinkColor = new Color(14, 182, 170);
        fields[12] = new GUIField(12, drinkColor, true, null);
        fields[28] = new GUIField(28, drinkColor, true, null);

        // Properties
        int[][] propertyIndexes = {{1, 3}, {6, 8, 9}, {11, 13, 14}, {16, 18, 19}, {21, 23, 24}, {26, 27, 29}, {31, 32, 34}, {37, 39}};
        Color[] propertyColors = {new Color(75, 109, 255), new Color(241, 94, 16), new Color(28, 157, 5), Color.GRAY, new Color(196, 34, 34), Color.WHITE, new Color(185, 133, 4), new Color(41, 9, 182)};
        for (int i = 0; i < propertyIndexes.length; ++i) {
            Color propertyColor = propertyColors[i];
            for (int j = 0; j < propertyIndexes[i].length; ++j) {
                int propertyIndex = propertyIndexes[i][j];
                fields[propertyIndex] = new GUIField(propertyIndex, propertyColor, true, null);
            }
        }

        return fields;
    }
}
