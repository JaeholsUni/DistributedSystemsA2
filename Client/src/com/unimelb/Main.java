package com.unimelb;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {

        String serviceName = "WhiteboardStateService";

        try {
            Registry registry = LocateRegistry.getRegistry(args[0], Integer.parseInt(args[1]));

            IWhiteboardState whiteboardState = (IWhiteboardState) registry.lookup(serviceName);

            JFrame frame = new JFrame("Whiteboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            whiteboard whiteboard = new whiteboard(whiteboardState);
            frame.add(whiteboard);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    try {
                        whiteboardState.removeUser(whiteboard.getUsername());
                    } catch (Exception ex)  {
                        ex.printStackTrace();
                    }
                    frame.dispose();
                }
            });
            frame.setSize(1280, 720);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
