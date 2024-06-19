package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;


/**
 * Controller class for handling the lobby screen in the GUI.
 */
public class ChooseSizeController extends GenericController {

    @FXML
    private TextField gameSize;

    public void actionEnter(ActionEvent actionEvent) {
        if (!gameSize.getText().isEmpty()){
            getInputReaderGUI().addTxt(gameSize.getText());
            System.out.println(gameSize.getText());
        }
    }

}
