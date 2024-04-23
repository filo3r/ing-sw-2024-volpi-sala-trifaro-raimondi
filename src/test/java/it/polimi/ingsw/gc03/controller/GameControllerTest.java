package it.polimi.ingsw.gc03.controller;

import com.sun.javafx.scene.shape.ArcHelper;
import com.sun.tools.javac.Main;
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
    @DisplayName("Placing cards on Codex and simple game")
    void placingCards() throws Exception {
        gameController.addPlayerToGame("Player1");
        gameController.getGame().setSize(2);
        gameController.addPlayerToGame("Player2");

        // For testing purposes the first player is in position 0
        gameController.getGame().setCurrPlayer(0);

        assertEquals(GameStatus.STARTING, gameController.getGame().getStatus());

        Desk gameDesk = gameController.getGame().getDesk();

        int firstPos = gameController.getGame().getCurrPlayer();
        Player firstPlayer = gameController.getGame().getPlayers().get(firstPos);


        CardResource firstPlayerCard = (CardResource) firstPlayer.getHand().get(1);
        Player secondPlayer = gameController.getGame().getPlayers().get(firstPos+1);


        // Check player current action
        assertEquals(PlayerAction.FIRSTMOVES, firstPlayer.getAction());
        assertEquals(GameStatus.STARTING, gameController.getGame().getStatus());

        // The player set up his desk
        gameController.placeStarterOnCodex(firstPlayer, firstPlayer.getCardStarter().getFrontStarter());
        gameController.selectCardObjective(firstPlayer, 1);

        assertEquals(PlayerAction.WAIT, firstPlayer.getAction());

        // The currPlayer tries to place again the starter card on the codex
        assertThrows(Exception.class,() ->  gameController.placeStarterOnCodex(firstPlayer, firstPlayer.getCardStarter().getFrontStarter()));
        // The currPlayer tries to place a card on the codex
        assertThrows(Exception.class, () -> gameController.placeCardOnCodex(firstPlayer, 1, false, 39, 41));

        // The second player sets up his desk
        gameController.placeStarterOnCodex(secondPlayer, firstPlayer.getCardStarter().getFrontStarter());
        gameController.selectCardObjective(secondPlayer, 1);
        assertEquals(PlayerAction.WAIT, secondPlayer.getAction());

        // The second player tries to place a card
        assertThrows(Exception.class, () -> gameController.placeCardOnCodex(secondPlayer, 1, false, 39, 41));

        assertEquals(GameStatus.RUNNING, gameController.getGame().getStatus());
        assertEquals(PlayerAction.PLACE, firstPlayer.getAction());
        // The first player place a card on the codex
        gameController.placeCardOnCodex(firstPlayer, 1, false, 39, 41);
        Side placedCard = firstPlayer.getCodex().getCodex()[39][41];
        assertEquals(firstPlayerCard.getBackResource(), placedCard);

        // Player1 draw from Displayed Gold
        gameController.drawCardDisplayed(firstPlayer, gameDesk.getDisplayedGold(), 0);

        assertEquals(2, gameDesk.getDisplayedGold().size());
        assertEquals(3, firstPlayer.getHand().size());
        assertEquals(PlayerAction.PLACE, secondPlayer.getAction());
        assertEquals(PlayerAction.WAIT, firstPlayer.getAction());
        gameController.placeCardOnCodex(secondPlayer, 1, false, 41, 39);

        gameController.drawCardDisplayed(secondPlayer, gameDesk.getDisplayedGold(), 0);

        gameController.placeCardOnCodex(firstPlayer, 1, false, 41, 39);

        gameController.drawCardDisplayed(firstPlayer, gameDesk.getDisplayedGold(), 0);

    }
}