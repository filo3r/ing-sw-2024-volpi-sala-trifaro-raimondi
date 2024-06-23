package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that only one player is connected.
 */
public class SocketServerMessageOnlyOnePlayerConnected extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The number of seconds to wait until the game ends.
     */
    private int timer;

    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     */
    public SocketServerMessageOnlyOnePlayerConnected(Model model, int timer) {
        this.model = model;
        this.timer = timer;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.onlyOnePlayerConnected(this.model, this.timer);
    }
}
