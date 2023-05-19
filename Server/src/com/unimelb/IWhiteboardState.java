/*
Distributed Systems Assignment 2
James Hollingsworth - 915178
jameswh@iinet.net.au
 */

package com.unimelb;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface IWhiteboardState extends Remote {

    ArrayList<IRenderable> getElementArray() throws RemoteException;

    void addElement(IRenderable element) throws RemoteException;

    void clearElements() throws RemoteException;

    ArrayList<String> getChatList() throws RemoteException;

    void addNewChatMessage(String newChat) throws RemoteException;

    ArrayList<String> getConnectedUsers() throws RemoteException;

    void addNewUser(String username) throws RemoteException;

    void removeUser(String username) throws RemoteException;

    void banUser(String username) throws RemoteException;

    void unbanUser(String username) throws RemoteException;

    boolean isOnBlackList(String username) throws RemoteException;

    void heartbeat() throws RemoteException;

    boolean checkPassword(String password) throws RemoteException;
}
