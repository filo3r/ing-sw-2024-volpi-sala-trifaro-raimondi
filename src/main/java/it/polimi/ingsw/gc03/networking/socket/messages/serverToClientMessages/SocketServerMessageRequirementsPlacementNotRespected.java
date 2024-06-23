package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to send a message from the server to the client to inform that the positioning requirements
 * are not respected.
 */
public class SocketServerMessageRequirementsPlacementNotRespected extends SocketServerGenericMessage {

    /**
     * The immutable game gameImmutable.
     */
    private GameImmutable gameImmutable;

    /**
     * The requirements for card placement.
     */
    private ArrayList<Value> requirementsPlacement;

    /**
     * Constructor of the class that creates the message.
     * @param gameImmutable The immutable game gameImmutable.
     * @param requirementsPlacement The requirements for card placement.
     */
    public SocketServerMessageRequirementsPlacementNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) {
        this.gameImmutable = gameImmutable;
        this.requirementsPlacement = requirementsPlacement;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.requirementsPlacementNotRespected(this.gameImmutable, this.requirementsPlacement);
    }
}
