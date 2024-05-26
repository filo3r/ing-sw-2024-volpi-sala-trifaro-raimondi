package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This class is used to send a message from the client to the server to indicate the intent to draw a card from the
 * visible cards.
 */
public class SocketClientMessageDrawCardDisplayed extends SocketClientGenericMessage {

    /**
     * The player who is drawing from the deck.
     */
    private Player player;

    /**
     * The deck from which the card is drawn.
     */
    private ArrayList<Card> deck;

    /**
     * The index of the card in the displayed deck that the player wishes to draw.
     */
    private int index;


    /**
     * Constructs a new message that requires drawing a card from the visible cards.
     * This message is flagged to be processed by the game controller of the application.
     * @param player The player representing the client.
     * @param deck The visible deck from which the card is drawn.
     * @param index The index of the card in the displayed deck that the player wishes to draw.
     */
    public SocketClientMessageDrawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) {
        this.nicknameClient = player.getNickname();
        this.messageType = MessageType.GAME_CONTROLLER;
        this.player = player;
        this.deck = new ArrayList<>();
        this.deck.addAll(deck);
        this.index = index;
    }


    /**
     * Executes the message.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller.
     * @return The game controller.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameController execute(GameListener gameListener, MainControllerInterface mainController) throws RemoteException {
        return null;
    }


    /**
     * Executes the message.
     * @param gameController The game controller.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an exception occurs.
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException, Exception {
        gameController.drawCardDisplayed(this.player, this.deck, this.index);
    }


}
