package it.polimi.ingsw.gc03.rmi;

import it.polimi.ingsw.gc03.model.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;
//manca tutto
public interface VirtualView extends Remote {

    public void updatePlayerJoined(String playerJoined) throws RemoteException;
    public void updateSizeChanged(int size) throws RemoteException;

    void reportError(String details) throws RemoteException;

}
