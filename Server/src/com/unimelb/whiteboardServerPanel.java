package com.unimelb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class whiteboardServerPanel extends JPanel {

    private WhiteboardStateServer serverLocalState;

    private topMenuPanel topMenuPanel;
    private connectedUserPanel connectedUserPanel;
    private bannedUserPanel bannedUserPanel;

    private JButton kickButton;


    private Timer timer;
    private String lastSelectedUser;

    public whiteboardServerPanel(WhiteboardStateServer whiteboardState) {
        serverLocalState = whiteboardState;

        topMenuPanel = new topMenuPanel(whiteboardState);
        connectedUserPanel = new connectedUserPanel(whiteboardState, lastSelectedUser);
        bannedUserPanel = new bannedUserPanel(whiteboardState, lastSelectedUser);

        setLayout(new BorderLayout());
        add(topMenuPanel, BorderLayout.NORTH);
        add(connectedUserPanel, BorderLayout.WEST);
        add(bannedUserPanel, BorderLayout.EAST);

        kickButton = new JButton("Kick User");
        kickButton.addActionListener(e -> {
            System.out.println(lastSelectedUser);
            try {
                serverLocalState.banUser(lastSelectedUser);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        add(kickButton);


        timer = new Timer(16, new RefreshElements());
        timer.start();
    }

    private class RefreshElements implements ActionListener {
        public void actionPerformed(ActionEvent e) {

        }
    }
}
