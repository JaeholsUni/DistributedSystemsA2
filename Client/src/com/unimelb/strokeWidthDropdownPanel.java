package com.unimelb;

import javax.swing.*;

public class strokeWidthDropdownPanel extends JPanel {

    private Integer[] widths = {1, 2, 3, 4, 5, 7, 10, 13, 15, 20};
    private JComboBox<Integer> strokeWidthDropdown;

    public strokeWidthDropdownPanel() {
        strokeWidthDropdown = new JComboBox<>(widths);
        strokeWidthDropdown.setSelectedItem(3);
        add(strokeWidthDropdown);
    }

    public Integer getSelectedWidth() {
        return (Integer) strokeWidthDropdown.getSelectedItem();
    }
}
