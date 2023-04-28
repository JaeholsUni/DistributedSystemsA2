package com.unimelb;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class freehandLine implements IRenderable {

    private ArrayList<Point> points;
    private Color color;
    private int strokeWidth;
    private renderTypes type;

    public freehandLine(ArrayList<Point> points, Color color, int strokeWidth, renderTypes type) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.type = type;
    }

    public freehandLine(IRenderable line) {
        this.points = line.getPoints();
        this.color = line.getColor();
        this.strokeWidth = line.getStrokeWidth();
        this.type = line.getType();
    }

    @Override
    public ArrayList<Point> getPoints() {
        return points;
    }

    @Override
    public void updateDrawing(Point newPoint) {
        points.add(newPoint);
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public int getStrokeWidth() {
        return strokeWidth;
    }

    @Override
    public renderTypes getType() {
        return type;
    }

    @Override
    public void renderSelf(Graphics2D g2d) {


        g2d.setColor(this.getColor());
        g2d.setStroke(new BasicStroke(this.getStrokeWidth()));

        ArrayList<Point> pointSet = this.getPoints();

        for (int i = 0; i < pointSet.size() - 1; i++) {
            Point p1 = pointSet.get(i);
            Point p2 = pointSet.get(i + 1);
            g2d.draw(new Line2D.Double(p1, p2));
        }
    }
}
