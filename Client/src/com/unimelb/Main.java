package com.unimelb;

import javax.swing.*;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {

        String hostname = "localhost";
        String serviceName = "StateService";
        String who = "client";

        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 4444);

            IState state = (IState) registry.lookup(serviceName);

            //IState state = (IState) Naming.lookup("rmi://" + hostname + "/" + serviceName);
            System.out.println(state.helloWorld(who));
            System.out.println(state.getMyState());
            state.setMyState(5);
            System.out.println(state.getMyState());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Line Drawer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new whiteboard());
        frame.setSize(400, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
