package it.polimi.ingsw.gc03.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;
import it.polimi.ingsw.gc03.model.side.Side;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * Virtual Interface for GameController and MainController methods.
 */
//Dovrebbe essere completo
public interface VirtualServer extends Remote{

    void connectClient(VirtualView client) throws RemoteException;

    Game getGame() throws RemoteException;


    List<GameController> getGameControllers() throws RemoteException;

    void resetInstance() throws RemoteException;

    GameController addPlayerToGame(String playerNickName) throws RemoteException, PlayerAlreadyJoinedException, DeskIsFullException, CannotJoinGameException;

    void updateSize(int size, int gameID) throws Exception;

    boolean checkNicknameValidity(String nickname) throws RemoteException;
}
