package it.polimi.ingsw.gc03.networking.socket.client;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.networking.AsyncLogger;
import it.polimi.ingsw.gc03.networking.Ping;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages.*;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.mainControllerMessages.*;
import it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages.SocketServerGenericMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * The ClientSocket class handles all network communications between the client and the server using sockets.
 * This class uses an ExecutorService to manage threads for asynchronous message processing.
 */
public class SocketClient implements ClientAction {

    /**
     * The socket connection to the server.
     */
    private Socket socketClient;

    /**
     * The nickname associated with the client socket.
     */
    private String nicknameClient;

    /**
     * Handler for processing messages received from the server.
     */
    private final GameListenerHandlerClient messageActionHandler;

    /**
     * The input stream to receive data form the server.
     */
    private ObjectInputStream inputStream;

    /**
     * The output stream to send data to the server.
     */
    private ObjectOutputStream outputStream;

    /**
     * Helper object to send periodic ping messages to the server to keep the connection alive.
     */
    private final Ping socketPing;

    /**
     * Executor service to manage thread for handling incoming messages asynchronously.
     */
    private final ExecutorService executorService;

    private final ScheduledExecutorService pingExecutor;
    private static final long PING_INTERVAL = 2; // Ping interval in seconds


    /**
     * Constructor for SocketClient.
     * @param ip The IP address of the server.
     * @param port The port number on which the server is listening.
     */
    public SocketClient(String ip, int port) {
        this.messageActionHandler = new GameListenerHandlerClient();
        this.executorService = Executors.newSingleThreadExecutor();
        this.pingExecutor = Executors.newSingleThreadScheduledExecutor();
        startConnection(ip, port);
    }


    /**
     * Starts the connection with the server using a specified IP address and port.
     * @param ip The IP address of the server.
     * @param port The port number on which the server is listening.
     */
    public void startConnection(String ip, int port) {
        try {
            this.socketClient = new Socket(ip, port);
            this.inputStream = new ObjectInputStream(this.socketClient.getInputStream());
            this.outputStream = new ObjectOutputStream(this.socketClient.getOutputStream());
            AsyncLogger.log(Level.INFO, "[CLIENT SOCKET] Connection established to server.");
            this.executorService.submit(this::processMessages);
            this.pingExecutor.scheduleAtFixedRate(this::ping, 0, PING_INTERVAL, TimeUnit.SECONDS);
        } catch (IOException e) {
            AsyncLogger.log(Level.SEVERE, "[CLIENT SOCKET] Failed to connect to server: " + e.getMessage());
            shutdownAndExit();
        }
    }


    /**
     * Continuously processes messages received from the server.
     * This method runs in a separate thread managed by ExecutorService.
     */
    private void processMessages() throws InterruptedException {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                SocketServerGenericMessage message = (SocketServerGenericMessage) this.inputStream.readObject();
                message.execute(this.messageActionHandler);
            }
        } catch (IOException | ClassNotFoundException e) {
            AsyncLogger.log(Level.SEVERE, "[CLIENT SOCKET] Error while processing messages: " + e.getMessage());
            shutdownAndExit();
        }
    }


    /**
     * Stops the connection and releases all resources.
     * This method also attempts to cleanly shutdown the ExecutorService.
     */
    public void stopConnection() {
        try {
            this.pingExecutor.shutdownNow();
            this.inputStream.close();
            this.outputStream.close();
            this.socketClient.close();
            this.executorService.shutdown();
            if (!this.executorService.awaitTermination(60, TimeUnit.SECONDS))
                this.executorService.shutdownNow();
            AsyncLogger.log(Level.INFO, "[CLIENT SOCKET] Connection with the server has been closed.");
        } catch (IOException | InterruptedException e) {
            AsyncLogger.log(Level.SEVERE, "[CLIENT SOCKET] Error when closing resources: " + e.getMessage());
        }
    }


    /**
     * Shutdown the client socket and exits the program. This method is called upon severe errors.
     */
    private void shutdownAndExit() {
        stopConnection();
        System.exit(-1);
    }


    /**
     * Flushes and resets the ObjectOutputStream. This ensures that all buffered data is sent
     * and the buffer is cleared, preparing the stream for future use.
     * @throws IOException If an error occurs during the flush or reset operations.
     */
    private void completeTransmission() throws IOException {
        // Force all data to be transmitted to the destination
        this.outputStream.flush();
        // Clean the stream to prepare it for the next transmission
        this.outputStream.reset();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to create a game.
     * @param nickname The nickname of the client.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void createGame(String nickname) throws IOException {
        this.nicknameClient = nickname;
        SocketClientMessageCreateGame message = new SocketClientMessageCreateGame(nickname);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to join the first available game.
     * @param nickname The nickname of the client.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void joinFirstAvailableGame(String nickname) throws IOException {
        this.nicknameClient = nickname;
        SocketClientMessageJoinFirstGame message = new SocketClientMessageJoinFirstGame(nickname);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to participate in a specific game.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void joinSpecificGame(String nickname, int idGame) throws IOException {
        this.nicknameClient = nickname;
        SocketClientMessageJoinSpecificGame message = new SocketClientMessageJoinSpecificGame(nickname, idGame);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to leave a game in progress.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void leaveGame(String nickname, int idGame) throws IOException {
        this.nicknameClient = nickname;
        SocketClientMessageLeaveGame message = new SocketClientMessageLeaveGame(nickname, idGame);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to reconnect to an ongoing game.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void reconnectToGame(String nickname, int idGame) throws IOException {
        this.nicknameClient = nickname;
        SocketClientMessageReconnectToGame message = new SocketClientMessageReconnectToGame(nickname, idGame);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to place the Starter card in the Codex.
     * @param player The player representing the client.
     * @param side The side of the Starter card to be placed into the Codex.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void placeStarterOnCodex(Player player, Side side) throws IOException {
        SocketClientMessagePlaceStarterOnCodex message = new SocketClientMessagePlaceStarterOnCodex(player, side);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to place a card in the Codex.
     * @param player The player representing the client.
     * @param index The index of the card in the player's hand to be placed.
     * @param frontCard A boolean indicating whether to place the front (true) or back (false) side of the card.
     * @param row The row in the Codex where the card is to be placed.
     * @param col The column in the Codex where the card is to be placed.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void placeCardOnCodex(Player player, int index, boolean frontCard, int row, int col) throws IOException {
        SocketClientMessagePlaceCardOnCodex message = new SocketClientMessagePlaceCardOnCodex(player, index, frontCard, row, col);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to select his personal Objective card.
     * @param player The player representing the client.
     * @param cardObjective The index of the card in the player's list of Objective cards that the player wishes to select.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void selectCardObjective(Player player, int cardObjective) throws IOException {
        SocketClientMessageSelectCardObjective message = new SocketClientMessageSelectCardObjective(player, cardObjective);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to draw a card from the deck of cards.
     * @param player The player representing the client.
     * @param deck The deck from which the card is drawn.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws IOException {
        SocketClientMessageDrawCardFromDeck message = new SocketClientMessageDrawCardFromDeck(player, deck);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to draw a card from the visible cards.
     * @param player The player representing the client.
     * @param deck The visible deck from which the card is drawn.
     * @param index The index of the card in the displayed deck that the player wishes to draw.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void drawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) throws IOException {
        SocketClientMessageDrawCardDisplayed message = new SocketClientMessageDrawCardDisplayed(player, deck, index);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


    /**
     * This method is used to write on the output stream the message that the client wants to send a message in chat.
     * @param chatMessage The message for the chat.
     */
    @Override
    public void sendChatMessage(ChatMessage chatMessage) {
        try {
            SocketClientMessageNewChatMessage message = new SocketClientMessageNewChatMessage(chatMessage);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * This method is used to write the message that the client sends as a ping to the output stream.
     */
    @Override
    public void ping() {
        if (this.outputStream != null) {
            try {
                SocketClientMessagePing message = new SocketClientMessagePing(this.nicknameClient);
                this.outputStream.writeObject(message);
                completeTransmission();
            } catch (IOException e) {
                AsyncLogger.log(Level.SEVERE, "[CLIENT SOCKET] Connection to server lost.");
                // STILL HAVE TO HANDLE THE DISCONNECTION FROM THE SERVER, BY NOTIFYING THE GUI AND TUI.
            }
        }
    }


    /**
     * This method is used to write on the output stream the message that the client changed the game size.
     * @param nickname The nickname of the client.
     * @param size The number of players participating in the game.
     * @param idGame The id of the game.
     * @throws IOException If an input or output exception occurs during action processing.
     */
    @Override
    public void setGameSize(String nickname, int size, int idGame) throws IOException {
        SocketClientMessageSetGameSize message = new SocketClientMessageSetGameSize(nickname, size, idGame);
        this.outputStream.writeObject(message);
        completeTransmission();
    }


}
