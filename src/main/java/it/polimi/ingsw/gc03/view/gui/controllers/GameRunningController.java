package it.polimi.ingsw.gc03.view.gui.controllers;

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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;

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
     * Button to display the deck of Resource cards.
     */
    @FXML
    private Button deckResource;

    /**
     * Button to display the deck of Gold cards.
     */
    @FXML
    private Button deckGold;

    /**
     * Button to display the first displayed card.
     */
    @FXML
    private Button displayed1;

    /**
     * Button to display the second displayed card.
     */
    @FXML
    private Button displayed2;

    /**
     * Button to display the third displayed card.
     */
    @FXML
    private Button displayed3;

    /**
     * Button to display the fourth displayed card.
     */
    @FXML
    private Button displayed4;

    /**
     *
     */
    @FXML
    private Button hand1;

    /**
     *
     */
    @FXML
    private Button hand2;

    /**
     *
     */
    @FXML
    private Button hand3;

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
        String IMAGE_PATH = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "polimi"
                + File.separator + "ingsw" + File.separator + "gc03" + File.separator + "fxml" +File.separator + "images" + File.separator +
                "cards" + File.separator + "frontSide" + File.separator;
        String imagePath = null;
        if (player != null)
            imagePath = IMAGE_PATH + player.getCardObjective().get(0).getIdCard() + "_front.png";
        if (imagePath != null) {
            try {
                Image image = new Image("file:" + imagePath);
                personalObjective.getChildren().add(new ImageView(image));
            } catch (Exception e) {
                showError("Error loading image", "There was an error loading the Objective card image.");
                System.exit(1);
            }
        } else {
            if(!(gameImmutable.getStatus().equals(GameStatus.STARTING) || gameImmutable.getStatus().equals(GameStatus.WAITING))){
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
        String IMAGE_PATH = System.getProperty("user.dir") + File.separator + "src" +
                File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "polimi"
                + File.separator + "ingsw" + File.separator + "gc03" + File.separator + "fxml" +File.separator + "images" + File.separator +
                "cards" + File.separator + "frontSide" + File.separator;
        String imagePath1 = IMAGE_PATH + gameImmutable.getDesk().getDisplayedObjective().get(0).getIdCard() + "_front.png";
        String imagePath2 = IMAGE_PATH + gameImmutable.getDesk().getDisplayedObjective().get(1).getIdCard() + "_front.png";
        try {
            Image image1 = new Image("file:" + imagePath1);
            sharedObjective1.getChildren().add(new ImageView(image1));
            Image image2 = new Image("file:" + imagePath2);
            sharedObjective2.getChildren().add(new ImageView(image2));
        } catch (Exception e) {
            showError("Error loading images", "There was an error loading the Objective cards images.");
            System.exit(1);
        }
    }


    /**
     * Sets the image for the deck of Resource cards.
     * @param gameImmutable The current state of the game.
     */
    public void setDeckResource(GameImmutable gameImmutable) {
        if (gameImmutable.getDesk().getDeckResource().isEmpty()) {
            deckResource.setVisible(false);
        } else {
            deckResource.setVisible(true);
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
                if (!gameImmutable.getDesk().getDeckResource().isEmpty()) {
                    showError("Image path is null", "The image path for the Deck Resource is null.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Handles the action of clicking the deck Resource button.
     * @param actionEvent The event triggered by clicking the deck Resource button.
     */
    public void actionDeckResource(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("rD");
    }


    /**
     * Sets the image for the deck of Gold cards.
     * @param gameImmutable The current state of the game.
     */
    public void setDeckGold(GameImmutable gameImmutable) {
        if (gameImmutable.getDesk().getDeckGold().isEmpty()) {
            deckGold.setVisible(false);
        } else {
            deckGold.setVisible(true);
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
                if (!gameImmutable.getDesk().getDeckGold().isEmpty()) {
                    showError("Image path is null", "The image path for the Deck Gold is null.");
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Handles the action of clicking the deck Gold button.
     * @param actionEvent The event triggered by clicking the deck Gold button.
     */
    public void actionDeckGold(ActionEvent actionEvent) {
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
        displayed1.setVisible(false);
        displayed2.setVisible(false);
        for (int i = 0; i < gameImmutable.getDesk().getDisplayedResource().size(); i++) {
            if (imagePathResource.get(i) != null) {
                try {
                    Image image = new Image("file:" + imagePathResource.get(i));
                    if (i == 0) {
                        displayed1.setVisible(true);
                        displayed1.setGraphic(new ImageView(image));
                    }
                    if (i == 1) {
                        displayed2.setVisible(true);
                        displayed2.setGraphic(new ImageView(image));
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
        displayed3.setVisible(false);
        displayed4.setVisible(false);
        for (int i = 0; i < gameImmutable.getDesk().getDisplayedGold().size(); i++) {
            if (imagePathGold.get(i) != null) {
                try {
                    Image image = new Image("file:" + imagePathGold.get(i));
                    if (i == 0) {
                        displayed3.setVisible(true);
                        displayed3.setGraphic(new ImageView(image));
                    }
                    if (i == 1) {
                        displayed4.setVisible(true);
                        displayed4.setGraphic(new ImageView(image));
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
     * @param actionEvent The event triggered by clicking the first displayed button.
     */
    public void actionDisplayed1(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("r1");
    }


    /**
     * Handles the action of clicking the second displayed button.
     * @param actionEvent The event triggered by clicking the second displayed button.
     */
    public void actionDisplayed2(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("r2");
    }


    /**
     * Handles the action of clicking the third displayed button.
     * @param actionEvent The event triggered by clicking the third displayed button.
     */
    public void actionDisplayed3(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("g1");
    }


    /**
     * Handles the action of clicking the fourth displayed button.
     * @param actionEvent The event triggered by clicking the fourth displayed button.
     */
    public void actionDisplayed4(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("g2");
    }


    /**
     *
     */
    public void setHand(GameImmutable gameImmutable, String nickname) {
        hand1.setVisible(false);
        hand2.setVisible(false);
        hand3.setVisible(false);
        // Hand
        Player player = getPlayer(gameImmutable, nickname);
        ArrayList<String> imagePath = new ArrayList<>(player.getHand().size());
        for (Card card : player.getHand()) {
            if (card instanceof CardResource) {
                CardResource cardResource = (CardResource) card;
                imagePath.add(cardResource.getFrontResource().getImage());
            } else if (card instanceof CardGold) {
                CardGold cardGold = (CardGold) card;
                imagePath.add(cardGold.getFrontGold().getImage());
            }
        }
        // Load images
        for (int i = 0; i < player.getHand().size(); i++) {
            if (imagePath.get(i) != null) {
                try {
                    Image image = new Image("file:" + imagePath.get(i));
                    if (i == 0) {
                        hand1.setVisible(true);
                        hand1.setGraphic(new ImageView(image));
                    }
                    if (i == 1) {
                        hand2.setVisible(true);
                        hand2.setGraphic(new ImageView(image));
                    }
                    if (i == 2) {
                        hand3.setVisible(true);
                        hand3.setGraphic(new ImageView(image));
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
        // Set as front images
        frontSideHand.clear();
        for (int i = 1; i <= player.getHand().size(); i++) {
            frontSideHand.put("hand" + i, true);
        }
    }


    /**
     *
     */
    private Card getCardHand(GameImmutable gameImmutable, String nickname, int index) {
        Player player = getPlayer(gameImmutable, nickname);
        return player.getHand().get(index);
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
    public void actionHand1(ActionEvent actionEvent, GameImmutable gameImmutable, String nickname) {
        hand1.setVisible(false);
        Card card = getCardHand(gameImmutable, nickname, 0);
        String frontSideImagePath = getFrontSideImagePath(card);
        String backSideImagePath = getBackSideImagePath(card);
        if (frontSideImagePath == null || backSideImagePath == null) {
            showError("Image path is null", "The image path for the hand card is null.");
            System.exit(1);
        } else {
            if (frontSideHand.get("hand1")) {
                try {
                    Image image = new Image("file:" + backSideImagePath);
                    hand1.setGraphic(new ImageView(image));
                    frontSideHand.put("hand1", false);
                    hand1.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image("file:" + frontSideImagePath);
                    hand1.setGraphic(new ImageView(image));
                    frontSideHand.put("hand1", true);
                    hand1.setVisible(true);
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
    public void actionHand2(ActionEvent actionEvent, GameImmutable gameImmutable, String nickname) {
        hand2.setVisible(false);
        Card card = getCardHand(gameImmutable, nickname, 1);
        String frontSideImagePath = getFrontSideImagePath(card);
        String backSideImagePath = getBackSideImagePath(card);
        if (frontSideImagePath == null || backSideImagePath == null) {
            showError("Image path is null", "The image path for the hand card is null.");
            System.exit(1);
        } else {
            if (frontSideHand.get("hand2")) {
                try {
                    Image image = new Image("file:" + backSideImagePath);
                    hand2.setGraphic(new ImageView(image));
                    frontSideHand.put("hand2", false);
                    hand2.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image("file:" + frontSideImagePath);
                    hand2.setGraphic(new ImageView(image));
                    frontSideHand.put("hand2", true);
                    hand2.setVisible(true);
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
    public void actionHand3(ActionEvent actionEvent, GameImmutable gameImmutable, String nickname) {
        hand3.setVisible(false);
        Card card = getCardHand(gameImmutable, nickname, 2);
        String frontSideImagePath = getFrontSideImagePath(card);
        String backSideImagePath = getBackSideImagePath(card);
        if (frontSideImagePath == null || backSideImagePath == null) {
            showError("Image path is null", "The image path for the hand card is null.");
            System.exit(1);
        } else {
            if (frontSideHand.get("hand3")) {
                try {
                    Image image = new Image("file:" + backSideImagePath);
                    hand3.setGraphic(new ImageView(image));
                    frontSideHand.put("hand3", false);
                    hand3.setVisible(true);
                } catch (Exception e) {
                    showError("Error loading images", "There was an error loading the hand cards images.");
                    System.exit(1);
                }
            } else {
                try {
                    Image image = new Image("file:" + frontSideImagePath);
                    hand3.setGraphic(new ImageView(image));
                    frontSideHand.put("hand3", true);
                    hand3.setVisible(true);
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


    // CAPIRE PERCHÉ È ERRORE (parametri?)
    public void actionHand1(ActionEvent actionEvent) {
    }

    public void actionHand2(ActionEvent actionEvent) {
    }

    public void actionHand3(ActionEvent actionEvent) {
    }
}
