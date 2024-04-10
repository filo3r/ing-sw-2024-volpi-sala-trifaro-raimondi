package it.polimi.ingsw.gc03.controller;

import com.sun.javafx.scene.shape.ArcHelper;
import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.gc03.model.exceptions.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    List<Player> players = new ArrayList<>();

    @BeforeEach
    void setUp() {
        gameController = new GameController();

        players.add(new Player("1", 1, new Desk()));
        players.add(new Player("2", 2, new Desk()));
        players.add(new Player("3", 3, new Desk()));
        players.add(new Player("4", 4, new Desk()));
    }

    @Test
    @DisplayName("Players added to the game")
    void addPlayerToGame() throws CannotJoinGameException, PlayerAlreadyJoinedException, DeskIsFullException {
        // A player is added to the game
        gameController.addPlayerToGame("Player1");
        // A player tries to join before the game's size is changed.
        assertThrows(DeskIsFullException.class, () -> gameController.addPlayerToGame("Player4"));
        // The player sets the game's size as 2
        gameController.getGame().setSize(2);
        // A second player tries to join
        gameController.addPlayerToGame("Player2");
        // A third player tries to join
        assertThrows(CannotJoinGameException.class, ()->gameController.addPlayerToGame("Player3"));

        assertEquals(2, gameController.getGame().getPlayers().size());
        assertEquals(GameStatus.STARTING, gameController.getGame().getStatus());
        assertThrows(CannotJoinGameException.class, ()->gameController.addPlayerToGame("Player3"));
    }


    @Test
    @DisplayName("Placing cards on Codex")
    void placingCards() throws Exception {
        gameController.addPlayerToGame("Player1");
        gameController.getGame().setSize(2);
        gameController.addPlayerToGame("Player2");
        assertEquals(gameController.getGame().getSize(), 2);
        int currPos = gameController.getGame().getCurrPlayer();
        Player currPlayer = gameController.getGame().getPlayers().get(currPos);
        Desk gameDesk = gameController.getGame().getDesk();

        String cardID = currPlayer.getHand().getFirst().getIdCard();
        CardResource actualCard = (CardResource) currPlayer.getHand().getFirst();
        // TODO: Complete this test with all possible cases.
    }
}