package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.util.concurrent.Semaphore;


public class GameTitleController extends GenericController {

    public void actionStart(){
        getInputReaderGUI().addTxt("0110010011010010110111101100011011000010110111001100101");
    }
}
