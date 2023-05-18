package com.unimelb;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WhiteboardStateServer extends UnicastRemoteObject implements IWhiteboardState {

    private ArrayList<IRenderable> elements = new ArrayList<IRenderable>();
    private ArrayList<String> chatMessages = new ArrayList<String>();
    private ArrayList<String> connectedUsers = new ArrayList<String>();
    private ArrayList<String> blackList = new ArrayList<String>();

    protected WhiteboardStateServer() throws RemoteException {
        super();
    }

    @Override
    public ArrayList<IRenderable> getElementArray() throws RemoteException {
        return elements;
    }

    @Override
    public void addElement(IRenderable element) throws RemoteException {
        elements.add(element);
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

    @Override
    public void removeUser(String username) throws RemoteException {
        connectedUsers.remove(username);
    }

    @Override
    public void banUser(String username) throws RemoteException {
        blackList.add(username);
        connectedUsers.remove(username);
    }

    @Override
    public void unbanUser(String username) throws RemoteException {
        blackList.remove(username);
    }

    @Override
    public boolean isOnBlackList(String username) throws RemoteException {
        if (blackList.contains(username)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void heartbeat() throws RemoteException {
    }

    public ArrayList<String> getBlackList() {
        return blackList;
    }
}
