package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private MainController mainController;
    @BeforeEach
    void setUp() {
        mainController = MainController.getInstance();
    }

    @Test
    @DisplayName("Simulate a player trying to join when there are no active games")
    void joinAndCreateGame(){
        mainController.joinGame("Player1");
        assertEquals(1, mainController.getGameControllers().size());
        GameController gc = mainController.getGameControllers().get(0);
        // The first player tries to place a card
        Player p1 = gc.getGame().getPlayers().getFirst();
        assertEquals(GameStatus.WAITING, gc.getGame().getStatus());
        assertThrows(Exception.class,() -> gc.placeCardOnCodex(p1, 0, true, 39, 41));
        assertThrows(Exception.class,() -> gc.placeStarterOnCodex(p1, p1.getCardStarter().getBackStarter()));
        // A new player tries join while the first player hasn't decided
        // yet the game's size
        assertThrows(Exception.class, () -> mainController.joinGame("Player2"));
        // The first player sets the game's size
        gc.getGame().setSize(2);
        mainController.joinGame("Player2");
        Player p2 = gc.getGame().getPlayers().getLast();
        assertEquals(GameStatus.STARTING, gc.getGame().getStatus());
    }
}