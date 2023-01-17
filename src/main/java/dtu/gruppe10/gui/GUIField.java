package dtu.gruppe10.gui;

import java.awt.*;

public class GUIField {
    protected final static int pointsPerArc = 5;

    public final int ID;
    public final Color PrimaryColor;
    public final boolean IsSplit;
    public final Image Image;

    public String fieldName;
    public int fieldPrice;
    public int fieldRent;

    public GUIField(int id, Color primaryColor, boolean isSplit, Image image) {
        this.ID = id;
        this.PrimaryColor = primaryColor;
        this.IsSplit = isSplit;
        this.Image = image;
    }

    public Polygon getFullPolygon(GUICircle outerCircle, GUICircle innerCircle, int fieldIndex, int totalFieldCount) {
        return makeFieldPolygon(outerCircle, innerCircle, fieldIndex, totalFieldCount);
    }

    public Polygon getBottomPolygon(GUICircle outerCircle, GUICircle splitCircle, int fieldIndex, int totalFieldCount) {
        return makeFieldPolygon(outerCircle, splitCircle, fieldIndex, totalFieldCount);
    }

    public Polygon getTopPolygon(GUICircle splitCircle, GUICircle innerCircle, int fieldIndex, int totalFieldCount) {
        return makeFieldPolygon(splitCircle, innerCircle, fieldIndex, totalFieldCount);
    }

    protected Polygon makeFieldPolygon(GUICircle outerCircle, GUICircle innerCircle, int fieldIndex, int totalFieldCount) {
        Polygon polygon = new Polygon();
        int multiplier = pointsPerArc - 1;

        // Do the outer arc
        for (int i = 0; i < pointsPerArc; ++i) {
            Point p = outerCircle.getSinglePoint(fieldIndex * multiplier + i, totalFieldCount * multiplier);
            polygon.addPoint(p.x, p.y);
        }

        for (int i = (pointsPerArc - 1); i >= 0; --i) {
            Point p = innerCircle.getSinglePoint(fieldIndex * multiplier + i, totalFieldCount * multiplier);
            polygon.addPoint(p.x, p.y);
        }

        return polygon;
    }
    public void setFieldName(String fieldName){
        this.fieldName = fieldName;
    }
    public void setFieldPrice(int fieldPrice){
        this.fieldPrice = fieldPrice;
    }

    public void setFieldRent(int fieldRent) {this.fieldRent = fieldRent;}
}
