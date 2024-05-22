package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import java.io.IOException;


/**
 * This class is used to send a message from the server to the client to inform that the points obtained with
 * the Objective cards have been calculated.
 */
public class SocketServerMessageGameSizeUpdated extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private GameImmutable gameImmutable;

    /**
     * The points obtained with Objective cards.
     */
    private int size;


    /**
     * Constructor of the class that creates the message.
     * @param gameImmutable The immutable game model.
     * @param gameSize The new Game size.
     */
    public SocketServerMessageGameSizeUpdated(GameImmutable gameImmutable, int gameSize) {
        this.gameImmutable = gameImmutable;
        this.size = gameSize;
    }


    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.gameSizeUpdated(this.gameImmutable, this.size);
    }
}
