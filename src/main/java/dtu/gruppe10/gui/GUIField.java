package dtu.gruppe10.gui;

import java.awt.*;

public class GUIField {
    protected final static int pointsPerArc = 5;

    public final Color PrimaryColor;
    public final boolean IsSplit;
    public final Image Image;

    public GUIField(Color primaryColor, boolean isSplit, Image image) {
        this.PrimaryColor = primaryColor;
        this.IsSplit = isSplit;
        this.Image = image;
    }

    public Polygon getPolygonToPaint(GUICircle outerCircle, GUICircle otherCircle, int fieldIndex, int totalFieldCount) {
        Polygon paintPolygon = new Polygon();
        int multiplier = pointsPerArc - 1;

        // Do the outer arc
        for (int i = 0; i < pointsPerArc; ++i) {
            Point p = outerCircle.getSinglePoint(fieldIndex * multiplier + i, totalFieldCount * multiplier);
            paintPolygon.addPoint(p.x, p.y);
        }

        for (int i = (pointsPerArc - 1); i >= 0; --i) {
            Point p = otherCircle.getSinglePoint(fieldIndex * multiplier + i, totalFieldCount * multiplier);
            paintPolygon.addPoint(p.x, p.y);
        }

        return paintPolygon;
    }
}
