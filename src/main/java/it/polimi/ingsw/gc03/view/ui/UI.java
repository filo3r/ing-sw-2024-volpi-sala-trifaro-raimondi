package it.polimi.ingsw.gc03.view.ui;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.util.ArrayList;
import java.util.List;

public abstract class UI {

    /**
     * List of the latest events.
     */
    protected List<String> latestEvents;

    /**
     * Initialises GUI or TUI
     */
    protected abstract void init();

    protected abstract void moveScreenView(int x, int y);

    protected abstract void resizeScreenView(int x, int y);
    /**
     * Shows menu options
     */
    protected abstract void show_menuOptions();

    /**
     * Shows the creating new game message
     *
     * @param nickname player's nickname
     */
    protected abstract void show_creatingNewGameMsg(String nickname);

    /**
     * Shows the join first available game message
     *
     * @param nickname player's nickname
     */
    protected abstract void show_joiningFirstAvailableMsg(String nickname);

    /**
     * Shows the join to specific game message
     *
     * @param idGame   id of the game the player is trying to join
     * @param nickname player's nickname
     */
    protected abstract void show_joiningToGameIdMsg(int idGame, String nickname);

    /**
     * Message that asks to insert specific game id
     */
    protected abstract void show_inputGameIdMsg();

    /**
     * Asks the player for his nickname
     */
    protected abstract void show_insertNicknameMsg();

    /**
     * Shows the player's chosen nickname
     *
     * @param nickname nickname just chosen by the player
     */
    protected abstract void show_chosenNickname(String nickname);

    /**
     * Shows game started message
     *
     * @param gameImmutable gameImmutable where the game has started
     */
    protected abstract void show_gameStarted(GameImmutable gameImmutable);

    /**
     * Shows error message when there are no games available for joining
     *
     * @param msgToVisualize message that needs visualisation
     */
    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);

    /**
     * Shows the game ended message
     *
     * @param gameImmutable where the game is ended
     */
    protected abstract void show_gameEnded(GameImmutable gameImmutable);

    /**
     * Shows the players that have joined
     *
     * @param gameImmutable model where events happen
     * @param nick      player's nickname
     */
    protected abstract void show_playerJoined(GameImmutable gameImmutable, String nick);

    /**
     * Show the message for next turn or reconnected player
     *
     * @param gameImmutable    gameImmutable where events happen
     * @param nickname nick of reconnected player (or of the player that is now in turn)
     */
    protected abstract void showNextTurn(GameImmutable gameImmutable, String nickname);

    /**
     * Message that shows the player's hand
     *
     * @param gameImmutable the model that has the player hand that needs to be shown
     */
    protected abstract void show_playerHand(GameImmutable gameImmutable, String nickname);

    /**
     * Shows the message that has been sent
     *
     * @param gameImmutable    the gameImmutable where the message need to be shown
     * @param nickname the sender's nickname
     */
    protected abstract void show_sentMessage(GameImmutable gameImmutable, String nickname);


    /**
     * Shows generic error message
     */
    protected abstract void show_NaNMsg();

    /**
     * Shows the message that asks to return to the main menu
     */
    protected abstract void show_returnToMenuMsg();


    //----------------------
    //ACTIONS
    //----------------------

    /**
     * Shows message on latest event added
     * @param input the string of the latest event to add
     */
    protected abstract void addLatestEvent(String input, GameImmutable gameImmutable);

    /**
     * @param gameImmutable the gameImmutable in which search for the longest message
     * @return the length of the longest message registered in chat
     */
    protected abstract int getLengthLongestMessage(GameImmutable gameImmutable);

    /**
     * @param msg   the message to add
     * @param gameImmutable the gameImmutable to which add the message
     */
    protected abstract void addMessage(ChatMessage msg, GameImmutable gameImmutable);

    /**
     * Resets the latest events
     */
    protected abstract void resetLatestEvents();

    /**
     * Shows an error when there's no connection
     */
    protected abstract void show_noConnectionError();

    protected abstract void showAskIndex(GameImmutable gameImmutable);

    protected abstract void show_wrongSelectionHandMsg();

    protected abstract void showAskCoordinates(GameImmutable gameImmutable);
    protected abstract void showAskToChooseADeck();

    protected abstract void showCardCannotBePlaced(GameImmutable gameImmutable, String nickname);

    protected abstract void showInvalidInput();

    protected abstract void showCodex(GameImmutable gameImmutable);

    protected abstract void showAskSize(GameImmutable gameImmutable);

    protected abstract void show_sizeSetted(int size, GameImmutable gameImmutable);

    protected abstract void showCardAddedToHand(GameImmutable gameImmutable, Card card);

    protected abstract void showWinner(GameImmutable gameImmutable);

    protected abstract void showObjectiveChosen(GameImmutable gameImmutable, CardObjective cardObjective, String nickname);

    protected abstract void showObjectiveNotChosen(GameImmutable gameImmutable);

    protected abstract void showReqNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement);

    protected abstract void show_GameTitle();

    protected abstract void showInvalidNickname(String nickname);

    protected abstract void showObjectiveChosen(Game game, Card card);

    protected abstract void showAskSide(GameImmutable game, Card card);

    protected abstract void show_askSideStarter(GameImmutable game, String nickname);

    protected abstract void setNickname(String nickname);

    protected abstract void showDesk(GameImmutable gameImmutable, String nickname);

    protected abstract void showYouLeft();

    public abstract void showChat(GameImmutable gameImmutable);

    //public abstract void showDrawnCard(GameImmutable model);


    //public abstract void showPoints(GameImmutable model);

}
