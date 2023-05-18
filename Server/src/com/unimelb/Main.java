package com.unimelb;

import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {

        String serviceName = "WhiteboardStateService";

        try {
            IWhiteboardState state = new WhiteboardStateServer();
            LocateRegistry.createRegistry(Integer.parseInt(args[1]));
            Registry registry = LocateRegistry.getRegistry(args[0],Integer.parseInt(args[1]));
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
}
