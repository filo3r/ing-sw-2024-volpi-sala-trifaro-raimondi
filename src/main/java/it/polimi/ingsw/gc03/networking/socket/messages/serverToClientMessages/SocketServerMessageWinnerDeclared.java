package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to send a message from the server to the client to inform which players won the game.
 */
public class SocketServerMessageWinnerDeclared extends SocketServerGenericMessage {

    /**
     * The immutable game gameImmutable.
     */
    private GameImmutable gameImmutable;

    /**
     * The nicknames of the players who won.
     */
    private ArrayList<String> nickname;


    /**
     * Constructor of the class that creates the message.
     * @param gameImmutable The immutable game gameImmutable.
     * @param nickname The nicknames of the players who won.
     */
    public SocketServerMessageWinnerDeclared(GameImmutable gameImmutable, ArrayList<String> nickname) {
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
        gameListener.winnerDeclared(this.gameImmutable, this.nickname);
    }
}
