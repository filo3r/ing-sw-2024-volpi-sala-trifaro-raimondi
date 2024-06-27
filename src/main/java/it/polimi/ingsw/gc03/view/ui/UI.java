package it.polimi.ingsw.gc03.view.ui;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import java.util.ArrayList;

/**
 * Abstract class for defining the user interface (GUI or TUI).
 * This class contains abstract methods to be implemented by concrete subclasses to handle various UI actions and displays.
 */
public abstract class UI {

    /**
     * Initialises GUI or TUI.
     */
    protected abstract void init();

    /**
     * Moves the screen view to the specified coordinates.
     * @param x The x-coordinate to move the view.
     * @param y The y-coordinate to move the view.
     */
    protected abstract void moveScreenView(int x, int y);

    /**
     * Resizes the screen view to the specified dimensions.
     * @param x The new width of the view.
     * @param y The new height of the view.
     */
    protected abstract void resizeScreenView(int x, int y);

    /**
     * Shows menu options.
     */
    protected abstract void show_menuOptions();

    /**
     * Shows the creating new game message.
     * @param nickname Player's nickname.
     */
    protected abstract void show_creatingNewGameMsg(String nickname);

    /**
     * Shows the join first available game message.
     * @param nickname Player's nickname.
     */
    protected abstract void show_joiningFirstAvailableMsg(String nickname);

    /**
     * Shows the join to specific game message.
     * @param idGame The id of the game the player is trying to join.
     * @param nickname The player's nickname.
     */
    protected abstract void show_joiningToGameIdMsg(int idGame, String nickname);

    /**
     * Message that asks to insert specific game id.
     */
    protected abstract void show_inputGameIdMsg();

    /**
     * Asks the player for his nickname.
     */
    protected abstract void show_insertNicknameMsg();

    /**
     * Shows the player's chosen nickname.
     * @param nickname The nickname just chosen by the player.
     */
    protected abstract void show_chosenNickname(String nickname);

    /**
     * Shows game started message.
     * @param gameImmutable The immutable game where the game has started.
     */
    protected abstract void show_gameStarted(GameImmutable gameImmutable);

    /**
     * Shows error message when there are no games available for joining.
     * @param msgToVisualize The message that needs visualization.
     */
    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);

    /**
     * Shows the game ended message.
     * @param gameImmutable The immutable game where the game has ended.
     */
    protected abstract void show_gameEnded(GameImmutable gameImmutable);

    /**
     * Shows the players that have joined.
     * @param gameImmutable The model where events happen.
     * @param nick The player's nickname.
     */
    protected abstract void show_playerJoined(GameImmutable gameImmutable, String nick);

    /**
     * Shows the message for the next turn or reconnected player.
     * @param gameImmutable The immutable game where events happen.
     * @param nickname The nickname of the reconnected player (or of the player that is now in turn).
     */
    protected abstract void showNextTurn(GameImmutable gameImmutable, String nickname);

    /**
     * Message that shows the player's hand.
     * @param gameImmutable The model that has the player hand that needs to be shown.
     * @param nickname The player's nickname.
     */
    protected abstract void show_playerHand(GameImmutable gameImmutable, String nickname);

    /**
     * Shows the message that has been sent.
     * @param gameImmutable The immutable game where the message needs to be shown.
     * @param nickname The sender's nickname.
     */
    protected abstract void show_sentMessage(GameImmutable gameImmutable, String nickname);

    /**
     * Shows generic error message.
     */
    protected abstract void show_NaNMsg();

    /**
     * Shows the message that asks to return to the main menu.
     */
    protected abstract void show_returnToMenuMsg();

    //----------------------
    //ACTIONS
    //----------------------

    /**
     * Shows message on latest event added.
     * @param input The string of the latest event to add.
     * @param gameImmutable The immutable game where the latest event happened.
     */
    protected abstract void addLatestEvent(String input, GameImmutable gameImmutable);

    /**
     * Gets the length of the longest message registered in the chat.
     * @param gameImmutable The gameImmutable in which to search for the longest message.
     * @return The length of the longest message registered in chat.
     */
    protected abstract int getLengthLongestMessage(GameImmutable gameImmutable);

    /**
     * Adds a message to the game's chat.
     * @param msg The message to add.
     * @param gameImmutable The gameImmutable to which add the message.
     */
    protected abstract void addMessage(ChatMessage msg, GameImmutable gameImmutable);

    /**
     * Shows an error when there's no connection.
     */
    protected abstract void show_noConnectionError();

    /**
     * Shows a prompt asking for an index.
     * @param gameImmutable The immutable game context.
     */
    protected abstract void showAskIndex(GameImmutable gameImmutable);

    /**
     * Shows an error message for a wrong selection from the hand.
     */
    protected abstract void show_wrongSelectionHandMsg();

    /**
     * Shows a prompt asking for coordinates.
     * @param gameImmutable The immutable game context.
     */
    protected abstract void showAskCoordinates(GameImmutable gameImmutable);

    /**
     * Shows a prompt asking to choose a deck.
     */
    protected abstract void showAskToChooseADeck();

    /**
     * Shows a message that the card cannot be placed.
     * @param gameImmutable The immutable game context.
     * @param nickname The player's nickname.
     */
    protected abstract void showCardCannotBePlaced(GameImmutable gameImmutable, String nickname);

    /**
     * Shows an invalid input message.
     */
    protected abstract void showInvalidInput();

    /**
     * Shows the Codex.
     * @param gameImmutable The immutable game context.
     */
    protected abstract void showCodex(GameImmutable gameImmutable);

    /**
     * Shows a prompt asking for the size of the game.
     * @param gameImmutable The immutable game context.
     */
    protected abstract void showAskSize(GameImmutable gameImmutable);

    /**
     * Shows a message indicating the game size has been set.
     * @param size The number of players participating in the game.
     * @param gameImmutable The immutable game context.
     */
    protected abstract void show_sizeSetted(int size, GameImmutable gameImmutable);

    /**
     * Shows a message that a card has been added to the hand.
     * @param gameImmutable The immutable game context.
     * @param card The card that has been added to the hand.
     */
    protected abstract void showCardAddedToHand(GameImmutable gameImmutable, Card card);

    /**
     * Shows the winner of the game.
     * @param gameImmutable The immutable game context.
     */
    protected abstract void showWinner(GameImmutable gameImmutable);

    /**
     * Shows a message that an objective card has been chosen.
     * @param gameImmutable The immutable game context.
     * @param cardObjective The objective card that has been chosen.
     * @param nickname The player's nickname.
     */
    protected abstract void showObjectiveChosen(GameImmutable gameImmutable, CardObjective cardObjective, String nickname);

    /**
     * Shows a message that an objective card has not been chosen.
     * @param gameImmutable The immutable game context.
     */
    protected abstract void showObjectiveNotChosen(GameImmutable gameImmutable);

    /**
     * Shows a message that the requirements for placement were not respected.
     * @param gameImmutable The immutable game context.
     * @param requirementsPlacement The list of requirements that were not respected.
     */
    protected abstract void showReqNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement);

    /**
     * Shows the game title.
     */
    protected abstract void show_GameTitle();

    /**
     * Shows an invalid nickname message.
     * @param nickname The invalid nickname.
     */
    protected abstract void showInvalidNickname(String nickname);

    /**
     * Shows a message that an objective card has been chosen.
     * @param game The game context.
     * @param card The card that has been chosen.
     */
    protected abstract void showObjectiveChosen(Game game, Card card);

    /**
     * Shows a prompt asking which side of a card to use.
     * @param game The game context.
     * @param card The card for which to choose a side.
     */
    protected abstract void showAskSide(GameImmutable game, Card card);

    /**
     * Shows a prompt asking which side of the starter card to use.
     * @param game The game context.
     * @param nickname The player's nickname.
     */
    protected abstract void show_askSideStarter(GameImmutable game, String nickname);

    /**
     * Sets the player's nickname.
     * @param nickname The player's nickname.
     */
    protected abstract void setNickname(String nickname);

    /**
     * Shows the game desk.
     * @param gameImmutable The immutable game context.
     * @param nickname The player's nickname.
     */
    protected abstract void showDesk(GameImmutable gameImmutable, String nickname);

    /**
     * Shows a message that the player has left the game.
     */
    protected abstract void showYouLeft();

    /**
     * Shows the chat.
     * @param gameImmutable The immutable game context.
     */
    public abstract void showChat(GameImmutable gameImmutable);

    /**
     * Shows a message that a player has disconnected.
     * @param nickname The nickname of the disconnected player.
     */
    public abstract void showPlayerDisconnected(String nickname);

}