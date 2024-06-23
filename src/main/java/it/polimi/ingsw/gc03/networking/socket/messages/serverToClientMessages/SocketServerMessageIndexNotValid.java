package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that the index is invalid.
 */
public class SocketServerMessageIndexNotValid extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The index.
     */
    private int index;

    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     * @param index The index;
     */
    public SocketServerMessageIndexNotValid(Model model, int index) {
        this.model = model;
        this.index = index;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.indexNotValid(this.model, this.index);
    }
}
