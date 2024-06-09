package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.view.gui.controllers.GenericController;
import javafx.scene.Scene;

public class Scenes {
    private Scene scene;
    private SceneEnum sceneEnum;
    private GenericController genericController;

    public Scenes(Scene scene, SceneEnum sceneEnum,GenericController gc){
        this.scene = scene;
        this.sceneEnum = sceneEnum;
        this.genericController = gc;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public void setSceneEnum(SceneEnum sceneEnum) {
        this.sceneEnum = sceneEnum;
    }

    public void setGenericController(GenericController genericController) {
        this.genericController = genericController;
    }

    public SceneEnum getSceneEnum() {
        return sceneEnum;
    }

    public GenericController getGenericController() {
        return genericController;
    }
}
