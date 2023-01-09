package dtu.gruppe10.gui;

import java.awt.*;

public class GUIPlayer {
    public final int ID;
    public final String Name;
    public final Color TokenColor;

    public GUIPlayer(int id, String name, Color tokenColor) {
        this.ID = id;
        this.Name = name;
        this.TokenColor = tokenColor;
    }
}
