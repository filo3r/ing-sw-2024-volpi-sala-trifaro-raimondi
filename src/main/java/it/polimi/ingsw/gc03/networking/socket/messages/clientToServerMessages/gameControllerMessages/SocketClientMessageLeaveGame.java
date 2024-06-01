package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages.SocketServerGenericMessage;

import java.io.IOException;
import java.rmi.RemoteException;

public class SocketClientMessageLeaveGame extends SocketClientGenericMessage {

    /**
     * The nickname of the player who disconnected.
     */
    private String nickname;



    /**
     * Constructs a new message that requires the change of the size of the game.
     * This message is flagged to be processed by the game controller of the application.
     * @param nickname The nickname of the player who disconnected.
     */
    public SocketClientMessageLeaveGame(String nickname) {
        this.messageType = MessageType.GAME_CONTROLLER;
        this.nickname = nickname;
    }


    /**
     * Executes the message.
     * @param gameListener The game listener to be notified about game events.
     * @param mainController The main controller.
     * @return The game controller.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public GameControllerInterface execute(GameListener gameListener, MainControllerInterface mainController) throws RemoteException {
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
        gameController.leaveGame(this.nickname);
    }
}

