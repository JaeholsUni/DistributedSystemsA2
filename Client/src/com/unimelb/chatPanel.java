package com.unimelb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class chatPanel extends JPanel {

    private ArrayList<String> list;
    private JTextArea textArea;
    private JTextField inputField;

    public chatPanel(ArrayList<String> list) {
        this.list = list;
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
        printList();
    }

    private void printList() {
        for (String str : list) {
            textArea.append(str + "\n");
        }
    }

    private class AddButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String inputText = inputField.getText();
            if (!inputText.isEmpty()) {
                list.add(inputText);
                textArea.append(inputText + "\n");
                inputField.setText("");
            }
        }
    }
}
