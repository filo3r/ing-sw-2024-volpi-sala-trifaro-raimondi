package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;

import java.rmi.RemoteException;


/**
 * This class is used to send a message from the client to the server to indicate the intent to change the game size.
 */
public class SocketClientMessageSetGameSize extends SocketClientGenericMessage {

    /**
     * The size of the game.
     */
    private int size;

    /**
     * The game id.
     */
    private int idGame;


    /**
     * Constructs a new message that requires the change of the size of the game.
     * This message is flagged to be processed by the game controller of the application.
     * @param size The size of the game.
     */
    public SocketClientMessageSetGameSize(int size) {
        this.messageType = MessageType.GAME_CONTROLLER;
        this.size = size;
        this.idGame = idGame;
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
        gameController.updateGameSize(this.size);
    }


}
