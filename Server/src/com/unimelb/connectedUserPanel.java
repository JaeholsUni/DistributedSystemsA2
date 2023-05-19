/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class connectedUserPanel extends JPanel {

    private IWhiteboardState localState;
    private bottomControlPanel bottomControlPanel;

    private JList<String> connectedUserList;
    private JScrollPane connectedUserScrollPane;

    private Timer timer;

    public connectedUserPanel(IWhiteboardState state, bottomControlPanel bottomPanel) {
        localState = state;
        bottomControlPanel = bottomPanel;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel connectedUserLabel = new JLabel("Connected Users");
        add(connectedUserLabel);

        try{
            connectedUserList = new JList<>(localState.getConnectedUsers().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        connectedUserList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                bottomControlPanel.setLastSelectedUserField(connectedUserList.getSelectedValue());
            }
        });

        connectedUserScrollPane = new JScrollPane(connectedUserList);

        add(connectedUserScrollPane);

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
            JList tempList = new JList<>(localState.getConnectedUsers().toArray(new String[0]));
            ListModel tempModel = tempList.getModel();

            if (currentModel.getSize() != tempModel.getSize()) {
                connectedUserList.setListData(localState.getConnectedUsers().toArray(new String[0]));
                return;
            } else {
                for (int i = 0; i < currentModel.getSize(); i++) {
                    if (!currentModel.getElementAt(i).equals(tempModel.getElementAt(i))) {
                        connectedUserList.setListData(localState.getConnectedUsers().toArray(new String[0]));
                        return;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
