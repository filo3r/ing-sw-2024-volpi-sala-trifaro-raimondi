package it.polimi.ingsw.gc03.networking.socket.client;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.view.ui.Flow;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This class implements the GameListener interface and handles game events by delegating actions to the Flow object.
 */
public class GameListenerHandlerClient implements GameListener, Serializable {

    /**
     * The Flow object that handles UI and game flow actions.
     */
    private Flow flow;


    /**
     * Constructs a GameListenersHandlerClient with a given Flow.
     * @param flow The Flow object to handle UI and game flow actions.
     */
    public GameListenerHandlerClient(Flow flow) {
        this.flow = flow;
    }


    /**
     * Handles the event when a player joins the game.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerJoined(GameImmutable gameImmutable) throws RemoteException {
        this.flow.playerJoined(gameImmutable);
    }


    /**
     * Handles the event when a player leaves the game.
     * @param gameImmutable The immutable game model.
     * @param nickname The nickname of the player who left the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerLeft(GameImmutable gameImmutable, String nickname) throws RemoteException {
        this.flow.playerLeft(gameImmutable, nickname);
    }


    /**
     * Handles the event when a player is unable to join a full game.
     * @param gameImmutable The immutable game model.
     * @param player The player that tried to join.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void joinUnableGameFull(GameImmutable gameImmutable, Player player) throws RemoteException {
        this.flow.joinUnableGameFull(gameImmutable, player);
    }


    /**
     * Handles the event when a player reconnects to the game.
     * @param gameImmutable The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerReconnected(GameImmutable gameImmutable, String nickname) throws RemoteException {
        this.flow.playerReconnected(gameImmutable, nickname);
    }


    /**
     * Handles the event when a player disconnects.
     * @param gameImmutable The immutable game model.
     * @param nickname The nickname of the player that has disconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerDisconnected(GameImmutable gameImmutable, String nickname) throws RemoteException {
        this.flow.playerDisconnected(gameImmutable, nickname);
    }


    /**
     * Handles the event when only one player is connected.
     * @param gameImmutable The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void onlyOnePlayerConnected(GameImmutable gameImmutable, int timer) throws RemoteException {
        this.flow.onlyOnePlayerConnected(gameImmutable, timer);
    }


    /**
     * Handles the event when a player is unable to join due to an already existing nickname.
     * @param player The player that tried to use the nickname.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void joinUnableNicknameAlreadyInUse(Player player) throws RemoteException {
        this.flow.joinUnableNicknameAlreadyInUse(player);
    }


    /**
     * Handles the event when a game ID does not exist.
     * @param gameId The game id.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameIdNotExists(int gameId) throws RemoteException {
        this.flow.gameIdNotExists(gameId);
    }


    /**
     * Handles the event when the game starts.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameStarted(GameImmutable gameImmutable) throws RemoteException {
        this.flow.gameStarted(gameImmutable);
    }


    /**
     * Handles the event when the game ends.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameEnded(GameImmutable gameImmutable) throws RemoteException {
        this.flow.gameEnded(gameImmutable);
    }


    /**
     * Handles the event when a message is sent.
     * @param gameImmutable The immutable game model.
     * @param chatMessage The message that has been sent.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void sentChatMessage(GameImmutable gameImmutable, ChatMessage chatMessage) throws RemoteException {
        this.flow.sentChatMessage(gameImmutable, chatMessage);
    }


    /**
     * Handles the event for the next turn.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void nextTurn(GameImmutable gameImmutable) throws RemoteException {
        this.flow.nextTurn(gameImmutable);
    }


    /**
     * Handles the event for the last circle.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void lastCircle(GameImmutable gameImmutable) throws RemoteException {
        this.flow.lastCircle(gameImmutable);
    }


    /**
     * Handles the event when a card is positioned into the Codex.
     * @param gameImmutable The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedCardIntoCodex(GameImmutable gameImmutable, int row, int column) throws RemoteException {
        this.flow.positionedCardIntoCodex(gameImmutable, row, column);
    }


    /**
     * Handles the event when the Starter card is positioned into the codex.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedStarterCardIntoCodex(GameImmutable gameImmutable) throws RemoteException {
        this.flow.positionedStarterCardIntoCodex(gameImmutable);
    }


    /**
     * Handles the event when invalid coordinates are provided.
     * @param gameImmutable The immutable game model.
     * @param row The row where the card wanted to be placed.
     * @param column The column where the card wanted to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void invalidCoordinates(GameImmutable gameImmutable, int row, int column) throws RemoteException {
        this.flow.invalidCoordinates(gameImmutable, row, column);
    }


    /**
     * Handles the event when the requirements for card placement are not respected.
     * @param gameImmutable The immutable game model.
     * @param requirementsPlacement The requirements for card placement.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void requirementsPlacementNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) throws RemoteException {
       this.flow.requirementsPlacementNotRespected(gameImmutable, requirementsPlacement);
    }


    /**
     * Handles the event when a point is added to a player.
     * @param gameImmutable The immutable game model.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPoint(GameImmutable gameImmutable, Player player, int point) throws RemoteException {
        this.flow.addedPoint(gameImmutable, player, point);
    }


    /**
     * Handles the event when the Objective card is chosen.
     * @param gameImmutable The immutable game model.
     * @param cardObjective The chosen Objective card.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardChosen(GameImmutable gameImmutable, CardObjective cardObjective) throws RemoteException {
        this.flow.objectiveCardChosen(gameImmutable, cardObjective);
    }


    /**
     * Handles the event when the Objective card is not chosen.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardNotChosen(GameImmutable gameImmutable) throws RemoteException {
        this.flow.objectiveCardNotChosen(gameImmutable);
    }


    /**
     * Handles the event when an invalid index is provided.
     * @param gameImmutable The immutable game model.
     * @param index The index.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void indexNotValid(GameImmutable gameImmutable, int index) throws RemoteException {
        this.flow.indexNotValid(gameImmutable, index);
    }


    /**
     * Handles the event when a deck has no cards left.
     * @param gameImmutable The immutable game model.
     * @param deck The deck without cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void deckHasNoCards(GameImmutable gameImmutable, ArrayList<? extends Card> deck) throws RemoteException {
        this.flow.deckHasNoCards(gameImmutable, deck);
    }


    /**
     * Handles the event when a card is added to the player's hand.
     * @param gameImmutable The immutable game model.
     * @param card The card that was added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardAddedToHand(GameImmutable gameImmutable, Card card) throws RemoteException {
        this.flow.cardAddedToHand(gameImmutable, card);
    }


    /**
     * Handles the event when a card is not added to the player's hand.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardNotAddedToHand(GameImmutable gameImmutable) throws RemoteException {
        this.flow.cardNotAddedToHand(gameImmutable);
    }


    /**
     * Handles the event when the end game conditions are reached.
     * @param gameImmutable The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void endGameConditionsReached(GameImmutable gameImmutable) throws RemoteException {
        this.flow.endGameConditionReached(gameImmutable);
    }


    /**
     * Handles the event when objective points are added.
     * @param gameImmutable The immutable game model.
     * @param objectivePoint The points obtained with Objective cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPointObjective(GameImmutable gameImmutable, int objectivePoint) throws RemoteException {
        this.flow.addedPointObjective(gameImmutable, objectivePoint);
    }


    /**
     * Handles the event when the winners are declared.
     * @param gameImmutable The immutable game model.
     * @param nickname The nicknames of the players who won.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void winnerDeclared(GameImmutable gameImmutable, ArrayList<String> nickname) throws RemoteException {
        this.flow.winnerDeclared(gameImmutable, nickname);
    }

    /**
     * Handles the event when the winners are declared.
     * @param gameImmutable The immutable game model.
     * @param size The new game size.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameSizeUpdated(GameImmutable gameImmutable, int size) throws RemoteException {
        this.flow.gameSizeUpdated(gameImmutable, size);
    }


}
