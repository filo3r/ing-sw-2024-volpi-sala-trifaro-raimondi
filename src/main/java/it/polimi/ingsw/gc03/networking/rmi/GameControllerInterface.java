package it.polimi.ingsw.gc03.networking.rmi;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.side.Side;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * The GameControllerInterface defines the contract for controlling the gameplay flow of a match,
 * from start to finish.
 * Implementations of this interface are responsible for ensuring that the game progresses
 * according to the rules and managing the interactions between players and the game state.
 */
public interface GameControllerInterface extends Remote {

    /**
     * This method places the Starter card in the Codex.
     * @param player The player representing the client.
     * @param side The side of the Starter card to be placed into the Codex.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void placeStarterOnCodex(Player player, Side side) throws RemoteException, Exception;


    /**
     * This method places a card in the Codex.
     * @param player The player representing the client.
     * @param index The index of the card in the player's hand to be placed.
     * @param frontCard A boolean indicating whether to place the front (true) or back (false) side of the card.
     * @param row The row in the Codex where the card is to be placed.
     * @param col The column in the Codex where the card is to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void placeCardOnCodex(Player player, int index, boolean frontCard, int row, int col) throws RemoteException, Exception;

    /**
     * This method selects the personal Objective card.
     * @param player The player representing the client.
     * @param cardObjective The index of the card in the player's list of Objective cards that the player wishes to select.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void selectCardObjective(Player player, int cardObjective) throws RemoteException, Exception;

    /**
     * This method draws a card from the deck of cards.
     * @param player The player representing the client.
     * @param deck The deck from which the card is drawn.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws RemoteException, Exception;

    /**
     * This method draws a card from the visible cards.
     * @param player The player representing the client.
     * @param deck The visible deck from which the card is drawn.
     * @param index The index of the card in the displayed deck that the player wishes to draw.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void drawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) throws RemoteException, Exception;

    /**
     * This method sends a message in chat.
     * @param chatMessage The message for the chat.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void sendChatMessage(ChatMessage chatMessage) throws RemoteException;

    /**
     * This method sends a ping message.
     * @param player The player who pinged the server
     * @throws RemoteException If an error occurs in remote communication.
     */
    void ping(String player) throws RemoteException;

    /**
     * This method updates the game's size.
     * @param size The new game size.
     * @throws Exception If the game size is not valid.
     */
    void updateGameSize(int size) throws Exception;

    /**
     * The method handle the player leaving the game
     * @param playerNickname The nickname of the player who left.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void leaveGame(String playerNickname) throws RemoteException;
}
