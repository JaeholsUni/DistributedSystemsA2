package com.unimelb;

import javax.swing.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {

        String hostname = "localhost";
        String serviceName = "StateService";
        String who = "client";

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4444);

            IWhiteboardState whiteboardState = (IWhiteboardState) registry.lookup(serviceName);

            //IWhiteboardState state = (IWhiteboardState) Naming.lookup("rmi://" + hostname + "/" + serviceName);
            System.out.println(whiteboardState.helloWorld(who));
            System.out.println(whiteboardState.getMyState());
            whiteboardState.setMyState(5);
            System.out.println(whiteboardState.getMyState());

            JFrame frame = new JFrame("Line Drawer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new whiteboard(whiteboardState));
            frame.setSize(1280, 720);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
