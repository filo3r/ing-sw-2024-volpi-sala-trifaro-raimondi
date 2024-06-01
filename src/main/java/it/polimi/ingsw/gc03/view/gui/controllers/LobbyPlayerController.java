package it.polimi.ingsw.gc03.view.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;


/**
 * Controller class for handling the display of a player in the lobby screen.
 */
public class LobbyPlayerController extends GenericController {

    /**
     * Text element for displaying the player's nickname.
     */
    @FXML
    private Text nickname;


    /**
     * Sets the player's nickname in the lobby screen.
     * @param nickname The player's nickname to display.
     */
    public void setNickname(String nickname) {
        this.nickname.setText(nickname);
    }


}
