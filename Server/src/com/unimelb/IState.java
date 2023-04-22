package com.unimelb;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IState extends Remote {

    public String helloWorld(String who) throws RemoteException;

    public int getMyState() throws RemoteException;

    public void setMyState(int newState) throws RemoteException;
}
