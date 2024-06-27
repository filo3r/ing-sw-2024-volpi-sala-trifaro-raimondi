package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.view.OptionSelection;
import it.polimi.ingsw.gc03.view.gui.controllers.*;
import it.polimi.ingsw.gc03.view.inputHandler.InputReaderGUI;
import it.polimi.ingsw.gc03.view.ui.Flow;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The ApplicationGui class represents the main GUI application for the game,
 * handling the initialization, scene management, and various interactions.
 */
public class ApplicationGui extends Application {

    /**
     * The flow manager for the game.
     */
    private Flow flow;

    /**
     * The connection type (e.g., LOCAL, REMOTE).
     */
    private String connectionType;

    /**
     * The IP address of the server.
     */
    private String ipAddress;

    /**
     * The port number of the server.
     */
    private int port;

    /**
     * The main stage of the application.
     */
    private Stage stage;

    /**
     * The popup stage for displaying additional windows.
     */
    private Stage popUpStage;

    /**
     * The root layout for the main stage.
     */
    private StackPane root;

    /**
     * A list of all scenes used in the application.
     */
    private ArrayList<Scenes> scenes;

    /**
     * The player's nickname.
     */
    private String nickname;

    /**
     * Indicates if the window is being resized.
     */
    private boolean resizing = true;

    /**
     * The previous width of the window.
     */
    private double widthOld;

    /**
     * The previous height of the window.
     */
    private double heightOld;

    /**
     * Starts the application.
     * @param stage The primary stage for this application.
     * @throws Exception If an error occurs during startup.
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parameters params = getParameters();
        List<String> parameters = params.getRaw();
        connectionType = parameters.get(0);
        ipAddress = parameters.get(1);
        port = Integer.parseInt(parameters.get(2));
        OptionSelection optionSelection = OptionSelection.valueOf(connectionType);
        this.flow = new Flow(this, optionSelection, ipAddress, port);
        this.stage = stage;
        this.stage.setTitle("CodeX");
        getScene();
        root = new StackPane();
        // Set icon
        this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/it/polimi/ingsw/gc03/gui/images/other/icon_javafx.png")));
    }

    /**
     * Creates a list of scenes from the SceneEnum values.
     */
    private void getScene() {
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController gc;
        for (SceneEnum sceneEnum : SceneEnum.values()) {
            String fxmlPath = sceneEnum.getValue();
            loader = new FXMLLoader(getClass().getResource(fxmlPath));
            try {
                root = loader.load();
                gc = loader.getController();
                scenes.add(new Scenes(new Scene(root), sceneEnum, gc));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Sets up the input reader for the GUI.
     * @param inputReader The InputReaderGUI instance.
     */
    public void setupGUIInputController(InputReaderGUI inputReader) {
        getScene();
        for (Scenes s : scenes) {
            s.setInputReaderGUI(inputReader);
        }
    }

    /**
     * Gets the controller for a specific scene.
     * @param scene The SceneEnum value representing the scene.
     * @return The GenericController associated with the scene.
     */
    public GenericController getController(SceneEnum scene) {
        int index = scenes.indexOf(scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().getFirst());
        return scenes.get(index).getGenericController();
    }

    /**
     * Sets the active scene and shows it.
     * @param scene The SceneEnum value representing the scene to activate.
     */
    public void setActiveScene(SceneEnum scene) {
        this.stage.setTitle("Codex - "+nickname+" - " + scene.name());
        if (!scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().isEmpty()) {
            Scenes activeScene = scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().getFirst();
            switch (scene) {
                case GAME_TITLE -> {
                    this.stage.initStyle(StageStyle.UNDECORATED);
                    this.stage.setAlwaysOnTop(false);
                }
                case POPUP, GAME_RUNNING -> {
                    this.closePopUps();
                }
                case MENU -> {
                    stage.setAlwaysOnTop(false);
                    stage.centerOnScreen();
                }
                default -> {
                    this.stage.setAlwaysOnTop(false);
                }
            }
            this.stage.setScene(activeScene.getScene());
            Node nodeGroup = stage.getScene().lookup("#contentGroup");
            if(nodeGroup != null){
                nodeGroup.getTransforms().clear();
            }
            Node node = stage.getScene().lookup("#content");
            if(node != null){
                node.getTransforms().clear();
            }
            this.stage.show();
        }
        try {
            widthOld=stage.getScene().getWidth();
            heightOld=stage.getScene().getHeight();
            this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                rescale((double)newVal,heightOld);
            });

            this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
                rescale(widthOld,(double)newVal);
            });
            resizing=true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Rescales the scene based on the new width and height.
     * @param width The new width of the window.
     * @param height The new height of the window.
     */
    public void rescale(double width, double height) {
        if(resizing) {
            double widthWindow = width;
            double heightWindow = height;

            double w = widthWindow / widthOld;
            double h = heightWindow / heightOld;

            widthOld = widthWindow;
            heightOld = heightWindow;
            Scale scale = new Scale(w, h, 0, 0);
            try{
                stage.getScene().lookup("#contentGroup").getTransforms().add(scale);
            } catch (Exception e){

            }
            try{
                stage.getScene().lookup("#content").getTransforms().add(scale);
            } catch (Exception e){

            }
        }
    }

    /**
     * Shows the lobby scene with the current game state.
     * @param gameImmutable The immutable game state.
     */
    public void showLobby(GameImmutable gameImmutable) {
        int gameSize = gameImmutable.getSize();
        for (int i = 0; i < gameSize; i++) {
            Pane panePlayerLobby = (Pane) this.stage.getScene().getRoot().lookup("#pane" + (i + 1));
            if (panePlayerLobby != null) {
                panePlayerLobby.setVisible(false);
            }
        }
        for (Player p : gameImmutable.getPlayers()) {
            showPlayerInLobby(p.getNickname(), gameImmutable);
        }
    }

    /**
     * Shows a player in the lobby scene.
     * @param nickname The player's nickname.
     * @param gameImmutable The immutable game state.
     */
    private void showPlayerInLobby(String nickname, GameImmutable gameImmutable) {
        int playerIndex = gameImmutable.getPlayers().indexOf(gameImmutable.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).findFirst().orElse(null))+1;
        if (playerIndex <= gameImmutable.getSize()) {
            SceneEnum sceneEnum = null;
            switch (playerIndex) {
                case 1 -> sceneEnum = SceneEnum.LOBBY_PLAYER1;
                case 2 -> sceneEnum = SceneEnum.LOBBY_PLAYER2;
                case 3 -> sceneEnum = SceneEnum.LOBBY_PLAYER3;
                case 4 -> sceneEnum = SceneEnum.LOBBY_PLAYER4;
            }
            SceneEnum finalSceneEnum = sceneEnum;
            Scenes sceneToLoad = scenes.stream().filter(x -> x.getSceneEnum().equals(finalSceneEnum)).findFirst().orElse(null);
            if (sceneToLoad != null) {
                Pane paneToLoad = (Pane) sceneToLoad.getScene().getRoot();
                ((LobbyPlayerController) sceneToLoad.getGenericController()).setNickname(nickname);
                Pane panePlayerLobby = (Pane) this.stage.getScene().getRoot().lookup("#pane" + playerIndex);
                if (panePlayerLobby != null) {
                    panePlayerLobby.setVisible(true);
                    panePlayerLobby.getChildren().clear();
                    paneToLoad.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                    StackPane stackPane = new StackPane();
                    stackPane.getChildren().add(paneToLoad);
                    StackPane.setAlignment(paneToLoad, Pos.CENTER);
                    paneToLoad.prefWidthProperty().bind(panePlayerLobby.widthProperty());
                    paneToLoad.prefHeightProperty().bind(panePlayerLobby.heightProperty());
                    panePlayerLobby.getChildren().add(stackPane);
                }
            }
        }
    }

    /**
     * Opens a popup scene.
     * @param scene The SceneEnum value representing the popup scene.
     */
    public void openPopUps(SceneEnum scene){
        Scene activeScene = scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().getFirst().getScene();
        popUpStage = new Stage();
        popUpStage.setResizable(false);
        popUpStage.setScene(activeScene);
        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.show();
        popUpStage.setX(stage.getX()+(stage.getWidth()-activeScene.getWidth())*0.5);
        popUpStage.setY(stage.getY()+(stage.getHeight()-activeScene.getHeight())*0.5);
    }

    /**
     * Closes the popup scene.
     */
    public void closePopUps() {
        if(popUpStage != null){
            popUpStage.hide();
        }
    }

    /**
     * Displays the game running scene with the current game state.
     * @param gameImmutable The immutable game state.
     * @param nickname The player's nickname.
     */
    public void showGameRunning(GameImmutable gameImmutable, String nickname){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setDeckGold(gameImmutable);
        controller.setDeckResource(gameImmutable);
        controller.setDisplayed(gameImmutable);
        controller.setSharedObjective(gameImmutable);
        controller.setHand(gameImmutable,nickname);
        controller.setPersonalObjective(gameImmutable,nickname);
        controller.setBoards(gameImmutable);
        controller.setPoints(gameImmutable);
        controller.setGameId(gameImmutable.getIdGame());
        controller.setTurnUsername(gameImmutable);
        controller.clear(gameImmutable);
        controller.setReceivers(gameImmutable,nickname);
        controller.setCodex(gameImmutable, nickname);
    }

    /**
     * Sets the nickname of the player whose turn it is.
     * @param gameImmutable The immutable game state.
     */
    public void showTurnUsername(GameImmutable gameImmutable){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setTurnUsername(gameImmutable);
    }

    /**
     * Displays the card starter selection scene.
     * @param game The immutable game state.
     * @param nickname The player's nickname.
     */
    public void show_askSideStarter(GameImmutable game, String nickname){
        CardStarterController controller = (CardStarterController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.CARD_STARTER)).toList().getFirst().getGenericController();
        controller.showCardStarter(game, nickname);
    }

    /**
     * Displays the card objective selection scene.
     * @param gameImmutable The immutable game state.
     */
    public void showObjectiveNotChosen(GameImmutable gameImmutable){
        CardObjectiveController controller = (CardObjectiveController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.CARD_OBJECTIVE)).toList().getFirst().getGenericController();
        controller.showCardObjective(gameImmutable, this.nickname);
    }

    /**
     * Displays the winners scene with the final game state.
     * @param gameImmutable The immutable game state.
     */
    public void showWinner(GameImmutable gameImmutable){
        WinnersController controller = (WinnersController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.WINNERS)).toList().getFirst().getGenericController();
        controller.showPoints(gameImmutable);

    }

    /**
     * Displays a popup with a message.
     * @param message The message to display.
     * @param label The label for the popup.
     */
    public void showPopup(String message, String label){
        PopupController controller = (PopupController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.POPUP)).toList().getFirst().getGenericController();
        controller.setText(message,false);
        controller.setLabel(label);
        System.err.println(message);
    }

    /**
     * Displays a fatal error popup with a message.
     * @param message The error message to display.
     */
    public void showFatalError(String message){
        PopupController controller = (PopupController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.POPUP)).toList().getFirst().getGenericController();
        controller.setText(message,true);
        controller.setLabel("FatalError");
    }

    /**
     * Creates a new window.
     */
    public void createNewWindow() {
        Stage newStage = new Stage();
        newStage.setScene(this.stage.getScene());
        newStage.show();
        this.stage.close();
        this.stage = newStage;
        this.stage.centerOnScreen();
        this.stage.setAlwaysOnTop(true);
        this.stage.setOnCloseRequest(event -> {
            System.exit(1);
        });
    }

    /**
     * Sets the player's nickname.
     * @param nickname The player's nickname.
     */
    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    /**
     * Main method to launch the application.
     * @param args The command-line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Adds a message to the chat in the game running scene.
     * @param message The chat message.
     * @param gameImmutable The immutable game state.
     */
    public void showChat(ChatMessage message, GameImmutable gameImmutable){
     GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
     controller.addMessages(message,nickname, gameImmutable);
    }

    /**
     * Adds an event to the event list in the game running scene.
     * @param event The event message.
     * @param gameImmutable The immutable game state.
     */
    public void showLatestEvent(String event, GameImmutable gameImmutable){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.addLatestEvents(event, gameImmutable);
    }

    /**
     * Sets the action to "draw" in the game running scene.
     */
    public void setActionIsDraw(){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.actionIsDraw();
    }

    /**
     * Sets the action to "place" in the game running scene.
     */
    public void setActionIsPlace(){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.actionIsPlace();
    }

}