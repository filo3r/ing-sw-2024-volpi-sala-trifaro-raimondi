package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameModel;
import java.io.IOException;


/**
 * This class is used to send a message from the server to the client to inform that a message has been sent.
 */
public class SocketServerMessageSentChatMessage extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private GameModel gameModel;

    /**
     * The message that has been sent.
     */
    private ChatMessage chatMessage;


    /**
     * Constructor of the class that creates the message.
     * @param gameModel The immutable game model.
     * @param chatMessage The message that has been sent.
     */
    public SocketServerMessageSentChatMessage(GameModel gameModel, ChatMessage chatMessage) {
        this.gameModel = gameModel;
        this.chatMessage = chatMessage;
    }


    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.sentChatMessage(this.gameModel, this.chatMessage);
    }


}
