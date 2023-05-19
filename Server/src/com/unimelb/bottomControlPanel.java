package com.unimelb;

import javax.swing.*;

public class bottomControlPanel extends JPanel {

    private IWhiteboardState localState;
    private JTextField lastSelectedUserField;
    private JButton kickUserButton;
    private JButton unbanUserButton;

    public bottomControlPanel(IWhiteboardState state) {

        localState = state;

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JLabel selectedLabel = new JLabel("Selected User:");

        add(selectedLabel);

        lastSelectedUserField = new JTextField(20);
        lastSelectedUserField.setEditable(false);

        add(lastSelectedUserField);

        kickUserButton = new JButton("Kick User");
        kickUserButton.addActionListener(e -> kickLastSelectedUser());
        add(kickUserButton);

        unbanUserButton = new JButton("Unban User");
        unbanUserButton.addActionListener(e -> unbanLastSelectedUser());
        add(unbanUserButton);

    }

    private void kickLastSelectedUser() {
        String selection = lastSelectedUserField.getText();

        if (selection == null) {
            return;
        }
        try {
            localState.banUser(selection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void unbanLastSelectedUser() {
        String selection = lastSelectedUserField.getText();

        if (selection == null) {
            return;
        }
        try {
            localState.unbanUser(selection);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setLastSelectedUserField(String user) {
        lastSelectedUserField.setText(user);
    }
}
