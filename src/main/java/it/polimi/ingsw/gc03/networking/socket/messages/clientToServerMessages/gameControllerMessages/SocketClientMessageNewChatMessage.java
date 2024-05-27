package it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.gameControllerMessages;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.controller.MainController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import it.polimi.ingsw.gc03.networking.rmi.MainControllerInterface;
import it.polimi.ingsw.gc03.networking.socket.messages.MessageType;
import it.polimi.ingsw.gc03.networking.socket.messages.clientToServerMessages.SocketClientGenericMessage;
import java.rmi.RemoteException;


/**
 * This class is used to send a message from the client to the server to indicate the intention to send a
 * new chat message.
 */
public class SocketClientMessageNewChatMessage extends SocketClientGenericMessage {

    /**
     * The message for the chat.
     */
    private ChatMessage chatMessage;


    /**
     * Constructs a new message that prompts to send a message in the chat.
     * This message is flagged to be processed by the game controller of the application.
     * @param chatMessage The message for the chat.
     */
    public SocketClientMessageNewChatMessage(ChatMessage chatMessage) {
        this.nicknameClient = chatMessage.getSender();
        this.messageType = MessageType.GAME_CONTROLLER;
        this.chatMessage = chatMessage;
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
        gameController.sendChatMessage(this.chatMessage);
    }


}
