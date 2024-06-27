package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Player;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that a player tried to use a nickname
 * that is already in use.
 */
public class SocketServerMessageJoinUnableNicknameAlreadyInUse extends SocketServerGenericMessage {

    /**
     * The player that tried to use the nickname.
     */
    private Player player;

    /**
     * Constructor of the class that creates the message.
     * @param player The player that tried to use the nickname.
     */
    public SocketServerMessageJoinUnableNicknameAlreadyInUse(Player player) {
        this.player = player;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.joinUnableNicknameAlreadyInUse(this.player);
    }

}