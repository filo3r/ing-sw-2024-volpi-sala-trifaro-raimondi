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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationGui extends Application {
    private Flow flow;
    private String connectionType;
    private String ipAddress;
    private int port;

    private Stage stage, popUpStage;
    private StackPane root;
    private ArrayList<Scenes> scenes;
    private String nickname;


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
    }

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

    public void setupGUIInputController(InputReaderGUI inputReader) {
        getScene();
        for (Scenes s : scenes) {
            s.setInputReaderGUI(inputReader);
        }
    }

    public GenericController getController(SceneEnum scene) {
        int index = scenes.indexOf(scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().getFirst());
        return scenes.get(index).getGenericController();
    }

    public void setActiveScene(SceneEnum scene) {
        this.stage.setTitle("Codex - " + scene.name());
        System.out.println(scene.name());
        if (!scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().isEmpty()) {
            Scenes activeScene = scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().getFirst();
            switch (scene) {
                case GAME_TITLE -> {
                    this.stage.initStyle(StageStyle.UNDECORATED);
                    this.stage.setAlwaysOnTop(true);
                    this.stage.centerOnScreen();
                }
                case ERROR, GAME_RUNNING -> {
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
            this.stage.show();
        }
        try {
            widthOld=stage.getScene().getWidth();
            heightOld=stage.getScene().getHeight();
            this.stage.widthProperty().addListener((obs, oldVal, newVal) -> {
                rescale((double)newVal-16,heightOld);
            });

            this.stage.heightProperty().addListener((obs, oldVal, newVal) -> {
                rescale(widthOld,(double)newVal-39);
            });
            resizing=true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private boolean resizing=true;
    private double widthOld, heightOld;
    /**
     * This method is used to rescale the scene.
     */
    public void rescale(double width, double heigh) {
        if(resizing) {
            double widthWindow = width;
            double heightWindow = heigh;


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


    public void openPopUps(Scene scene){
        popUpStage = new Stage();
        popUpStage.setResizable(false);
        popUpStage.setScene(scene);
        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.setOnCloseRequest(windowEvent -> System.exit(0));
        popUpStage.show();
        popUpStage.setX(stage.getX()+(stage.getWidth()-scene.getWidth())*0.5);
        popUpStage.setY(stage.getY()+(stage.getHeight()-scene.getHeight())*0.5);
    }
    public void closePopUps() {
        if(popUpStage != null){
            popUpStage.hide();
        }
    }
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
        controller.setReceivers(gameImmutable,nickname);
        controller.clearChat(gameImmutable);
        controller.clearEvents(gameImmutable);
    }
    public void showTurnUsername(GameImmutable gameImmutable){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setTurnUsername(gameImmutable);
    }
    /*

    public void showDrawnCard(GameImmutable model, String nickname){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setDrawnCard(model);
    }

    public void showPlacedCard(GameImmutable model,String nickname){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setAllCodex(model);
    }
    public void showMessageInGame(ChatMessage message){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setMessageToShow(message);
    }
    public void showAskCoordinates(){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.showSelectionCoordinates();
    }
   /* public void showMessages(GameImmutable model,String myNickname){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setMessage(model.getChat(),myNickname);
    }


    public void showPointsUpdate(GameImmutable model,Player player,int points){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setPointsUpdated(model,player,points);
    }

     */

    public void show_askSideStarter(GameImmutable game, String nickname){
        CardStarterController controller = (CardStarterController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.CARD_STARTER)).toList().getFirst().getGenericController();
        controller.showCardStarter(game, nickname);
    }

    public void showObjectiveNotChosen(GameImmutable gameImmutable){
        CardObjectiveController controller = (CardObjectiveController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.CARD_OBJECTIVE)).toList().getFirst().getGenericController();
        controller.showCardObjective(gameImmutable, this.nickname);
    }

    public void showWinner(GameImmutable gameImmutable){
        WinnersController controller = (WinnersController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.WINNERS)).toList().getFirst().getGenericController();
        controller.showPoints(gameImmutable);

    }
    public void close(){
        WinnersController controller = (WinnersController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
    }


    public void showError(String message){
        ErrorController controller = (ErrorController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.ERROR)).toList().getFirst().getGenericController();
        controller.setErrorText(message,false);
        System.err.println(message);
    }

    public void showFatalError(String message){
        ErrorController controller = (ErrorController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.ERROR)).toList().getFirst().getGenericController();
        controller.setErrorText(message,true);
    }

    public void createNewWindowWithStyle() {
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

    public void setNickname(String nickname){
        this.nickname = nickname;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void showChat(ChatMessage message, GameImmutable gameImmutable){
     GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
     controller.addMessages(message,nickname, gameImmutable);
    }

    public void showLatestEvent(String event, GameImmutable gameImmutable){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.addLatestEvents(event, gameImmutable);
    }

    public Scene getErrorSceneForPopUp(){
        Scene scene;
        scene = scenes.stream().filter(x -> x.getSceneEnum().equals(SceneEnum.ERROR)).toList().getFirst().getScene();
        return scene;
    }
}
