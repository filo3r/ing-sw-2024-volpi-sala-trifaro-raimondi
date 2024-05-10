package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.mainControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;

import java.rmi.RemoteException;


/**
 * This class is used to send a message from the client to the server to indicate the intention to leave a game.
 */
public class SocketClientMessageLeaveGame extends SocketClientGenericMessage {

    /**
     * The id of the game the client wants to leave.
     */
    protected int idGame;


    /**
     * Constructs a new game leave request message with the specified client nickname and game id.
     * This message is flagged to be processed by the main controller of the application.
     * @param nicknameClient The nickname of the client who is leaving the game.
     * @param idGame The id of the game the client wants to leave.
     */
    public SocketClientMessageLeaveGame(String nicknameClient, int idGame) {
        this.nicknameClient = nicknameClient;
        this.messageType = MessageType.MAIN_CONTROLLER;
        this.idGame = idGame;
    }


    /**
     * Executes the game's leave request message, using the provided game listener, client nickname and game id.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller that manages game abandonment.
     * @return A game controller to interact.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameController execute(GameListener gameListener, MainController mainController) throws RemoteException {
        return mainController.leaveGame(gameListener, this.nicknameClient, this.idGame);
    }


    /**
     * Executes the game's leave request message, using the provided game listener and client nickname.
     * @param gameController The controller of the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void execute(GameController gameController) throws RemoteException {}


}
