package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.mainControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;


/**
 * This class is used to send a message from the client to the server to indicate the intention to create a new game.
 */
public class SocketClientMessageCreateGame extends SocketClientGenericMessage {

    /**
     * Constructs a new game creation request message with the specified client nickname.
     * This message is flagged to be processed by the main controller of the application.
     * @param nicknameClient The nickname of the client who is initiating the game creation.
     */
    public SocketClientMessageCreateGame(String nicknameClient) {
        this.nicknameClient = nicknameClient;
        this.messageType = MessageType.MAIN_CONTROLLER;
    }


    /**
     * Executes the message by requesting the creation of a new game from the main controller,
     * using the provided game listener and client's nickname.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller that handles the creation of the game.
     * @return A game controller of the newly created game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameController execute(GameListener gameListener, MainController mainController) throws RemoteException {
        return mainController.createGame(gameListener, this.nicknameClient);
    }


    /**
     * Executes the message by requesting the creation of a new game from the main controller,
     * using the provided game listener and client's nickname.
     * @param gameController The controller of the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void execute(GameController gameController) throws RemoteException {}


}
