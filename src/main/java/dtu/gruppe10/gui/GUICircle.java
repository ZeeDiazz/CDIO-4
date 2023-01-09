package dtu.gruppe10.gui;

import java.awt.*;

public class GUICircle {
    public final Point Center;
    public final int Diameter;
    public final int Radius;

    public GUICircle(Point center, int radius) {
        this.Center = center;
        this.Diameter = radius * 2;
        this.Radius = radius;
    }

    public void draw(Graphics g, Color color, boolean filled) {
        // Shift the draw point, so the circle actually has correct center
        int radius = this.Diameter / 2;
        Point drawPoint = new Point(this.Center.x - radius, this.Center.y - radius);

        g.setColor(color);
        if (filled) {
            g.fillOval(drawPoint.x, drawPoint.y, this.Diameter, this.Diameter);
        }
        else {
            g.drawOval(drawPoint.x, drawPoint.y, this.Diameter, this.Diameter);
        }
    }

    public void draw(Graphics g, boolean filled) {
        draw(g, Color.BLACK, filled);
    }

    public Point[] getAllPoints(int pointCount) {
        return GUICircle.getPointsOnCircle(this.Center, this.Radius, pointCount);
    }

    public Point getSinglePoint(int pointIndex, int totalPointCount) {
        return getPointOnCircle(this.Center, this.Radius, totalPointCount, pointIndex);
    }

    public GUICircle getScaledCircle(float scale) {
        return new GUICircle(this.Center, (int)(this.Radius * scale));
    }

    public static Point[] getPointsOnCircle(Point center, int radius, int pointCount) {
        Point[] points = new Point[pointCount];
        double pointDegree = Math.toRadians(360) / pointCount;

        for (int i = 0; i < pointCount; ++i) {
            double currentDegree = pointDegree * i;
            int x = -(int)(radius * Math.sin(currentDegree)) + center.x;
            int y = (int)(radius * Math.cos(currentDegree)) + center.y;

            points[i] = new Point(x, y);
        }

        return points;
    }
    public static Point getPointOnCircle(Point center, int radius, int totalPointCount, int pointIndex) {
        double pointDegree = Math.toRadians(360) / totalPointCount * pointIndex;
        int x = -(int)(radius * Math.sin(pointDegree)) + center.x;
        int y = (int)(radius * Math.cos(pointDegree)) + center.y;
        return new Point(x, y);
    }
}
