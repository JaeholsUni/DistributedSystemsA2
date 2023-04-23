package com.unimelb;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public interface IRenderable extends Serializable {

    public ArrayList<Point> getPoints();

    public Color getColor();

    public int getStrokeWidth();

    public renderTypes getType();
}