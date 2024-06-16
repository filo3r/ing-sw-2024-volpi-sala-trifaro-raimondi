package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;


/**
 * Controller that manages the game scene.
 */
public class GameRunningController extends GenericController {

    /**
     * The game ID.
     */
    @FXML
    private Text gameId;

    /**
     * The nickname of the player of the current turn.
     */
    @FXML
    private Text turnUsername;

    /**
     * The button to leave the game.
     */
    @FXML
    private Button leave;

    /**
     * Label to display points for player 1.
     */
    @FXML
    private Label points1;

    /**
     * Label to display points for player 2.
     */
    @FXML
    private Label points2;

    /**
     * Label to display points for player 3.
     */
    @FXML
    private Label points3;

    /**
     * Label to display points for player 4.
     */
    @FXML
    private Label points4;

    /**
     * Pane to display the personal Objective card.
     */
    @FXML
    private Pane personalObjective;

    /**
     * Pane to display the first shared Objective card.
     */
    @FXML
    private Pane sharedObjective1;

    /**
     * Pane to display the second shared Objective card.
     */
    @FXML
    private Pane sharedObjective2;

    /**
     *
     */
    @FXML
    private Button deckResource;

    /**
     *
     */
    @FXML
    private Button deckGold;



    /**
     * Sets the game ID to be displayed in the scene.
     * @param gameId The game ID to be displayed.
     */
    public void setGameId(int gameId) {
        this.gameId.setText("Game ID: " + gameId);
    }


    /**
     * Sets the nickname of the player of the current turn to be displayed in the scene.
     * @param gameImmutable The current state of the game, used to get the current player's nickname.
     */
    public void setTurnUsername(GameImmutable gameImmutable) {
        this.turnUsername.setText("Turn: " + gameImmutable.getPlayers().get(gameImmutable.getCurrPlayer()).getNickname());
    }


    /**
     * Handles the action of leaving the game.
     * @param actionEvent The event triggered by clicking the leave button.
     */
    public void actionLeave(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("leave");
    }


    /**
     * Sets the points for each player to be displayed in the scene.
     * @param gameImmutable The current state of the game, used to get each player's points.
     */
    public void setPoints(GameImmutable gameImmutable) {
        this.points1.setVisible(false);
        this.points2.setVisible(false);
        this.points3.setVisible(false);
        this.points4.setVisible(false);
        // Set points
        int i = 0;
        Label temp = null;
        for (Player player : gameImmutable.getPlayers()) {
            switch (i) {
                case 0 -> temp = points1;
                case 1 -> temp = points2;
                case 2 -> temp = points3;
                case 3 -> temp = points4;
            }
            temp.setText(gameImmutable.getPlayers().get(i).getNickname() + ": " + gameImmutable.getPlayers().get(i).getCodex().getPointCodex());
            temp.setVisible(true);
            i++;
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
     * Sets the personal Objective image for the player.
     * @param gameImmutable The current state of the game.
     * @param nickname The nickname of the player whose Objective card should be displayed.
     */
    public void setPersonalObjective(GameImmutable gameImmutable, String nickname) {
        Player player = getPlayer(gameImmutable, nickname);
        String imagePath = null;
        if (player != null)
            imagePath = player.getCardObjective().get(0).getImage();
        if (imagePath != null) {
            try {
                Image image = new Image("file:" + imagePath);
                personalObjective.getChildren().add(new ImageView(image));
            } catch (Exception e) {
                showError("Error loading image", "There was an error loading the Objective card image.");
                System.exit(1);
            }
        } else {
            showError("Image path is null", "The image path for the Objective card is null.");
            System.exit(1);
        }
    }


    /**
     * Sets the shared Objective images to be displayed in the scene.
     * @param gameImmutable The current state of the game.
     */
    public void setSharedObjective(GameImmutable gameImmutable) {
        String imagePath1 = gameImmutable.getDesk().getDisplayedObjective().get(0).getImage();
        String imagePath2 = gameImmutable.getDesk().getDisplayedObjective().get(1).getImage();
        if (imagePath1 != null && imagePath2 != null) {
            try {
                Image image1 = new Image("file:" + imagePath1);
                sharedObjective1.getChildren().add(new ImageView(image1));
                Image image2 = new Image("file:" + imagePath2);
                sharedObjective2.getChildren().add(new ImageView(image2));
            } catch (Exception e) {
                showError("Error loading images", "There was an error loading the Objective cards images.");
                System.exit(1);
            }
        } else {
            showError("Images paths are null", "The images paths for the Objective cards are null.");
            System.exit(1);
        }
    }


    /**
     *
     */
    public void setDeckResource(GameImmutable gameImmutable) {
        String imagePath = gameImmutable.getDesk().getDeckResource().get(0).getBackResource().getImage();
        if (imagePath != null) {
            try {
                Image image = new Image("file:" + imagePath);
                deckResource.setGraphic(new ImageView(image));
            } catch (Exception e) {
                showError("Error loading image", "There was an error loading the Deck Resource images.");
                System.exit(1);
            }
        } else {
            showError("Image path is null", "The image path for the Deck Resource is null.");
            System.exit(1);
        }
    }


    /**
     *
     */
    public void actionDeckResource(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("rD");
    }


    /**
     *
     */
    public void setDeckGold(GameImmutable gameImmutable) {
        String imagePath = gameImmutable.getDesk().getDeckGold().get(0).getBackGold().getImage();
        if (imagePath != null) {
            try {
                Image image = new Image("file:" + imagePath);
                deckGold.setGraphic(new ImageView(image));
            } catch (Exception e) {
                showError("Error loading image", "There was an error loading the Deck Gold images.");
                System.exit(1);
            }
        } else {
            showError("Image path is null", "The image path for the Deck Gold is null.");
            System.exit(1);
        }
    }


    /**
     *
     */
    public void actionDeckGold(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("gD");
    }









}
