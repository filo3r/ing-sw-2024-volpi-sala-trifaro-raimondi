package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


/**
 * Controller for handling the selection of the Starter card side.
 */
public class CardStarterController extends GenericController {

    /**
     * Button to select the front side of the Starter card.
     */
    @FXML
    private ImageView frontSide;

    /**
     * Button to select the back side of the Starter card.
     */
    @FXML
    private ImageView backSide;


    /**
     * Displays the Starter card images on the buttons.
     * @param gameImmutable The game gameImmutable.
     * @param nickname The nickname of the player.
     */
    public void showCardStarter(GameImmutable gameImmutable, String nickname) {
        // Set visibility
        frontSide.setVisible(false);
        backSide.setVisible(false);
        // Get player Starter card
        Player player = getPlayer(gameImmutable, nickname);
        String frontImagePath = null;
        String backImagePath = null;
        if (player != null) {
            frontImagePath = player.getCardStarter().getFrontStarter().getImage();
            backImagePath = player.getCardStarter().getBackStarter().getImage();
        }
        // Load images
        if (frontImagePath != null && backImagePath != null) {
            try {
                Image frontImage = new Image(getClass().getResourceAsStream(frontImagePath));
                Image backImage = new Image(getClass().getResourceAsStream(backImagePath));
                // Set images to buttons
                frontSide.setImage(frontImage);
                backSide.setImage(backImage);
                frontSide.setVisible(true);
                backSide.setVisible(true);
            } catch (Exception e) {
                showError("Error loading images", "There was an error loading the Starter card images.");
                System.exit(1);
            }
        } else {
            showError("Image paths are null", "The image paths for the Starter card are null.");
            System.exit(1);
        }
    }


    /**
     * Get the player based on the nickname.
     * @param gameImmutable The game gameImmutable.
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
     * Handles the click event for the front side of the Starter card.
     * @param mouseEvent The mouse event triggered by the user.
     */
    @FXML
    public void actionClickOnFrontSide(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("f");
        backSide.setVisible(false);
    }


    /**
     * Handles the click event for the back side of the Starter card.
     * @param mouseEvent The mouse event triggered by the user.
     */
    @FXML
    public void actionClickOnBackSide(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("b");
        frontSide.setVisible(false);
    }


}