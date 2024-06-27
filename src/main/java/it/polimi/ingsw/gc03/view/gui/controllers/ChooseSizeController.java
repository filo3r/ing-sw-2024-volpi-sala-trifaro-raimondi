package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for handling the lobby screen in the GUI.
 */
public class ChooseSizeController extends GenericController {

    /**
     * TextField for entering the game size.
     */
    @FXML
    private TextField gameSize;

    /**
     * Handles the action when the user enters a game size and presses the enter button.
     * @param actionEvent The event triggered by the user's action.
     */
    @FXML
    public void actionEnter(ActionEvent actionEvent) {
        if (!gameSize.getText().isEmpty()){
            getInputReaderGUI().addTxt(gameSize.getText());
            System.out.println(gameSize.getText());
        }
    }

}