package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for handling game ID input in the GUI.
 */
public class GameIdController extends GenericController {

    /**
     * TextField for entering the game ID.
     */
    @FXML
    private TextField gameIdTextField;

    /**
     * Handles the action event when the user presses enter.
     * If the game ID text field is not empty, the text is added to the input reader.
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void actionEnter(ActionEvent actionEvent) {
        if (!gameIdTextField.getText().isEmpty())
            getInputReaderGUI().addTxt(gameIdTextField.getText());
    }

}