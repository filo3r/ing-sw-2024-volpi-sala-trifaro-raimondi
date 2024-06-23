package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;

import java.io.IOException;

public class SocketServerMessageNoGameToReconnect extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The player nickname.
     */
    private String nickname;

    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     */
    public SocketServerMessageNoGameToReconnect(Model model, String nickname) {
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
        gameListener.noGameToReconnect(this.model, this.nickname);
    }
}
