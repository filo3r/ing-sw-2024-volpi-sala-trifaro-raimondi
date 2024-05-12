package it.polimi.ingsw.gc03.listeners;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameModel;
import it.polimi.ingsw.gc03.model.Player;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * This interface is used to inform the client about changes in the game.
 */
public interface GameListener extends Remote {

    /**
     * This method is used to inform the client that a player has joined the game.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerJoined(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that a player has left the game.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player who left the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerLeft(GameModel gameModel, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that a player tried to join a full game.
     * @param gameModel The immutable game model.
     * @param player The player that tried to join.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void joinUnableGameFull(GameModel gameModel, Player player) throws RemoteException;


    /**
     * This method is used to inform the client that a player has reconnected to the game.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerReconnected(GameModel gameModel, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that a player has disconnected.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player that has disconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerDisconnected(GameModel gameModel, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that only one player is connected.
     * @param gameModel The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void onlyOnePlayerConnected(GameModel gameModel, int timer) throws RemoteException;


    /**
     * This method is used to inform the client that a player tried to use a nickname that is already in use.
     * @param player The player that tried to use the nickname.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void joinUnableNicknameAlreadyInUse(Player player) throws RemoteException;


    /**
     * This method is used to inform the client that the game id doesn't exist.
     * @param gameId The game id.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameIdNotExists(int gameId) throws RemoteException;


    /**
     * This method is used to inform the client that the game has started.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameStarted(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the game has ended.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameEnded(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that a message has been sent.
     * @param gameModel The immutable game model.
     * @param chatMessage The message that has been sent.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void sentChatMessage(GameModel gameModel, ChatMessage chatMessage) throws RemoteException;


    /**
     * This method is used to inform the client that the next turn triggered.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void nextTurn(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the last circle has started.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void lastCircle(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the card was placed into the Codex.
     * @param gameModel The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void positionedCardIntoCodex(GameModel gameModel, int row, int column) throws RemoteException;


    Aggiungere ancora metodi



}
