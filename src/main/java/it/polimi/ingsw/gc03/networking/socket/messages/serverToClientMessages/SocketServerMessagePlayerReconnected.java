package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that a player has reconnected to the game.
 */
public class SocketServerMessagePlayerReconnected extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The nickname of the player that has reconnected.
     */
    private String nickname;

    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     */
    public SocketServerMessagePlayerReconnected(Model model, String nickname) {
        this.model = model;
        this.nickname = nickname;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.playerReconnected(this.model, this.nickname);
    }
}
