package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import java.io.IOException;
import java.io.Serializable;

/**
 * Abstract class representing a generic message that is sent from the server to the client.
 * This class serves as a base for all server-to-client communication within the networked application,
 * providing a common structure for handling incoming messages.
 */
public abstract class SocketServerGenericMessage implements Serializable {

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    public abstract void execute(GameListener gameListener) throws IOException, InterruptedException;

}