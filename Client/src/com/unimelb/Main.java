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

            IWhiteboardState state = (IWhiteboardState) registry.lookup(serviceName);

            //IWhiteboardState state = (IWhiteboardState) Naming.lookup("rmi://" + hostname + "/" + serviceName);
            System.out.println(state.helloWorld(who));
            System.out.println(state.getMyState());
            state.setMyState(5);
            System.out.println(state.getMyState());

            JFrame frame = new JFrame("Line Drawer");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new whiteboard(state));
            frame.setSize(400, 400);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
