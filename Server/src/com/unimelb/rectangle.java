package com.unimelb;

import java.awt.*;
import java.awt.geom.Rectangle2D;
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

    public rectangle(IRenderable renderable) {
        this.points = renderable.getPoints();
        this.color = renderable.getColor();
        this.strokeWidth = renderable.getStrokeWidth();
        this.type = renderable.getType();
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
        Double width = Math.abs(points.get(0).getX() - points.get(1).getX());
        Double height = Math.abs(points.get(0).getY() - points.get(1).getY());

        g2d.draw(new Rectangle2D.Double(points.get(0).getX(), points.get(0).getY(), width, height));
    }
}
