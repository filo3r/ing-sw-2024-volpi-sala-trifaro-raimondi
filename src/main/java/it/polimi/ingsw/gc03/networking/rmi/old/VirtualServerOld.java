package it.polimi.ingsw.gc03.networking.rmi.old;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Virtual Interface for GameController and MainController methods.
 */
//Dovrebbe essere completo
public interface VirtualServerOld extends Remote{

    void connectClient(VirtualViewOld client) throws RemoteException;

    Game getGame() throws RemoteException;


    List<GameController> getGameControllers() throws RemoteException;

    void resetInstance() throws RemoteException;

    GameController addPlayerToGame(String playerNickName, VirtualViewOld listener) throws RemoteException, PlayerAlreadyJoinedException, DeskIsFullException, CannotJoinGameException;

    void updateSize(int size, int gameID) throws Exception;

    boolean checkNicknameValidity(String nickname) throws RemoteException;

    void addPlayerToSpecificGame(String nickname, int id, VirtualViewOld listener) throws RemoteException;

    void infiniteTask(int id, String p) throws Exception;


    void ping(VirtualViewOld client) throws RemoteException;
}
