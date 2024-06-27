package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.mainControllerMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;

/**
 * This class is used to send a message from the client to the server to indicate the intention to reconnect to a game.
 */
public class SocketClientMessageReconnectToGame extends SocketClientGenericMessage {

    /**
     * The id of the game the client wants to reconnect to.
     */
    protected int idGame;

    /**
     * Constructs a new game reconnect request message with the specified client nickname and game id.
     * This message is flagged to be processed by the main controller of the application.
     * @param nicknameClient The nickname of the client who is reconnecting to the game.
     */
    public SocketClientMessageReconnectToGame(String nicknameClient) {
        this.nicknameClient = nicknameClient;
        this.messageType = MessageType.MAIN_CONTROLLER;
        this.idGame = idGame;
    }

    /**
     * Executes the reconnect request message to a game, using the provided game listener, client nickname, and game id.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller that manages game reconnection.
     * @return A game controller to interact.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameControllerInterface execute(GameListener gameListener, MainControllerInterface mainController) throws RemoteException {
        return mainController.reconnectToGame(gameListener, this.nicknameClient);
    }

    /**
     * Executes the reconnect request message to a game, using the provided game listener, client nickname, and game id.
     * @param gameController The controller of the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {}

}