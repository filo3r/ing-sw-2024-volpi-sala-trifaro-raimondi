package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that the card was placed into the Codex.
 */
public class SocketServerMessagePositionedCardIntoCodex extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The row where the card was placed.
     */
    private int row;

    /**
     * The column where the card was placed.
     */
    private int column;

    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     */
    public SocketServerMessagePositionedCardIntoCodex(Model model, int row, int column) {
        this.model = model;
        this.row = row;
        this.column = column;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.positionedCardIntoCodex(this.model, this.row, this.column);
    }
}
