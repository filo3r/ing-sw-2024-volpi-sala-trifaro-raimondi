package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.card.Card;

import java.io.IOException;

public class SocketServerMessageCanNotPlaceCard extends SocketServerGenericMessage{

    /**
     * The immutable game model.
     */
    private GameImmutable gameImmutable;

    /**
     * The player's nickname.
     */
    private String nickname;

    /**
     * Constructor of the class that creates the message.
     * @param gameImmutable The immutable game model.
     * @param nickname The player's nickname.
     */
    public SocketServerMessageCanNotPlaceCard(GameImmutable gameImmutable, String nickname) {
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
        gameListener.canNotPlaceCard(this.gameImmutable, this.nickname);
    }
}
