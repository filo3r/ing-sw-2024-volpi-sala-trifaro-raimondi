package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;
import it.polimi.ingsw.gc03.model.Player;
import java.io.IOException;

/**
 * This class is used to send a message from the server to the client to inform that the points have been added.
 */
public class SocketServerMessageAddedPoint extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The player that has added the points.
     */
    private Player player;

    /**
     * The number of points that have been added.
     */
    private int point;

    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     */
    public SocketServerMessageAddedPoint(Model model, Player player, int point) {
        this.model = model;
        this.player = player;
        this.point = point;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.addedPoint(this.model, this.player, this.point);
    }
}
