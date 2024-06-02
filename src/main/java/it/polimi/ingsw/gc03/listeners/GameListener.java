package it.polimi.ingsw.gc03.listeners;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This interface is used to inform the client about changes in the game.
 */
public interface GameListener extends Remote {

    /**
     * This method is used to inform the client that a player has joined the game.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerJoined(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that a player has left the game.
     * @param gameImmutable The immutable game model.
     * @param nickname The nickname of the player who left the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerLeft(GameImmutable gameImmutable, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that a player tried to join a full game.
     * @param gameImmutable The immutable game model.
     * @param player The player that tried to join.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void joinUnableGameFull(GameImmutable gameImmutable, Player player) throws RemoteException;


    /**
     * This method is used to inform the client that a player has reconnected to the game.
     * @param gameImmutable The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerReconnected(GameImmutable gameImmutable, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that a player has disconnected.
     * @param gameImmutable The immutable game model.
     * @param nickname The nickname of the player that has disconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerDisconnected(GameImmutable gameImmutable, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that only one player is connected.
     * @param gameImmutable The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void onlyOnePlayerConnected(GameImmutable gameImmutable, int timer) throws RemoteException;


    /**
     * This method is used to inform the client that a player tried to use a nickname that is already in use.
     * @param player The player that tried to use the nickname.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void joinUnableNicknameAlreadyInUse(Player player) throws RemoteException;


    /**
     * This method is used to inform the client that the game id doesn't exist.
     * @param gameId The game id.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameIdNotExists(int gameId) throws RemoteException;


    /**
     * This method is used to inform the client that the game has started.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameStarted(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that the game has ended.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameEnded(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that a message has been sent.
     * @param gameImmutable The immutable game model.
     * @param chatMessage The message that has been sent.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void sentChatMessage(GameImmutable gameImmutable, ChatMessage chatMessage) throws RemoteException;


    /**
     * This method is used to inform the client that the next turn triggered.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void nextTurn(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that the last circle has started.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void lastCircle(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that the card was placed into the Codex.
     * @param gameImmutable The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void positionedCardIntoCodex(GameImmutable gameImmutable, int row, int column) throws RemoteException;


    /**
     * This method is used to inform the client that the Starter card was placed into the Codex.
     * @param gameImmutable The immutable game model.
     * @param nickname The player who placed the starter.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void positionedStarterCardIntoCodex(GameImmutable gameImmutable, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that the coordinates where he wants to insert the card are not valid.
     * @param gameImmutable The immutable game model.
     * @param row The row where the card wanted to be placed.
     * @param column The column where the card wanted to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void invalidCoordinates(GameImmutable gameImmutable, int row, int column) throws RemoteException;


    /**
     * This method is used to inform the client that the positioning requirements are not respected.
     * @param gameImmutable The immutable game model.
     * @param requirementsPlacement The requirements for card placement.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void requirementsPlacementNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) throws RemoteException;


    /**
     * This method is used to inform the client that the points have been added.
     * @param gameImmutable The immutable game model.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void addedPoint(GameImmutable gameImmutable, Player player, int point) throws RemoteException;


    /**
     * This method is used to inform the client that the Objective card was chosen correctly.
     * @param gameImmutable The immutable game model.
     * @param cardObjective The chosen Objective card.
     * @param nickname The player who chose the card objective.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void objectiveCardChosen(GameImmutable gameImmutable, CardObjective cardObjective, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that the Objective card was not chosen correctly.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void objectiveCardNotChosen(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that the index is invalid.
     * @param gameImmutable The immutable game model.
     * @param index The index.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void indexNotValid(GameImmutable gameImmutable, int index) throws RemoteException;


    /**
     * This method is used to inform the client that a deck has no cards.
     * @param gameImmutable The immutable game model.
     * @param deck The deck without cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void deckHasNoCards(GameImmutable gameImmutable, ArrayList<? extends Card> deck) throws RemoteException;


    /**
     * This method is used to inform the client that a card has been successfully added to his hand.
     * @param gameImmutable The immutable game model.
     * @param card The card that was added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void cardAddedToHand(GameImmutable gameImmutable, Card card) throws RemoteException;


    /**
     * This method is used to inform the client that a card was not added to his hand.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void cardNotAddedToHand(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that the conditions to end the game have been reached.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void endGameConditionsReached(GameImmutable gameImmutable) throws RemoteException;


    /**
     * This method is used to inform the client that the points obtained with the Objective cards have been calculated.
     * @param gameImmutable The immutable game model.
     * @param objectivePoint The points obtained with Objective cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void addedPointObjective(GameImmutable gameImmutable, int objectivePoint) throws RemoteException;


    /**
     * This method is used to inform the client which players won the game.
     * @param gameImmutable The immutable game model.
     * @param nickname The nicknames of the players who won.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void winnerDeclared(GameImmutable gameImmutable, ArrayList<String> nickname) throws RemoteException;

    /**
     * This method is used to inform the client of the game size change.
     * @param gameImmutable The immutable game model.
     * @param size The new game size.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameSizeUpdated(GameImmutable gameImmutable, int size) throws RemoteException;


    void drawCard(GameImmutable gameImmutable, String nickname) throws RemoteException;


    void noGameToReconnect(GameImmutable gameImmutable, String nickname) throws RemoteException;


    // Additional methods may be necessary
    // For other methods you have to create the respective message in serverToClientMessages package and method in ListenersHandler


}
