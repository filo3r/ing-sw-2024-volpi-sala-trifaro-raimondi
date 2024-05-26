package it.polimi.ingsw.gc03.view.ui;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.view.tui.AsyncPrint;

import java.util.ArrayList;
import java.util.List;

public abstract class UI {
    protected List<String> importantEvents; //events that needs to be showed always in screen

    /**
     * Initialises GUI or TUI
     */
    public abstract void init();

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
    public abstract void addImportantEvent(String input);

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

    public void showAskIndex(GameImmutable model) {
    }

    public void show_wrongSelectionHandMsg() {
    }

    public void showAskCoordinatesCol(GameImmutable model) {
    }

    public void showAskCoordinatesRow(GameImmutable model) {
    }

    public void showDisplayedResource(GameImmutable gameModel) {
    }

    public void showDisplayedGold(GameImmutable gameModel) {
    }

    public void showAskToChooseADeck() {
    }

    public void showCardCannotBePlaced(GameImmutable model, String nickname) {
    }

    public void showDrawnCard(GameImmutable model) {
    }

    public void showPlaceStarterCardOnCodex(GameImmutable model) {
    }

    public void showInvalidInput() {
    }

    public void show_askSide(GameImmutable model) {
    }

    public void show_askChooseACardObjective(GameImmutable model) {
    }

    public void showCardObjectiveToChoose(GameImmutable model) {
    }

    public void showCommonCards(GameImmutable model) {
    }

    public void showCodex(GameImmutable model) {
    }

    public void showAskSize(GameImmutable model) {
    }

    public void show_sizeSetted(int size) {
    }

    public void showCardAddedToHand(GameImmutable model, Card card) {
    }

    public void showWinner(GameImmutable model) {
    }

    public void showAskUI() {
        AsyncPrint.asyncPrint(new StringBuilder("\nCHOOSE WHICH TYPE OF UI YOU WANT TO USE:\n" +
                " t) TUI            g)GUI\n" +
                "(Write the letter and press Enter to make a choice)\n"));
    }

    public void showAskConnection() {

    }

    public void showAskNickname() {
    }

    public void showAskGameID() {
    }

    public void showAskJoin() {
    }

    public void showCardHasBeenDrawn(GameImmutable gameModel) {
    }

    public void showAskReconnectGameID() {
    }

    public void showCardNotAddedHand(GameImmutable model) {
    }

    public void showObjectiveChosen(GameImmutable model, CardObjective cardObjective) {
    }

    public void showObjectiveNotChosen(GameImmutable model) {
    }

    public void showReqNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) {
    }

    public void show_GameTitle(){
    }
}
