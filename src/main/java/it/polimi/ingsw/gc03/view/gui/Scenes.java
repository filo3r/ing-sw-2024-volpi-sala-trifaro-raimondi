package it.polimi.ingsw.gc03.view.gui;

import it.polimi.ingsw.gc03.view.gui.controllers.GenericController;
import it.polimi.ingsw.gc03.view.inputHandler.InputReaderGUI;
import javafx.scene.Scene;

/**
 * The Scenes class represents a JavaFX scene and its associated controller.
 */
public class Scenes {

    /**
     * The JavaFX scene.
     */
    private Scene scene;

    /**
     * The enumeration representing the scene type.
     */
    private SceneEnum sceneEnum;

    /**
     * The generic controller associated with the scene.
     */
    private GenericController genericController;

    /**
     * Constructor for the Scenes class.
     * @param scene The JavaFX scene.
     * @param sceneEnum The scene type enumeration.
     * @param gc The generic controller associated with the scene.
     */
    public Scenes(Scene scene, SceneEnum sceneEnum,GenericController gc){
        this.scene = scene;
        this.sceneEnum = sceneEnum;
        this.genericController = gc;
    }

    /**
     * Gets the JavaFX scene.
     * @return The JavaFX scene.
     */
    public Scene getScene() {
        return scene;
    }

    /**
     * Sets the JavaFX scene.
     * @param scene The JavaFX scene to set.
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Sets the scene type enumeration.
     * @param sceneEnum The scene type enumeration to set.
     */
    public void setSceneEnum(SceneEnum sceneEnum) {
        this.sceneEnum = sceneEnum;
    }

    /**
     * Sets the generic controller associated with the scene.
     * @param genericController The generic controller to set.
     */
    public void setGenericController(GenericController genericController) {
        this.genericController = genericController;
    }

    /**
     * Sets the InputReaderGUI for the associated controller, if it exists.
     * @param inputReaderGUI The InputReaderGUI to set.
     */
    public void setInputReaderGUI(InputReaderGUI inputReaderGUI){
        if(genericController!=null) {
            genericController.setInputReaderGUI(inputReaderGUI);
        }
    }

    /**
     * Gets the scene type enumeration.
     * @return The scene type enumeration.
     */
    public SceneEnum getSceneEnum() {
        return sceneEnum;
    }

    /**
     * Gets the generic controller associated with the scene.
     * @return The generic controller.
     */
    public GenericController getGenericController() {
        return genericController;
    }

}