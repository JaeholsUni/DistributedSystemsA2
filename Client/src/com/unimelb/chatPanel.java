package com.unimelb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class chatPanel extends JPanel {

    private JTextArea textArea;
    private JTextField inputField;
    private IWhiteboardState localState;
    private Timer timer;
    private String username;


    public chatPanel(IWhiteboardState state, String username) {
        localState = state;
        this.username = username;
        textArea = new JTextArea(20, 40);
        JScrollPane scrollPane = new JScrollPane(textArea);
        inputField = new JTextField(40);
        JButton addButton = new JButton("Send");
        addButton.addActionListener(new AddButtonListener());
        JPanel inputPanel = new JPanel();
        inputPanel.add(inputField);
        inputPanel.add(addButton);
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        timer = new Timer(16, new ReWriteTextWindow());
        timer.start();
    }

    private void printList() {
        try{
            for (String str : localState.getChatList()) {
                textArea.append(str + "\n");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputText = inputField.getText();
            if (!inputText.isEmpty()) {
                try {
                    localState.addNewChatMessage(username + ": " + inputText);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                inputField.setText("");
            }
        }
    }

    private class ReWriteTextWindow implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            textArea.setText(null);
            printList();
        }
    }
}
