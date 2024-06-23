package it.polimi.ingsw.gc03.networking.socket.server;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Model;
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
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerJoined(Model model) throws RemoteException {
        try {
            SocketServerMessagePlayerJoined message = new SocketServerMessagePlayerJoined(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a player has left the game.
     * @param model The immutable game model.
     * @param nickname The nickname of the player who left the game.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerLeft(Model model, String nickname) throws RemoteException {
        try {
            SocketServerMessagePlayerLeft message = new SocketServerMessagePlayerLeft(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a player tried to join a full game.
     * @param model The immutable game model.
     * @param player The player that tried to join.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void joinUnableGameFull(Model model, Player player) throws RemoteException {
        try {
            SocketServerMessageJoinUnableGameFull message = new SocketServerMessageJoinUnableGameFull(model, player);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a player has reconnected to the game.
     * @param model The immutable game model.
     * @param nickname The nickname of the player that has reconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerReconnected(Model model, String nickname) throws RemoteException {
        try {
            SocketServerMessagePlayerReconnected message = new SocketServerMessagePlayerReconnected(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a player has disconnected.
     * @param model The immutable game model.
     * @param nickname The nickname of the player that has disconnected.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void playerDisconnected(Model model, String nickname) throws RemoteException {
        try {
            SocketServerMessagePlayerDisconnected message = new SocketServerMessagePlayerDisconnected(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that only one player is connected.
     * @param model The immutable game model.
     * @param timer The number of seconds to wait until the game ends.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void onlyOnePlayerConnected(Model model, int timer) throws RemoteException {
        try {
            SocketServerMessageOnlyOnePlayerConnected message = new SocketServerMessageOnlyOnePlayerConnected(model, timer);
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
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameStarted(Model model) throws RemoteException {
        try {
            SocketServerMessageGameStarted message = new SocketServerMessageGameStarted(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the game has ended.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameEnded(Model model) throws RemoteException {
        try {
            SocketServerMessageGameEnded message = new SocketServerMessageGameEnded(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a message has been sent.
     * @param model The immutable game model.
     * @param chatMessage The message that has been sent.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void sentChatMessage(Model model, ChatMessage chatMessage) throws RemoteException {
        try {
            SocketServerMessageSentChatMessage message = new SocketServerMessageSentChatMessage(model, chatMessage);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the next turn triggered.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void nextTurn(Model model) throws RemoteException {
        try {
            SocketServerMessageNextTurn message = new SocketServerMessageNextTurn(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the last circle has started.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void lastCircle(Model model) throws RemoteException {
        try {
            SocketServerMessageLastCircle message = new SocketServerMessageLastCircle(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the card was placed into the Codex.
     * @param model The immutable game model.
     * @param row The row where the card was placed.
     * @param column The column where the card was placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedCardIntoCodex(Model model, int row, int column) throws RemoteException {
        try {
            SocketServerMessagePositionedCardIntoCodex message = new SocketServerMessagePositionedCardIntoCodex(model, row, column);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the Starter card was placed into the Codex.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void positionedStarterCardIntoCodex(Model model, String nickname) throws RemoteException {
        try {
            SocketServerMessagePositionedStarterCardIntoCodex message = new SocketServerMessagePositionedStarterCardIntoCodex(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the coordinates where he wants to insert
     * the card are not valid.
     * @param model The immutable game model.
     * @param row The row where the card wanted to be placed.
     * @param column The column where the card wanted to be placed.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void invalidCoordinates(Model model, int row, int column) throws RemoteException {
        try {
            SocketServerMessageInvalidCoordinates message = new SocketServerMessageInvalidCoordinates(model, row, column);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the positioning requirements are not respected.
     * @param model The immutable game model.
     * @param requirementsPlacement The requirements for card placement.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void requirementsPlacementNotRespected(Model model, ArrayList<Value> requirementsPlacement) throws RemoteException {
        try {
            SocketServerMessageRequirementsPlacementNotRespected message = new SocketServerMessageRequirementsPlacementNotRespected(model, requirementsPlacement);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the points have been added.
     * @param model The immutable game model.
     * @param player The player that has added the points.
     * @param point The number of points that have been added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPoint(Model model, Player player, int point) throws RemoteException {
        try {
            SocketServerMessageAddedPoint message = new SocketServerMessageAddedPoint(model, player, point);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the Objective card was chosen correctly.
     * @param model The immutable game model.
     * @param cardObjective The chosen Objective card.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardChosen(Model model, CardObjective cardObjective, String nickname) throws RemoteException {
        try {
            SocketServerMessageObjectiveCardChosen message = new SocketServerMessageObjectiveCardChosen(model, cardObjective, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the Objective card was not chosen correctly.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void objectiveCardNotChosen(Model model) throws RemoteException {
        try {
            SocketServerMessageObjectiveCardNotChosen message = new SocketServerMessageObjectiveCardNotChosen(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the index is invalid.
     * @param model The immutable game model.
     * @param index The index.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void indexNotValid(Model model, int index) throws RemoteException {
        try {
            SocketServerMessageIndexNotValid message = new SocketServerMessageIndexNotValid(model, index);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a deck has no cards.
     * @param model The immutable game model.
     * @param deck The deck without cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void deckHasNoCards(Model model, ArrayList<? extends Card> deck) throws RemoteException {
        try {
            SocketServerMessageDeckHasNoCards message = new SocketServerMessageDeckHasNoCards(model, deck);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a card has been successfully added to his hand.
     * @param model The immutable game model.
     * @param card The card that was added.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardAddedToHand(Model model, Card card) throws RemoteException {
        try {
            SocketServerMessageCardAddedToHand message = new SocketServerMessageCardAddedToHand(model, card);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that a card was not added to his hand.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void cardNotAddedToHand(Model model) throws RemoteException {
        try {
            SocketServerMessageCardNotAddedToHand message = new SocketServerMessageCardNotAddedToHand(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the conditions to end the game have been reached.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void endGameConditionsReached(Model model) throws RemoteException {
        try {
            SocketServerMessageEndGameConditionsReached message = new SocketServerMessageEndGameConditionsReached(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write on the output stream the message that the points obtained with the Objective cards
     * have been calculated.
     * @param model The immutable game model.
     * @param objectivePoint The points obtained with Objective cards.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void addedPointObjective(Model model, int objectivePoint) throws RemoteException {
        try {
            SocketServerMessageAddedPointObjective message = new SocketServerMessageAddedPointObjective(model, objectivePoint);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write the message indicating which players won the game to the output stream.
     * @param model The immutable game model.
     * @param nickname The nicknames of the players who won.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void winnerDeclared(Model model, ArrayList<String> nickname) throws RemoteException {
        try {
            SocketServerMessageWinnerDeclared message = new SocketServerMessageWinnerDeclared(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write the message indicating which players won the game to the output stream.
     * @param model The immutable game model.
     * @param size The new game size.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameSizeUpdated(Model model, int size) throws RemoteException {
        try {
            SocketServerMessageGameSizeUpdated message = new SocketServerMessageGameSizeUpdated(model, size);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write the message indicating that a player has drawn a card.
     * @param model The immutable game model.
     * @param nickname The player that has drawn a card.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void drawCard(Model model, String nickname) throws RemoteException {
        try {
            SocketServerMessageDrawCard message = new SocketServerMessageDrawCard(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write the message indicating that a player tried to reconnect to a game but wasn't in any game.
     * @param model The immutable game model.
     * @param nickname The player that tried to reconnect.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void noGameToReconnect(Model model, String nickname) throws RemoteException {
        try {
            SocketServerMessageNoGameToReconnect message = new SocketServerMessageNoGameToReconnect(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to write the message indicating that a game has been created.
     * @param model The immutable game model.
     * @throws RemoteException If an error occurs in remote communication.
     */
    @Override
    public void gameCreated(Model model) throws RemoteException {
        try {
            SocketServerMessageGameCreated message = new SocketServerMessageGameCreated(model);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }

    /**
     * This method is used to inform the clients that a player tried to place a card when he can't.
     * @param model The immutable game object.
     * @param nickname The nickname of the player who tried.
     * @throws RemoteException If an error occurs in remote communication.
     */
    public void canNotPlaceCard(Model model, String nickname) throws RemoteException{
        try {
            SocketServerMessageCanNotPlaceCard message = new SocketServerMessageCanNotPlaceCard(model, nickname);
            this.outputStream.writeObject(message);
            completeTransmission();
        } catch (IOException e) {}
    }


}
