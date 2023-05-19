/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import com.unimelb.renderElements.renderTypes;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

public interface IRenderable extends Serializable {

    ArrayList<Point> getPoints();

    void updateDrawing(Point newPoint);

    Color getColor();

    int getStrokeWidth();

    renderTypes getType();

    void renderSelf(Graphics2D g2d);

    void addCharacter(Character c);

    void removeCharacter();
}
