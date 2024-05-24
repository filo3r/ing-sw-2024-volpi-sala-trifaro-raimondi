package it.polimi.ingsw.gc03.networking.rmi;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.networking.AsyncLogger;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;


/**
 * The RMIServer class handles incoming RMI requests from clients.
 * It communicates using the Java RMI (Remote Method Invocation) protocol,
 * allowing remote method calls between the client and the server.
 * It uses the Singleton pattern to ensure that there is only one instance of the RMI server running.
 */
public class RmiServer extends UnicastRemoteObject implements MainControllerInterface {

    /**
     * The singleton instance of the RMI server.
     */
    private static RmiServer rmiServer = null;

    /**
     * The registry for binding the RMI server instance.
     */
    private static Registry rmiRegistry = null;

    /**
     * The main controller handling game logic and management.
     */
    private final MainController mainController;

    /**
     * Flag indicating whether the RMI server is currently running.
     */
    private static boolean isRmiServerRunning = false;


    /**
     * Constructor to create an RMI Server.
     * @throws RemoteException If an error occurs in remote communication.
     */
    public RmiServer() throws RemoteException {
        super(0);
        this.mainController = MainController.getInstance();
    }


    /**
     * Starts the RMI server on the specified port.
     * @param port The port on which the RMI server will listen.
     * @throws RemoteException If an error occurs in remote communication.
     */
    public synchronized static void startRmiServer(int port) throws RemoteException {
        if (isRmiServerRunning) {
            AsyncLogger.log(Level.INFO, "[SERVER RMI] Server RMI already initialized and listening for connections.");
            return;
        } else {
            try {
                rmiServer = new RmiServer();
                rmiRegistry = LocateRegistry.createRegistry(port);
                rmiRegistry.rebind("RMIServer", rmiServer);
                isRmiServerRunning = true;
                AsyncLogger.log(Level.INFO, "[SERVER RMI] Server RMI initialized and listening for connections.");
            } catch (RemoteException e) {
                AsyncLogger.log(Level.SEVERE, "[SERVER RMI] Error initializing server RMI: " + e.getMessage());
            }
        }
    }


    /**
     * Shuts down the RMI Server.
     */
    public synchronized static void stopRmiServer() {
        if (!isRmiServerRunning) {
            AsyncLogger.log(Level.INFO, "[SERVER RMI] Server RMI is not running.");
            return;
        } else {
            try {
                if (rmiRegistry != null)
                    rmiRegistry.unbind("RMIServer");
                UnicastRemoteObject.unexportObject(rmiServer, true);
                rmiServer = null;
                rmiRegistry = null;
                isRmiServerRunning = false;
                AsyncLogger.log(Level.INFO, "[SERVER RMI] Server RMI has been shut down.");
            } catch (RemoteException e) {
                AsyncLogger.log(Level.SEVERE, "[SERVER RMI] Error shutting down server RMI: " + e.getMessage());
            } catch (Exception e) {
                AsyncLogger.log(Level.SEVERE, "[SERVER RMI] Error shutting down server RMI: " + e.getMessage());
            }
        }
    }


    /**
     * Gets the current RMI server instance.
     * @return The current RmiServer instance.
     */
    public static synchronized RmiServer getRmiServer() {
        return rmiServer;
    }


    /**
     * Gets the registry associated with the RMI server.
     * @return The RMI registry.
     */
    public static synchronized Registry getRmiRegistry() {
        return rmiRegistry;
    }


    /**
     * Checks if the RMI server is running.
     * @return True if the RMI server is running, otherwise false.
     */
    public static synchronized boolean isRmiServerRunning() {
        return isRmiServerRunning;
    }


    /**
     * Exports the object if it is not already exported.
     * @param object The remote object to export.
     * @throws RemoteException If exporting the object fails.
     */
    private void exportObjectIfNeeded(GameController object) throws RemoteException {
        try {
            UnicastRemoteObject.exportObject(object, 0);
        } catch (RemoteException e) {
            // Object already exported
        }
    }


    /**
     * This method creates a new game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     */
    public GameController createGame(GameListener gameListener, String nickname) throws RemoteException {
        GameController gameController = mainController.createGame(gameListener, nickname);
        exportObjectIfNeeded(gameController);
        AsyncLogger.log(Level.INFO, "[SERVER RMI] The client " + nickname + " has created a new game.");
        return gameController;
    }


    /**
     * This method adds a player to the first available game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     */
    public GameController joinFirstAvailableGame(GameListener gameListener, String nickname) throws RemoteException {
        GameController gameController = mainController.joinFirstAvailableGame(gameListener, nickname);
        if (gameController == null) {
            AsyncLogger.log(Level.WARNING, "[SERVER RMI] The client " + nickname + " has not been added to any games.");
        } else {
            exportObjectIfNeeded(gameController);
            AsyncLogger.log(Level.INFO, "[SERVER RMI] The client " + nickname + " has been added to a game.");
        }
        return gameController;
    }


    /**
     * This method adds a player to a specific game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    public GameController joinSpecificGame(GameListener gameListener, String nickname, int idGame) throws RemoteException {
        GameController gameController = mainController.joinSpecificGame(gameListener, nickname, idGame);
        if (gameController == null) {
            AsyncLogger.log(Level.WARNING, "[SERVER RMI] The client " + nickname + " was not added to the game: " + idGame + ".");
        } else {
            exportObjectIfNeeded(gameController);
            AsyncLogger.log(Level.INFO, "[SERVER RMI] The client " + nickname + " has been added to the game: " + idGame + ".");
        }
        return gameController;
    }


    /**
     * This method reconnects a player to the game.
     * @param gameListener The game listener to be notified about game events.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     */
    public GameController reconnectToGame(GameListener gameListener, String nickname) throws RemoteException {
        GameController gameController = mainController.reconnectToGame(gameListener, nickname);
        if (gameController == null) {
            AsyncLogger.log(Level.WARNING, "[SERVER RMI] The client " + nickname + " did not reconnect to the game.");
        } else {
            exportObjectIfNeeded(gameController);
            AsyncLogger.log(Level.INFO, "[SERVER RMI] The client " + nickname + " reconnected to the game.");
        }
        return gameController;
    }


}