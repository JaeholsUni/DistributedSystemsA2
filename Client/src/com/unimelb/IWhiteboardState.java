package com.unimelb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IWhiteboardState extends Remote {

    public ArrayList<IRenderable> getElementArray() throws RemoteException;

    public void addElement(IRenderable element) throws RemoteException;

    public void clearElements() throws RemoteException;

    public ArrayList<String> getChatList() throws RemoteException;

    public void addNewChatMessage(String newChat) throws RemoteException;

    public ArrayList<String> getConnectedUsers() throws RemoteException;

    public void addNewUser(String username) throws RemoteException;

    public void removeUser(String username) throws RemoteException;

    public void banUser(String username) throws RemoteException;

    public void unbanUser(String username) throws RemoteException;

    public boolean isOnBlackList(String username) throws RemoteException;

    public void heartbeat() throws RemoteException;

    public boolean checkPassword(String password) throws RemoteException;
}
