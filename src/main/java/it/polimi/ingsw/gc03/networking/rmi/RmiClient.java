package it.polimi.ingsw.gc03.networking.rmi;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.view.tui.print.AsyncLogger;
import it.polimi.ingsw.gc03.networking.socket.client.ClientAction;
import it.polimi.ingsw.gc03.networking.socket.client.GameListenerHandlerClient;
import it.polimi.ingsw.gc03.view.ui.Flow;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * The RmiClient class handles the connection to the RMI server and allows
 * clients to interact with the game through remote method invocations.
 */
public class RmiClient implements ClientAction {

    /**
     * The nickname associated with the client.
     */
    private String nicknameClient;

    /**
     * The RMI registry.
     */
    private Registry rmiRegistry;

    /**
     * The remote listener interface for receiving game events from the server.
     */
    private static GameListener gameListener;

    /**
     * The handler for processing game events received from the server.
     */
    private final GameListenerHandlerClient gameListenerHandlerClient;

    /**
     * The main controller returned by the registry.
     */
    private static MainControllerInterface mainController;

    /**
     * The game controller returned by the RMI server.
     */
    private GameControllerInterface gameController;

    /**
     * The Flow object that handles UI and game flow actions.
     */
    private Flow flow;

    /**
     * ping Executor to periodically send a ping message to the server
     */
    private final ScheduledExecutorService pingExecutor = Executors.newSingleThreadScheduledExecutor();

    /**
     * Server ip.
     */
    private String ip;

    /**
     * Server port.
     */
    private int port;


    /**
     * Class constructor.
     * Creates, starts, and connects an RMI Client to the server.
     * @param flow The Flow object that handles UI and game flow actions.
     */
    public RmiClient(String ip, int port, Flow flow) {
        super();
        this.nicknameClient = null;
        this.rmiRegistry = null;
        this.gameController = null;
        this.gameListenerHandlerClient = new GameListenerHandlerClient(flow);
        this.flow = flow;
        pingExecutor.scheduleAtFixedRate(() -> sendPing(nicknameClient), 0, 2, TimeUnit.SECONDS);
        connectToServer();
        this.ip = ip;
        this.port = port;
    }


    /**
     * Setups the connection to the RMI server.
     * @throws RemoteException   If an error occurs in remote communication.
     * @throws NotBoundException If a name in the registry was not found.
     */
    private void setupConnection() throws RemoteException, NotBoundException {
        this.rmiRegistry = LocateRegistry.getRegistry(this.ip, this.port);
        this.mainController = (MainControllerInterface) this.rmiRegistry.lookup("RMIServer");
        gameListener = (GameListener) UnicastRemoteObject.exportObject(gameListenerHandlerClient, 0);
    }


    /**
     * Connects the client to the RMI server.
     */
    private void connectToServer() {
        try {
            setupConnection();
            AsyncLogger.log(Level.INFO, "[CLIENT RMI] Connection established to server.");
        } catch (Exception e) {
            AsyncLogger.log(Level.SEVERE, "[CLIENT RMI] Failed to connect to server: " + e.getMessage());
        }
    }


    /**
     * Disconnects the client from the RMI server.
     */
    public void disconnectFromServer() {
        try {
            UnicastRemoteObject.exportObject(this.gameListenerHandlerClient);
            this.gameController = null;
            this.nicknameClient = null;
            AsyncLogger.log(Level.INFO, "[CLIENT RMI] Connection with the server has been closed.");
        } catch (Exception e) {
            AsyncLogger.log(Level.SEVERE, "[CLIENT RMI] Error closing connection with server: " + e.getMessage());
        }
    }


    /**
     * Connects to the game server.
     * @throws RemoteException   If an error occurs in remote communication.
     * @throws NotBoundException If a name in the registry was not found.
     */
    private void connectToGameServer() throws RemoteException, NotBoundException {
        this.rmiRegistry = LocateRegistry.getRegistry(this.ip, this.port);
        this.mainController = (MainControllerInterface) this.rmiRegistry.lookup("RMIServer");
    }


    /**
     * The client can create a new game.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws NotBoundException If a name in the registry was not found.
     */
    @Override
    public void createGame(String nickname) throws RemoteException, NotBoundException {
        connectToGameServer();
        this.nicknameClient = nickname;
        this.gameController = this.mainController.createGame(this.gameListener, this.nicknameClient);
    }


    /**
     * The client can join the first available game.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws NotBoundException If a name in the registry was not found.
     */
    @Override
    public void joinFirstAvailableGame(String nickname) throws RemoteException, NotBoundException {
        connectToGameServer();
        this.nicknameClient = nickname;
        this.gameController = this.mainController.joinFirstAvailableGame(this.gameListener, nickname);
    }


    /**
     * The client can participate in a specific game.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws NotBoundException If a name in the registry was not found.
     */
    @Override
    public void joinSpecificGame(String nickname, int idGame) throws RemoteException, NotBoundException {
        connectToGameServer();
        this.nicknameClient = nickname;
        this.gameController = this.mainController.joinSpecificGame(this.gameListener, nickname, idGame);
    }


    /**
     * The client can reconnect to an ongoing game.
     * @param nickname The nickname of the client.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws NotBoundException If a name in the registry was not found.
     */
    @Override
    public void reconnectToGame(String nickname) throws RemoteException, NotBoundException {
        connectToGameServer();
        this.nicknameClient = nickname;
        this.gameController = this.mainController.reconnectToGame(this.gameListener, nickname);
    }


    /**
     * The client can place the Starter card in the Codex.
     * @param player The player representing the client.
     * @param side The side of the Starter card to be placed into the Codex.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    @Override
    public void placeStarterOnCodex(Player player, Side side) throws RemoteException, Exception {
        this.gameController.placeStarterOnCodex(player, side);
    }


    /**
     * The client can place a card in the Codex.
     * @param player The player representing the client.
     * @param index The index of the card in the player's hand to be placed.
     * @param frontCard A boolean indicating whether to place the front (true) or back (false) side of the card.
     * @param row The row in the Codex where the card is to be placed.
     * @param col The column in the Codex where the card is to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    @Override
    public void placeCardOnCodex(Player player, int index, boolean frontCard, int row, int col) throws RemoteException, Exception {
        this.gameController.placeCardOnCodex(player, index, frontCard, row, col);
    }


    /**
     * The client can select his personal Objective card.
     * @param player The player representing the client.
     * @param cardObjective The index of the card in the player's list of Objective cards that the player wishes to select.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    @Override
    public void selectCardObjective(Player player, int cardObjective) throws RemoteException, Exception {
        this.gameController.selectCardObjective(player, cardObjective);
    }


    /**
     * The client can draw a card from the deck of cards.
     * @param player The player representing the client.
     * @param deck The deck from which the card is drawn.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    @Override
    public void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws RemoteException, Exception {
        this.gameController.drawCardFromDeck(player, deck);
    }


    /**
     * The client can draw a card from the visible cards.
     * @param player The player representing the client.
     * @param deck The visible deck from which the card is drawn.
     * @param index The index of the card in the displayed deck that the player wishes to draw.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    @Override
    public void drawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) throws RemoteException, Exception {
        this.gameController.drawCardDisplayed(player, deck, index);
    }


    /**
     * The client can send a message in chat.
     * @param chatMessage The message for the chat.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void sendChatMessage(ChatMessage chatMessage) throws RemoteException {
        this.gameController.sendChatMessage(chatMessage);
    }


    /**
     * The client can choose the number of players participating in the game.
     * @param size The number of players participating in the game.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    @Override
    public void gameSizeUpdated(int size) throws RemoteException, Exception {
        this.gameController.updateGameSize(size);
    }

    @Override
    public void sendPing(String player) {
        try {
            if (this.gameController != null) {
                this.gameController.ping(player);
            }
        } catch (RemoteException e) {
            System.err.println("Error pinging server: " + e.getMessage());
            stopPingThread();
        }
    }

    public void stopPingThread() {
        pingExecutor.shutdown();
    }


}
