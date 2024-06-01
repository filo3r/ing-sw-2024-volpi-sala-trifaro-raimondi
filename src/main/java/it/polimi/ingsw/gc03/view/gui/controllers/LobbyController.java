package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;


/**
 * Controller class for handling the lobby screen in the GUI.
 */
public class LobbyController extends GenericController {

    /**
     * Text element for displaying the user's nickname.
     */
    @FXML
    private Text nickname;

    /**
     * Text element for displaying the game ID.
     */
    @FXML
    private Text gameId;


    /**
     * Sets the user's nickname in the lobby screen.
     * @param nickname The user's nickname to display.
     */
    public void setUsername(String nickname) {
        this.nickname.setText(nickname);
    }


    /**
     * Sets the game ID in the lobby screen.
     * @param gameId The game ID to display.
     */
    public void setGameId(int gameId) {
        this.gameId.setText("Game ID: " + gameId);
    }


}
