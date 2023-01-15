package dtu.gruppe10.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class GUIDie {
    private static final int pixelHeight = 64;
    private static final int pixelWidth = 64;
    private static Image[] sprites;

    static {
        BufferedImage spriteSheet;
        try {
            spriteSheet = ImageIO.read(new File("src/main/resources/dice-spritesheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sprites = new Image[6];
        for (int i = 0; i < 6; ++i) {
            sprites[i] = spriteSheet.getSubimage(pixelWidth * i, 0, pixelWidth, pixelHeight);
        }
    }
}
