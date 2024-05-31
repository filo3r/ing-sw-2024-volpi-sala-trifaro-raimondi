package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.GameImmutable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


/**
 *
 */
public class WinnersController extends GenericController {

    /**
     *
     */
    @FXML
    private Label player1;

    /**
     *
     */
    @FXML
    private Label player2;

    /**
     *
     */
    @FXML
    private Label player3;

    /**
     *
     */
    @FXML
    private Label player4;

    /**
     *
     */
    @FXML
    private Button buttonClose;


    /**
     *
     */
    public void show(GameImmutable gameImmutable) {
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        player4.setVisible(false);
        buttonClose.setVisible(true);
        // Continuare con la logica per ottenere i punti di tutti i giocatori, ordinarli in base al punteggio, colorare
        // di oro/giallo i giocatori che hanno vinto
    }


    /**
     *
     */
    public void actionClose(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("e");
    }


}
