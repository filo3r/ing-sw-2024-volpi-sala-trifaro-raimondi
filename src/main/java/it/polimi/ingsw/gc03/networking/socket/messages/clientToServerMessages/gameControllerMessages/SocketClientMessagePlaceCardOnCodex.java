package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;


/**
 * This class is used to send a message from the client to the server to indicate the intent to place a card
 * into the Codex.
 */
public class SocketClientMessagePlaceCardOnCodex extends SocketClientGenericMessage {

    /**
     * The player who is placing the Starter card.
     */
    private Player player;

    /**
     * The index of the card in the player's hand to be placed.
     */
    private int index;

    /**
     * A boolean indicating whether to place the front (true) or back (false) side of the card.
     */
    private boolean frontCard;

    /**
     * The row in the Codex where the card is to be placed.
     */
    private int row;

    /**
     * The column in the Codex where the card is to be placed.
     */
    private int col;


    /**
     * Constructs a new message that requires the placement of the card into the Codex.
     * This message is flagged to be processed by the game controller of the application.
     * @param player The player who is placing the card.
     * @param index The index of the card in the player's hand to be placed.
     * @param frontCard A boolean indicating whether to place the front (true) or back (false) side of the card.
     * @param row The row in the Codex where the card is to be placed.
     * @param col The column in the Codex where the card is to be placed.
     */
    public SocketClientMessagePlaceCardOnCodex(Player player, int index, boolean frontCard, int row, int col) {
        this.nicknameClient = player.getNickname();
        this.messageType = MessageType.GAME_CONTROLLER;
        this.player = player;
        this.index = index;
        this.frontCard = frontCard;
        this.row = row;
        this.col = col;
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
        gameController.placeCardOnCodex(this.player, this.index, this.frontCard, this.row, this.col);
    }


}
