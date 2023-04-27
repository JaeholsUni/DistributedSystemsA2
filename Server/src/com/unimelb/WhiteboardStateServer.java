package com.unimelb;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WhiteboardStateServer extends UnicastRemoteObject implements IWhiteboardState {

    int myState = 0;
    private ArrayList<renderElement> elements = new ArrayList<renderElement>();

    protected WhiteboardStateServer() throws RemoteException {
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

    @Override
    public ArrayList<renderElement> getElementArray() throws RemoteException {
        //System.out.println("hehe someone wants my elements");
        return elements;
    }

    @Override
    public void addElement(renderElement element) throws RemoteException {
        elements.add(element);
        System.out.println("Element Added :)");
        System.out.println("Total number of elements" + elements.size());
    }

    @Override
    public void clearElements() throws RemoteException {
        elements.clear();
    }
}
