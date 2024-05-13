package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;


/**
 * This class is used to send a message from the client to the server to indicate the intent to place the Starter card
 * into the Codex.
 */
public class SocketClientMessagePlaceStarterOnCodex extends SocketClientGenericMessage {

    /**
     * The player who is placing the Starter card.
     */
    private Player player;

    /**
     * The side of the Starter card to be placed into the Codex.
     */
    private Side side;


    /**
     * Constructs a new message that requires the placement of the Starter card into the Codex.
     * This message is flagged to be processed by the game controller of the application.
     * @param player The player representing the client.
     * @param side The side of the Starter card to be placed into the Codex.
     */
    public SocketClientMessagePlaceStarterOnCodex(Player player, Side side) {
        this.nicknameClient = player.getNickname();
        this.messageType = MessageType.GAME_CONTROLLER;
        this.player = player;
        this.side = side;
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
        gameController.placeStarterOnCodex(this.player, this.side);
    }


}
