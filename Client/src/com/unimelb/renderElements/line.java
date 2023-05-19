/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb.renderElements;

import com.unimelb.IRenderable;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class line implements IRenderable {

    private ArrayList<Point> points;
    private Color color;
    private int strokeWidth;
    private renderTypes type;

    public line(ArrayList<Point> points, Color color, int strokeWidth, renderTypes type) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.type = type;
    }

    public line(IRenderable line) {
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
        if (points.isEmpty()){
            points.add(newPoint);
            points.add(newPoint);
        } else {
            points.remove(1);
            points.add(newPoint);
        }
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


        Point p1 = points.get(0);
        Point p2 = points.get(1);
        g2d.draw(new Line2D.Double(p1, p2));
    }

    @Override
    public void addCharacter(Character c) {

    }

    @Override
    public void removeCharacter() {

    }
}
