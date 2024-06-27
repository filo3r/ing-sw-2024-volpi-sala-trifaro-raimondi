package it.polimi.ingsw.gc03.view.gui.controllers;

/**
 * Controller class for handling the game title screen in the GUI.
 */
public class GameTitleController extends GenericController {

    /**
     * Handles the action when the user starts the game from the title screen.
     * Sends a "Hi" message to the input reader.
     */
    public void actionStart(){
        getInputReaderGUI().addTxt("Hi");
    }

}