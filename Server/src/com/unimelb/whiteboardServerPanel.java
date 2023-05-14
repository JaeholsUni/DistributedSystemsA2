package com.unimelb;

import javax.swing.*;
import java.awt.*;

public class whiteboardServerPanel extends JPanel {

    private WhiteboardStateServer serverLocalState;

    private topMenuPanel topMenuPanel;
    private connectedUserPanel connectedUserPanel;
    private bannedUserPanel bannedUserPanel;
    private bottomControlPanel bottomMenuPanel;

    public whiteboardServerPanel(WhiteboardStateServer whiteboardState) {
        serverLocalState = whiteboardState;

        topMenuPanel = new topMenuPanel(whiteboardState);
        bottomMenuPanel = new bottomControlPanel(whiteboardState);
        connectedUserPanel = new connectedUserPanel(whiteboardState, bottomMenuPanel);
        bannedUserPanel = new bannedUserPanel(whiteboardState, bottomMenuPanel);

        setLayout(new BorderLayout());
        add(topMenuPanel, BorderLayout.NORTH);
        add(connectedUserPanel, BorderLayout.WEST);
        add(bannedUserPanel, BorderLayout.EAST);
        add(bottomMenuPanel, BorderLayout.SOUTH);

    }

}
