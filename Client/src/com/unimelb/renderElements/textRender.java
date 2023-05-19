/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb.renderElements;

import com.unimelb.IRenderable;

import java.awt.*;
import java.util.ArrayList;

public class textRender implements IRenderable {
    private ArrayList<Point> points;
    private Color color;
    private int strokeWidth;
    private renderTypes type;
    private String textString;

    public textRender(ArrayList<Point> points, Color color, int strokeWidth, renderTypes type) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.type = type;
        this.textString = new String();
    }

    public textRender(ArrayList<Point> points, Color color, int strokeWidth, renderTypes type, String text) {
        this.points = points;
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.type = type;
        this.textString = text;
    }

    public textRender(textRender text) {
        this.points = text.points;
        this.color = text.color;
        this.strokeWidth = text.strokeWidth;
        this.type = text.type;
        this.textString = text.textString;
    }

    @Override
    public ArrayList<Point> getPoints() {
        return points;
    }

    @Override
    public void updateDrawing(Point newPoint) {
        if (points.isEmpty()) {
            points.add(newPoint);
        } else {
            points.remove(0);
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
    public void addCharacter(Character c) {
        textString += c;
    }

    @Override
    public void removeCharacter() {
        if (textString.length() > 0) {
            textString = textString.substring(0, textString.length() - 1);
        }
    }

    @Override
    public void renderSelf(Graphics2D g2d) {
        g2d.setColor(this.getColor());

        String renderString;
        if (textString.length() == 0) {
            renderString = "|";
        } else {
            renderString = textString;
        }

        g2d.drawString(renderString, points.get(0).x, points.get(0).y);
    }

    public String getTextString() {
        return textString;
    }
}
