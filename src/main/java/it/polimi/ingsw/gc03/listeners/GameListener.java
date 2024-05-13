package it.polimi.ingsw.gc03.listeners;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameModel;
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
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerJoined(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that a player has left the game.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player who left the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerLeft(GameModel gameModel, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that a player tried to join a full game.
     * @param gameModel The immutable game model.
     * @param player The player that tried to join.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void joinUnableGameFull(GameModel gameModel, Player player) throws RemoteException;


    /**
     * This method is used to inform the client that a player has reconnected to the game.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerReconnected(GameModel gameModel, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that a player has disconnected.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player that has disconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void playerDisconnected(GameModel gameModel, String nickname) throws RemoteException;


    /**
     * This method is used to inform the client that only one player is connected.
     * @param gameModel The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void onlyOnePlayerConnected(GameModel gameModel, int timer) throws RemoteException;


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
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameStarted(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the game has ended.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void gameEnded(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that a message has been sent.
     * @param gameModel The immutable game model.
     * @param chatMessage The message that has been sent.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void sentChatMessage(GameModel gameModel, ChatMessage chatMessage) throws RemoteException;


    /**
     * This method is used to inform the client that the next turn triggered.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void nextTurn(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the last circle has started.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void lastCircle(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the card was placed into the Codex.
     * @param gameModel The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void positionedCardIntoCodex(GameModel gameModel, int row, int column) throws RemoteException;


    /**
     * This method is used to inform the client that the Starter card was placed into the Codex.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void positionedStarterCardIntoCodex(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the coordinates where he wants to insert the card are not valid.
     * @param gameModel The immutable game model.
     * @param row The row where the card wanted to be placed.
     * @param column The column where the card wanted to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void invalidCoordinates(GameModel gameModel, int row, int column) throws RemoteException;


    /**
     * This method is used to inform the client that the positioning requirements are not respected.
     * @param gameModel The immutable game model.
     * @param requirementsPlacement The requirements for card placement.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void requirementsPlacementNotRespected(GameModel gameModel, ArrayList<Value> requirementsPlacement) throws RemoteException;


    /**
     * This method is used to inform the client that the points have been added.
     * @param gameModel The immutable game model.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void addedPoint(GameModel gameModel, Player player, int point) throws RemoteException;


    /**
     * This method is used to inform the client that the Objective card was chosen correctly.
     * @param gameModel The immutable game model.
     * @param cardObjective The chosen Objective card.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void objectiveCardChosen(GameModel gameModel, CardObjective cardObjective) throws RemoteException;


    /**
     * This method is used to inform the client that the Objective card was not chosen correctly.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void objectiveCardNotChosen(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the index is invalid.
     * @param gameModel The immutable game model.
     * @param index The index.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void indexNotValid(GameModel gameModel, int index) throws RemoteException;


    /**
     * This method is used to inform the client that a deck has no cards.
     * @param gameModel The immutable game model.
     * @param deck The deck without cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void deckHasNoCards(GameModel gameModel, ArrayList<? extends Card> deck) throws RemoteException;


    /**
     * This method is used to inform the client that a card has been successfully added to his hand.
     * @param gameModel The immutable game model.
     * @param card The card that was added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void cardAddedToHand(GameModel gameModel, Card card) throws RemoteException;


    /**
     * This method is used to inform the client that a card was not added to his hand.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void cardNotAddedToHand(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the conditions to end the game have been reached.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void endGameConditionsReached(GameModel gameModel) throws RemoteException;


    /**
     * This method is used to inform the client that the points obtained with the Objective cards have been calculated.
     * @param gameModel The immutable game model.
     * @param objectivePoint The points obtained with Objective cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void addedPointObjective(GameModel gameModel, int objectivePoint) throws RemoteException;


    /**
     * This method is used to inform the client which players won the game.
     * @param gameModel The immutable game model.
     * @param nickname The nicknames of the players who won.
     * @throws RemoteException If an error occurs in remote communication.
     */
    void winnerDeclared(GameModel gameModel, ArrayList<String> nickname) throws RemoteException;


    // Additional methods may be necessary
    // For other methods you have to create the respective message in serverToClientMessages


}
