package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.mainControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;


/**
 * This class represents a message sent from the client to the server to request joining a specific game.
 */
public class SocketClientMessageJoinSpecificGame extends SocketClientGenericMessage {

    /**
     * The id of the game the client wants to join.
     */
    protected int idGame;


    /**
     * Constructs a new message requesting to join a specific game with the specified client nickname and game id.
     * This message is flagged to be processed by the main controller of the application.
     * @param nicknameClient The nickname of the client requesting participation at the specific game.
     * @param idGame The id of the game the client wants to join.
     */
    public SocketClientMessageJoinSpecificGame(String nicknameClient, int idGame) {
        this.nicknameClient = nicknameClient;
        this.messageType = MessageType.MAIN_CONTROLLER;
        this.idGame = idGame;
    }


    /**
     * Executes the request message to join the specific game, using the provided game listener,
     * the client nickname and the game id.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller that manages participation in the specific game.
     * @return A game controller to interact with the joined game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameController execute(GameListener gameListener, MainController mainController) throws RemoteException {
        return mainController.joinSpecificGame(GameListener gamelistener, this.nicknameClient, this.idGame);
    }


    /**
     * Executes the request message to join the specific game, using the provided game listener,
     * the client nickname and the game id.
     * @param gameController The controller of the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void execute(GameController gameController) throws RemoteException {}


}
