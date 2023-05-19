/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import javax.swing.*;
import java.awt.*;

public class whiteboardServerPanel extends JPanel {

    private topMenuPanel topMenuPanel;
    private connectedUserPanel connectedUserPanel;
    private bannedUserPanel bannedUserPanel;
    private bottomControlPanel bottomMenuPanel;

    public whiteboardServerPanel(WhiteboardStateServer whiteboardState) {

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
