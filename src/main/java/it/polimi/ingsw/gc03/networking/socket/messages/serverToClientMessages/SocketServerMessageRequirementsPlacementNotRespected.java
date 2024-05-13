package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameModel;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.io.IOException;
import java.util.ArrayList;


/**
 * This class is used to send a message from the server to the client to inform that the positioning requirements
 * are not respected.
 */
public class SocketServerMessageRequirementsPlacementNotRespected extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private GameModel gameModel;

    /**
     * The requirements for card placement.
     */
    private ArrayList<Value> requirementsPlacement;


    /**
     * Constructor of the class that creates the message.
     * @param gameModel The immutable game model.
     * @param requirementsPlacement The requirements for card placement.
     */
    public SocketServerMessageRequirementsPlacementNotRespected(GameModel gameModel, ArrayList<Value> requirementsPlacement) {
        this.gameModel = gameModel;
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
        gameListener.requirementsPlacementNotRespected(this.gameModel, this.requirementsPlacement);
    }


}
