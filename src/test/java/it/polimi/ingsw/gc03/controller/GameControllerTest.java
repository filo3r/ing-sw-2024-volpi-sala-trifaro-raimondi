package it.polimi.ingsw.gc03.controller;

import com.sun.javafx.scene.shape.ArcHelper;
import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.PlayerAction;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.side.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.gc03.model.exceptions.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() {
        gameController = new GameController();
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

        assertEquals(GameStatus.STARTING, gameController.getGame().getStatus());

        int currPos = gameController.getGame().getCurrPlayer();
        Player currPlayer = gameController.getGame().getPlayers().get(currPos);
        Desk gameDesk = gameController.getGame().getDesk();
        CardResource firstPlayerCard = (CardResource) currPlayer.getHand().get(1);
        Player secondPlayer = gameController.getGame().getPlayers().get(currPos+1);
        CardResource secondPlayerCard = (CardResource) currPlayer.getHand().get(1);

        // Check player current action
        assertEquals(PlayerAction.FIRSTMOVES, currPlayer.getAction());
        assertEquals(GameStatus.STARTING, gameController.getGame().getStatus());

        // The player set up his desk
        gameController.placeStarterOnCodex(currPlayer, currPlayer.getCardStarter().getBackStarter());
        gameController.selectCardObjective(currPlayer, 1);

        assertEquals(PlayerAction.WAIT, currPlayer.getAction());

        // The currPlayer tries to place again the starter card on the codex
        assertThrows(Exception.class,() ->  gameController.placeStarterOnCodex(currPlayer, currPlayer.getCardStarter().getFrontStarter()));
        // The currPlayer tries to place a card on the codex
        assertThrows(Exception.class, () -> gameController.placeCardOnCodex(currPlayer, 1, true, 39, 41));

        // The second player sets up his desk
        gameController.placeStarterOnCodex(secondPlayer, currPlayer.getCardStarter().getBackStarter());
        gameController.selectCardObjective(secondPlayer, 1);
        assertEquals(PlayerAction.WAIT, secondPlayer.getAction());

        // The second player tries to place a card
        assertThrows(Exception.class, () -> gameController.placeCardOnCodex(secondPlayer, 1, true, 39, 41));

        assertEquals(GameStatus.RUNNING, gameController.getGame().getStatus());
        assertEquals(PlayerAction.PLACE, currPlayer.getAction());
        // The first player place a card on the codex
        gameController.placeCardOnCodex(currPlayer, 1, true, 39, 41);
        Side placedCard = currPlayer.getCodex().getCodex()[39][41];
        assertEquals(firstPlayerCard.getFrontResource(), placedCard);

        // Player1 draw from Displayed Gold
        gameController.drawCardDisplayed(currPlayer, gameDesk.getDisplayedGold(), 0);

        assertEquals(2, gameDesk.getDisplayedGold().size());
        assertEquals(3, currPlayer.getHand().size());
        assertEquals(PlayerAction.PLACE, secondPlayer.getAction());
        assertEquals(PlayerAction.WAIT, currPlayer.getAction());
    }
}