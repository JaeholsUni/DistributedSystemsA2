package com.unimelb;

import java.awt.*;
import java.util.ArrayList;

public class rectangle implements IRenderable{
    private ArrayList<Point> points;
    private Color color;
    private int strokeWidth;
    private renderTypes type;

    public rectangle(ArrayList<Point> points, Color color, int strokeWidth, renderTypes type) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.type = type;
    }

    @Override
    public ArrayList<Point> getPoints() {
        return points;
    }

    @Override
    public void updateDrawing(Point newPoint) {
        points.remove(1);
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

    }
}
