package it.polimi.ingsw.gc03.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
//manca tutto
public interface VirtualView extends Remote {

    void showUpdate() throws RemoteException;

    void reportError(String details) throws RemoteException;

}
