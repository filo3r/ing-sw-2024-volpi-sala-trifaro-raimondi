package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;


/**
 * Controller class for handling error messages in the GUI.
 */
public class PopupController extends GenericController {

    /**
     * TextField for displaying the message.
     */
    @FXML
    private TextArea text;

    /**
     * Label for displaying the type of message
     */
    @FXML
    private Label label;

    /**
     * Button to close the message.
     */
    @FXML
    private Button buttonClose;

    /**
     * Indicates whether the error is fatal.
     */
    private boolean isFatalError;


    /**
     * Handles the action event for closing the  message.
     * If the error is fatal, the application exits with status 1.
     * Otherwise, it sends an error acknowledgment command to the input reader.
     * @param actionEvent The action event triggered by the user.
     */
    public void actionClose(ActionEvent actionEvent) {
        if (isFatalError)
            System.exit(1);
        else {
            Stage stage = (Stage) buttonClose.getScene().getWindow();
            stage.close();
        }
    }



    /**
     * Sets the message in the  text area and if its an error specifies if it is fatal.
     * @param message The message to display.
     * @param isFatalError True if the error is fatal, false otherwise.
     */
    public void setText(String message, boolean isFatalError) {
        this.text.setText(message);
        this.isFatalError = isFatalError;
    }

    /**
     * Sets the Label
     * @param text
     */
    public void setLabel(String text){
        this.label.setText(text);
    }


}
