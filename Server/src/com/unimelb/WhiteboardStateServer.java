/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class WhiteboardStateServer extends UnicastRemoteObject implements IWhiteboardState {

    private byte[] statePassword;
    private passwordHandler securityHandler;
    private ArrayList<IRenderable> elements = new ArrayList<IRenderable>();
    private ArrayList<String> chatMessages = new ArrayList<String>();
    private ArrayList<String> connectedUsers = new ArrayList<String>();
    private ArrayList<String> blackList = new ArrayList<String>();

    protected WhiteboardStateServer(String password) throws RemoteException {
        this.securityHandler = new passwordHandler();

        if (password == null || password.equals("")){
            this.statePassword = null;
        } else {
            this.statePassword = securityHandler.hashPassword(password);
        }
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

    @Override
    public boolean checkPassword(String password) throws RemoteException {
        if (statePassword == null) {
            return true;
        }
        if (securityHandler.checkPassword(statePassword, securityHandler.hashPassword(password))){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<String> getBlackList() {
        return blackList;
    }
}
