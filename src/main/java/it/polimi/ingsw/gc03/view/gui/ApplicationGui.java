package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.view.OptionSelection;
import it.polimi.ingsw.gc03.view.gui.controllers.*;
import it.polimi.ingsw.gc03.view.ui.Flow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.*;
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
        getScene();
        this.stage = stage;
        this.stage.setTitle("CodeX");
        root = new StackPane();
    }

    private void getScene() {
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController gc;
        for (int i = 0; i < SceneEnum.values().length; i++) {
            loader = new FXMLLoader(getClass().getResource(SceneEnum.values()[i].getValue()));
            try {
                root = loader.load();
                gc = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scenes.add(new Scenes(new Scene(root), SceneEnum.values()[i], gc));
        }
    }



    public GenericController getController(SceneEnum scene) {
        int index = scenes.indexOf(scenes.stream().filter(x->x.getSceneEnum().equals(scene)).toList().getFirst());
        return scenes.get(index).getGenericController();
    }

    public void setActiveScene(SceneEnum scene) {
        this.stage.setTitle("Codex - " + scene.name());
        if (!scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().isEmpty()) {
            Scenes activeScene = scenes.stream().filter(x -> x.getSceneEnum().equals(scene)).toList().getFirst();
            switch (scene) {
                case ERROR, GAME_RUNNING -> {
                    this.closePopUps();
                }
                case MENU -> {
                    stage.initStyle(StageStyle.UNDECORATED);
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

    }

    public void showPlayersInLobby(GameImmutable model){
        SceneEnum scene = null;
        int size = model.getSize();
        switch(size){
            case 2->{
                scene = SceneEnum.LOBBY2;
            }
            case 3->{
                scene = SceneEnum.LOBBY3;
            }
            case 4->{
                scene = SceneEnum.LOBBY4;
            }
        }
        SceneEnum finalScene = scene;
        Scenes sceneToGet = scenes.stream().filter(x -> x.getSceneEnum().equals(finalScene)).toList().getFirst();
        ((LobbyController) sceneToGet.getGenericController()).setUsername(nickname);
        ((LobbyController) sceneToGet.getGenericController()).setGameId(model.getIdGame());
        for(Player player: model.getPlayers()) {
            showPlayerInLobby(player,model);
        }
    }
    private void showPlayerInLobby(Player player,GameImmutable model){
        SceneEnum scene = null;
        int indexPlayer = model.getPlayers().indexOf(player);
        switch(indexPlayer){
            case 0->{
                scene = SceneEnum.LOBBY_PLAYER1;
            }
            case 1->{
                scene = SceneEnum.LOBBY_PLAYER2;
            }
            case 2->{
                scene = SceneEnum.LOBBY_PLAYER3;
            }
            case 3->{
                scene = SceneEnum.LOBBY_PLAYER4;
            }
        }
        SceneEnum finalScene = scene;
        Scenes sceneToGet = scenes.stream().filter(x -> x.getSceneEnum().equals(finalScene)).toList().getFirst();
        if(!nickname.equals(player.getNickname())) {
            ((LobbyPlayerController) sceneToGet.getGenericController()).setNickname(player.getNickname());
        }else{
            ((LobbyPlayerController)sceneToGet.getGenericController()).setNickname("You");
        }
    }

    private void openPopUps(Scene scene){
        popUpStage = new Stage();
        popUpStage.setResizable(false);
        popUpStage.setScene(scene);
        popUpStage.initStyle(StageStyle.UNDECORATED);
        popUpStage.setOnCloseRequest(windowEvent -> System.exit(0));
        popUpStage.show();
        popUpStage.setX(stage.getX()+(stage.getWidth()-scene.getWidth())*0.5);
        popUpStage.setY(stage.getY()+(stage.getHeight()-scene.getHeight())*0.5);
    }
    private void closePopUps() {
        if(popUpStage != null){
            popUpStage.hide();
        }
    }
    /*public void showGameRunning(GameImmutable model,String nickname){
        GameRunningController controller = (GameRunningController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setNickname(model,nickname);
        controller.setDesk(model);
        controller.setCommonObjectives(model);
        controller.setHand(model);
        controller.setPersonalObjective(model);
        controller.setCodex(model);
    }


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
    public void showWinner(GameImmutable model){
        WinnersController controller = (WinnersController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.show(model);
    }
   public void close(){
        WinnersController controller = (WinnersController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.;
    }

    */
    public void showError(String message){
        ErrorController controller = (ErrorController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setErrorText(message,false);
    }
    public void showFatalError(String message){
        ErrorController controller = (ErrorController) scenes.stream().filter(x->x.getSceneEnum().equals(SceneEnum.GAME_RUNNING)).toList().getFirst().getGenericController();
        controller.setErrorText(message,true);
    } public void createNewWindowWithStyle() {
        Stage newStage = new Stage();
        newStage.setScene(this.stage.getScene());
        newStage.show();
        this.stage.close();
        this.stage = newStage;
        this.stage.centerOnScreen();
        this.stage.setAlwaysOnTop(true);
        this.stage.setOnCloseRequest(event -> {
            System.out.println("Closing all");
            System.exit(1);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

}


