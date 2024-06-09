package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import java.io.Serializable;
import java.rmi.RemoteException;

/**
 * Abstract class representing a generic message sent from the client to the server.
 * This class serves as a base for different types of messages that can be sent, each with specific actions when executed.
 */
public abstract class SocketClientGenericMessage implements Serializable {

    /**
     * The nickname associated with the client socket.
     */
    protected String nicknameClient;

    /**
     * The types of messages.
     */
    protected MessageType messageType;

    /**
     * Executes the specified actions associated with the message within the context of the main controller.
     * @param gameListener The game listener to interact with.
     * @param mainController The main controller for managing game sessions.
     * @return The game controller.
     * @throws RemoteException If a remote exception occurs.
     */
    public abstract GameControllerInterface execute(GameListener gameListener, MainControllerInterface mainController) throws RemoteException;

    /**
     * Executes the specified actions associated with the message within the context of game control.
     * @param gameController The game controller to interact with.
     * @throws RemoteException If a remote exception occurs.
     * @throws Exception If an exception occurs.
     */
    public abstract void execute(GameControllerInterface gameController) throws RemoteException, Exception;

    /**
     * Get the client nickname associated with the message.
     * @return The nickname of the client sending the message.
     */
    public String getNicknameClient() {
        return this.nicknameClient;
    }

    /**
     * Set the client nickname associated with the message.
     * @param nicknameClient The nickname of the client sending the message.
     */
    public void setNicknameClient(String nicknameClient) {
        this.nicknameClient = nicknameClient;
    }

    /**
     * Get the type of the message.
     * @return The message type.
     */
    public MessageType getMessageType() {
        return this.messageType;
    }

    /**
     * Set the type of the message.
     * @param messageType The type of the message to set.
     */
    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }
}
