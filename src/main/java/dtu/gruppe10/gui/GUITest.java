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

        GUIWindow window = new GUIWindow(new Rectangle(100, 100, 300, 300), generateFields());
        window.addPlayers(players);

        window.setNewPlayerPosition(1, 2);
        window.setNewPlayerPosition(2, 4);

        Thread uiThread = new Thread(window, "uiThread");
        uiThread.start();

        window.setState(GUIState.START_GAME);
        GUIAnswer<Integer> playerCountAnswer = window.getUserInt("",2, 6);

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
    public static GUIField[] generateFields() {
        GUIField[] fields = new GUIField[40];

        // Start, prison, free parking, go to jail
        fields[0] = new GUIField(0, new Color(218, 0, 0), false, null);
        fields[10] = new GUIField(1, Color.BLACK, true, null);
        fields[20] = new GUIField(9, null, false, null);
        fields[30] = new GUIField(2, Color.BLACK, true, null);

        // Tax fields
        fields[4] = new GUIField(6, null, false, null);
        fields[38] = new GUIField(7, null, false, null);

        // Draw card fields
        for (int luckyIndex : new int[]{2, 7, 17, 22, 33, 36}) {
            fields[luckyIndex] = new GUIField(5, Color.BLACK, false, null);
        }

        // Ships
        Color shipColor = new Color(229, 11, 229);
        int[] shipIndexes = new int[]{5, 15, 25, 35};
        for (int i = 0; i < shipIndexes.length; ++i) {
            fields[shipIndexes[i]] = new GUIField(91 + i, shipColor, true, null);
        }

        // Drinks
        Color drinkColor = new Color(17, 225, 195);
        fields[12] = new GUIField(101, drinkColor, true, null);
        fields[28] = new GUIField(102, drinkColor, true, null);

        // Properties
        int[][] propertyIndexes = {{1, 3}, {6, 8, 9}, {11, 13, 14}, {16, 18, 19}, {21, 23, 24}, {26, 27, 29}, {31, 32, 34}, {37, 39}};
        Color[] propertyColors = {new Color(34, 70, 224), new Color(241, 94, 16), new Color(40, 200, 40), Color.GRAY, new Color(255, 41, 41), Color.WHITE, new Color(252, 218, 21), new Color(76, 2, 255)};
        for (int i = 0; i < propertyIndexes.length; ++i) {
            Color propertyColor = propertyColors[i];
            for (int j = 0; j < propertyIndexes[i].length; ++j) {
                int propertyIndex = propertyIndexes[i][j];
                fields[propertyIndex] = new GUIField(10*(i+1)+(j+1), propertyColor, true, null);
            }
        }

        return fields;
    }
}
