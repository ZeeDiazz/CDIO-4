package dtu.gruppe10.gui;

import javax.swing.*;
import java.awt.*;

public class GUIField extends JPanel {
    public final Color PrimaryColor;
    public final Color SecondaryColor;
    public final String PrimaryText;
    public final Color PrimaryTextColor;
    public final String SecondaryText;
    public final Color SecondaryTextColor;
    public final Image Image;
    public final boolean HasSplit;

    public GUIField(Color primaryColor, Color secondaryColor, String primaryText, Color primaryTextColor, String secondaryText, Color secondaryTextColor, Image image) {
        this.PrimaryColor = primaryColor;
        this.SecondaryColor = secondaryColor;

        this.HasSplit = (primaryColor != secondaryColor);

        this.PrimaryText = primaryText;
        this.PrimaryTextColor = primaryTextColor;
        this.SecondaryText = secondaryText;
        this.SecondaryTextColor = secondaryTextColor;

        this.Image = image;
    }

    public GUIField(Color primaryColor, Color secondaryColor, String primaryText, Color primaryTextColor, String secondaryText, Color secondaryTextColor) {
        this(primaryColor, secondaryColor, primaryText, primaryTextColor, secondaryText, secondaryTextColor, null);
    }

    public GUIField(Color color, String primaryText, String secondaryText, Color textColor, Image image) {
        this(color, color, primaryText, textColor, secondaryText, textColor, image);
    }

    public GUIField(Color color, String primaryText, String secondaryText, Color textColor) {
        this(color, color, primaryText, textColor, secondaryText, textColor, null);
    }
}
