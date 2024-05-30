package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.event.ActionEvent;
import java.io.IOException;


/**
 * Controller class for handling menu actions in the GUI.
 */
public class MenuController extends GenericController {

    /**
     * Handles the action event for creating a new game.
     * Sends the command to create a game to the input reader.
     * @param actionEvent The action event triggered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public void actionCreateGame(ActionEvent actionEvent) throws IOException {
        getInputReaderGUI().addTxt("c");
    }


    /**
     * Handles the action event for joining the first available game.
     * Sends the command to join the first available game to the input reader.
     * @param actionEvent The action event triggered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public void actionJoinFirstAvailableGame(ActionEvent actionEvent) throws IOException {
        getInputReaderGUI().addTxt("js");
    }


    /**
     * Handles the action event for joining a specific game.
     * Sends the command to join a specific game to the input reader.
     * @param actionEvent The action event triggered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public void actionJoinSpecificGame(ActionEvent actionEvent) throws IOException {
        getInputReaderGUI().addTxt("js");
    }


    /**
     * Handles the action event for reconnecting to a game.
     * Sends the command to reconnect to a game to the input reader.
     * @param actionEvent The action event triggered by the user.
     * @throws IOException If an I/O error occurs.
     */
    public void actionReconnectToGame(ActionEvent actionEvent) throws IOException {
        getInputReaderGUI().addTxt("r");
    }


}
