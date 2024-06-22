package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.view.gui.controllers.ChooseSizeController;
import it.polimi.ingsw.gc03.view.gui.controllers.LobbyController;
import it.polimi.ingsw.gc03.view.gui.controllers.LobbyPlayerController;
import it.polimi.ingsw.gc03.view.inputHandler.InputReaderGUI;
import it.polimi.ingsw.gc03.view.ui.UI;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Gui extends UI {

    private ApplicationGui applicationGui;
    private InputReaderGUI inputReaderGUI;
    private String nickname = null;

    public Gui(ApplicationGui applicationGui, InputReaderGUI inputReaderGUI){
        this.applicationGui = applicationGui;
        this.inputReaderGUI = inputReaderGUI;
        init();
    }

    @Override
    protected void init() {
        importantEvents = new ArrayList<>();
    }

    @Override
    protected void moveScreenView(int x, int y) {

    }

    @Override
    protected void resizeScreenView(int x, int y) {

    }

    /*
    protected Image getFrontCardImage(Card card) throws IOException {
        String idCard = card.getIdCard();
        String imagePath ="/it.polimi.ingsw.gc03/images/cards_front/"+ idCard +".png";
        InputStream inputStream = getClass().getResourceAsStream(imagePath);
        return new Image(inputStream);
    }

    protected Image getBackCardImage(Card card) throws IOException{
        String idCard = card.getIdCard();
        String imagePath = "/it.polimi.ingsw.gc03/images/cards_back/"+ idCard +".png";
        InputStream inputStream = getClass().getResourceAsStream(imagePath);
        return new Image(inputStream);
    }

    protected Image getPlateauScore() throws IOException {
        String imagePath = "/it.polimi.ingsw.gc03/images/plateau_score.png";
        InputStream inputStream = getClass().getResourceAsStream(imagePath);
        return new Image(inputStream);
    }
    */

    @Override
    protected void show_menuOptions() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.MENU));
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
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.GAME_ID));
    }

    @Override
    protected void show_insertNicknameMsg() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.NICKNAME));
    }

    @Override
    protected void show_chosenNickname(String nickname) {
    }

    @Override
    protected void show_gameStarted(GameImmutable model) {
    }

    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {
        Platform.runLater(()->this.applicationGui.showError(msgToVisualize));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.ERROR));
    }

    @Override
    protected void show_gameEnded(GameImmutable model) {
        Platform.runLater(()->this.applicationGui.showWinner(model));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.WINNERS));
    }

    @Override
    protected void show_playerJoined(GameImmutable gameImmutable, String nickname) {
        SceneEnum scene = null;
        switch (gameImmutable.getSize()){
            case 1,2 -> scene = SceneEnum.LOBBY2;
            case 3 -> scene = SceneEnum.LOBBY3;
            case 4 -> scene = SceneEnum.LOBBY4;
        }

        Platform.runLater(()->this.applicationGui.closePopUps());
        LobbyController lc = (LobbyController) this.applicationGui.getController(scene);
        Platform.runLater(()->lc.setUsername(this.nickname));
        Platform.runLater(()->lc.setGameId(gameImmutable.getIdGame()));
        SceneEnum finalScene = scene;
        Platform.runLater(()-> this.applicationGui.setActiveScene(finalScene));
        Platform.runLater(()->this.applicationGui.showLobby(gameImmutable));
    }

    @Override
    protected void showNextTurn(GameImmutable model, String nickname) {
        Platform.runLater(()->this.applicationGui.showTurnUsername(model));
    }

    @Override
    protected void show_playerHand(GameImmutable gameModel, String nickname) {
    }

    @Override
    protected void show_sentMessage(GameImmutable model, String nickname) {
        Platform.runLater(()->this.applicationGui.showChat(model,nickname));
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
        Platform.runLater(()-> this.applicationGui.setActiveScene(SceneEnum.GAME_RUNNING));
        Platform.runLater(()-> this.applicationGui.showGameRunning(model,nickname));
    }

    @Override
    protected void show_wrongSelectionHandMsg() {
    }



    @Override
    protected void showAskCoordinates(GameImmutable model) {
    }

    @Override
    protected void showAskToChooseADeck() {
    }

    @Override
    protected void showCardCannotBePlaced(GameImmutable model, String nickname) {
    }



    @Override
    protected void showInvalidInput() {
        Platform.runLater(()->this.applicationGui.showError("Invalid Input"));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.ERROR));
    }


    @Override
    protected void showCodex(GameImmutable model) {
        Platform.runLater(()-> this.applicationGui.setActiveScene(SceneEnum.GAME_RUNNING));
        Platform.runLater(()-> this.applicationGui.showGameRunning(model,nickname));
    }

    @Override
    protected void showAskSize(GameImmutable gameImmutable) {
        Platform.runLater(()->{this.applicationGui.setActiveScene(SceneEnum.CHOOSE_SIZE);});
    }

    @Override
    protected void show_sizeSetted(int size, GameImmutable gameImmutable) {
        SceneEnum scene = null;
        switch (size) {
            case 2 -> scene = SceneEnum.LOBBY2;
            case 3 -> scene = SceneEnum.LOBBY3;
            case 4 -> scene = SceneEnum.LOBBY4;
        }
        Platform.runLater(()->this.applicationGui.closePopUps());
        LobbyController lc = (LobbyController) this.applicationGui.getController(scene);
        Platform.runLater(()-> lc.setUsername(this.nickname));
        Platform.runLater(()-> lc.setGameId(gameImmutable.getIdGame()));
        SceneEnum finalScene = scene;
        Platform.runLater(()-> this.applicationGui.setActiveScene(finalScene));
        Platform.runLater(()->this.applicationGui.showLobby(gameImmutable));
    }

    @Override
    protected void showCardAddedToHand(GameImmutable model, Card card) {

    }

    @Override
    protected void showWinner(GameImmutable model) {
        Platform.runLater(()->this.applicationGui.showWinner(model));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.WINNERS));
    }

    @Override
    protected void showObjectiveChosen(GameImmutable model, CardObjective cardObjective, String nickname) {
    }

    @Override
    protected void showObjectiveNotChosen(GameImmutable model) {
        Platform.runLater(()->this.applicationGui.showObjectiveNotChosen(model));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.CARD_OBJECTIVE));
    }

    @Override
    protected void showReqNotRespected(GameImmutable model, ArrayList<Value> requirementsPlacement) {
        StringBuilder req = new StringBuilder(" ");
        for(Value v: requirementsPlacement){
            req.append(v.name()+" ");
        }
        Platform.runLater(()->this.applicationGui.showError("You can't place the card because you dont have : "+req+" on your Codex" ));
        Platform.runLater(()->this.applicationGui.openPopUps(this.applicationGui.getErrorSceneForPopUp()));
    }

    @Override
    protected void show_GameTitle() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.GAME_TITLE));
        Platform.runLater(()->this.applicationGui.setupGUIInputController(this.inputReaderGUI));
        Platform.runLater(()->this.applicationGui.createNewWindowWithStyle());

    }

    @Override
    protected void showInvalidNickname(String nickname) {
        Platform.runLater(()->this.applicationGui.showError("Invalid Nickname"));
        Platform.runLater(()->this.applicationGui.openPopUps((this.applicationGui.getErrorSceneForPopUp())));
    }

    @Override
    protected void showObjectiveChosen(Game game, Card card) {

    }

    @Override
    protected void showAskSide(GameImmutable game, Card card) {

    }

    @Override
    protected void show_askSideStarter(GameImmutable game, String nickname) {
        Platform.runLater(()->this.applicationGui.show_askSideStarter(game, nickname));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.CARD_STARTER));
    }

    /**
     * Sets the player's nickname.
     *
     * @param nickname the player's nickname.
     */
    @Override
    protected void setNickname(String nickname) {
        if(this.nickname == null){
            this.nickname = nickname;
            this.applicationGui.setNickname(nickname);
        }
    }

    @Override
    protected void showDesk(GameImmutable gameImmutable, String player) {

    }

    @Override
    protected void showYouLeft() {

    }

    @Override
    public void showChat(GameImmutable gameImmutable) {

    }

    //@Override
    // public void showDrawnCard(GameImmutable model) {
    //}

    //@Override
    //public void showPoints(GameImmutable model) {
    //}

}
