package it.polimi.ingsw.gc03.networking.socket.client;

import it.polimi.ingsw.gc03.networking.AsyncLogger;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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


    /**
     * Constructor for SocketClient.
     * @param ip The IP address of the server.
     * @param port The port number on which the server is listening.
     */
    public SocketClient(String ip, int port) {
        this.messageActionHandler = new GameListenerHandlerClient();
        this.socketPing = new Ping;
        this.executorService = Executors.newSingleThreadExecutor();
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
            this.executorService.submit(this::processMessages);
            this.socketPing.start();
        } catch (IOException e) {
            AsyncLogger.log(Level.SEVERE, "[CLIENT SOCKET] Failed to connect to server: " + e.getMessage());
            shutdownAndExit();
        }
    }


    /**
     * Continuously processes messages received from the server.
     * This method runs in a separate thread managed by ExecutorService.
     */
    private void processMessages() {
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
            if (this.socketPing.isAlive())
                this.socketPing.interrupt();
            this.inputStream.close();
            this.outputStream.close();
            this.socketClient.close();
            this.executorService.shutdown();
            if (!this.executorService.awaitTermination(60, TimeUnit.SECONDS))
                this.executorService.shutdownNow();
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


    // IMPLEMENTARE TUTTI I METODI DELLA ClientAction




}
