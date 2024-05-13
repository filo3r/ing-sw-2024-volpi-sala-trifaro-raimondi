package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;


/**
 * This class is used to send a message from the client to the server to indicate the intent to select the personal
 * Objective card.
 */
public class SocketClientMessageSelectCardObjective extends SocketClientGenericMessage {

    /**
     * The player who is selecting the Objective card.
     */
    private Player player;

    /**
     * The index of the card in the player's list of Objective cards that the player wishes to select.
     */
    private int cardObjective;


    /**
     * Constructs a new message that requires the selection of the Objective card.
     * This message is flagged to be processed by the game controller of the application.
     * @param player The player representing the client.
     * @param cardObjective The index of the card in the player's list of Objective cards that the player wishes to select.
     */
    public SocketClientMessageSelectCardObjective(Player player, int cardObjective) {
        this.nicknameClient = player.getNickname();
        this.messageType = MessageType.GAME_CONTROLLER;
        this.player = player;
        this.cardObjective = cardObjective;
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
        gameController.selectCardObjective(this.player, this.cardObjective);
    }


}
