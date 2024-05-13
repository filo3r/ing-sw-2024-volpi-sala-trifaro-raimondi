package it.polimi.ingsw.gc03.networking.socket.server;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameModel;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.networking.socket.messages.serverToClientMessages.*;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;


/**
 * This class facilitates communication of GameListener events to the client over a socket connection.
 */
public class GameListenerHandlerServer implements GameListener, Serializable {

    /**
     * A private ObjectOutputStream for transmitting the data.
     */
    private final ObjectOutputStream outputStream;


    /**
     * Constructs a handler for transmitting GameListener events.
     * @param outputStream The ObjectOutputStream used for data transmission.
     */
    public GameListenerHandlerServer(ObjectOutputStream outputStream) {
        this.outputStream = outputStream;
    }


    /**
     * Flushes and resets the ObjectOutputStream. This ensures that all buffered data is sent
     * and the buffer is cleared, preparing the stream for future use.
     * @throws IOException If an error occurs during the flush or reset operations.
     */
    private void completeTransmission() throws IOException {
        // Force all data to be transmitted to the destination
        this.outputStream.flush();
        // Clean the stream to prepare it for the next transmission
        this.outputStream.reset();
    }


    /**
     * This method is used to write on the output stream the message that a player has joined the game.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerJoined(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessagePlayerJoined message = new SocketServerMessagePlayerJoined(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a player has left the game.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player who left the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerLeft(GameModel gameModel, String nickname) throws RemoteException {
        try {
            SocketServerMessagePlayerLeft message = new SocketServerMessagePlayerLeft(gameModel, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a player tried to join a full game.
     * @param gameModel The immutable game model.
     * @param player The player that tried to join.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void joinUnableGameFull(GameModel gameModel, Player player) throws RemoteException {
        try {
            SocketServerMessageJoinUnableGameFull message = new SocketServerMessageJoinUnableGameFull(gameModel, player);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a player has reconnected to the game.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerReconnected(GameModel gameModel, String nickname) throws RemoteException {
        try {
            SocketServerMessagePlayerReconnected message = new SocketServerMessagePlayerReconnected(gameModel, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a player has disconnected.
     * @param gameModel The immutable game model.
     * @param nickname The nickname of the player that has disconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerDisconnected(GameModel gameModel, String nickname) throws RemoteException {
        try {
            SocketServerMessagePlayerDisconnected message = new SocketServerMessagePlayerDisconnected(gameModel, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that only one player is connected.
     * @param gameModel The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void onlyOnePlayerConnected(GameModel gameModel, int timer) throws RemoteException {
        try {
            SocketServerMessageOnlyOnePlayerConnected message = new SocketServerMessageOnlyOnePlayerConnected(gameModel, timer);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a player tried to use a nickname that is already in use.
     * @param player The player that tried to use the nickname.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void joinUnableNicknameAlreadyInUse(Player player) throws RemoteException {
        try {
            SocketServerMessageJoinUnableNicknameAlreadyInUse message = new SocketServerMessageJoinUnableNicknameAlreadyInUse(player);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the game id doesn't exist.
     * @param gameId The game id.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameIdNotExists(int gameId) throws RemoteException {
        try {
            SocketServerMessageGameIdNotExists message = new SocketServerMessageGameIdNotExists(gameId);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the game has started.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameStarted(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessageGameStarted message = new SocketServerMessageGameStarted(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the game has ended.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameEnded(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessageGameEnded message = new SocketServerMessageGameEnded(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a message has been sent.
     * @param gameModel The immutable game model.
     * @param chatMessage The message that has been sent.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void sentChatMessage(GameModel gameModel, ChatMessage chatMessage) throws RemoteException {
        try {
            SocketServerMessageSentChatMessage message = new SocketServerMessageSentChatMessage(gameModel, chatMessage);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the next turn triggered.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void nextTurn(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessageNextTurn message = new SocketServerMessageNextTurn(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the last circle has started.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void lastCircle(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessageLastCircle message = new SocketServerMessageLastCircle(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the card was placed into the Codex.
     * @param gameModel The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedCardIntoCodex(GameModel gameModel, int row, int column) throws RemoteException {
        try {
            SocketServerMessagePositionedCardIntoCodex message = new SocketServerMessagePositionedCardIntoCodex(gameModel, row, column);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the Starter card was placed into the Codex.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedStarterCardIntoCodex(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessagePositionedStarterCardIntoCodex message = new SocketServerMessagePositionedStarterCardIntoCodex(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the coordinates where he wants to insert
     * the card are not valid.
     * @param gameModel The immutable game model.
     * @param row The row where the card wanted to be placed.
     * @param column The column where the card wanted to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void invalidCoordinates(GameModel gameModel, int row, int column) throws RemoteException {
        try {
            SocketServerMessageInvalidCoordinates message = new SocketServerMessageInvalidCoordinates(gameModel, row, column);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the positioning requirements are not respected.
     * @param gameModel The immutable game model.
     * @param requirementsPlacement The requirements for card placement.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void requirementsPlacementNotRespected(GameModel gameModel, ArrayList<Value> requirementsPlacement) throws RemoteException {
        try {
            SocketServerMessageRequirementsPlacementNotRespected message = new SocketServerMessageRequirementsPlacementNotRespected(gameModel, requirementsPlacement);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the points have been added.
     * @param gameModel The immutable game model.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPoint(GameModel gameModel, Player player, int point) throws RemoteException {
        try {
            SocketServerMessageAddedPoint message = new SocketServerMessageAddedPoint(gameModel, player, point);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the Objective card was chosen correctly.
     * @param gameModel The immutable game model.
     * @param cardObjective The chosen Objective card.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardChosen(GameModel gameModel, CardObjective cardObjective) throws RemoteException {
        try {
            SocketServerMessageObjectiveCardChosen message = new SocketServerMessageObjectiveCardChosen(gameModel, cardObjective);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the Objective card was not chosen correctly.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardNotChosen(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessageObjectiveCardNotChosen message = new SocketServerMessageObjectiveCardNotChosen(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the index is invalid.
     * @param gameModel The immutable game model.
     * @param index The index.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void indexNotValid(GameModel gameModel, int index) throws RemoteException {
        try {
            SocketServerMessageIndexNotValid message = new SocketServerMessageIndexNotValid(gameModel, index);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a deck has no cards.
     * @param gameModel The immutable game model.
     * @param deck The deck without cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void deckHasNoCards(GameModel gameModel, ArrayList<? extends Card> deck) throws RemoteException {
        try {
            SocketServerMessageDeckHasNoCards message = new SocketServerMessageDeckHasNoCards(gameModel, deck);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a card has been successfully added to his hand.
     * @param gameModel The immutable game model.
     * @param card The card that was added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardAddedToHand(GameModel gameModel, Card card) throws RemoteException {
        try {
            SocketServerMessageCardAddedToHand message = new SocketServerMessageCardAddedToHand(gameModel, card);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that a card was not added to his hand.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardNotAddedToHand(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessageCardNotAddedToHand message = new SocketServerMessageCardNotAddedToHand(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the conditions to end the game have been reached.
     * @param gameModel The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void endGameConditionsReached(GameModel gameModel) throws RemoteException {
        try {
            SocketServerMessageEndGameConditionsReached message = new SocketServerMessageEndGameConditionsReached(gameModel);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write on the output stream the message that the points obtained with the Objective cards
     * have been calculated.
     * @param gameModel The immutable game model.
     * @param objectivePoint The points obtained with Objective cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPointObjective(GameModel gameModel, int objectivePoint) throws RemoteException {
        try {
            SocketServerMessageAddedPointObjective message = new SocketServerMessageAddedPointObjective(gameModel, objectivePoint);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


    /**
     * This method is used to write the message indicating which players won the game to the output stream.
     * @param gameModel The immutable game model.
     * @param nickname The nicknames of the players who won.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void winnerDeclared(GameModel gameModel, ArrayList<String> nickname) throws RemoteException {
        try {
            SocketServerMessageWinnerDeclared message = new SocketServerMessageWinnerDeclared(gameModel, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


}
