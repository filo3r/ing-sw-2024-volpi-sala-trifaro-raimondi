package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that the Objective card was chosen correctly.
 */
public class SocketServerMessageObjectiveCardChosen extends SocketServerGenericMessage {

    /**
     * The immutable game gameImmutable.
     */
    private GameImmutable gameImmutable;

    /**
     * The chosen Objective card.
     */
    private CardObjective cardObjective;

    /**
     * The nickname of the player who chose the card objective.
     */
    private String nickname;

    /**
     * Constructor of the class that creates the message.
     * @param gameImmutable The immutable game gameImmutable.
     * @param cardObjective The chosen Objective card.
     * @param nickname The nickname of the player.
     */
    public SocketServerMessageObjectiveCardChosen(GameImmutable gameImmutable, CardObjective cardObjective, String nickname) {
        this.gameImmutable = gameImmutable;
        this.cardObjective = cardObjective;
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
        gameListener.objectiveCardChosen(this.gameImmutable, this.cardObjective, this.nickname);
    }

}