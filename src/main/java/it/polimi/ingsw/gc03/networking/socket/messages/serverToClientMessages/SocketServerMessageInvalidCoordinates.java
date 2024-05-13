package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameModel;
import java.io.IOException;


/**
 * This class is used to send a message from the server to the client to inform that the coordinates where he wants to
 * insert the card are not valid.
 */
public class SocketServerMessageInvalidCoordinates extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private GameModel gameModel;

    /**
     * The row where the card wanted to be placed.
     */
    private int row;

    /**
     * The column where the card wanted to be placed.
     */
    private int column;


    /**
     * Constructor of the class that creates the message.
     * @param gameModel The immutable game model.
     * @param row The row where the card wanted to be placed.
     * @param column TThe column where the card wanted to be placed.
     */
    public SocketServerMessageInvalidCoordinates(GameModel gameModel, int row, int column) {
        this.gameModel = gameModel;
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
        gameListener.invalidCoordinates(this.gameModel, this.row, this.column);
    }


}
