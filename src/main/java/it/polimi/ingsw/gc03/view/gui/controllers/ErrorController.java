package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;


/**
 * Controller class for handling error messages in the GUI.
 */
public class ErrorController extends GenericController {

    /**
     * TextField for displaying the error message.
     */
    @FXML
    private TextArea errorText;

    /**
     * Button to close the error message.
     */
    @FXML
    private Button buttonClose;

    /**
     * Indicates whether the error is fatal.
     */
    private boolean isFatalError;


    /**
     * Handles the action event for closing the error message.
     * If the error is fatal, the application exits with status 1.
     * Otherwise, it sends an error acknowledgment command to the input reader.
     * @param actionEvent The action event triggered by the user.
     */
    public void actionClose(ActionEvent actionEvent) {
        if (isFatalError)
            System.exit(1);
        else
            getInputReaderGUI().addTxt("e");
    }


    /**
     * Sets the error message in the error text area and specifies if the error is fatal.
     * @param error The error message to display.
     * @param isFatalError True if the error is fatal, false otherwise.
     */
    public void setErrorTextArea(String error, boolean isFatalError) {
        this.errorText.setText(error);
        this.isFatalError = isFatalError;
    }


}
