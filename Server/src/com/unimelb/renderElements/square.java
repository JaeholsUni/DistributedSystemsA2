package com.unimelb.renderElements;

import com.unimelb.IRenderable;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class square implements IRenderable {
    private ArrayList<Point> points;
    private Color color;
    private int strokeWidth;
    private renderTypes type;

    public square(ArrayList<Point> points, Color color, int strokeWidth, renderTypes type) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.type = type;
    }

    public square(IRenderable renderable) {
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
        g2d.setColor(this.getColor());
        g2d.setStroke(new BasicStroke(this.getStrokeWidth()));

        Double width = Math.abs(points.get(0).getX() - points.get(1).getX());
        Double height = Math.abs(points.get(0).getY() - points.get(1).getY());
        Double edgeSize = Math.max(width, height);

        Point topLeft = findTopLeft(points.get(0).x, points.get(0).y, points.get(1).x, points.get(1).y);

        g2d.draw(new Rectangle2D.Double(topLeft.getX(), topLeft.getY(), edgeSize, edgeSize));
    }

    @Override
    public void addCharacter(Character c) {

    }

    @Override
    public void removeCharacter() {

    }

    private Point findTopLeft(int point0x, int point0y, int point1x, int point1y) {
        if ((point0x < point1x) && (point0y < point1y)) {
            return points.get(0);
        } else if((point0x > point1x) && (point0y > point1y)) {
            return points.get(1);
        } else if((point0x < point1x) && (point0y > point1y)) {
            return new Point(point0x, point1y);
        } else {
            return new Point(point1x, point0y);
        }
    }
}
