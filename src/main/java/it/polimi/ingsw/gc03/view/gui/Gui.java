package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.view.ui.UI;

import java.util.ArrayList;

public class Gui extends UI {

    @Override
    protected void init() {

    }

    @Override
    protected void show_menuOptions() {

    }

    @Override
    protected void show_creatingNewGameMsg(String nickname) {

    }

    @Override
    protected void show_joiningFirstAvailableMsg(String nickname) {

    }

    @Override
    protected void show_joiningToGameIdMsg(int idGame, String nickname) {

    }

    @Override
    protected void show_inputGameIdMsg() {

    }

    @Override
    protected void show_insertNicknameMsg() {

    }

    @Override
    protected void show_chosenNickname(String nickname) {

    }

    @Override
    protected void show_gameStarted(GameImmutable model) {

    }

    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {

    }

    @Override
    protected void show_gameEnded(GameImmutable model) {

    }

    @Override
    protected void show_playerJoined(GameImmutable gameModel, String nick) {

    }

    @Override
    protected void show_nextTurnOrPlayerReconnected(GameImmutable model, String nickname) {

    }

    @Override
    protected void show_playerHand(GameImmutable gameModel, String nickname) {

    }

    @Override
    protected void show_sentMessage(GameImmutable model, String nickname) {

    }

    @Override
    protected void show_NaNMsg() {

    }

    @Override
    protected void show_returnToMenuMsg() {

    }

    @Override
    protected void addImportantEvent(String input) {

    }

    @Override
    protected int getLengthLongestMessage(GameImmutable model) {
        return 0;
    }

    @Override
    protected void addMessage(ChatMessage msg, GameImmutable model) {

    }

    @Override
    protected void resetImportantEvents() {

    }

    @Override
    protected void show_noConnectionError() {

    }

    @Override
    protected void showAskIndex(GameImmutable model) {

    }

    @Override
    protected void show_wrongSelectionHandMsg() {

    }

    @Override
    protected void showAskCoordinatesCol(GameImmutable model) {

    }

    @Override
    protected void showAskCoordinatesRow(GameImmutable model) {

    }

    @Override
    protected void showDisplayedResource(GameImmutable gameModel) {

    }

    @Override
    protected void showDisplayedGold(GameImmutable gameModel) {

    }

    @Override
    protected void showAskToChooseADeck() {

    }

    @Override
    protected void showCardCannotBePlaced(GameImmutable model, String nickname) {

    }

    @Override
    protected void showDrawnCard(GameImmutable model) {

    }

    @Override
    protected void showPlaceStarterCardOnCodex(GameImmutable model) {

    }

    @Override
    protected void showInvalidInput() {

    }

    @Override
    protected void show_askSide(GameImmutable model) {

    }

    @Override
    protected void show_askChooseACardObjective(GameImmutable model, String nickname) {

    }

    @Override
    protected void showCardObjectiveToChoose(GameImmutable model) {

    }

    @Override
    protected void showCommonCards(GameImmutable model) {

    }

    @Override
    protected void showCodex(GameImmutable model) {

    }

    @Override
    protected void showAskSize(GameImmutable model) {

    }

    @Override
    protected void show_sizeSetted(int size, GameImmutable gameImmutable) {

    }

    @Override
    protected void showCardAddedToHand(GameImmutable model, Card card) {

    }

    @Override
    protected void showWinner(GameImmutable model) {

    }

    @Override
    protected void showAskUI() {

    }

    @Override
    protected void showAskConnection() {

    }

    @Override
    protected void showAskNickname() {

    }

    @Override
    protected void showAskGameID() {

    }

    @Override
    protected void showAskJoin() {

    }

    @Override
    protected void showCardHasBeenDrawn(GameImmutable gameModel) {

    }

    @Override
    protected void showAskReconnectGameID() {

    }

    @Override
    protected void showCardNotAddedHand(GameImmutable model) {

    }

    @Override
    protected void showObjectiveChosen(GameImmutable model, CardObjective cardObjective, String nickname) {

    }

    @Override
    protected void showObjectiveNotChosen(GameImmutable model) {

    }

    @Override
    protected void showReqNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) {

    }

    @Override
    protected void show_GameTitle() {

    }

    @Override
    protected void showInvalidNickname(String nickname) {

    }

    @Override
    protected void showObjectiveChosen(Game game, Card card) {

    }

    @Override
    protected void show_askSide(Game game) {

    }

    @Override
    protected void show_askSideStarter(GameImmutable game, String nickname) {

    }

    @Override
    protected void setNickname(String nickname) {

    }
}
