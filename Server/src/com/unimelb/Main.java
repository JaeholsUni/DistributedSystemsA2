/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {

        String serviceName = "WhiteboardStateService";

        try {
            String password = enterPassword();
            IWhiteboardState state = new WhiteboardStateServer(password);
            LocateRegistry.createRegistry(Integer.parseInt(args[0]));
            Registry registry = LocateRegistry.getRegistry("localhost",Integer.parseInt(args[0]));
            registry.bind(serviceName, state);

            JFrame frame = new JFrame("Whiteboard Host");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            whiteboardServerPanel serverPanel = new whiteboardServerPanel((WhiteboardStateServer) state);
            frame.add(serverPanel);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Cannot host ensure valid params", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    private static String enterPassword() {
        return JOptionPane.showInputDialog(null, "Please enter password \n Leave blank for no password");
    }
}
