package com.unimelb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class whiteboardServerPanel extends JPanel {

    private WhiteboardStateServer serverLocalState;
    private JButton resetButton;

    private JList<String> connectedUserList;
    private JScrollPane connectedUserScrollPane;
    private JButton kickButton;
    private Timer timer;
    private String lastSelectedUser;

    public whiteboardServerPanel(WhiteboardStateServer whiteboardState) {
        serverLocalState = whiteboardState;

        try{
            connectedUserList = new JList<>(serverLocalState.getConnectedUsers().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        connectedUserScrollPane = new JScrollPane(connectedUserList);
        add(connectedUserScrollPane);

        connectedUserList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                lastSelectedUser = connectedUserList.getSelectedValue();
                System.out.println(lastSelectedUser);
            }
        });

        resetButton = new JButton("Reset Points");
        resetButton.addActionListener(e -> {
            try {
                serverLocalState.clearElements();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

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

        add(resetButton);

        timer = new Timer(16, new RefreshElements());
        timer.start();
    }

    private class RefreshElements implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setConnectedUserList();
        }
    }

    private void setConnectedUserList() {
        try {
            ListModel currentModel = connectedUserList.getModel();
            JList tempList = new JList<>(serverLocalState.getConnectedUsers().toArray(new String[0]));
            ListModel tempModel = tempList.getModel();

            if (currentModel.getSize() != tempModel.getSize()) {
                connectedUserList.setListData(serverLocalState.getConnectedUsers().toArray(new String[0]));
                System.out.println("Change");
                return;
            } else {
                for (int i = 0; i < currentModel.getSize(); i++) {
                    if (!currentModel.getElementAt(i).equals(tempModel.getElementAt(i))) {
                        connectedUserList.setListData(serverLocalState.getConnectedUsers().toArray(new String[0]));
                        System.out.println("Change");
                        return;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
