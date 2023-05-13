package com.unimelb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IWhiteboardState extends Remote {

    public String helloWorld(String who) throws RemoteException;

    public int getMyState() throws RemoteException;

    public void setMyState(int newState) throws RemoteException;

    public ArrayList<IRenderable> getElementArray() throws RemoteException;

    public void addElement(IRenderable element) throws RemoteException;

    public void clearElements() throws RemoteException;

    public ArrayList<String> getChatList() throws RemoteException;

    public void addNewChatMessage(String newChat) throws RemoteException;

    public ArrayList<String> getConnectedUsers() throws RemoteException;

    public void addNewUser(String username) throws RemoteException;

    public void removeUser(String username) throws RemoteException;

    public void heartbeat() throws RemoteException;
}
