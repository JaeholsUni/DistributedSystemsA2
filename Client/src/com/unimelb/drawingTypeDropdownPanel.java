package com.unimelb;

import javax.swing.*;

public class drawingTypeDropdownPanel extends JPanel {
    private String[] types = {"Freehand", "Line", "Circle", "Rectangle", "Square", "Ellipse", "Text"};
    private JComboBox<String> drawingTypeDropdown;

    public drawingTypeDropdownPanel() {
        drawingTypeDropdown = new JComboBox<>(types);
        add(drawingTypeDropdown);
    }

    public String getSelectedType() {
        return (String) drawingTypeDropdown.getSelectedItem();
    }
}
