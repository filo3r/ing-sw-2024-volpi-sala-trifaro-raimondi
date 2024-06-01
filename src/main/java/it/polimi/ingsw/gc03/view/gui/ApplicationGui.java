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
    private ArrayList<SceneInfo> scenes;

    public ApplicationGui(Flow flow){
        this.flow = flow;
        showTitle();
    }
    //Probabilmente inutile
    private void showTitle() {
        //prendere immagine giusta
        String imagePath = "/codexTitle.jpg";
        InputStream inputStream = getClass().getResourceAsStream(imagePath);
        Image img = new Image(inputStream);
        BackgroundImage backgroundImage = new BackgroundImage(img,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.CENTER,new BackgroundSize(200,150, false, false,false, true));
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setBackground(new Background(backgroundImage));
        javafx.scene.control.Label txt = new Label("Click to Continue...");
        AnchorPane.setTopAnchor(txt,800.0);
        AnchorPane.setLeftAnchor(txt,750.0);
        txt.setScaleX(2);
        txt.setScaleY(2);
        BackgroundFill backgroundFill = new BackgroundFill(Color.GREEN,null,null);
        Background background = new Background(backgroundFill);
        txt.setBackground(background);
        javafx.scene.text.Font font = Font.font("MedievalSharp",FontWeight.BOLD,14);
        txt.setFont(font);
        Button button = new Button();
        button.setScaleX(400);
        button.setScaleY(500);
        button.setOpacity(0);
        button.setOnMouseClicked(e->{
            controller.handleButtonAction();
            //passa al menu
        });
        AnchorPane.setTopAnchor(button,450.0);
        AnchorPane.setLeftAnchor(button, 800.0);
        anchorPane.getChildren().add(button);
        anchorPane.getChildren().add(txt);
        Scene scene = new Scene(anchorPane, 1600, 900);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {
        loadScene();
        this.stage = stage;
        this.stage.setTitle("CodeX");
        root = new StackPane();
    }

    private void loadScene(){
    }

    private void showMenu(){
         stage.initStyle(StageStyle.UNDECORATED);
         stage.setAlwaysOnTop(true);
         stage.centerOnScreen();

    }


}
