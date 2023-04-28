package com.unimelb;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public interface IRenderable extends Serializable {

    public ArrayList<Point> getPoints();

    public void updateDrawing(Point newPoint);

    public Color getColor();

    public int getStrokeWidth();

    public renderTypes getType();

    public abstract void renderSelf(Graphics2D g2d);
}
