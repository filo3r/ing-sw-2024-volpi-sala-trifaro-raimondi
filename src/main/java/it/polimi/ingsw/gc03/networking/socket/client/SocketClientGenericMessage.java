package it.polimi.ingsw.gc03.networking.socket.client;

import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import java.io.Serializable;


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



}
