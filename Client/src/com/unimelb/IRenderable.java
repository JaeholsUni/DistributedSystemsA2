package com.unimelb;

import com.unimelb.renderElements.renderTypes;

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

    public void addCharacter(Character c);
    public void removeCharacter();
}
