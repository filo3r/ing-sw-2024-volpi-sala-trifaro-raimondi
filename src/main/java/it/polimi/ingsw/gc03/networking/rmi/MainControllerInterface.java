package it.polimi.ingsw.gc03.networking.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 * The MainControllerInterface defines the contract for managing the lifecycle and interactions of multiple
 * game sessions within the application. It ensures a standardized approach for creating, joining,
 * and deleting games, as well as handling player reconnections.
 * Implementations of this interface should follow the Singleton design pattern to ensure only one instance
 * manages the game controllers globally, providing a centralized point of control for game session management.
 */
public interface MainControllerInterface extends Remote {

    /**
     * This method creates a new game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     */
    GameControllerInterface createGame(GameListener gameListener, String nickname) throws RemoteException;


    /**
     * This method adds a player to the first available game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     */
    GameControllerInterface joinFirstAvailableGame(GameListener gameListener, String nickname) throws RemoteException;


    /**
     * This method adds a player to a specific game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    GameControllerInterface joinSpecificGame(GameListener gameListener, String nickname, int idGame) throws RemoteException;


    /**
     * This method reconnects a player to the game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     */
    GameControllerInterface reconnectToGame(GameListener gameListener, String nickname) throws RemoteException;

}
