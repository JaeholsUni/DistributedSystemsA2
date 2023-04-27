package com.unimelb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IWhiteboardState extends Remote {

    public String helloWorld(String who) throws RemoteException;

    public int getMyState() throws RemoteException;

    public void setMyState(int newState) throws RemoteException;

    public ArrayList<renderElement> getElementArray() throws RemoteException;

    public void addElement(renderElement element) throws RemoteException;

    public void clearElements() throws RemoteException;
}
