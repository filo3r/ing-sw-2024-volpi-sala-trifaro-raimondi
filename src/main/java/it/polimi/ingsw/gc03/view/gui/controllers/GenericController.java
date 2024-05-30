package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.view.inputHandler.InputReaderGUI;


/**
 * Abstract class representing a generic controller for the GUI.
 */
public abstract class GenericController {

    /**
     * Input reader for the GUI.
     */
    private InputReaderGUI inputReaderGUI;


    /**
     * Sets the input reader for the GUI.
     * @param inputReaderGUI The input reader to set.
     */
    public void setInputReaderGUI(InputReaderGUI inputReaderGUI) {
        this.inputReaderGUI = inputReaderGUI;
    }


    /**
     * Gets the input reader for the GUI.
     * @return The current input reader.
     */
    public InputReaderGUI getInputReaderGUI() {
        return this.inputReaderGUI;
    }


}
