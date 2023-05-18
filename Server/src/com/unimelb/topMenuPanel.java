package com.unimelb;

import javax.swing.*;
import java.util.ArrayList;

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
            ArrayList<IRenderable> newRenders =  whiteboardStateExtractor.loadWhiteboardRenderables();

            try {
                state.clearElements();
                for (int i = 0; i < newRenders.size(); i++) {
                    state.addElement(newRenders.get(i));
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        add(saveButton);
        add(loadButton);
        add(resetButton);
    }
}
