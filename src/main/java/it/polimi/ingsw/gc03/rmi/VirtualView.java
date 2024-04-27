package it.polimi.ingsw.gc03.rmi;

import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.Desk;
import it.polimi.ingsw.gc03.model.Game;

import java.rmi.Remote;
import java.rmi.RemoteException;
//manca tutto
public interface VirtualView extends Remote {

    void updatePlayerJoined(String playerJoined) throws RemoteException;
    void updateSizeChanged(int size) throws RemoteException;

    void updateGame(Game game) throws RemoteException;

    void updateDesk(Desk desk) throws RemoteException;

    void updateCodex(Codex codex)throws RemoteException;

    void reportError(String details) throws RemoteException;

}
