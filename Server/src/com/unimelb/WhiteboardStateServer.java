package com.unimelb;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WhiteboardStateServer extends UnicastRemoteObject implements IWhiteboardState {

    int myState = 0;
    private ArrayList<IRenderable> elements = new ArrayList<IRenderable>();
    private ArrayList<String> chatMessages = new ArrayList<String>();
    private ArrayList<String> connectedUsers = new ArrayList<String>();
    private ArrayList<String> blackList = new ArrayList<String>();

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
    public ArrayList<IRenderable> getElementArray() throws RemoteException {
        return elements;
    }

    @Override
    public void addElement(IRenderable element) throws RemoteException {
        elements.add(element);
        System.out.println("Element Added :)");
        System.out.println("Total number of elements" + elements.size());
    }

    @Override
    public void clearElements() throws RemoteException {
        elements.clear();
    }

    @Override
    public ArrayList<String> getChatList() throws RemoteException {
        return chatMessages;
    }

    @Override
    public void addNewChatMessage(String newChat) throws RemoteException {
        chatMessages.add(newChat);
    }

    @Override
    public ArrayList<String> getConnectedUsers() throws RemoteException {
        return connectedUsers;
    }

    @Override
    public void addNewUser(String username) throws RemoteException {
        connectedUsers.add(username);
    }
}
