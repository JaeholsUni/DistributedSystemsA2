package com.unimelb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class bannedUserPanel extends JPanel {

    private WhiteboardStateServer localState;
    private String lastSelection;

    private JList<String> bannedUserList;
    private JScrollPane bannedUserScrollPane;

    private Timer timer;

    public bannedUserPanel(WhiteboardStateServer state, String lastSelectedUser) {
        localState = state;
        lastSelection = lastSelectedUser;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel bannedUserLabel = new JLabel("Banned Users");
        add(bannedUserLabel);

        try{
            bannedUserList = new JList<>(localState.getBlackList().toArray(new String[0]));
        } catch (Exception e) {
            e.printStackTrace();
        }

        bannedUserList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                lastSelection = bannedUserList.getSelectedValue();
                System.out.println("From the Banned List: " + lastSelection);
            }
        });

        bannedUserScrollPane = new JScrollPane(bannedUserList);

        add(bannedUserScrollPane);

        timer = new Timer(16, new RefreshElements());
        timer.start();

    }

    private class RefreshElements implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            setBannedUserList();
        }
    }

    private void setBannedUserList() {
        try {
            ListModel currentModel = bannedUserList.getModel();
            JList tempList = new JList<>(localState.getBlackList().toArray(new String[0]));
            ListModel tempModel = tempList.getModel();

            if (currentModel.getSize() != tempModel.getSize()) {
                bannedUserList.setListData(localState.getBlackList().toArray(new String[0]));
                System.out.println("Change");
                return;
            } else {
                for (int i = 0; i < currentModel.getSize(); i++) {
                    if (!currentModel.getElementAt(i).equals(tempModel.getElementAt(i))) {
                        bannedUserList.setListData(localState.getBlackList().toArray(new String[0]));
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
