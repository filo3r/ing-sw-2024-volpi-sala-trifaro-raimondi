package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.view.gui.controllers.GenericController;
import it.polimi.ingsw.gc03.view.ui.Flow;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ApplicationGui extends Application {

    private Flow flow;

    private Stage stage,popUpStage;

    private StackPane root;
    private ArrayList<Scenes> scenes;

    public ApplicationGui(Flow flow){
        this.flow = flow;
    }


    @Override
    public void start(Stage stage) throws Exception {
        getScene();
        this.stage = stage;
        this.stage.setTitle("CodeX");
        root = new StackPane();
    }

    private void getScene(){
        scenes = new ArrayList<>();
        FXMLLoader loader;
        Parent root;
        GenericController gc;
        for(int i=0;i<SceneEnum.values().length;i++){
            loader = new FXMLLoader(getClass().getResource(SceneEnum.values()[i].getValue()));

            try{
                root = loader.load();
                gc = loader.getController();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scenes.add(new Scenes(new Scene(root),SceneEnum.values()[i], gc));
        }
    }


    private void showMenu(){
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setAlwaysOnTop(true);
         stage.centerOnScreen();

    }


}
