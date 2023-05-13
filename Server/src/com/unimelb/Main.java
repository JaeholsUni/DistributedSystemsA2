package com.unimelb;

import javax.swing.*;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteObject;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) {

        String hostname = "localhost";
        String serviceName = "StateService";

        try {
            IWhiteboardState state = new WhiteboardStateServer();
            LocateRegistry.createRegistry(4444);
            Registry registry = LocateRegistry.getRegistry(hostname,4444);
            registry.bind(serviceName, state);

            JFrame frame = new JFrame("Whiteboard Host");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            whiteboardServerPanel serverPanel = new whiteboardServerPanel((WhiteboardStateServer) state);
            frame.add(serverPanel);

            frame.setSize(1280, 720);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            System.out.println("RMI ready to go");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
