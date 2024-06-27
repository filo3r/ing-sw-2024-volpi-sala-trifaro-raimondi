package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Controller class for handling nickname input in the GUI.
 */
public class NicknameController extends GenericController {

    /**
     * TextField for entering the nickname.
     */
    @FXML
    private TextField nicknameTextField;

    /**
     * Handles the action event when the user presses enter.
     * If the nickname text field is not empty, the text is added to the input reader.
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void actionEnter(ActionEvent actionEvent) {
        if (!nicknameTextField.getText().isEmpty()){
            getInputReaderGUI().addTxt(nicknameTextField.getText());
        }
    }

}