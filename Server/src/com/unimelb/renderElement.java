package com.unimelb;

import java.awt.*;
import java.util.ArrayList;

public class renderElement implements IRenderable {

    private ArrayList<Point> points;
    private Color color;
    private int strokeWidth;
    private renderTypes type;

    public renderElement(ArrayList<Point> points, Color color, int strokeWidth, renderTypes type) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.type = type;
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public Color getColor() {
        return color;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public renderTypes getType() {
        return type;
    }
}
