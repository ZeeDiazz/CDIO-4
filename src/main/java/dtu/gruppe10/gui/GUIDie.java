package dtu.gruppe10.gui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUIDie {
    private static final int pixelSize = 64;
    private static BufferedImage[] sprites;

    static {
        BufferedImage spriteSheet;
        try {
            spriteSheet = ImageIO.read(new File("src/main/resources/dice-spritesheet.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        sprites = new BufferedImage[6];
        for (int i = 0; i < 6; ++i) {
            sprites[i] = spriteSheet.getSubimage(pixelSize * i, 0, pixelSize, pixelSize);
        }
    }

    protected int currentFace;
    protected int currentSize = pixelSize;

    public GUIDie() {
        currentFace = -1;
    }

    public void setFace(int face) {
        currentFace = face - 1;
    }

    public void setSize(int size) {
        this.currentSize = size;
    }

    public Image getCurrentFace() {
        if (currentFace < 0 || currentFace >= sprites.length || currentSize == 0) {
            return null;
        }
        return sprites[currentFace].getScaledInstance(currentSize, currentSize, Image.SCALE_FAST);
    }
}
