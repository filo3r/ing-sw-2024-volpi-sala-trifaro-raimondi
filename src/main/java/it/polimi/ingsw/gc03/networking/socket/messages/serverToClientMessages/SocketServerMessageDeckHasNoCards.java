package it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Model;
import it.polimi.ingsw.gc03.model.card.Card;
import java.io.IOException;
import java.util.ArrayList;

/**
 * This class is used to send a message from the server to the client to inform that a deck has no cards.
 */
public class SocketServerMessageDeckHasNoCards extends SocketServerGenericMessage {

    /**
     * The immutable game model.
     */
    private Model model;

    /**
     * The deck without cards.
     */
    private ArrayList<? extends Card> deck;


    /**
     * Constructor of the class that creates the message.
     * @param model The immutable game model.
     * @param deck The deck without cards.
     */
    public SocketServerMessageDeckHasNoCards(Model model, ArrayList<? extends Card> deck) {
        this.model = model;
        this.deck = deck;
    }

    /**
     * Executes the appropriate action based on the content of the message.
     * @param gameListener The game listener to which this message's actions are directed.
     * @throws IOException If an input or output exception occurs during message processing.
     * @throws InterruptedException If the thread running the method is interrupted.
     */
    @Override
    public void execute(GameListener gameListener) throws IOException, InterruptedException {
        gameListener.deckHasNoCards(this.model, this.deck);
    }
}
