package it.polimi.ingsw.gc03.view.ui;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.view.tui.print.AsyncPrint;

import java.util.ArrayList;
import java.util.List;

public abstract class UI {
    protected List<String> importantEvents; //events that needs to be showed always in screen

    /**
     * Initialises GUI or TUI
     */
    protected abstract void init();

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
     * @param model model where the game has started
     */
    protected abstract void show_gameStarted(GameImmutable model);

    /**
     * Shows error message when there are no games available for joining
     *
     * @param msgToVisualize message that needs visualisation
     */
    protected abstract void show_noAvailableGamesToJoin(String msgToVisualize);

    /**
     * Shows the game ended message
     *
     * @param model where the game is ended
     */
    protected abstract void show_gameEnded(GameImmutable model);

    /**
     * Shows the players that have joined
     *
     * @param gameModel model where events happen
     * @param nick      player's nickname
     */
    protected abstract void show_playerJoined(GameImmutable gameModel, String nick);

    /**
     * Show the message for next turn or reconnected player
     *
     * @param model    model where events happen
     * @param nickname nick of reconnected player (or of the player that is now in turn)
     */
    protected abstract void show_nextTurnOrPlayerReconnected(GameImmutable model, String nickname);

    /**
     * Message that shows the player's hand
     *
     * @param gameModel the model that has the player hand that needs to be shown
     */
    protected abstract void show_playerHand(GameImmutable gameModel);

    /**
     * Shows the message that has been sent
     *
     * @param model    the model where the message need to be shown
     * @param nickname the sender's nickname
     */
    protected abstract void show_sentMessage(GameImmutable model, String nickname);


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
     * Shows message on important event added
     * @param input the string of the important event to add
     */
    protected abstract void addImportantEvent(String input);

    /**
     * @param model the model in which search for the longest message
     * @return the length of the longest message registered in chat
     */
    protected abstract int getLengthLongestMessage(GameImmutable model);

    /**
     * @param msg   the message to add
     * @param model the model to which add the message
     */
    protected abstract void addMessage(ChatMessage msg, GameImmutable model);

    /**
     * Resets the important events
     */
    protected abstract void resetImportantEvents();

    /**
     * Shows an error when there's no connection
     */
    protected abstract void show_noConnectionError();

    protected abstract void showAskIndex(GameImmutable model);

    protected abstract void show_wrongSelectionHandMsg();

    protected abstract void showAskCoordinatesCol(GameImmutable model);

    protected abstract void showAskCoordinatesRow(GameImmutable model);

    protected abstract void showDisplayedResource(GameImmutable gameModel);

    protected abstract void showDisplayedGold(GameImmutable gameModel);

    protected abstract void showAskToChooseADeck();

    protected abstract void showCardCannotBePlaced(GameImmutable model, String nickname);

    protected abstract void showDrawnCard(GameImmutable model);

    protected abstract void showPlaceStarterCardOnCodex(GameImmutable model);

    protected abstract void showInvalidInput();

    protected abstract void show_askSide(GameImmutable model);

    protected abstract void show_askChooseACardObjective(GameImmutable model, String nickname);

    protected abstract void showCardObjectiveToChoose(GameImmutable model);

    protected abstract void showCommonCards(GameImmutable model);

    protected abstract void showCodex(GameImmutable model);

    protected abstract void showAskSize(GameImmutable model);

    protected abstract void show_sizeSetted(int size);

    protected abstract void showCardAddedToHand(GameImmutable model, Card card);

    protected abstract void showWinner(GameImmutable model);

    protected abstract void showAskUI();

    protected abstract void showAskConnection();

    protected abstract void showAskNickname();

    protected abstract void showAskGameID();

    protected abstract void showAskJoin();

    protected abstract void showCardHasBeenDrawn(GameImmutable gameModel);

    protected abstract void showAskReconnectGameID();

    protected abstract void showCardNotAddedHand(GameImmutable model);

    protected abstract void showObjectiveChosen(GameImmutable model, CardObjective cardObjective);

    protected abstract void showObjectiveNotChosen(GameImmutable model);

    protected abstract void showReqNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement);

    protected abstract void show_GameTitle();

    protected abstract void showInvalidNickname(String nickname);

    protected abstract void showObjectiveChosen(Game game, Card card);

    protected abstract void show_askSide(Game game);

    protected abstract void show_askSideStarter(GameImmutable game, String nickname);
}
