package it.polimi.ingsw.gc03.view.gui.controllers;

import it.polimi.ingsw.gc03.model.GameImmutable;
import it.polimi.ingsw.gc03.model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Controller class for displaying the winners in the GUI.
 */
public class WinnersController extends GenericController {

    /**
     * Label for displaying the first player's information.
     */
    @FXML
    private Label player1;

    /**
     * Label for displaying the second player's information.
     */
    @FXML
    private Label player2;

    /**
     * Label for displaying the third player's information.
     */
    @FXML
    private Label player3;

    /**
     * Label for displaying the fourth player's information.
     */
    @FXML
    private Label player4;

    /**
     * Button to close the winners display.
     */
    @FXML
    private Button buttonClose;


    /**
     * Displays the players' rankings and highlights the winners.
     * @param gameImmutable The game data containing players' scores and winners.
     */
    public void showPoints(GameImmutable gameImmutable) {
        player1.setVisible(false);
        player2.setVisible(false);
        player3.setVisible(false);
        player4.setVisible(false);
        buttonClose.setVisible(true);
        // Get players sorted by descending score
        List<Player> sortedPlayers = gameImmutable.getPlayers().stream()
                .sorted(Comparator.comparingInt(Player::getScore).reversed())
                .collect(Collectors.toUnmodifiableList());
        // Get the list of winners
        List<Player> winners = gameImmutable.getWinner();
        // Show the ranking
        int i = 0;
        Label temp = null;
        for (Player player : sortedPlayers) {
            switch (i) {
                case 0 -> temp = player1;
                case 1 -> temp = player2;
                case 2 -> temp = player3;
                case 3 -> temp = player4;
            }
            // Set the Label text with the player's nickname and score
            System.out.println(player.getNickname() + ": " + player.getScore() + " points");
            temp.setText(player.getNickname() + ": " + player.getScore() + " points");
            // Color the nickname if the player is a winner
            if (winners.contains(player))
                temp.setTextFill(Color.GOLD);
            else
                temp.setTextFill(Color.BLACK); // Default color
            // Make the Label visible
            temp.setVisible(true);
            i++;
        }
    }


    /**
     * Handles the action event for closing the winners display.
     * Sends an error acknowledgment command to the input reader.
     * @param actionEvent The action event triggered by the user.
     */
    public void actionClose(ActionEvent actionEvent) {
        getInputReaderGUI().addTxt("leave");
    }


}
