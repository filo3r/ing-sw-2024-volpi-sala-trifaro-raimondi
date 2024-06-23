package it.polimi.ingsw.gc03.networking.socket.client;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Model;
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
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerJoined(Model model) throws RemoteException {
        this.flow.playerJoined(model);
    }

    /**
     * Handles the event when a player leaves the game.
     * @param model The immutable game model.
     * @param nickname The nickname of the player who left the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerLeft(Model model, String nickname) throws RemoteException {
        this.flow.playerLeft(model, nickname);
    }

    /**
     * Handles the event when a player is unable to join a full game.
     * @param model The immutable game model.
     * @param player The player that tried to join.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void joinUnableGameFull(Model model, Player player) throws RemoteException {
        this.flow.joinUnableGameFull(model, player);
    }

    /**
     * Handles the event when a player reconnects to the game.
     * @param model The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerReconnected(Model model, String nickname) throws RemoteException {
        this.flow.playerReconnected(model, nickname);
    }

    /**
     * Handles the event when a player disconnects.
     * @param model The immutable game model.
     * @param nickname The nickname of the player that has disconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerDisconnected(Model model, String nickname) throws RemoteException {
        this.flow.playerDisconnected(model, nickname);
    }

    /**
     * Handles the event when only one player is connected.
     * @param model The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void onlyOnePlayerConnected(Model model, int timer) throws RemoteException {
        this.flow.onlyOnePlayerConnected(model, timer);
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
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameStarted(Model model) throws RemoteException {
        this.flow.gameStarted(model);
    }

    /**
     * Handles the event when the game ends.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameEnded(Model model) throws RemoteException {
        this.flow.gameEnded(model);
    }

    /**
     * Handles the event when a message is sent.
     * @param model The immutable game model.
     * @param chatMessage The message that has been sent.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void sentChatMessage(Model model, ChatMessage chatMessage) throws RemoteException {
        this.flow.sentChatMessage(model, chatMessage);
    }

    /**
     * Handles the event for the next turn.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void nextTurn(Model model) throws RemoteException {
        this.flow.nextTurn(model);
    }

    /**
     * Handles the event for the last circle.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void lastCircle(Model model) throws RemoteException {
        this.flow.lastCircle(model);
    }

    /**
     * Handles the event when a card is positioned into the Codex.
     * @param model The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedCardIntoCodex(Model model, int row, int column) throws RemoteException {
        this.flow.positionedCardIntoCodex(model, row, column);
    }

    /**
     * Handles the event when the Starter card is positioned into the codex.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedStarterCardIntoCodex(Model model, String nickname) throws RemoteException {
        this.flow.positionedStarterCardIntoCodex(model, nickname);
    }

    /**
     * Handles the event when invalid coordinates are provided.
     * @param model The immutable game model.
     * @param row The row where the card wanted to be placed.
     * @param column The column where the card wanted to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void invalidCoordinates(Model model, int row, int column) throws RemoteException {
        this.flow.invalidCoordinates(model, row, column);
    }

    /**
     * Handles the event when the requirements for card placement are not respected.
     * @param model The immutable game model.
     * @param requirementsPlacement The requirements for card placement.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void requirementsPlacementNotRespected(Model model, ArrayList<Value> requirementsPlacement) throws RemoteException {
       this.flow.requirementsPlacementNotRespected(model, requirementsPlacement);
    }

    /**
     * Handles the event when a point is added to a player.
     * @param model The immutable game model.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPoint(Model model, Player player, int point) throws RemoteException {
        this.flow.addedPoint(model, player, point);
    }

    /**
     * Handles the event when the Objective card is chosen.
     * @param model The immutable game model.
     * @param cardObjective The chosen Objective card.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardChosen(Model model, CardObjective cardObjective, String nickname) throws RemoteException {
        this.flow.objectiveCardChosen(model, cardObjective, nickname);
    }

    /**
     * Handles the event when the Objective card is not chosen.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardNotChosen(Model model) throws RemoteException {
        this.flow.objectiveCardNotChosen(model);
    }

    /**
     * Handles the event when an invalid index is provided.
     * @param model The immutable game model.
     * @param index The index.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void indexNotValid(Model model, int index) throws RemoteException {
        this.flow.indexNotValid(model, index);
    }

    /**
     * Handles the event when a deck has no cards left.
     * @param model The immutable game model.
     * @param deck The deck without cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void deckHasNoCards(Model model, ArrayList<? extends Card> deck) throws RemoteException {
        this.flow.deckHasNoCards(model, deck);
    }

    /**
     * Handles the event when a card is added to the player's hand.
     * @param model The immutable game model.
     * @param card The card that was added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardAddedToHand(Model model, Card card) throws RemoteException {
        this.flow.cardAddedToHand(model, card);
    }

    /**
     * Handles the event when a card is not added to the player's hand.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardNotAddedToHand(Model model) throws RemoteException {
        this.flow.cardNotAddedToHand(model);
    }

    /**
     * Handles the event when the end game conditions are reached.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void endGameConditionsReached(Model model) throws RemoteException {
        //this.flow.endGameConditionReached(gameImmutable);
    }

    /**
     * Handles the event when objective points are added.
     * @param model The immutable game model.
     * @param objectivePoint The points obtained with Objective cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPointObjective(Model model, int objectivePoint) throws RemoteException {
        this.flow.addedPointObjective(model, objectivePoint);
    }

    /**
     * Handles the event when the winners are declared.
     * @param model The immutable game model.
     * @param nickname The nicknames of the players who won.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void winnerDeclared(Model model, ArrayList<String> nickname) throws RemoteException {
        this.flow.winnerDeclared(model, nickname);
    }

    /**
     * Handles the event when the winners are declared.
     * @param model The immutable game model.
     * @param size The new game size.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameSizeUpdated(Model model, int size) throws RemoteException {
        this.flow.gameSizeUpdated(model, size);
    }

    /**
     * Handles the drawing of a card.
     * @param model The immutable game model.
     * @param nickname The nickname of the player who has drawn a card.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void drawCard(Model model, String nickname) throws RemoteException {
        this.flow.drawCard(model, nickname);
    }

    /**
     * Handles failure of reconnection.
     * @param model The immutable game model.
     * @param nickname The nickname of the player who tried to reconnect to a game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void noGameToReconnect(Model model, String nickname) throws RemoteException{
        this.flow.noGameToReconnect(model, nickname);
    }

    /**
     * Handles the creation of a game.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameCreated(Model model) throws RemoteException{
        this.flow.gameCreated(model);
    }

    /**
     * Handles the failure of placing a card.
     * @param model The immutable game object.
     * @param nickname The nickname of the player who tried.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void canNotPlaceCard(Model model, String nickname) throws RemoteException{
        this.flow.canNotPlaceCard(model, nickname);
    }

}
