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

        GUIBoard b = new GUIBoard(new Rectangle(100, 100, 300, 300), players);
        b.setNewPlayerPosition(1, 2);
        b.setNewPlayerPosition(2, 4);

        Thread uiThread = new Thread(b, "uiThread");
        uiThread.start();

        System.out.println( "Hello World!" );

        Random rng = new Random();
        int[] positions = new int[4];
        while (true) {
            b.setNewPlayerPosition(1, positions[0]);
            positions[0] = (positions[0] + rng.nextInt(11) + 2) % 40;
            for (int j = 0; j < Integer.MAX_VALUE; ++j) {
                continue;
            }

            b.setNewPlayerPosition(2, positions[1]);
            positions[1] = (positions[1] + rng.nextInt(11) + 2) % 40;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                continue;
            }

            b.setNewPlayerPosition(3, positions[2]);
            positions[2] = (positions[2] + rng.nextInt(11) + 2) % 40;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                continue;
            }

            b.setNewPlayerPosition(4, positions[3]);
            positions[3] = (positions[3] + rng.nextInt(11) + 2) % 40;
            for (int i = 0; i < Integer.MAX_VALUE; ++i) {
                continue;
            }
        }
    }
}
