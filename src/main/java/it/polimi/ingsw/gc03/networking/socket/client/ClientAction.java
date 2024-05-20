package it.polimi.ingsw.gc03.networking.socket.client;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.side.Side;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This class represents the actions that a client can perform within the game.
 */
public interface ClientAction {

    /**
     * The client can create a new game.
     * @param nickname The nickname of the client.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws NotBoundException If a name in the registry was not found.
     */
    void createGame(String nickname) throws IOException, InterruptedException, NotBoundException;


    /**
     * The client can join the first available game.
     * @param nickname The nickname of the client.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws NotBoundException If a name in the registry was not found.
     */
    void joinFirstAvailableGame(String nickname) throws IOException, InterruptedException, NotBoundException;


    /**
     * The client can participate in a specific game.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws NotBoundException If a name in the registry was not found.
     */
    void joinSpecificGame(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException;


    /**
     * The client can leave a game in progress.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws NotBoundException If a name in the registry was not found.
     */
    void playerLeft(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException;


    /**
     * The client can reconnect to an ongoing game.
     * @param nickname The nickname of the client.
     * @param idGame The id of the game.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws NotBoundException If a name in the registry was not found.
     */
    void reconnectToGame(String nickname, int idGame) throws IOException, InterruptedException, NotBoundException;


    /**
     * The client can place the Starter card in the Codex.
     * @param player The player representing the client.
     * @param side The side of the Starter card to be placed into the Codex.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void placeStarterOnCodex(Player player, Side side) throws IOException, InterruptedException, Exception;


    /**
     * The client can place a card in the Codex.
     * @param player The player representing the client.
     * @param index The index of the card in the player's hand to be placed.
     * @param frontCard A boolean indicating whether to place the front (true) or back (false) side of the card.
     * @param row The row in the Codex where the card is to be placed.
     * @param col The column in the Codex where the card is to be placed.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void placeCardOnCodex(Player player, int index, boolean frontCard, int row, int col) throws IOException, InterruptedException, Exception;


    /**
     * The client can select his personal Objective card.
     * @param player The player representing the client.
     * @param cardObjective The index of the card in the player's list of Objective cards that the player wishes to select.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void selectCardObjective(Player player, int cardObjective) throws IOException, InterruptedException, Exception;


    /**
     * The client can draw a card from the deck of cards.
     * @param player The player representing the client.
     * @param deck The deck from which the card is drawn.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void drawCardFromDeck(Player player, ArrayList<? extends Card> deck) throws IOException, InterruptedException, Exception;


    /**
     * The client can draw a card from the visible cards.
     * @param player The player representing the client.
     * @param deck The visible deck from which the card is drawn.
     * @param index The index of the card in the displayed deck that the player wishes to draw.
     * @throws IOException If an input or output exception occurs during action processing.
     * @throws InterruptedException If the thread is interrupted, either before or during the action.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void drawCardDisplayed(Player player, ArrayList<? extends Card> deck, int index) throws IOException, InterruptedException, Exception;


    /**
     * The client can send a message in chat.
     * @param chatMessage The message for the chat.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void sendChatMessage(ChatMessage chatMessage) throws RemoteException;


    /**
     * The client sends a ping message.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void ping() throws RemoteException;


    /**
     * The client can choose the number of players participating in the game.
     * @param nickname The nickname of the client.
     * @param size The number of players participating in the game.
     * @param idGame The id of the game.
     * @throws RemoteException If an error occurs in remote communication.
     * @throws Exception If an abnormal condition has occurred during the execution of the action.
     */
    void setGameSize(String nickname, int size, int idGame) throws RemoteException, Exception;


}
