package com.unimelb;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StateServer extends UnicastRemoteObject implements IState {

    int myState = 0;

    protected StateServer() throws RemoteException {
        super();
    }

    @Override
    public String helloWorld(String who) throws RemoteException {
        return "Hello " + who + "from the server via RMI :)";
    }

    @Override
    public int getMyState() throws RemoteException {
        return myState;
    }

    @Override
    public void setMyState(int newState) throws RemoteException {
        myState = newState;
    }

}
