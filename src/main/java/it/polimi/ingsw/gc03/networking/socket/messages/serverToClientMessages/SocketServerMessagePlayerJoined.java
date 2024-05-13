package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameModel;
import java.io.IOException;


/**
 * This class is used to send a message from the server to the client to inform that a player has joined the game.
 */
public class SocketServerMessagePlayerJoined extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private GameModel gameModel;


    /**
     * Constructor of the class that creates the message.
     * @param gameModel The immutable game model.
     */
    public SocketServerMessagePlayerJoined(GameModel gameModel) {
        this.gameModel = gameModel;
    }


    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.playerJoined(this.gameModel);
    }


}
