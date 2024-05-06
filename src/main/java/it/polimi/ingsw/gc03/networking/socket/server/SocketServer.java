package it.polimi.ingsw.gc03.networking.socket.server;

import it.polimi.ingsw.gc03.networking.AsyncLogger;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;


/**
 * The SocketServer class handles incoming network request and spawns a ClientHandler to manage each client connection.
 * It communicates using the Socket Network protocol.
 */
public class SocketServer extends Thread {

    /**
     * The main server socket for listening to client connections.
     */
    private ServerSocket socketServer;

    /**
     * A thread pool for handling client handlers.
     */
    private final ExecutorService clientHandlerPool = Executors.newCachedThreadPool();


    /**
     * Initializes and starts the server socket on the specified port.
     * @param port The port on which the server should listen.
     * @throws IOException if an I/O error occurs while opening the socket.
     */
    public void startSocketServer(int port) throws IOException {
        try {
            this.socketServer = new ServerSocket(port);
            AsyncLogger.log(Level.INFO, "[SERVER SOCKET] Server socket initialized and listening for connections.");
            this.start();
        } catch (IOException e) {
            AsyncLogger.log(Level.SEVERE, "[SERVER SOCKET] Error initializing server socket: " + e.getMessage());
        }
    }


    /**
     * Closes the server socket.
     */
    private void closeSocketServer() {
        try {
            if (!this.socketServer.isClosed() && this.socketServer != null)
                this.socketServer.close();
        } catch (IOException e) {
            AsyncLogger.log(Level.SEVERE, "[SERVER SOCKET] Error closing server socket: " + e.getMessage());
        }
    }


    /**
     * Shuts down the client handler thread pool.
     */
    private void shutdownClientHandlerPool() {
        this.clientHandlerPool.shutdown();
        try {
            if (!this.clientHandlerPool.awaitTermination(60, TimeUnit.SECONDS))
                this.clientHandlerPool.shutdownNow();
        } catch (InterruptedException e) {
            this.clientHandlerPool.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }


    /**
     * Stops the server socket and terminates the thread pool.
     */
    public void stopSocketConnection() {
        interrupt();
        closeSocketServer();
        shutdownClientHandlerPool();
        AsyncLogger.log(Level.INFO, "[SERVER SOCKET] Server socket closed and connections dropped.");
    }


    /**
     * Listens for incoming connections and spawns ClientHandler instances to manage them.
     */
    @Override
    public void run() {
        try {
            while (!isInterrupted()) {
                // Accept a new client connection
                Socket socketClient = this.socketServer.accept();
                // Submit the connection to a new client handler
                clientHandlerPool.submit(new ClientHandler(socketClient));
                AsyncLogger.log(Level.INFO, "[SERVER SOCKET] New connection accepted on server socket.");
            }
        } catch (IOException e) {
            // Handle errors in accepting client connections unless the thread has been interrupted
            if (!isInterrupted())
                AsyncLogger.log(Level.SEVERE, "[SERVER SOCKET] Error accepting client connection on server socket: " + e.getMessage());
        } finally {
            // Ensure the server socket is closed
            closeSocketServer();
            // Ensure the client handler pool is shut down
            shutdownClientHandlerPool();
        }
    }
}
