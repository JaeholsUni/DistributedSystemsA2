package com.unimelb;

import java.rmi.Naming;

public class Main {

    public static void main(String[] args) {
	// write your code here

        String hostname = "localhost";
        String serviceName = "StateService";

        try {
            IState state = new StateServer();
            Naming.rebind("rmi://" + hostname + "/" + serviceName, state);
            System.out.println("RMI ready to go");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
