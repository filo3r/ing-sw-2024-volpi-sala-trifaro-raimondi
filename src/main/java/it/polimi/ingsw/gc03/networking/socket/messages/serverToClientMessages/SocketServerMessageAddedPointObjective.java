package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that the points obtained with
 * the Objective cards have been calculated.
 */
public class SocketServerMessageAddedPointObjective extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The points obtained with Objective cards.
     */
    private int objectivePoint;

    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     * @param objectivePoint The points obtained with Objective cards.
     */
    public SocketServerMessageAddedPointObjective(Model model, int objectivePoint) {
        this.model = model;
        this.objectivePoint = objectivePoint;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.addedPointObjective(this.model, this.objectivePoint);
    }
}
