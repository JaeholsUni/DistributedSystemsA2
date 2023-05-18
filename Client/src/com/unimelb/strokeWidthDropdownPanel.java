package com.unimelb;

import javax.swing.*;

public class strokeWidthDropdownPanel extends JPanel {

    private JLabel strokeLabel = new JLabel("Stroke Width");
    private Integer[] widths = {1, 2, 3, 4, 5, 7, 10, 13, 15, 20};
    private JComboBox<Integer> strokeWidthDropdown;

    public strokeWidthDropdownPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(strokeLabel);
        strokeWidthDropdown = new JComboBox<>(widths);
        strokeWidthDropdown.setSelectedItem(3);
        add(strokeWidthDropdown);
        add(Box.createHorizontalStrut(10));
    }

    public Integer getSelectedWidth() {
        return (Integer) strokeWidthDropdown.getSelectedItem();
    }
}
