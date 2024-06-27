package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.mainControllerMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;

/**
 * This class is used to send a message from the client to the server to request joining the first available game.
 */
public class SocketClientMessageJoinFirstGame extends SocketClientGenericMessage {

    /**
     * Constructs a new message requesting participation in the first available game with the specified client nickname.
     * This message is flagged to be processed by the main controller of the application.
     * @param nicknameClient The nickname of the client who is requesting participation in the first available game.
     */
    public SocketClientMessageJoinFirstGame(String nicknameClient) {
        this.nicknameClient = nicknameClient;
        this.messageType = MessageType.MAIN_CONTROLLER;
    }

    /**
     * Executes the message requesting participation in the first available game,
     * using the provided game listener and client nickname.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller that manages participation in the first available game.
     * @return A game controller to interact with the joined game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameControllerInterface execute(GameListener gameListener, MainControllerInterface mainController) throws RemoteException {
        return mainController.joinFirstAvailableGame(gameListener, this.nicknameClient);
    }

    /**
     * Executes the message requesting participation in the first available game,
     * using the provided game listener and client nickname.
     * @param gameController The controller of the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void execute(GameControllerInterface gameController) throws RemoteException {}

}