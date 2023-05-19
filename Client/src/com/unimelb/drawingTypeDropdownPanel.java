/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import javax.swing.*;

public class drawingTypeDropdownPanel extends JPanel {
    private JLabel typeLabel = new JLabel("Drawing Type");
    private String[] types = {"Freehand", "Line", "Circle", "Rectangle", "Square", "Ellipse", "Text"};
    private JComboBox<String> drawingTypeDropdown;

    public drawingTypeDropdownPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(typeLabel);
        drawingTypeDropdown = new JComboBox<>(types);
        add(drawingTypeDropdown);
        add(Box.createHorizontalStrut(10));
    }

    public String getSelectedType() {
        return (String) drawingTypeDropdown.getSelectedItem();
    }
}
