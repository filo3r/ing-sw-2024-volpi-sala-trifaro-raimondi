package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.view.gui.controllers.LobbyController;
import it.polimi.ingsw.gc03.view.inputHandler.InputReaderGUI;
import it.polimi.ingsw.gc03.view.ui.UI;
import javafx.application.Platform;
import java.util.ArrayList;

/**
 * The Gui class represents the graphical user interface for the game,
 * extending the abstract UI class and implementing its methods.
 */
public class Gui extends UI {

    /**
     * The main application GUI instance.
     */
    private ApplicationGui applicationGui;

    /**
     * The input reader for GUI.
     */
    private InputReaderGUI inputReaderGUI;

    /**
     * The player's nickname.
     */
    private String nickname = null;

    /**
     * Constructor for the Gui class.
     * @param applicationGui The ApplicationGui instance.
     * @param inputReaderGUI The InputReaderGUI instance.
     */
    public Gui(ApplicationGui applicationGui, InputReaderGUI inputReaderGUI){
        this.applicationGui = applicationGui;
        this.inputReaderGUI = inputReaderGUI;
        init();
    }

    /**
     * Initializes the GUI by setting up the input controller.
     */
    @Override
    protected void init() {
        Platform.runLater(()->this.applicationGui.setupGUIInputController(this.inputReaderGUI));
    }

    /**
     * Moves the screen view. Not implemented for GUI.
     * @param x The x-coordinate to move.
     * @param y The y-coordinate to move.
     */
    @Override
    protected void moveScreenView(int x, int y) {}

    /**
     * Resizes the screen view. Not implemented for GUI.
     * @param x The new width.
     * @param y The new height.
     */
    @Override
    protected void resizeScreenView(int x, int y) {}

    /**
     * Displays the menu options.
     */
    @Override
    protected void show_menuOptions() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.MENU));
    }

    /**
     * Displays a message indicating that a new game is being created. Not implemented for GUI.
     * @param nickname The player's nickname.
     */
    @Override
    protected void show_creatingNewGameMsg(String nickname) {}

    /**
     * Displays a message indicating that the player is joining the first available game. Not implemented for GUI.
     * @param nickname The player's nickname.
     */
    @Override
    protected void show_joiningFirstAvailableMsg(String nickname) {}

    /**
     * Displays a message indicating that the player is joining a specific game by ID. Not implemented for GUI.
     * @param idGame The game ID.
     * @param nickname The player's nickname.
     */
    @Override
    protected void show_joiningToGameIdMsg(int idGame, String nickname) {}

    /**
     * Displays the input prompt for the game ID.
     */
    @Override
    protected void show_inputGameIdMsg() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.GAME_ID));
    }

    /**
     * Displays the input prompt for the player's nickname.
     */
    @Override
    protected void show_insertNicknameMsg() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.NICKNAME));
    }

    /**
     * Displays a message indicating the chosen nickname. Not implemented for GUI.
     * @param nickname The player's chosen nickname.
     */
    @Override
    protected void show_chosenNickname(String nickname) {
    }

    /**
     * Displays a message indicating that the game has started. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void show_gameStarted(GameImmutable gameImmutable) {}

    /**
     * Displays an error message when no available games to join are found.
     * @param msgToVisualize The error message to display.
     */
    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {
        Platform.runLater(()->this.applicationGui.showPopup(msgToVisualize,"ERROR"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));

    }

    /**
     * Displays a message indicating that the game has ended.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void show_gameEnded(GameImmutable gameImmutable) {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.NICKNAME));
        inputReaderGUI.addTxt("\n");
    }

    /**
     * Displays a message indicating that a player has joined the game.
     * @param gameImmutable The immutable game instance.
     * @param nickname The player's nickname.
     */
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

    /**
     * Displays a message indicating the next player's turn.
     * @param gameImmutable The immutable game instance.
     * @param nickname The next player's nickname.
     */
    @Override
    protected void showNextTurn(GameImmutable gameImmutable, String nickname) {
        Platform.runLater(()->this.applicationGui.showTurnUsername(gameImmutable));
    }

    /**
     * Displays the player's hand. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     * @param nickname The player's nickname.
     */
    @Override
    protected void show_playerHand(GameImmutable gameImmutable, String nickname) {}

    /**
     * Displays a message that was sent. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     * @param nickname The player's nickname.
     */
    @Override
    protected void show_sentMessage(GameImmutable gameImmutable, String nickname) {}

    /**
     * Displays a NaN (Not a Number) message. Not implemented for GUI.
     */
    @Override
    protected void show_NaNMsg() {}

    /**
     * Displays a return to menu message. Not implemented for GUI.
     */
    @Override
    protected void show_returnToMenuMsg() {}

    /**
     * Adds a latest event to be displayed.
     * @param input The event message.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void addLatestEvent(String input, GameImmutable gameImmutable) {
        Platform.runLater(()->this.applicationGui.showLatestEvent(input, gameImmutable));
    }

    /**
     * Returns the length of the longest message in the game's chat. Not used in the GUI.
     * @param gameImmutable The immutable game instance.
     * @return The length of the longest message.
     */
    @Override
    protected int getLengthLongestMessage(GameImmutable gameImmutable) {
        return 0;
    }

    /**
     * Adds a chat message to the personal chat and displays a notification.
     * @param msg The chat message.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void addMessage(ChatMessage msg, GameImmutable gameImmutable) {
        Platform.runLater(()->this.applicationGui.showChat(msg, gameImmutable));
    }

    /**
     * Displays a notification for no connection error.
     */
    @Override
    protected void show_noConnectionError() {
        Platform.runLater(()->this.applicationGui.showFatalError("Connection error!"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

    /**
     * Prompts the user to choose the index of a card from their hand.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void showAskIndex(GameImmutable gameImmutable) {
        Platform.runLater(()->this.applicationGui.setActionIsPlace());
        Platform.runLater(()->this.applicationGui.showPopup("Please, place a card from your hand \n     by dragging it on the grid","Prompt"));
        Platform.runLater(()-> this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

    /**
     * Displays a message indicating a wrong selection from the hand. Not implemented for GUI.
     */
    @Override
    protected void show_wrongSelectionHandMsg() {}

    /**
     * Prompts the user to choose coordinates to place a card. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void showAskCoordinates(GameImmutable gameImmutable) {
    }

    /**
     * Prompts the user to choose a deck.
     */
    @Override
    protected void showAskToChooseADeck() {
        Platform.runLater(()->this.applicationGui.setActionIsDraw());
        Platform.runLater(()->this.applicationGui.showPopup("Please, choose a card to draw","Prompt"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

    /**
     * Displays a notification that the card cannot be placed.
     * @param gameImmutable The immutable game instance.
     * @param nickname The player's nickname.
     */
    @Override
    protected void showCardCannotBePlaced(GameImmutable gameImmutable, String nickname) {
        Platform.runLater(()->this.applicationGui.showPopup("You can't place a card here!","Prompt"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

    /**
     * Displays a notification for invalid input.
     */
    @Override
    protected void showInvalidInput() {
        Platform.runLater(()->this.applicationGui.showPopup("Invalid Input","ERROR"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

    /**
     * Displays the codex of the player.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void showCodex(GameImmutable gameImmutable) {
        Platform.runLater(()-> this.applicationGui.setActiveScene(SceneEnum.GAME_RUNNING));
        Platform.runLater(()-> this.applicationGui.showGameRunning(gameImmutable,nickname));
    }

    /**
     * Prompts the user to choose the game size.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void showAskSize(GameImmutable gameImmutable) {
        Platform.runLater(()->{this.applicationGui.setActiveScene(SceneEnum.CHOOSE_SIZE);});
    }

    /**
     * Displays a notification that the game size has been updated.
     * @param size The new game size.
     * @param gameImmutable The immutable game instance.
     */
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

    /**
     * Displays a notification that a card has been added to the player's hand. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     * @param card The card added to the hand.
     */
    @Override
    protected void showCardAddedToHand(GameImmutable gameImmutable, Card card) {}

    /**
     * Displays the winner of the game.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void showWinner(GameImmutable gameImmutable) {
        Platform.runLater(()->this.applicationGui.showWinner(gameImmutable));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.WINNERS));
        inputReaderGUI.addTxt("\n");
    }

    /**
     * Displays a notification that a personal objective has been chosen. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     * @param cardObjective The card objective chosen.
     * @param nickname The player's nickname.
     */
    @Override
    protected void showObjectiveChosen(GameImmutable gameImmutable, CardObjective cardObjective, String nickname) {
    }

    /**
     * Prompts the user to choose their personal objective.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    protected void showObjectiveNotChosen(GameImmutable gameImmutable) {
        Platform.runLater(()->this.applicationGui.showObjectiveNotChosen(gameImmutable));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.CARD_OBJECTIVE));
    }

    /**
     * Displays a notification that the requirements for card placement were not respected.
     * @param gameImmutable The immutable game instance.
     * @param requirementsPlacement The list of requirements not respected.
     */
    @Override
    protected void showReqNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) {
        Platform.runLater(()->this.applicationGui.showPopup("You can't place the card because you dont have enough resources on your Codex","ERROR"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

    /**
     * Displays the game title scene.
     */
    @Override
    protected void show_GameTitle() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.GAME_TITLE));
        Platform.runLater(()->this.applicationGui.createNewWindow());

    }

    /**
     * Displays a notification for an invalid nickname.
     * @param nickname The invalid nickname.
     */
    @Override
    protected void showInvalidNickname(String nickname) {
        Platform.runLater(()->this.applicationGui.showPopup("Invalid Nickname","ERROR"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

    /**
     * Displays a notification that a personal objective has been chosen. Not implemented for GUI.
     * @param game The game instance.
     * @param card The chosen card.
     */
    @Override
    protected void showObjectiveChosen(Game game, Card card) {}

    /**
     * Prompts the user to choose the side of a card. Not implemented for GUI.
     * @param game The game instance.
     * @param card The card to place.
     */
    @Override
    protected void showAskSide(GameImmutable game, Card card) {}

    /**
     * Prompts the user to choose a side of the starter card.
     * @param game The game instance.
     * @param nickname The player's nickname.
     */
    @Override
    protected void show_askSideStarter(GameImmutable game, String nickname) {
        Platform.runLater(()->this.applicationGui.show_askSideStarter(game, nickname));
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.CARD_STARTER));
    }

    /**
     * Sets the player's nickname.
     * @param nickname The player's nickname.
     */
    @Override
    protected void setNickname(String nickname) {
        if(this.nickname == null){
            this.nickname = nickname;
            this.applicationGui.setNickname(nickname);
        }
        if(nickname == null){
            this.nickname = null;
            this.applicationGui.setNickname(null);
        }
    }

    /**
     * Displays the desk, containing the decks, displayed cards and the public and private objectives. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     * @param player The player's nickname.
     */
    @Override
    protected void showDesk(GameImmutable gameImmutable, String player) {}

    /**
     * Displays a notification that the player has left the game.
     */
    @Override
    protected void showYouLeft() {
        Platform.runLater(()->this.applicationGui.setActiveScene(SceneEnum.NICKNAME));
    }

    /**
     * Displays the chat messages on the screen. Not implemented for GUI.
     * @param gameImmutable The immutable game instance.
     */
    @Override
    public void showChat(GameImmutable gameImmutable) {}

    /**
     * Displays a notification that a player has disconnected.
     * @param nickname The player's nickname.
     */
    @Override
    public void showPlayerDisconnected(String nickname) {
        Platform.runLater(()->this.applicationGui.showPopup(nickname+" has disconnected!","DISCONNECTION!"));
        Platform.runLater(()->this.applicationGui.openPopUps(SceneEnum.POPUP));
    }

}