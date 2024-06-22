package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.ChatMessage;
import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
     *
     */
    @FXML
    private Pane personalObjectivePane;

    /**
     * Pane to display the personal Objective card.
     */
    @FXML
    private ImageView personalObjectiveImage;

    /**
     *
     */
    @FXML
    private Pane sharedObjective1Pane;

    /**
     * Pane to display the first shared Objective card.
     */
    @FXML
    private ImageView sharedObjective1Image;

    /**
     *
     */
    @FXML
    private Pane sharedObjective2Pane;

    /**
     * Pane to display the second shared Objective card.
     */
    @FXML
    private ImageView sharedObjective2Image;

    /**
     *
     */
    @FXML
    private Pane deckResourcePane;

    /**
     * Button to display the deck of Resource cards.
     */
    @FXML
    private ImageView deckResourceImage;

    /**
     *
     */
    @FXML
    private Pane deckGoldPane;

    /**
     * Button to display the deck of Gold cards.
     */
    @FXML
    private ImageView deckGoldImage;

    /**
     *
     */
    @FXML
    private Pane displayed1Pane;

    /**
     * Button to display the first displayed card.
     */
    @FXML
    private ImageView displayed1Image;

    /**
     *
     */
    @FXML
    private Pane displayed2Pane;

    /**
     * Button to display the second displayed card.
     */
    @FXML
    private ImageView displayed2Image;

    /**
     *
     */
    @FXML
    private Pane displayed3Pane;

    /**
     * Button to display the third displayed card.
     */
    @FXML
    private ImageView displayed3Image;

    /**
     *
     */
    @FXML
    private Pane displayed4Pane;

    /**
     * Button to display the fourth displayed card.
     */
    @FXML
    private ImageView displayed4Image;

    /**
     *
     */
    @FXML
    private Pane hand1Pane;

    /**
     *
     */
    @FXML
    private ImageView hand1Image;

    /**
     *
     */
    @FXML
    private Pane hand2Pane;

    /**
     *
     */
    @FXML
    private ImageView hand2Image;

    /**
     *
     */
    @FXML
    private Pane hand3Pane;

    /**
     *
     */
    @FXML
    private ImageView hand3Image;

    /**
     *
     */
    private HashMap<String, Card> hand = new HashMap<>(3);

    /**
     *
     */
    private HashMap<String, Boolean> frontSideHand = new HashMap<>(3);

    /**
     *
     */
    @FXML
    private Pane board1;

    /**
     *
     */
    @FXML
    private Pane color1;

    /**
     *
     */
    @FXML
    private Text nickname1;

    /**
     *
     */
    @FXML
    private Pane fungi1;

    /**
     *
     */
    @FXML
    private Text fungiCount1;

    /**
     *
     */
    @FXML
    private Pane plant1;

    /**
     *
     */
    @FXML
    private Text plantCount1;

    /**
     *
     */
    @FXML
    private Pane animal1;

    /**
     *
     */
    @FXML
    private Text animalCount1;

    /**
     *
     */
    @FXML
    private Pane insect1;

    /**
     *
     */
    @FXML
    private Text insectCount1;

    /**
     *
     */
    @FXML
    private Pane quill1;

    /**
     *
     */
    @FXML
    private Text quillCount1;

    /**
     *
     */
    @FXML
    private Pane inkwell1;

    /**
     *
     */
    @FXML
    private Text inkwellCount1;

    /**
     *
     */
    @FXML
    private Pane manuscript1;

    /**
     *
     */
    @FXML
    private Text manuscriptCount1;

    /**
     *
     */
    @FXML
    private Pane covered1;

    /**
     *
     */
    @FXML
    private Text coveredCount1;

    /**
     *
     */
    @FXML
    private Pane hand11;

    /**
     *
     */
    @FXML
    private Pane hand12;

    /**
     *
     */
    @FXML
    private Pane hand13;

    /**
     *
     */
    @FXML
    private Pane board2;

    /**
     *
     */
    @FXML
    private Pane color2;

    /**
     *
     */
    @FXML
    private Text nickname2;

    /**
     *
     */
    @FXML
    private Pane fungi2;

    /**
     *
     */
    @FXML
    private Text fungiCount2;

    /**
     *
     */
    @FXML
    private Pane plant2;

    /**
     *
     */
    @FXML
    private Text plantCount2;

    /**
     *
     */
    @FXML
    private Pane animal2;

    /**
     *
     */
    @FXML
    private Text animalCount2;

    /**
     *
     */
    @FXML
    private Pane insect2;

    /**
     *
     */
    @FXML
    private Text insectCount2;

    /**
     *
     */
    @FXML
    private Pane quill2;

    /**
     *
     */
    @FXML
    private Text quillCount2;

    /**
     *
     */
    @FXML
    private Pane inkwell2;

    /**
     *
     */
    @FXML
    private Text inkwellCount2;

    /**
     *
     */
    @FXML
    private Pane manuscript2;

    /**
     *
     */
    @FXML
    private Text manuscriptCount2;

    /**
     *
     */
    @FXML
    private Pane covered2;

    /**
     *
     */
    @FXML
    private Text coveredCount2;

    /**
     *
     */
    @FXML
    private Pane hand21;

    /**
     *
     */
    @FXML
    private Pane hand22;

    /**
     *
     */
    @FXML
    private Pane hand23;

    /**
     *
     */
    @FXML
    private Pane board3;

    /**
     *
     */
    @FXML
    private Pane color3;

    /**
     *
     */
    @FXML
    private Text nickname3;

    /**
     *
     */
    @FXML
    private Pane fungi3;

    /**
     *
     */
    @FXML
    private Text fungiCount3;

    /**
     *
     */
    @FXML
    private Pane plant3;

    /**
     *
     */
    @FXML
    private Text plantCount3;

    /**
     *
     */
    @FXML
    private Pane animal3;

    /**
     *
     */
    @FXML
    private Text animalCount3;

    /**
     *
     */
    @FXML
    private Pane insect3;

    /**
     *
     */
    @FXML
    private Text insectCount3;

    /**
     *
     */
    @FXML
    private Pane quill3;

    /**
     *
     */
    @FXML
    private Text quillCount3;

    /**
     *
     */
    @FXML
    private Pane inkwell3;

    /**
     *
     */
    @FXML
    private Text inkwellCount3;

    /**
     *
     */
    @FXML
    private Pane manuscript3;

    /**
     *
     */
    @FXML
    private Text manuscriptCount3;

    /**
     *
     */
    @FXML
    private Pane covered3;

    /**
     *
     */
    @FXML
    private Text coveredCount3;

    /**
     *
     */
    @FXML
    private Pane hand31;

    /**
     *
     */
    @FXML
    private Pane hand32;

    /**
     *
     */
    @FXML
    private Pane hand33;

    /**
     *
     */
    @FXML
    private Pane board4;

    /**
     *
     */
    @FXML
    private Pane color4;

    /**
     *
     */
    @FXML
    private Text nickname4;

    /**
     *
     */
    @FXML
    private Pane fungi4;

    /**
     *
     */
    @FXML
    private Text fungiCount4;

    /**
     *
     */
    @FXML
    private Pane plant4;

    /**
     *
     */
    @FXML
    private Text plantCount4;

    /**
     *
     */
    @FXML
    private Pane animal4;

    /**
     *
     */
    @FXML
    private Text animalCount4;

    /**
     *
     */
    @FXML
    private Pane insect4;

    /**
     *
     */
    @FXML
    private Text insectCount4;

    /**
     *
     */
    @FXML
    private Pane quill4;

    /**
     *
     */
    @FXML
    private Text quillCount4;

    /**
     *
     */
    @FXML
    private Pane inkwell4;

    /**
     *
     */
    @FXML
    private Text inkwellCount4;

    /**
     *
     */
    @FXML
    private Pane manuscript4;

    /**
     *
     */
    @FXML
    private Text manuscriptCount4;

    /**
     *
     */
    @FXML
    private Pane covered4;

    /**
     *
     */
    @FXML
    private Text coveredCount4;

    /**
     *
     */
    @FXML
    private Pane hand41;

    /**
     *
     */
    @FXML
    private Pane hand42;

    /**
     *
     */
    @FXML
    private Pane hand43;

    /**
     *
     */
    @FXML
    private ListView<String> chat;

    /**
     *
     */
    @FXML
    private ListView<String> latestEvent;


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
                Image image1 = new Image("file:" + imagePath1);
                sharedObjective1Image.setImage(image1);
                sharedObjective1Image.setFitWidth(sharedObjective1Pane.getPrefWidth());
                sharedObjective1Image.setFitHeight(sharedObjective1Pane.getPrefHeight());
                sharedObjective1Image.setPreserveRatio(true);
                sharedObjective1Image.setSmooth(true);
                sharedObjective1Image.setCache(true);
                // Card 2
                Image image2 = new Image("file:" + imagePath2);
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
                    Image image = new Image("file:" + imagePath);
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
     * @param mouseEvent
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
                    Image image = new Image("file:" + imagePath);
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
     * @param mouseEvent
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
                    Image image = new Image("file:" + imagePathResource.get(i));
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
                    Image image = new Image("file:" + imagePathGold.get(i));
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
     * @param mouseEvent
     */
    @FXML
    public void actionDisplayed1(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("r1");
    }


    /**
     * Handles the action of clicking the second displayed button.
     * @param mouseEvent
     */
    @FXML
    public void actionDisplayed2(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("r2");
    }


    /**
     * Handles the action of clicking the third displayed button.
     * @param mouseEvent
     */
    @FXML
    public void actionDisplayed3(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("g1");
    }


    /**
     * Handles the action of clicking the fourth displayed button.
     * @param mouseEvent
     */
    @FXML
    public void actionDisplayed4(MouseEvent mouseEvent) {
        getInputReaderGUI().addTxt("g2");
    }


    /**
     *
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
                    Image image = new Image(imagePath);
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
                    showError("Error loading images", "There was an error loading the hand cards images.");
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
     *
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
     *
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
     *
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
                    Image image = new Image("file:" + backSideImagePath);
                    hand1Image.setImage(image);
                    hand1Image.setFitWidth(hand1Pane.getPrefWidth());
                    hand1Image.setFitHeight(hand1Pane.getPrefHeight());
                    hand1Image.setPreserveRatio(true);
                    hand1Image.setSmooth(true);
                    hand1Image.setCache(true);
                    frontSideHand.put("hand1", false);
                    hand1Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image("file:" + frontSideImagePath);
                    hand1Image.setImage(image);
                    hand1Image.setFitWidth(hand1Pane.getPrefWidth());
                    hand1Image.setFitHeight(hand1Pane.getPrefHeight());
                    hand1Image.setPreserveRatio(true);
                    hand1Image.setSmooth(true);
                    hand1Image.setCache(true);
                    frontSideHand.put("hand1", true);
                    hand1Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     *
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
                    Image image = new Image("file:" + backSideImagePath);
                    hand2Image.setImage(image);
                    hand2Image.setFitWidth(hand2Pane.getPrefWidth());
                    hand2Image.setFitHeight(hand2Pane.getPrefHeight());
                    hand2Image.setPreserveRatio(true);
                    hand2Image.setSmooth(true);
                    hand2Image.setCache(true);
                    frontSideHand.put("hand2", false);
                    hand2Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image("file:" + frontSideImagePath);
                    hand2Image.setImage(image);
                    hand2Image.setFitWidth(hand2Pane.getPrefWidth());
                    hand2Image.setFitHeight(hand2Pane.getPrefHeight());
                    hand2Image.setPreserveRatio(true);
                    hand2Image.setSmooth(true);
                    hand2Image.setCache(true);
                    frontSideHand.put("hand2", true);
                    hand2Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     *
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
                    Image image = new Image("file:" + backSideImagePath);
                    hand3Image.setImage(image);
                    hand3Image.setFitWidth(hand3Pane.getPrefWidth());
                    hand3Image.setFitHeight(hand3Pane.getPrefHeight());
                    hand3Image.setPreserveRatio(true);
                    hand3Image.setSmooth(true);
                    hand3Image.setCache(true);
                    frontSideHand.put("hand3", false);
                    hand3Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image("file:" + frontSideImagePath);
                    hand3Image.setImage(image);
                    hand3Image.setFitWidth(hand3Pane.getPrefWidth());
                    hand3Image.setFitHeight(hand3Pane.getPrefHeight());
                    hand3Image.setPreserveRatio(true);
                    hand3Image.setSmooth(true);
                    hand3Image.setCache(true);
                    frontSideHand.put("hand3", true);
                    hand3Pane.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     *
     */
    private void setBoards(GameImmutable gameImmutable) {
        // Set visibility
        board1.setVisible(false);
        board2.setVisible(false);
        board3.setVisible(false);
        board4.setVisible(false);
        // Set images

        // Game size = 2
        if (gameImmutable.getSize() == 2) {

        }
    }

    public void addMessages(List<ChatMessage> messages , String nickname){

    }


}
