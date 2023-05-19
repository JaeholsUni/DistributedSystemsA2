/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import javax.swing.*;
import java.awt.*;

public class colourDropdownPanel extends JPanel {
    private JLabel colourLabel = new JLabel("Colours");
    private String[] colours = {"Black", "Red", "Green", "Blue", "Grey", "Yellow", "Orange", "Purple", "Magenta", "Cyan", "Navy", "Pink", "Brown", "Tan", "Lime", "Salmon"};
    private JComboBox<String> colourDropdown;

    public colourDropdownPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(colourLabel);
        colourDropdown = new JComboBox<>(colours);
        add(colourDropdown);
        add(Box.createHorizontalStrut(10));
    }

    public Color getColour() {
        if (colourDropdown.getSelectedItem() == null) {
            return Color.BLACK;
        }
        try {
            return Color.decode(colourTranslate((String) colourDropdown.getSelectedItem()));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Color.BLACK;
        }
    }

    public String colourTranslate(String name) {
        switch (name) {
            case "Red":
                return "#FF0000";
            case "Green":
                return "#008000";
            case "Blue":
                return "#0000FF";
            case "Grey":
                return "#808080";
            case "Yellow":
                return "#FFFF00";
            case "Orange":
                return "#ffa500";
            case "Purple":
                return "#800080";
            case "Magenta":
                return "#ff00ff";
            case "Cyan":
                return "#00ffff";
            case "Navy":
                return "#000080";
            case "Pink":
                return "#ffc0cb";
            case "Brown":
                return "#a0522d";
            case "Tan":
                return "#d2b48c";
            case "Lime":
                return "#00FF00";
            case "Salmon":
                return "#fa8072";
            default:
                return "#000000";
        }
    }
}
