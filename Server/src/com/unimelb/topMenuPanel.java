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
            try {
                whiteboardStateExtractor.extractWhiteboardCSV(state.getElementArray(), "Whiteboard");
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
        });

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(saveButton);
        add(loadButton);
        add(resetButton);
    }
}
