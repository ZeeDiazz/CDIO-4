package dtu.gruppe10.gui;

import java.awt.*;
import java.util.Random;

public class GUITest {
    public static void main(String[] args) {
        GUIPlayer[] players = new GUIPlayer[4];
        players[0] = new GUIPlayer(1, Color.green);
        players[1] = new GUIPlayer(2, Color.blue);
        players[2] = new GUIPlayer(3, Color.magenta);
        players[3] = new GUIPlayer(4, Color.red);

        GUIWindow window = new GUIWindow(new Rectangle(100, 100, 300, 300), players);
        window.setNewPlayerPosition(1, 2);
        window.setNewPlayerPosition(2, 4);

        Thread uiThread = new Thread(window, "uiThread");
        uiThread.start();

        System.out.println( "Hello World!" );

        Random rng = new Random();
        int[] positions = new int[4];
        while (true) {
            window.setNewPlayerPosition(1, positions[0]);
            positions[0] = (positions[0] + rng.nextInt(11) + 2) % 40;
            for (int j = 0; j < Integer.MAX_VALUE; ++j) {
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
}
