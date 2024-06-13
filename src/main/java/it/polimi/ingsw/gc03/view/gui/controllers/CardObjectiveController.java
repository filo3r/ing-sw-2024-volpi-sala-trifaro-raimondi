package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Controller for handling the selection of the Objective card.
 */
public class CardObjectiveController extends GenericController {

    /**
     * Button to select the first Objective card.
     */
    @FXML
    private Button firstCard;

    /**
     * Button to select the second Objective card.
     */
    @FXML
    private Button secondCard;


    /**
     * Displays the Objective cards images on the buttons.
     * @param gameImmutable The game model.
     * @param nickname The nickname of the player.
     */
    public void showCardObjective(GameImmutable gameImmutable, String nickname) {
        // Get player Objective cards
        Player player = getPlayer(gameImmutable, nickname);
        String firstImagePath = null;
        String secondImagePath = null;
        if (player != null) {
            firstImagePath = player.getCardObjective().get(0).getImage();
            secondImagePath = player.getCardObjective().get(1).getImage();
        }
        // Load images
        if (firstImagePath != null && secondImagePath != null) {
            try {
                Image firstImage = new Image("file:" + firstImagePath);
                Image secondImage = new Image("file:" + secondImagePath);
                // Set images to buttons
                firstCard.setGraphic(new ImageView(firstImage));
                secondCard.setGraphic(new ImageView(secondImage));
            } catch (Exception e) {
                showError("Error loading images", "There was an error loading the Objective cards images.");
                System.exit(1);
            }
        } else {
            showError("Image paths are null", "The image paths for the Objective cards are null.");
            System.exit(1);
        }
    }


    /**
     * Get the player based on the nickname.
     * @param gameImmutable The game model.
     * @param nickname The nickname of the player.
     * @return The player.
     */
    private Player getPlayer(GameImmutable gameImmutable, String nickname) {
        for (Player player : gameImmutable.getPlayers()) {
            if (player.getNickname().equals(nickname))
                return player;
        }
        return null;
    }


    /**
     * Show an error message in an alert dialog.
     * @param title The title of the alert.
     * @param message The message to display.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    /**
     * Handles the click event for the first Objective card.
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void actionClickOnFirstCard(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("0");
    }


    /**
     * Handles the click event for the second Objective card.
     * @param actionEvent The action event triggered by the user.
     */
    @FXML
    public void actionClickOnSecondCard(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("1");
    }


}