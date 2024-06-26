package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.Color;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.view.tui.Coords;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Point2D;
import javafx.geometry.VPos;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.transform.Transform;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


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
     * Text to display the title "Points".
     */
    @FXML
    private Text points;

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
    private Pane personalObjectivePane;

    /**
     * ImageView to display the personal Objective card.
     */
    @FXML
    private ImageView personalObjectiveImage;

    /**
     * Pane to display the first shared Objective card.
     */
    @FXML
    private Pane sharedObjective1Pane;

    /**
     * ImageView to display the first shared Objective card.
     */
    @FXML
    private ImageView sharedObjective1Image;

    /**
     * Pane to display the second shared Objective card.
     */
    @FXML
    private Pane sharedObjective2Pane;

    /**
     * ImageView to display the second shared Objective card.
     */
    @FXML
    private ImageView sharedObjective2Image;

    /**
     * Pane to display the deck of Resource cards.
     */
    @FXML
    private Pane deckResourcePane;

    /**
     * ImageView to display the deck of Resource cards.
     */
    @FXML
    private ImageView deckResourceImage;

    /**
     * Pane to display the deck of Gold cards.
     */
    @FXML
    private Pane deckGoldPane;

    /**
     * ImageView to display the deck of Gold cards.
     */
    @FXML
    private ImageView deckGoldImage;

    /**
     * Pane to display the first displayed card.
     */
    @FXML
    private Pane displayed1Pane;

    /**
     * ImageView to display the first displayed card.
     */
    @FXML
    private ImageView displayed1Image;

    /**
     * Pane to display the second displayed card.
     */
    @FXML
    private Pane displayed2Pane;

    /**
     * ImageView to display the second displayed card.
     */
    @FXML
    private ImageView displayed2Image;

    /**
     * Pane to display the third displayed card.
     */
    @FXML
    private Pane displayed3Pane;

    /**
     * ImageView to display the third displayed card.
     */
    @FXML
    private ImageView displayed3Image;

    /**
     * Pane to display the fourth displayed card.
     */
    @FXML
    private Pane displayed4Pane;

    /**
     * ImageView to display the fourth displayed card.
     */
    @FXML
    private ImageView displayed4Image;

    /**
     * Pane to display the first hand card.
     */
    @FXML
    private Pane hand1Pane;

    /**
     * ImageView to display the first hand card.
     */
    @FXML
    private ImageView hand1Image;

    /**
     * Pane to display the second hand card.
     */
    @FXML
    private Pane hand2Pane;

    /**
     * ImageView to display the second hand card.
     */
    @FXML
    private ImageView hand2Image;

    /**
     * Pane to display the third hand card.
     */
    @FXML
    private Pane hand3Pane;

    /**
     * ImageView to display the third hand card.
     */
    @FXML
    private ImageView hand3Image;

    /**
     * A map to store the cards in the player's hand.
     */
    private HashMap<String, Card> hand = new HashMap<>(3);

    /**
     * A map to store the front side visibility of the cards in the player's hand.
     */
    private HashMap<String, Boolean> frontSideHand = new HashMap<>(3);

    /**
     * Pane to display the first board.
     */
    @FXML
    private Pane board1;

    /**
     * Pane to display the first player's color.
     */
    @FXML
    private Pane color1;

    /**
     * Text to display the first player's nickname.
     */
    @FXML
    private Text nickname1;

    /**
     * Pane to display the first player's fungi.
     */
    @FXML
    private Pane fungi1Pane;

    /**
     * ImageView to display the first player's fungi.
     */
    @FXML
    private ImageView fungi1Image;


    /**
     * Text to display the first player's fungi count.
     */
    @FXML
    private Text fungiCount1;

    /**
     * Pane to display the first player's plants.
     */
    @FXML
    private Pane plant1Pane;

    /**
     * ImageView to display the first player's plants.
     */
    @FXML
    private ImageView plant1Image;

    /**
     * Text to display the first player's plant count.
     */
    @FXML
    private Text plantCount1;

    /**
     * Pane to display the first player's animals.
     */
    @FXML
    private Pane animal1Pane;

    /**
     * ImageView to display the first player's animals.
     */
    @FXML
    private ImageView animal1Image;

    /**
     * Text to display the first player's animal count.
     */
    @FXML
    private Text animalCount1;

    /**
     * Pane to display the first player's insects.
     */
    @FXML
    private Pane insect1Pane;

    /**
     * ImageView to display the first player's insects.
     */
    @FXML
    private ImageView insect1Image;

    /**
     * Text to display the first player's insect count.
     */
    @FXML
    private Text insectCount1;

    /**
     * Pane to display the first player's quills.
     */
    @FXML
    private Pane quill1Pane;

    /**
     * ImageView to display the first player's quills.
     */
    @FXML
    private ImageView quill1Image;

    /**
     * Text to display the first player's quill count.
     */
    @FXML
    private Text quillCount1;

    /**
     * Pane to display the first player's inkwells.
     */
    @FXML
    private Pane inkwell1Pane;

    /**
     * ImageView to display the first player's inkwells.
     */
    @FXML
    private ImageView inkwell1Image;

    /**
     * Text to display the first player's inkwell count.
     */
    @FXML
    private Text inkwellCount1;

    /**
     * Pane to display the first player's manuscripts.
     */
    @FXML
    private Pane manuscript1Pane;

    /**
     * ImageView to display the first player's manuscripts.
     */
    @FXML
    private ImageView manuscript1Image;

    /**
     * Text to display the first player's manuscript count.
     */
    @FXML
    private Text manuscriptCount1;

    /**
     * Pane to display the first player's covered items.
     */
    @FXML
    private Pane covered1Pane;

    /**
     * ImageView to display the first player's covered items.
     */
    @FXML
    private ImageView covered1Image;

    /**
     * Text to display the first player's covered count.
     */
    @FXML
    private Text coveredCount1;

    /**
     * Pane to display the first player's first hand card.
     */
    @FXML
    private Pane hand11Pane;

    /**
     * ImageView to display the first player's first hand card.
     */
    @FXML
    private ImageView hand11Image;

    /**
     * Pane to display the first player's second hand card.
     */
    @FXML
    private Pane hand12Pane;

    /**
     * ImageView to display the first player's second hand card.
     */
    @FXML
    private ImageView hand12Image;

    /**
     * Pane to display the first player's third hand card.
     */
    @FXML
    private Pane hand13Pane;

    /**
     * ImageView to display the first player's third hand card.
     */
    @FXML
    private ImageView hand13Image;

    /**
     * Pane to display the second board.
     */
    @FXML
    private Pane board2;

    /**
     * Pane to display the second player's color.
     */
    @FXML
    private Pane color2;

    /**
     * Text to display the second player's nickname.
     */
    @FXML
    private Text nickname2;

    /**
     * Pane to display the second player's fungi.
     */
    @FXML
    private Pane fungi2Pane;

    /**
     * ImageView to display the second player's fungi.
     */
    @FXML
    private ImageView fungi2Image;

    /**
     * Text to display the second player's fungi count.
     */
    @FXML
    private Text fungiCount2;

    /**
     * Pane to display the second player's plants.
     */
    @FXML
    private Pane plant2Pane;

    /**
     * ImageView to display the second player's plants.
     */
    @FXML
    private ImageView plant2Image;

    /**
     * Text to display the second player's plant count.
     */
    @FXML
    private Text plantCount2;

    /**
     * Pane to display the second player's animals.
     */
    @FXML
    private Pane animal2Pane;

    /**
     * ImageView to display the second player's animals.
     */
    @FXML
    private ImageView animal2Image;

    /**
     * Text to display the second player's animal count.
     */
    @FXML
    private Text animalCount2;

    /**
     * Pane to display the second player's insects.
     */
    @FXML
    private Pane insect2Pane;

    /**
     * ImageView to display the second player's insects.
     */
    @FXML
    private ImageView insect2Image;

    /**
     * Text to display the second player's insect count.
     */
    @FXML
    private Text insectCount2;

    /**
     * Pane to display the second player's quills.
     */
    @FXML
    private Pane quill2Pane;

    /**
     * ImageView to display the second player's quills.
     */
    @FXML
    private ImageView quill2Image;

    /**
     * Text to display the second player's quill count.
     */
    @FXML
    private Text quillCount2;

    /**
     * Pane to display the second player's inkwells.
     */
    @FXML
    private Pane inkwell2Pane;

    /**
     * ImageView to display the second player's inkwells.
     */
    @FXML
    private ImageView inkwell2Image;

    /**
     * Text to display the second player's inkwell count.
     */
    @FXML
    private Text inkwellCount2;

    /**
     * Pane to display the second player's manuscripts.
     */
    @FXML
    private Pane manuscript2Pane;

    /**
     * ImageView to display the second player's manuscripts.
     */
    @FXML
    private ImageView manuscript2Image;

    /**
     * Text to display the second player's manuscript count.
     */
    @FXML
    private Text manuscriptCount2;

    /**
     *  Pane to display the second player's covered items.
     */
    @FXML
    private Pane covered2Pane;

    /**
     * ImageView to display the second player's covered items.
     */
    @FXML
    private ImageView covered2Image;

    /**
     * Text to display the second player's covered count.
     */
    @FXML
    private Text coveredCount2;

    /**
     * Pane to display the second player's first hand card.
     */
    @FXML
    private Pane hand21Pane;

    /**
     * ImageView to display the second player's first hand card.
     */
    @FXML
    private ImageView hand21Image;

    /**
     * Pane to display the second player's second hand card.
     */
    @FXML
    private Pane hand22Pane;

    /**
     * ImageView to display the second player's second hand card.
     */
    @FXML
    private ImageView hand22Image;

    /**
     * Pane to display the second player's third hand card.
     */
    @FXML
    private Pane hand23Pane;

    /**
     * ImageView to display the second player's third hand card.
     */
    @FXML
    private ImageView hand23Image;

    /**
     * Pane to display the third board.
     */
    @FXML
    private Pane board3;

    /**
     * Pane to display the third player's color.
     */
    @FXML
    private Pane color3;

    /**
     * Text to display the third player's nickname.
     */
    @FXML
    private Text nickname3;

    /**
     * Pane to display the third player's fungi.
     */
    @FXML
    private Pane fungi3Pane;

    /**
     * ImageView to display the third player's fungi.
     */
    @FXML
    private ImageView fungi3Image;

    /**
     * Text to display the third player's fungi count.
     */
    @FXML
    private Text fungiCount3;

    /**
     * Pane to display the third player's plants.
     */
    @FXML
    private Pane plant3Pane;

    /**
     * ImageView to display the third player's plants.
     */
    @FXML
    private ImageView plant3Image;

    /**
     * Text to display the third player's plant count.
     */
    @FXML
    private Text plantCount3;

    /**
     * Pane to display the third player's animals.
     */
    @FXML
    private Pane animal3Pane;

    /**
     * ImageView to display the third player's animals.
     */
    @FXML
    private ImageView animal3Image;

    /**
     * Text to display the third player's animal count.
     */
    @FXML
    private Text animalCount3;

    /**
     * Pane to display the third player's insects.
     */
    @FXML
    private Pane insect3Pane;

    /**
     * ImageView to display the third player's insects.
     */
    @FXML
    private ImageView insect3Image;

    /**
     * Text to display the third player's insect count.
     * */
    @FXML
    private Text insectCount3;

    /**
     * Pane to display the third player's quills.
     */
    @FXML
    private Pane quill3Pane;

    /**
     * ImageView to display the third player's quills.
     */
    @FXML
    private ImageView quill3Image;

    /**
     * Text to display the third player's quill count.
     */
    @FXML
    private Text quillCount3;

    /**
     * Pane to display the third player's inkwells.
     */
    @FXML
    private Pane inkwell3Pane;

    /**
     * ImageView to display the third player's inkwells.
     */
    @FXML
    private ImageView inkwell3Image;

    /**
     * Text to display the third player's inkwell count.
     */
    @FXML
    private Text inkwellCount3;

    /**
     * Pane to display the third player's manuscripts.
     */
    @FXML
    private Pane manuscript3Pane;

    /**
     * ImageView to display the third player's manuscripts.
     */
    @FXML
    private ImageView manuscript3Image;

    /**
     * Text to display the third player's manuscript count.
     */
    @FXML
    private Text manuscriptCount3;

    /**
     * Pane to display the third player's covered items.
     */
    @FXML
    private Pane covered3Pane;

    /**
     * ImageView to display the third player's covered items.
     */
    @FXML
    private ImageView covered3Image;

    /**
     * Text to display the third player's covered count.
     */
    @FXML
    private Text coveredCount3;

    /**
     * Pane to display the third player's first hand card.
     */
    @FXML
    private Pane hand31Pane;

    /**
     * ImageView to display the third player's first hand card.
     */
    @FXML
    private ImageView hand31Image;

    /**
     * Pane to display the third player's second hand card.
     */
    @FXML
    private Pane hand32Pane;

    /**
     * ImageView to display the third player's second hand card.
     */
    @FXML
    private ImageView hand32Image;

    /**
     * Pane to display the third player's third hand card.
     */
    @FXML
    private Pane hand33Pane;

    /**
     * ImageView to display the third player's third hand card.
     */
    @FXML
    private ImageView hand33Image;

    /**
     * Pane to display the fourth board.
     */
    @FXML
    private Pane board4;

    /**
     * Pane to display the fourth player's color.
     */
    @FXML
    private Pane color4;

    /**
     * Text to display the fourth player's nickname.
     */
    @FXML
    private Text nickname4;

    /**
     * Pane to display the fourth player's fungi.
     */
    @FXML
    private Pane fungi4Pane;

    /**
     * ImageView to display the fourth player's fungi.
     */
    @FXML
    private ImageView fungi4Image;

    /**
     * Text to display the fourth player's fungi count.
     */
    @FXML
    private Text fungiCount4;

    /**
     * Pane to display the fourth player's plants.
     */
    @FXML
    private Pane plant4Pane;

    /**
     * ImageView to display the fourth player's plants.
     */
    @FXML
    private ImageView plant4Image;

    /**
     * Text to display the fourth player's plant count.
     */
    @FXML
    private Text plantCount4;

    /**
     * Pane to display the fourth player's animals.
     */
    @FXML
    private Pane animal4Pane;

    /**
     * ImageView to display the fourth player's animals.
     */
    @FXML
    private ImageView animal4Image;

    /**
     * Text to display the fourth player's animal count.
     */
    @FXML
    private Text animalCount4;

    /**
     * Pane to display the fourth player's insects.
     */
    @FXML
    private Pane insect4Pane;

    /**
     * ImageView to display the fourth player's insects.
     */
    @FXML
    private ImageView insect4Image;

    /**
     * Text to display the fourth player's insect count.
     */
    @FXML
    private Text insectCount4;

    /**
     * Pane to display the fourth player's quills.
     */
    @FXML
    private Pane quill4Pane;

    /**
     * ImageView to display the fourth player's quills.
     */
    @FXML
    private ImageView quill4Image;

    /**
     * Text to display the fourth player's quill count.
     */
    @FXML
    private Text quillCount4;

    /**
     * Pane to display the fourth player's inkwells.
     */
    @FXML
    private Pane inkwell4Pane;

    /**
     * ImageView to display the fourth player's inkwells.
     */
    @FXML
    private ImageView inkwell4Image;

    /**
     * Text to display the fourth player's inkwell count.
     */
    @FXML
    private Text inkwellCount4;

    /**
     * Pane to display the fourth player's manuscripts.
     */
    @FXML
    private Pane manuscript4Pane;

    /**
     * ImageView to display the fourth player's manuscripts.
     */
    @FXML
    private ImageView manuscript4Image;

    /**
     * Text to display the fourth player's manuscript count.
     */
    @FXML
    private Text manuscriptCount4;

    /**
     * Pane to display the fourth player's covered items.
     */
    @FXML
    private Pane covered4Pane;

    /**
     * ImageView to display the fourth player's covered items.
     */
    @FXML
    private ImageView covered4Image;

    /**
     * Text to display the fourth player's covered count.
     */
    @FXML
    private Text coveredCount4;

    /**
     * Pane to display the fourth player's first hand card.
     */
    @FXML
    private Pane hand41Pane;

    /**
     * ImageView to display the fourth player's first hand card.
     */
    @FXML
    private ImageView hand41Image;

    /**
     * Pane to display the fourth player's second hand card.
     */
    @FXML
    private Pane hand42Pane;

    /**
     * ImageView to display the fourth player's second hand card.
     */
    @FXML
    private ImageView hand42Image;

    /**
     * Pane to display the fourth player's third hand card.
     */
    @FXML
    private Pane hand43Pane;

    /**
     * ImageView to display the fourth player's third hand card.
     */
    @FXML
    private ImageView hand43Image;

    /**
     * ListView to display chat messages.
     */
    @FXML
    private ListView<String> chat;

    /**
     * ListView to display the latest events.
     */
    @FXML
    private ListView<String> latestEvent;

    /**
     * ComboBox to select the receiver of the chat message.
     */
    @FXML
    private ComboBox<String> chatReceiver;

    /**
     * TextField to input chat messages.
     */
    @FXML
    private TextField chatMessage;

    /**
     * The game ID.
     */
    private int idGame = 0;

    /**
     * The GridPane to display the codex.
     */
    @FXML
    private GridPane codexGrid;

    /**
     * The ScrollPane for the codex.
     */
    @FXML
    private ScrollPane codexScroll;



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
    @FXML
    public void actionLeave(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("leave");
    }


    /**
     * Sets the points for each player to be displayed in the scene.
     * @param gameImmutable The current state of the game, used to get each player's points.
     */
    public void setPoints(GameImmutable gameImmutable) {
        this.points.setVisible(true);
        this.points.setText("Points");
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
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                personalObjectiveImage.setImage(image);
                personalObjectiveImage.setFitWidth(personalObjectivePane.getPrefWidth());
                personalObjectiveImage.setFitHeight(personalObjectivePane.getPrefHeight());
                personalObjectiveImage.setPreserveRatio(true);
                personalObjectiveImage.setSmooth(true);
                personalObjectiveImage.setCache(true);
            } catch (Exception e) {
                showError("Error loading image", "There was an error loading the Objective card image.");
                System.exit(1);
            }
        } else {
            if (!(gameImmutable.getStatus().equals(GameStatus.STARTING) || gameImmutable.getStatus().equals(GameStatus.WAITING))) {
                showError("Image path is null", "The image path for the Objective card is null.");
                System.exit(1);
            }
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
                // Card 1
                Image image1 = new Image(getClass().getResourceAsStream(imagePath1));
                sharedObjective1Image.setImage(image1);
                sharedObjective1Image.setFitWidth(sharedObjective1Pane.getPrefWidth());
                sharedObjective1Image.setFitHeight(sharedObjective1Pane.getPrefHeight());
                sharedObjective1Image.setPreserveRatio(true);
                sharedObjective1Image.setSmooth(true);
                sharedObjective1Image.setCache(true);
                // Card 2
                Image image2 = new Image(getClass().getResourceAsStream(imagePath2));
                sharedObjective2Image.setImage(image2);
                sharedObjective2Image.setFitWidth(sharedObjective2Pane.getPrefWidth());
                sharedObjective2Image.setFitHeight(sharedObjective2Pane.getPrefHeight());
                sharedObjective2Image.setPreserveRatio(true);
                sharedObjective2Image.setSmooth(true);
                sharedObjective2Image.setCache(true);
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
     * Sets the image for the deck of Resource cards.
     * @param gameImmutable The current state of the game.
     */
    public void setDeckResource(GameImmutable gameImmutable) {
        if (gameImmutable.getDesk().getDeckResource().isEmpty()) {
            deckResourcePane.setVisible(false);
        } else {
            deckResourcePane.setVisible(true);
            String imagePath = gameImmutable.getDesk().getDeckResource().get(0).getBackResource().getImage();
            if (imagePath != null) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(imagePath));
                    deckResourceImage.setImage(image);
                    deckResourceImage.setFitWidth(deckResourcePane.getPrefWidth());
                    deckResourceImage.setFitHeight(deckResourcePane.getPrefHeight());
                    deckResourceImage.setPreserveRatio(true);
                    deckResourceImage.setSmooth(true);
                    deckResourceImage.setCache(true);
                } catch (Exception e) {
                    showError("Error loading image", "There was an error loading the Deck Resource images.");
                    System.exit(1);
                }
            } else {
                if (!gameImmutable.getDesk().getDeckResource().isEmpty()) {
                    showError("Image path is null", "The image path for the Deck Resource is null.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Handles the action of clicking the deck Resource button.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionDeckResource(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("rD");
    }


    /**
     * Sets the image for the deck of Gold cards.
     * @param gameImmutable The current state of the game.
     */
    public void setDeckGold(GameImmutable gameImmutable) {
        if (gameImmutable.getDesk().getDeckGold().isEmpty()) {
            deckGoldPane.setVisible(false);
        } else {
            deckGoldPane.setVisible(true);
            String imagePath = gameImmutable.getDesk().getDeckGold().get(0).getBackGold().getImage();
            if (imagePath != null) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(imagePath));
                    deckGoldImage.setImage(image);
                    deckGoldImage.setFitWidth(deckGoldPane.getPrefWidth());
                    deckGoldImage.setFitHeight(deckGoldPane.getPrefHeight());
                    deckGoldImage.setPreserveRatio(true);
                    deckGoldImage.setSmooth(true);
                    deckGoldImage.setCache(true);
                } catch (Exception e) {
                    showError("Error loading image", "There was an error loading the Deck Gold images.");
                    System.exit(1);
                }
            } else {
                if (!gameImmutable.getDesk().getDeckGold().isEmpty()) {
                    showError("Image path is null", "The image path for the Deck Gold is null.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Handles the action of clicking the deck Gold button.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionDeckGold(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("gD");
    }


    /**
     * Sets the images for the displayed cards.
     * @param gameImmutable The current state of the game.
     */
    public void setDisplayed(GameImmutable gameImmutable) {
        // Displayed Resource
        ArrayList<String> imagePathResource = new ArrayList<>(gameImmutable.getDesk().getDisplayedResource().size());
        for (Card card : gameImmutable.getDesk().getDisplayedResource()) {
            if (card instanceof CardResource) {
                CardResource cardResource = (CardResource) card;
                String imagePath = cardResource.getFrontResource().getImage();
                imagePathResource.add(imagePath);
            } else if (card instanceof CardGold) {
                CardGold cardGold = (CardGold) card;
                String imagePath = cardGold.getFrontGold().getImage();
                imagePathResource.add(imagePath);
            }
        }
        // Displayed Gold
        ArrayList<String> imagePathGold = new ArrayList<>(gameImmutable.getDesk().getDisplayedGold().size());
        for (Card card : gameImmutable.getDesk().getDisplayedGold()) {
            if (card instanceof CardResource) {
                CardResource cardResource = (CardResource) card;
                String imagePath = cardResource.getFrontResource().getImage();
                imagePathGold.add(imagePath);
            } else if (card instanceof CardGold) {
                CardGold cardGold = (CardGold) card;
                String imagePath = cardGold.getFrontGold().getImage();
                imagePathGold.add(imagePath);
            }
        }
        // Load images Resource
        displayed1Pane.setVisible(false);
        displayed2Pane.setVisible(false);
        for (int i = 0; i < gameImmutable.getDesk().getDisplayedResource().size(); i++) {
            if (imagePathResource.get(i) != null) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(imagePathResource.get(i)));
                    if (i == 0) {
                        displayed1Pane.setVisible(true);
                        displayed1Image.setImage(image);
                        displayed1Image.setFitWidth(displayed1Pane.getPrefWidth());
                        displayed1Image.setFitHeight(displayed1Pane.getPrefHeight());
                        displayed1Image.setPreserveRatio(true);
                        displayed1Image.setSmooth(true);
                        displayed1Image.setCache(true);
                    }
                    if (i == 1) {
                        displayed2Pane.setVisible(true);
                        displayed2Image.setImage(image);
                        displayed2Image.setFitWidth(displayed2Pane.getPrefWidth());
                        displayed2Image.setFitHeight(displayed2Pane.getPrefHeight());
                        displayed2Image.setPreserveRatio(true);
                        displayed2Image.setSmooth(true);
                        displayed2Image.setCache(true);
                    }
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the displayed cards images.");
                    System.exit(1);
                }
            } else {
                if (i < gameImmutable.getDesk().getDisplayedResource().size()) {
                    showError("Image path is null", "The image path for the displayed card is null.");
                    System.exit(1);
                }
            }
        }
        // Load images Gold
        displayed3Pane.setVisible(false);
        displayed4Pane.setVisible(false);
        for (int i = 0; i < gameImmutable.getDesk().getDisplayedGold().size(); i++) {
            if (imagePathGold.get(i) != null) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(imagePathGold.get(i)));
                    if (i == 0) {
                        displayed3Pane.setVisible(true);
                        displayed3Image.setImage(image);
                        displayed3Image.setFitWidth(displayed3Pane.getPrefWidth());
                        displayed3Image.setFitHeight(displayed3Pane.getPrefHeight());
                        displayed3Image.setPreserveRatio(true);
                        displayed3Image.setSmooth(true);
                        displayed3Image.setCache(true);
                    }
                    if (i == 1) {
                        displayed4Pane.setVisible(true);
                        displayed4Image.setImage(image);
                        displayed4Image.setFitWidth(displayed4Pane.getPrefWidth());
                        displayed4Image.setFitHeight(displayed4Pane.getPrefHeight());
                        displayed4Image.setPreserveRatio(true);
                        displayed4Image.setSmooth(true);
                        displayed4Image.setCache(true);
                    }
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the displayed cards images.");
                    System.exit(1);
                }
            } else {
                if (i < gameImmutable.getDesk().getDisplayedGold().size()) {
                    showError("Image path is null", "The image path for the displayed card is null.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Handles the action of clicking the first displayed button.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionDisplayed1(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("r1");
    }


    /**
     * Handles the action of clicking the second displayed button.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionDisplayed2(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("r2");
    }


    /**
     * Handles the action of clicking the third displayed button.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionDisplayed3(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("g1");
    }


    /**
     * Handles the action of clicking the fourth displayed button.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionDisplayed4(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("g2");
    }


    /**
     * Sets the hand images for the player.
     * @param gameImmutable The current state of the game.
     * @param nickname The nickname of the player.
     */
    public void setHand(GameImmutable gameImmutable, String nickname) {
        hand1Pane.setVisible(false);
        hand2Pane.setVisible(false);
        hand3Pane.setVisible(false);
        // Get the player's hand
        Player player = getPlayer(gameImmutable, nickname);
        // Set hashmaps
        this.hand.clear();
        this.frontSideHand.clear();
        for (int i = 1; i <= player.getHand().size(); i++) {
            this.hand.put("hand" + i, player.getHand().get(i - 1));
            this.frontSideHand.put("hand" + i, true);
        }
        // Loading images
        for (int i = 0; i < player.getHand().size(); i++) {
            String imagePath = getFrontSideImagePath(player.getHand().get(i));
            if (imagePath != null) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(imagePath));
                    if (i == 0) {
                        hand1Pane.setVisible(true);
                        hand1Image.setImage(image);
                        hand1Image.setFitWidth(hand1Pane.getPrefWidth());
                        hand1Image.setFitHeight(hand1Pane.getPrefHeight());
                        hand1Image.setPreserveRatio(true);
                        hand1Image.setSmooth(true);
                        hand1Image.setCache(true);
                    }
                    if (i == 1) {
                        hand2Pane.setVisible(true);
                        hand2Image.setImage(image);
                        hand2Image.setFitWidth(hand2Pane.getPrefWidth());
                        hand2Image.setFitHeight(hand2Pane.getPrefHeight());
                        hand2Image.setPreserveRatio(true);
                        hand2Image.setSmooth(true);
                        hand2Image.setCache(true);
                    }
                    if (i == 2) {
                        hand3Pane.setVisible(true);
                        hand3Image.setImage(image);
                        hand3Image.setFitWidth(hand3Pane.getPrefWidth());
                        hand3Image.setFitHeight(hand3Pane.getPrefHeight());
                        hand3Image.setPreserveRatio(true);
                        hand3Image.setSmooth(true);
                        hand3Image.setCache(true);
                    }
                } catch (Exception e) {
                    showError("Error loading images", "1 There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                if (i < player.getHand().size()) {
                    showError("Image path is null", "The image path for the hand card is null.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Returns the front side image path of a card.
     * @param card The card.
     * @return The front side image path.
     */
    private String getFrontSideImagePath(Card card) {
        if (card instanceof CardResource)
            return ((CardResource) card).getFrontResource().getImage();
        else if (card instanceof CardGold)
            return ((CardGold) card).getFrontGold().getImage();
        else
            return null;
    }


    /**
     * Returns the back side image path of a card.
     * @param card The card.
     * @return The back side image path.
     */
    private String getBackSideImagePath(Card card) {
        if (card instanceof CardResource)
            return ((CardResource) card).getBackResource().getImage();
        else if (card instanceof CardGold)
            return ((CardGold) card).getBackGold().getImage();
        else
            return null;
    }


    /**
     * Handles the action of clicking the first hand card.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionHand1(MouseEvent mouseEvent) {
        hand1Pane.setVisible(false);
        Card card = this.hand.get("hand1");
        String frontSideImagePath = getFrontSideImagePath(card);
        String backSideImagePath = getBackSideImagePath(card);
        if (frontSideImagePath == null || backSideImagePath == null) {
            showError("Image path is null", "The image path for the hand card is null.");
            System.exit(1);
        } else {
            if (frontSideHand.get("hand1")) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(backSideImagePath));
                    hand1Image.setImage(image);
                    hand1Image.setFitWidth(hand1Pane.getPrefWidth());
                    hand1Image.setFitHeight(hand1Pane.getPrefHeight());
                    hand1Image.setPreserveRatio(true);
                    hand1Image.setSmooth(true);
                    hand1Image.setCache(true);
                    frontSideHand.put("hand1", false);
                    hand1Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "2 There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image(getClass().getResourceAsStream(frontSideImagePath));
                    hand1Image.setImage(image);
                    hand1Image.setFitWidth(hand1Pane.getPrefWidth());
                    hand1Image.setFitHeight(hand1Pane.getPrefHeight());
                    hand1Image.setPreserveRatio(true);
                    hand1Image.setSmooth(true);
                    hand1Image.setCache(true);
                    frontSideHand.put("hand1", true);
                    hand1Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "3 There was an error loading the hand cards images.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Handles the action of clicking the second hand card.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionHand2(MouseEvent mouseEvent) {
        hand2Pane.setVisible(false);
        Card card = this.hand.get("hand2");
        String frontSideImagePath = getFrontSideImagePath(card);
        String backSideImagePath = getBackSideImagePath(card);
        if (frontSideImagePath == null || backSideImagePath == null) {
            showError("Image path is null", "The image path for the hand card is null.");
            System.exit(1);
        } else {
            if (frontSideHand.get("hand2")) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(backSideImagePath));
                    hand2Image.setImage(image);
                    hand2Image.setFitWidth(hand2Pane.getPrefWidth());
                    hand2Image.setFitHeight(hand2Pane.getPrefHeight());
                    hand2Image.setPreserveRatio(true);
                    hand2Image.setSmooth(true);
                    hand2Image.setCache(true);
                    frontSideHand.put("hand2", false);
                    hand2Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "4 There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image(getClass().getResourceAsStream(frontSideImagePath));
                    hand2Image.setImage(image);
                    hand2Image.setFitWidth(hand2Pane.getPrefWidth());
                    hand2Image.setFitHeight(hand2Pane.getPrefHeight());
                    hand2Image.setPreserveRatio(true);
                    hand2Image.setSmooth(true);
                    hand2Image.setCache(true);
                    frontSideHand.put("hand2", true);
                    hand2Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "5 There was an error loading the hand cards images.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Handles the action of clicking the third hand card.
     * @param mouseEvent The mouse event.
     */
    @FXML
    public void actionHand3(MouseEvent mouseEvent) {
        hand3Pane.setVisible(false);
        Card card = this.hand.get("hand3");
        String frontSideImagePath = getFrontSideImagePath(card);
        String backSideImagePath = getBackSideImagePath(card);
        if (frontSideImagePath == null || backSideImagePath == null) {
            showError("Image path is null", "The image path for the hand card is null.");
            System.exit(1);
        } else {
            if (frontSideHand.get("hand3")) {
                try {
                    Image image = new Image(getClass().getResourceAsStream(backSideImagePath));
                    hand3Image.setImage(image);
                    hand3Image.setFitWidth(hand3Pane.getPrefWidth());
                    hand3Image.setFitHeight(hand3Pane.getPrefHeight());
                    hand3Image.setPreserveRatio(true);
                    hand3Image.setSmooth(true);
                    hand3Image.setCache(true);
                    frontSideHand.put("hand3", false);
                    hand3Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "6 There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image(getClass().getResourceAsStream(frontSideImagePath));
                    hand3Image.setImage(image);
                    hand3Image.setFitWidth(hand3Pane.getPrefWidth());
                    hand3Image.setFitHeight(hand3Pane.getPrefHeight());
                    hand3Image.setPreserveRatio(true);
                    hand3Image.setSmooth(true);
                    hand3Image.setCache(true);
                    frontSideHand.put("hand3", true);
                    hand3Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "7 There was an error loading the hand cards images.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Sets the boards information to be displayed in the scene.
     * @param gameImmutable The current state of the game.
     */
    public void setBoards(GameImmutable gameImmutable) {
        // Set visibility
        board1.setVisible(false);
        board2.setVisible(false);
        board3.setVisible(false);
        board4.setVisible(false);
        // Set images
        setValuesImages();
        // Set boards
        setBoardsInformation(gameImmutable);
    }


    /**
     * Sets the images for the board items.
     */
    private void setValuesImages() {
        String valuesImagePath = "/it/polimi/ingsw/gc03/gui/images/values/";
        try {
            Image fungiImage = new Image(getClass().getResourceAsStream(valuesImagePath + "fungi.png"));
            fungi1Image.setImage(fungiImage);
            fungi1Image.setFitWidth(fungi1Pane.getPrefWidth());
            fungi1Image.setFitHeight(fungi1Pane.getPrefHeight());
            fungi1Image.setPreserveRatio(true);
            fungi1Image.setSmooth(true);
            fungi1Image.setCache(true);
            fungi2Image.setImage(fungiImage);
            fungi2Image.setFitWidth(fungi2Pane.getPrefWidth());
            fungi2Image.setFitHeight(fungi2Pane.getPrefHeight());
            fungi2Image.setPreserveRatio(true);
            fungi2Image.setSmooth(true);
            fungi2Image.setCache(true);
            fungi3Image.setImage(fungiImage);
            fungi3Image.setFitWidth(fungi3Pane.getPrefWidth());
            fungi3Image.setFitHeight(fungi3Pane.getPrefHeight());
            fungi3Image.setPreserveRatio(true);
            fungi3Image.setSmooth(true);
            fungi3Image.setCache(true);
            fungi4Image.setImage(fungiImage);
            fungi4Image.setFitWidth(fungi4Pane.getPrefWidth());
            fungi4Image.setFitHeight(fungi4Pane.getPrefHeight());
            fungi4Image.setPreserveRatio(true);
            fungi4Image.setSmooth(true);
            fungi4Image.setCache(true);
            Image plantImage = new Image(getClass().getResourceAsStream(valuesImagePath + "plant.png"));
            plant1Image.setImage(plantImage);
            plant1Image.setFitWidth(plant1Pane.getPrefWidth());
            plant1Image.setFitHeight(plant1Pane.getPrefHeight());
            plant1Image.setPreserveRatio(true);
            plant1Image.setSmooth(true);
            plant1Image.setCache(true);
            plant2Image.setImage(plantImage);
            plant2Image.setFitWidth(plant2Pane.getPrefWidth());
            plant2Image.setFitHeight(plant2Pane.getPrefHeight());
            plant2Image.setPreserveRatio(true);
            plant2Image.setSmooth(true);
            plant2Image.setCache(true);
            plant3Image.setImage(plantImage);
            plant3Image.setFitWidth(plant3Pane.getPrefWidth());
            plant3Image.setFitHeight(plant3Pane.getPrefHeight());
            plant3Image.setPreserveRatio(true);
            plant3Image.setSmooth(true);
            plant3Image.setCache(true);
            plant4Image.setImage(plantImage);
            plant4Image.setFitWidth(plant4Pane.getPrefWidth());
            plant4Image.setFitHeight(plant4Pane.getPrefHeight());
            plant4Image.setPreserveRatio(true);
            plant4Image.setSmooth(true);
            plant4Image.setCache(true);
            Image animalImage = new Image(getClass().getResourceAsStream(valuesImagePath + "animal.png"));
            animal1Image.setImage(animalImage);
            animal1Image.setFitWidth(animal1Pane.getPrefWidth());
            animal1Image.setFitHeight(animal1Pane.getPrefHeight());
            animal1Image.setPreserveRatio(true);
            animal1Image.setSmooth(true);
            animal1Image.setCache(true);
            animal2Image.setImage(animalImage);
            animal2Image.setFitWidth(animal2Pane.getPrefWidth());
            animal2Image.setFitHeight(animal2Pane.getPrefHeight());
            animal2Image.setPreserveRatio(true);
            animal2Image.setSmooth(true);
            animal2Image.setCache(true);
            animal3Image.setImage(animalImage);
            animal3Image.setFitWidth(animal3Pane.getPrefWidth());
            animal3Image.setFitHeight(animal3Pane.getPrefHeight());
            animal3Image.setPreserveRatio(true);
            animal3Image.setSmooth(true);
            animal3Image.setCache(true);
            animal4Image.setImage(animalImage);
            animal4Image.setFitWidth(animal4Pane.getPrefWidth());
            animal4Image.setFitHeight(animal4Pane.getPrefHeight());
            animal4Image.setPreserveRatio(true);
            animal4Image.setSmooth(true);
            animal4Image.setCache(true);
            Image insectImage = new Image(getClass().getResourceAsStream(valuesImagePath + "insect.png"));
            insect1Image.setImage(insectImage);
            insect1Image.setFitWidth(insect1Pane.getPrefWidth());
            insect1Image.setFitHeight(insect1Pane.getPrefHeight());
            insect1Image.setPreserveRatio(true);
            insect1Image.setSmooth(true);
            insect1Image.setCache(true);
            insect2Image.setImage(insectImage);
            insect2Image.setFitWidth(insect2Pane.getPrefWidth());
            insect2Image.setFitHeight(insect2Pane.getPrefHeight());
            insect2Image.setPreserveRatio(true);
            insect2Image.setSmooth(true);
            insect2Image.setCache(true);
            insect3Image.setImage(insectImage);
            insect3Image.setFitWidth(insect3Pane.getPrefWidth());
            insect3Image.setFitHeight(insect3Pane.getPrefHeight());
            insect3Image.setPreserveRatio(true);
            insect3Image.setSmooth(true);
            insect3Image.setCache(true);
            insect4Image.setImage(insectImage);
            insect4Image.setFitWidth(insect4Pane.getPrefWidth());
            insect4Image.setFitHeight(insect4Pane.getPrefHeight());
            insect4Image.setPreserveRatio(true);
            insect4Image.setSmooth(true);
            insect4Image.setCache(true);
            Image quillImage = new Image(getClass().getResourceAsStream(valuesImagePath + "quill.png"));
            quill1Image.setImage(quillImage);
            quill1Image.setFitWidth(quill1Pane.getPrefWidth());
            quill1Image.setFitHeight(quill1Pane.getPrefHeight());
            quill1Image.setPreserveRatio(true);
            quill1Image.setSmooth(true);
            quill1Image.setCache(true);
            quill2Image.setImage(quillImage);
            quill2Image.setFitWidth(quill2Pane.getPrefWidth());
            quill2Image.setFitHeight(quill2Pane.getPrefHeight());
            quill2Image.setPreserveRatio(true);
            quill2Image.setSmooth(true);
            quill2Image.setCache(true);
            quill3Image.setImage(quillImage);
            quill3Image.setFitWidth(quill3Pane.getPrefWidth());
            quill3Image.setFitHeight(quill3Pane.getPrefHeight());
            quill3Image.setPreserveRatio(true);
            quill3Image.setSmooth(true);
            quill3Image.setCache(true);
            quill4Image.setImage(quillImage);
            quill4Image.setFitWidth(quill4Pane.getPrefWidth());
            quill4Image.setFitHeight(quill4Pane.getPrefHeight());
            quill4Image.setPreserveRatio(true);
            quill4Image.setSmooth(true);
            quill4Image.setCache(true);
            Image inkwellImage = new Image(getClass().getResourceAsStream(valuesImagePath + "inkwell.png"));
            inkwell1Image.setImage(inkwellImage);
            inkwell1Image.setFitWidth(inkwell1Pane.getPrefWidth());
            inkwell1Image.setFitHeight(inkwell1Pane.getPrefHeight());
            inkwell1Image.setPreserveRatio(true);
            inkwell1Image.setSmooth(true);
            inkwell1Image.setCache(true);
            inkwell2Image.setImage(inkwellImage);
            inkwell2Image.setFitWidth(inkwell2Pane.getPrefWidth());
            inkwell2Image.setFitHeight(inkwell2Pane.getPrefHeight());
            inkwell2Image.setPreserveRatio(true);
            inkwell2Image.setSmooth(true);
            inkwell2Image.setCache(true);
            inkwell3Image.setImage(inkwellImage);
            inkwell3Image.setFitWidth(inkwell3Pane.getPrefWidth());
            inkwell3Image.setFitHeight(inkwell3Pane.getPrefHeight());
            inkwell3Image.setPreserveRatio(true);
            inkwell3Image.setSmooth(true);
            inkwell3Image.setCache(true);
            inkwell4Image.setImage(inkwellImage);
            inkwell4Image.setFitWidth(inkwell4Pane.getPrefWidth());
            inkwell4Image.setFitHeight(inkwell4Pane.getPrefHeight());
            inkwell4Image.setPreserveRatio(true);
            inkwell4Image.setSmooth(true);
            inkwell4Image.setCache(true);
            Image manuscriptImage = new Image(getClass().getResourceAsStream(valuesImagePath + "manuscript.png"));
            manuscript1Image.setImage(manuscriptImage);
            manuscript1Image.setFitWidth(manuscript1Pane.getPrefWidth());
            manuscript1Image.setFitHeight(manuscript1Pane.getPrefHeight());
            manuscript1Image.setPreserveRatio(true);
            manuscript1Image.setSmooth(true);
            manuscript1Image.setCache(true);
            manuscript2Image.setImage(manuscriptImage);
            manuscript2Image.setFitWidth(manuscript2Pane.getPrefWidth());
            manuscript2Image.setFitHeight(manuscript2Pane.getPrefHeight());
            manuscript2Image.setPreserveRatio(true);
            manuscript2Image.setSmooth(true);
            manuscript2Image.setCache(true);
            manuscript3Image.setImage(manuscriptImage);
            manuscript3Image.setFitWidth(manuscript3Pane.getPrefWidth());
            manuscript3Image.setFitHeight(manuscript3Pane.getPrefHeight());
            manuscript3Image.setPreserveRatio(true);
            manuscript3Image.setSmooth(true);
            manuscript3Image.setCache(true);
            manuscript4Image.setImage(manuscriptImage);
            manuscript4Image.setFitWidth(manuscript4Pane.getPrefWidth());
            manuscript4Image.setFitHeight(manuscript4Pane.getPrefHeight());
            manuscript4Image.setPreserveRatio(true);
            manuscript4Image.setSmooth(true);
            manuscript4Image.setCache(true);
            Image coveredImage = new Image(getClass().getResourceAsStream(valuesImagePath + "covered.png"));
            covered1Image.setImage(coveredImage);
            covered1Image.setFitWidth(covered1Pane.getPrefWidth());
            covered1Image.setFitHeight(covered1Pane.getPrefHeight());
            covered1Image.setPreserveRatio(true);
            covered1Image.setSmooth(true);
            covered1Image.setCache(true);
            covered2Image.setImage(coveredImage);
            covered2Image.setFitWidth(covered2Pane.getPrefWidth());
            covered2Image.setFitHeight(covered2Pane.getPrefHeight());
            covered2Image.setPreserveRatio(true);
            covered2Image.setSmooth(true);
            covered2Image.setCache(true);
            covered3Image.setImage(coveredImage);
            covered3Image.setFitWidth(covered3Pane.getPrefWidth());
            covered3Image.setFitHeight(covered3Pane.getPrefHeight());
            covered3Image.setPreserveRatio(true);
            covered3Image.setSmooth(true);
            covered3Image.setCache(true);
            covered4Image.setImage(coveredImage);
            covered4Image.setFitWidth(covered4Pane.getPrefWidth());
            covered4Image.setFitHeight(covered4Pane.getPrefHeight());
            covered4Image.setPreserveRatio(true);
            covered4Image.setSmooth(true);
            covered4Image.setCache(true);
        } catch (Exception e) {
            showError("Error loading images", "There was an error loading the values images.");
            System.exit(1);
        }
    }


    /**
     * Sets the boards information for each player.
     * @param gameImmutable The current state of the game.
     */
    private void setBoardsInformation(GameImmutable gameImmutable) {
        // Set boards' hands to invisible
        hand11Pane.setVisible(false);
        hand12Pane.setVisible(false);
        hand13Pane.setVisible(false);
        hand21Pane.setVisible(false);
        hand22Pane.setVisible(false);
        hand23Pane.setVisible(false);
        hand31Pane.setVisible(false);
        hand32Pane.setVisible(false);
        hand33Pane.setVisible(false);
        hand41Pane.setVisible(false);
        hand42Pane.setVisible(false);
        hand43Pane.setVisible(false);
        // players <= 2
        if (gameImmutable.getPlayers().size() <= 2) {
            for (int i = 0; i < gameImmutable.getPlayers().size(); i++) {
                // Set board 2
                if (i == 0) {
                    // Set color and nickname
                    String color = getPlayerColor(gameImmutable.getPlayers().get(i).getColor());
                    color2.setStyle("-fx-background-color: " + color + ";");
                    nickname2.setText(gameImmutable.getPlayers().get(i).getNickname());
                    // Set counters
                    fungiCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[0]));
                    plantCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[1]));
                    animalCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[2]));
                    insectCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[3]));
                    quillCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[4]));
                    inkwellCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[5]));
                    manuscriptCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[6]));
                    coveredCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[7]));
                    // Set hand images
                    ArrayList<String> imagePaths = new ArrayList<>(3);
                    for (Card card : gameImmutable.getPlayers().get(i).getHand()) {
                        String imagePath = getBackSideImagePath(card);
                        imagePaths.add(imagePath);
                    }
                    for (int j = 0; j < gameImmutable.getPlayers().get(i).getHand().size(); j++) {
                        if (imagePaths.get(j) != null) {
                            try {
                                Image image = new Image(getClass().getResourceAsStream(imagePaths.get(j)));
                                if (j == 0) {
                                    hand21Pane.setVisible(true);
                                    hand21Image.setImage(image);
                                    hand21Image.setFitWidth(hand21Pane.getPrefWidth());
                                    hand21Image.setFitHeight(hand21Pane.getPrefHeight());
                                    hand21Image.setPreserveRatio(true);
                                    hand21Image.setSmooth(true);
                                    hand21Image.setCache(true);
                                }
                                if (j == 1) {
                                    hand22Pane.setVisible(true);
                                    hand22Image.setImage(image);
                                    hand22Image.setFitWidth(hand22Pane.getPrefWidth());
                                    hand22Image.setFitHeight(hand22Pane.getPrefHeight());
                                    hand22Image.setPreserveRatio(true);
                                    hand22Image.setSmooth(true);
                                    hand22Image.setCache(true);
                                }
                                if (j == 2) {
                                    hand23Pane.setVisible(true);
                                    hand23Image.setImage(image);
                                    hand23Image.setFitWidth(hand23Pane.getPrefWidth());
                                    hand23Image.setFitHeight(hand23Pane.getPrefHeight());
                                    hand23Image.setPreserveRatio(true);
                                    hand23Image.setSmooth(true);
                                    hand23Image.setCache(true);
                                }
                            } catch (Exception e) {
                                showError("Error loading images", "8 There was an error loading the hand cards images.");
                                System.exit(1);
                            }
                        } else {
                            if (j < gameImmutable.getPlayers().get(i).getHand().size()){
                                showError("Image path is null", "The image path for the hand card is null.");
                                System.exit(1);
                            }
                        }
                    }
                    board2.setVisible(true);
                }
                // Set board 3
                if (i == 1) {
                    // Set color and nickname
                    String color = getPlayerColor(gameImmutable.getPlayers().get(i).getColor());
                    color3.setStyle("-fx-background-color: " + color + ";");
                    nickname3.setText(gameImmutable.getPlayers().get(i).getNickname());
                    // Set counters
                    fungiCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[0]));
                    plantCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[1]));
                    animalCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[2]));
                    insectCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[3]));
                    quillCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[4]));
                    inkwellCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[5]));
                    manuscriptCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[6]));
                    coveredCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[7]));
                    // Set hand images
                    ArrayList<String> imagePaths = new ArrayList<>(3);
                    for (Card card : gameImmutable.getPlayers().get(i).getHand()) {
                        String imagePath = getBackSideImagePath(card);
                        imagePaths.add(imagePath);
                    }
                    for (int j = 0; j < gameImmutable.getPlayers().get(i).getHand().size(); j++) {
                        if (imagePaths.get(j) != null) {
                            try {
                                Image image = new Image(getClass().getResourceAsStream(imagePaths.get(j)));
                                if (j == 0) {
                                    hand31Pane.setVisible(true);
                                    hand31Image.setImage(image);
                                    hand31Image.setFitWidth(hand31Pane.getPrefWidth());
                                    hand31Image.setFitHeight(hand31Pane.getPrefHeight());
                                    hand31Image.setPreserveRatio(true);
                                    hand31Image.setSmooth(true);
                                    hand31Image.setCache(true);
                                }
                                if (j == 1) {
                                    hand32Pane.setVisible(true);
                                    hand32Image.setImage(image);
                                    hand32Image.setFitWidth(hand32Pane.getPrefWidth());
                                    hand32Image.setFitHeight(hand32Pane.getPrefHeight());
                                    hand32Image.setPreserveRatio(true);
                                    hand32Image.setSmooth(true);
                                    hand32Image.setCache(true);
                                }
                                if (j == 2) {
                                    hand33Pane.setVisible(true);
                                    hand33Image.setImage(image);
                                    hand33Image.setFitWidth(hand33Pane.getPrefWidth());
                                    hand33Image.setFitHeight(hand33Pane.getPrefHeight());
                                    hand33Image.setPreserveRatio(true);
                                    hand33Image.setSmooth(true);
                                    hand33Image.setCache(true);
                                }
                            } catch (Exception e) {
                                showError("Error loading images", "9 There was an error loading the hand cards images.");
                                System.exit(1);
                            }
                        } else {
                            if (j < gameImmutable.getPlayers().get(i).getHand().size()){
                                showError("Image path is null", "The image path for the hand card is null.");
                                System.exit(1);
                            }
                        }
                    }
                    board3.setVisible(true);
                }
            }
            // players > 2
        } else {
            for (int i = 0; i < gameImmutable.getPlayers().size(); i++) {
                // Set board 1
                if (i == 0) {
                    // Set color and nickname
                    String color = getPlayerColor(gameImmutable.getPlayers().get(i).getColor());
                    color1.setStyle("-fx-background-color: " + color + ";");
                    nickname1.setText(gameImmutable.getPlayers().get(i).getNickname());
                    // Set counters
                    fungiCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[0]));
                    plantCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[1]));
                    animalCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[2]));
                    insectCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[3]));
                    quillCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[4]));
                    inkwellCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[5]));
                    manuscriptCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[6]));
                    coveredCount1.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[7]));
                    // Set hand images
                    ArrayList<String> imagePaths = new ArrayList<>(3);
                    for (Card card : gameImmutable.getPlayers().get(i).getHand()) {
                        String imagePath = getBackSideImagePath(card);
                        imagePaths.add(imagePath);
                    }
                    for (int j = 0; j < gameImmutable.getPlayers().get(i).getHand().size(); j++) {
                        if (imagePaths.get(j) != null) {
                            try {
                                Image image = new Image(getClass().getResourceAsStream(imagePaths.get(j)));
                                if (j == 0) {
                                    hand11Pane.setVisible(true);
                                    hand11Image.setImage(image);
                                    hand11Image.setFitWidth(hand11Pane.getPrefWidth());
                                    hand11Image.setFitHeight(hand11Pane.getPrefHeight());
                                    hand11Image.setPreserveRatio(true);
                                    hand11Image.setSmooth(true);
                                    hand11Image.setCache(true);
                                }
                                if (j == 1) {
                                    hand12Pane.setVisible(true);
                                    hand12Image.setImage(image);
                                    hand12Image.setFitWidth(hand12Pane.getPrefWidth());
                                    hand12Image.setFitHeight(hand12Pane.getPrefHeight());
                                    hand12Image.setPreserveRatio(true);
                                    hand12Image.setSmooth(true);
                                    hand12Image.setCache(true);
                                }
                                if (j == 2) {
                                    hand13Pane.setVisible(true);
                                    hand13Image.setImage(image);
                                    hand13Image.setFitWidth(hand13Pane.getPrefWidth());
                                    hand13Image.setFitHeight(hand13Pane.getPrefHeight());
                                    hand13Image.setPreserveRatio(true);
                                    hand13Image.setSmooth(true);
                                    hand13Image.setCache(true);
                                }
                            } catch (Exception e) {
                                showError("Error loading images", "10 There was an error loading the hand cards images.");
                                System.exit(1);
                            }
                        } else {
                            if (j < gameImmutable.getPlayers().get(i).getHand().size()){
                                showError("Image path is null", "The image path for the hand card is null.");
                                System.exit(1);
                            }
                        }
                    }
                    board1.setVisible(true);
                }
                // Set board 2
                if (i == 1) {
                    // Set color and nickname
                    String color = getPlayerColor(gameImmutable.getPlayers().get(i).getColor());
                    color2.setStyle("-fx-background-color: " + color + ";");
                    nickname2.setText(gameImmutable.getPlayers().get(i).getNickname());
                    // Set counters
                    fungiCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[0]));
                    plantCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[1]));
                    animalCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[2]));
                    insectCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[3]));
                    quillCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[4]));
                    inkwellCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[5]));
                    manuscriptCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[6]));
                    coveredCount2.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[7]));
                    // Set hand images
                    ArrayList<String> imagePaths = new ArrayList<>(3);
                    for (Card card : gameImmutable.getPlayers().get(i).getHand()) {
                        String imagePath = getBackSideImagePath(card);
                        imagePaths.add(imagePath);
                    }
                    for (int j = 0; j < gameImmutable.getPlayers().get(i).getHand().size(); j++) {
                        if (imagePaths.get(j) != null) {
                            try {
                                Image image = new Image(getClass().getResourceAsStream(imagePaths.get(j)));
                                if (j == 0) {
                                    hand21Pane.setVisible(true);
                                    hand21Image.setImage(image);
                                    hand21Image.setFitWidth(hand21Pane.getPrefWidth());
                                    hand21Image.setFitHeight(hand21Pane.getPrefHeight());
                                    hand21Image.setPreserveRatio(true);
                                    hand21Image.setSmooth(true);
                                    hand21Image.setCache(true);
                                }
                                if (j == 1) {
                                    hand22Pane.setVisible(true);
                                    hand22Image.setImage(image);
                                    hand22Image.setFitWidth(hand22Pane.getPrefWidth());
                                    hand22Image.setFitHeight(hand22Pane.getPrefHeight());
                                    hand22Image.setPreserveRatio(true);
                                    hand22Image.setSmooth(true);
                                    hand22Image.setCache(true);
                                }
                                if (j == 2) {
                                    hand23Pane.setVisible(true);
                                    hand23Image.setImage(image);
                                    hand23Image.setFitWidth(hand23Pane.getPrefWidth());
                                    hand23Image.setFitHeight(hand23Pane.getPrefHeight());
                                    hand23Image.setPreserveRatio(true);
                                    hand23Image.setSmooth(true);
                                    hand23Image.setCache(true);
                                }
                            } catch (Exception e) {
                                showError("Error loading images", "11 There was an error loading the hand cards images.");
                                System.exit(1);
                            }
                        } else {
                            if (j < gameImmutable.getPlayers().get(i).getHand().size()){
                                showError("Image path is null", "The image path for the hand card is null.");
                                System.exit(1);
                            }
                        }
                    }
                    board2.setVisible(true);
                }
                // Set board 3
                if (i == 2) {
                    // Set color and nickname
                    String color = getPlayerColor(gameImmutable.getPlayers().get(i).getColor());
                    color3.setStyle("-fx-background-color: " + color + ";");
                    nickname3.setText(gameImmutable.getPlayers().get(i).getNickname());
                    // Set counters
                    fungiCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[0]));
                    plantCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[1]));
                    animalCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[2]));
                    insectCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[3]));
                    quillCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[4]));
                    inkwellCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[5]));
                    manuscriptCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[6]));
                    coveredCount3.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[7]));
                    // Set hand images
                    ArrayList<String> imagePaths = new ArrayList<>(3);
                    for (Card card : gameImmutable.getPlayers().get(i).getHand()) {
                        String imagePath = getBackSideImagePath(card);
                        imagePaths.add(imagePath);
                    }
                    for (int j = 0; j < gameImmutable.getPlayers().get(i).getHand().size(); j++) {
                        if (imagePaths.get(j) != null) {
                            try {
                                Image image = new Image(getClass().getResourceAsStream(imagePaths.get(j)));
                                if (j == 0) {
                                    hand31Pane.setVisible(true);
                                    hand31Image.setImage(image);
                                    hand31Image.setFitWidth(hand31Pane.getPrefWidth());
                                    hand31Image.setFitHeight(hand31Pane.getPrefHeight());
                                    hand31Image.setPreserveRatio(true);
                                    hand31Image.setSmooth(true);
                                    hand31Image.setCache(true);
                                }
                                if (j == 1) {
                                    hand32Pane.setVisible(true);
                                    hand32Image.setImage(image);
                                    hand32Image.setFitWidth(hand32Pane.getPrefWidth());
                                    hand32Image.setFitHeight(hand32Pane.getPrefHeight());
                                    hand32Image.setPreserveRatio(true);
                                    hand32Image.setSmooth(true);
                                    hand32Image.setCache(true);
                                }
                                if (j == 2) {
                                    hand33Pane.setVisible(true);
                                    hand33Image.setImage(image);
                                    hand33Image.setFitWidth(hand33Pane.getPrefWidth());
                                    hand33Image.setFitHeight(hand33Pane.getPrefHeight());
                                    hand33Image.setPreserveRatio(true);
                                    hand33Image.setSmooth(true);
                                    hand33Image.setCache(true);
                                }
                            } catch (Exception e) {
                                showError("Error loading images", "12 There was an error loading the hand cards images.");
                                System.exit(1);
                            }
                        } else {
                            if (j < gameImmutable.getPlayers().get(i).getHand().size()){
                                showError("Image path is null", "The image path for the hand card is null.");
                                System.exit(1);
                            }
                        }
                    }
                    board3.setVisible(true);
                }
                // Set board 4
                if (i == 3) {
                    // Set color and nickname
                    String color = getPlayerColor(gameImmutable.getPlayers().get(i).getColor());
                    color4.setStyle("-fx-background-color: " + color + ";");
                    nickname4.setText(gameImmutable.getPlayers().get(i).getNickname());
                    // Set counters
                    fungiCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[0]));
                    plantCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[1]));
                    animalCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[2]));
                    insectCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[3]));
                    quillCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[4]));
                    inkwellCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[5]));
                    manuscriptCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[6]));
                    coveredCount4.setText(Integer.toString(gameImmutable.getPlayers().get(i).getCodex().getCounterCodex()[7]));
                    // Set hand images
                    ArrayList<String> imagePaths = new ArrayList<>(3);
                    for (Card card : gameImmutable.getPlayers().get(i).getHand()) {
                        String imagePath = getBackSideImagePath(card);
                        imagePaths.add(imagePath);
                    }
                    for (int j = 0; j < gameImmutable.getPlayers().get(i).getHand().size(); j++) {
                        if (imagePaths.get(j) != null) {
                            try {
                                Image image = new Image(getClass().getResourceAsStream(imagePaths.get(j)));
                                if (j == 0) {
                                    hand41Pane.setVisible(true);
                                    hand41Image.setImage(image);
                                    hand41Image.setFitWidth(hand41Pane.getPrefWidth());
                                    hand41Image.setFitHeight(hand41Pane.getPrefHeight());
                                    hand41Image.setPreserveRatio(true);
                                    hand41Image.setSmooth(true);
                                    hand41Image.setCache(true);
                                }
                                if (j == 1) {
                                    hand42Pane.setVisible(true);
                                    hand42Image.setImage(image);
                                    hand42Image.setFitWidth(hand42Pane.getPrefWidth());
                                    hand42Image.setFitHeight(hand42Pane.getPrefHeight());
                                    hand42Image.setPreserveRatio(true);
                                    hand42Image.setSmooth(true);
                                    hand42Image.setCache(true);
                                }
                                if (j == 2) {
                                    hand43Pane.setVisible(true);
                                    hand43Image.setImage(image);
                                    hand43Image.setFitWidth(hand43Pane.getPrefWidth());
                                    hand43Image.setFitHeight(hand43Pane.getPrefHeight());
                                    hand43Image.setPreserveRatio(true);
                                    hand43Image.setSmooth(true);
                                    hand43Image.setCache(true);
                                }
                            } catch (Exception e) {
                                showError("Error loading images", "13 There was an error loading the hand cards images.");
                                System.exit(1);
                            }
                        } else {
                            if (j < gameImmutable.getPlayers().get(i).getHand().size()){
                                showError("Image path is null", "The image path for the hand card is null.");
                                System.exit(1);
                            }
                        }
                    }
                    board4.setVisible(true);
                }
            }
        }
    }


    /**
     * Returns the color of a player as a string.
     * @param color The color enumeration.
     * @return The color as a string.
     */
    private String getPlayerColor(Color color) {
        if (color == Color.RED)
            return "red";
        if (color == Color.BLUE)
            return "blue";
        if (color == Color.GREEN)
            return "green";
        if (color == Color.YELLOW)
            return "yellow";
        return null;
    }


    /**
     * Adds a message to the chat ListView.
     * @param message The chat message.
     * @param nickname The player's nickname.
     * @param gameImmutable The current state of the game.
     */
    public void addMessages(ChatMessage message , String nickname, GameImmutable gameImmutable) {
        String text = "["+ message.getTimestamp().getHour()+ ":" + message.getTimestamp().getMinute()+":" + message.getTimestamp().getSecond()+"] "
                +message.getSender()+": "+message.getText();
        if (message.getReceiver().equals("everyone")) {
            chat.getItems().add("\n"+ text);
        }else if (message.getReceiver().equals(nickname) || message.getSender().equals(nickname)) {
            chat.getItems().add("\n"+ "[Private] " + text);
        }
    }


    /**
     * Adds an event to the latest events ListView.
     * @param event The event description.
     * @param gameImmutable The current state of the game.
     */
    public void addLatestEvents(String event, GameImmutable gameImmutable) {
        latestEvent.getItems().add("\n" + event);
    }


    /**
     * Sends the chat message to the selected receiver.
     */
    public void actionSend() {
       String receiver = chatReceiver.getSelectionModel().getSelectedItem();
       if (receiver.equals("everyone")) {
           getInputReaderGUI().addTxt("m " + chatMessage.getText());
       }
       else {
           getInputReaderGUI().addTxt("pm " + receiver + " " + chatMessage.getText());
       }
       chatMessage.clear();
    }


    /**
     * Sets the possible receivers in the chat receiver ComboBox.
     * Sets the default receiver to "everyone".
     * @param gameImmutable The current state of the game.
     * @param nickname The player's nickname.
     */
    public void setReceivers(GameImmutable gameImmutable, String nickname) {
        if(idGame!=gameImmutable.getIdGame()) {
            chatReceiver.getItems().add("everyone");
            for (Player p : gameImmutable.getPlayers()) {
                if (!p.getNickname().equals(nickname)) {
                    chatReceiver.getItems().add(p.getNickname());
                }
            }
            chatReceiver.setValue("everyone");
            idGame = gameImmutable.getIdGame();
        }
    }


    /**
     * Clears the chat ListView, event ListView, and receivers ComboBox if the game ID has changed.
     * @param gameImmutable The current state of the game.
     */
    public void clear(GameImmutable gameImmutable) {
        if (idGame!= gameImmutable.getIdGame() || idGame==0) {
            chat.getItems().clear();
            chatReceiver.getItems().clear();
            latestEvent.getItems().clear();
        }
    }


    /**
     * Sets the codex for the player.
     * @param gameImmutable The current state of the game.
     * @param nickname The player's nickname.
     */
    public void setCodex(GameImmutable gameImmutable, String nickname) {
        // Clean all grid cells
        clearImageViewsFromGridPane(codexGrid);
        // Insert the image into the cells
        Player player = getPlayer(gameImmutable, nickname);
        for (Coords coords : player.getCodex().getCodexFillOrder()) {
            Side side = player.getCodex().getSideAt(coords.getX(), coords.getY());
            String imagePath = side.getImage();
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(201.15);
            imageView.setFitHeight(134.1);
            imageView.setPreserveRatio(false);
            codexGrid.setHalignment(imageView, HPos.CENTER);
            codexGrid.setValignment(imageView, VPos.CENTER);
            codexGrid.add(imageView, coords.getY(), coords.getX());
        }
    }


    /**
     * Clears all ImageView nodes from the given GridPane.
     * @param gridPane The GridPane from which ImageView nodes will be removed.
     */
    private void clearImageViewsFromGridPane(GridPane gridPane) {
        gridPane.getChildren().removeIf(node -> node instanceof ImageView);
    }


    /**
     * Initializes the controller class.
     * This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    public void initialize() {
        // Set cell IDs
        setGridCellIds();
        // Make the hands draggable and assign their respective indices
        makeHandDraggable(this.hand1Image, "0");
        makeHandDraggable(this.hand2Image, "1");
        makeHandDraggable(this.hand3Image, "2");
        // Set drag over event handler for the GridPane
        this.codexGrid.setOnDragOver(event -> {
            if (event.getGestureSource() != this.codexGrid && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });
        // Set drag dropped event handler for the GridPane
        this.codexGrid.setOnDragDropped(event -> {
            Dragboard dragboard = event.getDragboard();
            boolean success = false;
            if (dragboard.hasString()) {
                // Find the node at the drop location
                Node targetNode = event.getPickResult().getIntersectedNode();
                // Trace your way back until you find a node with an ID
                while (targetNode != null && !(targetNode instanceof Pane) && targetNode.getId() == null) {
                    targetNode = targetNode.getParent();
                }
                if (targetNode != null && targetNode.getId() != null && targetNode.getId().startsWith("cell_")) {
                    String cellId = targetNode.getId();
                    String[] parts = cellId.split("_");
                    int row = Integer.parseInt(parts[1]);
                    int col = Integer.parseInt(parts[2]);
                    // Check if the cell satisfies the condition
                    if ((row % 2 == 0 && col % 2 == 0) || (row % 2 != 0 && col % 2 != 0)) {
                        String handIndex = dragboard.getString();
                        boolean frontSide = this.frontSideHand.get("hand" + (Integer.parseInt(handIndex) + 1));
                        String command = frontSide ? "PLACECARDGUI true" : "PLACECARDGUI false";
                        command += " " + handIndex + " " + col + " " + row;
                        getInputReaderGUI().addTxt(command);
                        success = true;
                    }
                }
            }
            event.setDropCompleted(success);
            event.consume();
        });

        // Make the ScrollPane moveable with the mouse
        makeScrollPaneDraggable(codexScroll);
        codexScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        codexScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }


    /**
     * Makes the specified ScrollPane draggable using mouse events.
     * @param scrollPane The ScrollPane to be made draggable.
     */
    private void makeScrollPaneDraggable(ScrollPane scrollPane) {
        // ObjectProperty to store the last mouse position
        final ObjectProperty<Point2D> lastMousePosition = new SimpleObjectProperty<>();
        // Event handler for mouse press event on the ScrollPane
        scrollPane.setOnMousePressed(event -> {
            // Store the current mouse position
            lastMousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
            event.consume();
        });
        // Event handler for mouse drag event on the ScrollPane
        scrollPane.setOnMouseDragged(event -> {
            if (lastMousePosition.get() != null) {
                // Calculate the distance the mouse has moved
                double deltaX = event.getSceneX() - lastMousePosition.get().getX();
                double deltaY = event.getSceneY() - lastMousePosition.get().getY();
                // Update the last mouse position
                lastMousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                // Adjust the ScrollPane's horizontal and vertical scroll values
                scrollPane.setHvalue(scrollPane.getHvalue() - deltaX / scrollPane.getContent().getBoundsInLocal().getWidth());
                scrollPane.setVvalue(scrollPane.getVvalue() - deltaY / scrollPane.getContent().getBoundsInLocal().getHeight());
            }
            event.consume();
        });
        // Ensure the content within the ScrollPane does not interfere with the drag
        // Event handler for mouse press event on the content of the ScrollPane
        scrollPane.getContent().setOnMousePressed(event -> {
            // Store the current mouse position
            lastMousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
            event.consume();
        });
        // Event handler for mouse drag event on the content of the ScrollPane
        scrollPane.getContent().setOnMouseDragged(event -> {
            if (lastMousePosition.get() != null) {
                // Calculate the distance the mouse has moved
                double deltaX = event.getSceneX() - lastMousePosition.get().getX();
                double deltaY = event.getSceneY() - lastMousePosition.get().getY();
                // Update the last mouse position
                lastMousePosition.set(new Point2D(event.getSceneX(), event.getSceneY()));
                // Adjust the ScrollPane's horizontal and vertical scroll values
                scrollPane.setHvalue(scrollPane.getHvalue() - deltaX / scrollPane.getContent().getBoundsInLocal().getWidth());
                scrollPane.setVvalue(scrollPane.getVvalue() - deltaY / scrollPane.getContent().getBoundsInLocal().getHeight());
            }
            event.consume();
        });
    }


    /**
     * Sets the cell IDs for the grid.
     */
    private void setGridCellIds() {
        for (int row = 0; row < codexGrid.getRowCount(); row++) {
            for (int col = 0; col < codexGrid.getColumnCount(); col++) {
                Pane cellPane = new Pane();
                cellPane.setId("cell_" + row + "_" + col);
                codexGrid.add(cellPane, col, row);
            }
        }
    }


    /**
     * Makes the specified ImageView draggable and associates it with a given index.
     * @param handImage The ImageView to be made draggable.
     * @param index The index associated with the ImageView.
     */
    private void makeHandDraggable(ImageView handImage, String index) {
        // Set an event handler for when a drag is detected on the ImageView
        handImage.setOnDragDetected(event -> {
            // Start a drag-and-drop operation with the MOVE transfer mode
            Dragboard dragboard = handImage.startDragAndDrop(TransferMode.MOVE);
            // Create a ClipboardContent object to hold the data to be transferred
            ClipboardContent clipboardContent = new ClipboardContent();
            // Put the index (converted to a string) into the ClipboardContent
            clipboardContent.putString(index);
            // Set the content of the Dragboard with the ClipboardContent data
            dragboard.setContent(clipboardContent);
            // Sets the drag view for the Dragboard to the specified image.
            SnapshotParameters snapshotParameters = new SnapshotParameters();
            snapshotParameters.setTransform(Transform.scale(0.75, 0.75));
            WritableImage dragView = handImage.snapshot(snapshotParameters, null);
            dragboard.setDragView(dragView, dragView.getWidth()/2 ,dragView.getHeight()/2);
            // Consume the event, indicating that it has been handled
            event.consume();
        });
        // Set an event handler for when the drag-and-drop operation is done
        handImage.setOnDragDone(DragEvent::consume);
    }


    /**
     * Sets all relevant ImageViews to be mouse transparent, making them non-interactive.
     */
    public void actionIsPlace(){
        displayed1Image.setMouseTransparent(true);
        displayed2Image.setMouseTransparent(true);
        displayed3Image.setMouseTransparent(true);
        displayed4Image.setMouseTransparent(true);
        deckGoldImage.setMouseTransparent(true);
        deckResourceImage.setMouseTransparent(true);
    }


    /**
     * Sets all relevant ImageViews to be mouse interactive, making them responsive to user input.
     */
    public void actionIsDraw(){
        displayed1Image.setMouseTransparent(false);
        displayed2Image.setMouseTransparent(false);
        displayed3Image.setMouseTransparent(false);
        displayed4Image.setMouseTransparent(false);
        deckGoldImage.setMouseTransparent(false);
        deckResourceImage.setMouseTransparent(false);
    }


}