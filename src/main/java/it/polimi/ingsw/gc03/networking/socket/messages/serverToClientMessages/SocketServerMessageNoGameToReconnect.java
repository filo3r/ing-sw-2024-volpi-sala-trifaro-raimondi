package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that there is no game to reconnect.
 */
public class SocketServerMessageNoGameToReconnect extends SocketServerGenericMessage {

    /**
     * The immutable game gameImmutable.
     */
    private GameImmutable gameImmutable;

    /**
     * The player nickname.
     */
    private String nickname;

    /**
     * Constructor of the class that creates the message.
     * @param gameImmutable The immutable game gameImmutable.
     * @param nickname The nickname of the player.
     */
    public SocketServerMessageNoGameToReconnect(GameImmutable gameImmutable, String nickname) {
        this.gameImmutable = gameImmutable;
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
        gameListener.noGameToReconnect(this.gameImmutable, this.nickname);
    }

}