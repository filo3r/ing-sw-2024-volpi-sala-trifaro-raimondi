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

    void connect(VirtualView client) throws RemoteException;

    void addPlayerToGame(String playerNickName) throws RemoteException, PlayerAlreadyJoinedException, DeskIsFullException, CannotJoinGameException;

    void reconnectPlayer(String playerNickname) throws RemoteException;

    void placeStarterOnCodex(Player player, Side side) throws RemoteException,Exception;

    void drawCardFromDeck(Player player, ArrayList<?extends Card> deck) throws RemoteException,Exception;

    void drawCardDisplayed(Player player,ArrayList<?extends Card> deck, int index) throws RemoteException,Exception;

    void selectCardObjective(Player player, int cardObjective) throws RemoteException,Exception;

    Game getGame() throws RemoteException;

    void placeCardOnCodex(Player player, int index, boolean frontCard, int row,int col) throws RemoteException,Exception;

    void run() throws RemoteException;

    //MainController Methods;

    MainController getInstance() throws RemoteException;

    void createGame(String firstPlayerNickname) throws RemoteException;

    void joinGame(String playerNickname) throws RemoteException;

    void reconnect(String playerNickname) throws RemoteException;

    void deleteGame(int idGame) throws RemoteException, NoSuchGameException;

    List<GameController> getGameControllers() throws RemoteException;

    void resetInstance() throws RemoteException;

}
