package com.unimelb;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class chatPanel extends JPanel {

    private JTextArea textArea;
    private JTextField inputField;
    private IWhiteboardState localState;
    private Timer timer;
    private String username;
    private JList<String> connectedUsers;
    private JScrollPane userListScrollPane;


    public chatPanel(IWhiteboardState state, String username) {
        localState = state;
        this.username = username;
        try {
            connectedUsers = new JList<>(localState.getConnectedUsers().toArray(new String[0]));
        } catch (Exception e) {
        }
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        inputField = new JTextField(40);
        JButton addButton = new JButton("Send");
        JLabel connectedUsersLabel = new JLabel("Connected Users:");
        userListScrollPane = new JScrollPane(connectedUsers);
        addButton.addActionListener(new AddButtonListener());
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(scrollPane);
        add(inputPanel);
        add(connectedUsersLabel);
        add(userListScrollPane);


        timer = new Timer(16, new ReWriteTextWindow());
        timer.start();
    }

    private void printList() {
        try{
            for (String str : localState.getChatList()) {
                textArea.append(str + "\n");
            }
        }catch (Exception e) {
            timer.stop();
        }

    }

    private void printConnectedUsers() {
        try {
            connectedUsers.setListData(localState.getConnectedUsers().toArray(new String[0]));
        } catch (Exception e) {
            timer.stop();
        }
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputText = inputField.getText();
            if (!inputText.isEmpty()) {
                try {
                    localState.addNewChatMessage(username + ": " + inputText);
                } catch (Exception ex) {
                    timer.stop();
                }
                inputField.setText("");
            }
        }
    }

    private class ReWriteTextWindow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.setText(null);
            printList();
            printConnectedUsers();
        }
    }
}
