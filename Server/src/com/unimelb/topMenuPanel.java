package com.unimelb;

import javax.swing.*;

public class topMenuPanel extends JPanel {

    private IWhiteboardState localState;
    private JButton resetButton;
    private JButton saveButton;
    private JButton loadButton;

    public topMenuPanel(IWhiteboardState state) {
        this.localState = state;

        resetButton = new JButton("Reset Whiteboard");
        resetButton.addActionListener(e -> {
            try {
                localState.clearElements();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
        });

        loadButton = new JButton("Save");
        loadButton.addActionListener(e -> {
        });

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(saveButton);
        add(loadButton);
        add(resetButton);
    }
}
