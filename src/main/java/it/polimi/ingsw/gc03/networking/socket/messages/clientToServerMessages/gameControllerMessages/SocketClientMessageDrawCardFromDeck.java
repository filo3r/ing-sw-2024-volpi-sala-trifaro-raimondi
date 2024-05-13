package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This class is used to send a message from the client to the server to indicate the intent to draw a card from a
 * deck of cards.
 */
public class SocketClientMessageDrawCardFromDeck extends SocketClientGenericMessage {

    /**
     * The player who is drawing from the deck.
     */
    private Player player;

    /**
     * The deck from which the card is drawn.
     */
    private ArrayList<Card> deck;


    /**
     * Constructs a new message that requires drawing a card from the deck of cards.
     * This message is flagged to be processed by the game controller of the application.
     * @param player The player representing the client.
     * @param deck The deck from which the card is drawn.
     */
    public SocketClientMessageDrawCardFromDeck(Player player, ArrayList<? extends Card> deck) {
        this.nicknameClient = player.getNickname();
        this.messageType = MessageType.GAME_CONTROLLER;
        this.player = player;
        this.deck = new ArrayList<>();
        this.deck.addAll(deck);
    }


    /**
     * Executes the message.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller.
     * @return The game controller.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameController execute(GameListener gameListener, MainController mainController) throws RemoteException {
        return null;
    }


    /**
     * Executes the message.
     * @param gameController The game controller.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an exception occurs.
     */
    @Override
    public void execute(GameController gameController) throws RemoteException, Exception {
        gameController.drawCardFromDeck(this.player, this.deck);
    }


}
