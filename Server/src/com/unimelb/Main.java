package com.unimelb;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String hostname = "localhost";
        String serviceName = "StateService";

        try {
            IWhiteboardState state = new WhiteboardStateServer();
            LocateRegistry.createRegistry(4444);
            Registry registry = LocateRegistry.getRegistry(hostname,4444);
            registry.bind(serviceName, state);

            //Naming.rebind("rmi://" + hostname + "/" + serviceName, state);
            System.out.println("RMI ready to go");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
