package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.PlayerAction;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
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

    @AfterEach
    void tearDown(){
        MainController.resetInstance();
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
    @Test
    @DisplayName("Game ending and winner")
    void gameEnd() throws Exception {
        mainController.joinGame("Player1");
        GameController gameController= mainController.getGameControllers().getFirst();
        Game game = gameController.getGame();
        game.setSize(2);
        gameController.addPlayerToGame("Player2");
        assertEquals(2, game.getNumPlayer());
        assertEquals(GameStatus.STARTING, game.getStatus());
        // p1 is the first player to play.
        Player p1 = game.getPlayers().get(game.getCurrPlayer());
        Player p2 = null;

        if (game.getCurrPlayer() == 0) {
            p2 = game.getPlayers().getLast();
        } else {
            p2 = game.getPlayers().getFirst();
        }
        gameController.placeStarterOnCodex(p1, p1.getCardStarter().getFrontStarter());
        gameController.placeStarterOnCodex(p2, p2.getCardStarter().getFrontStarter());
        gameController.selectCardObjective(p1, 1);
        gameController.selectCardObjective(p2, 1);

        assertEquals(GameStatus.RUNNING, game.getStatus());
        gameController.placeCardOnCodex(p1, 0, false, 39, 41);
        p1.setScore(21);
        gameController.drawCardDisplayed(p1, game.getDesk().getDisplayedResource(), 1);
        if(game.getPlayers().indexOf(p1)==0){
            assertEquals(GameStatus.ENDING, game.getStatus());
            gameController.placeCardOnCodex(p2, 0, false, 39, 41);
            gameController.drawCardDisplayed(p2, game.getDesk().getDisplayedResource(), 1);
            gameController.placeCardOnCodex(p1, 1, false, 41, 39);
            gameController.placeCardOnCodex(p2, 1, false, 41, 39);
            return;
        } else {
            assertEquals(GameStatus.LASTROUND, game.getStatus());
            gameController.placeCardOnCodex(p2, 0, false, 39, 41);
            gameController.placeCardOnCodex(p1, 1, false, 41, 39);


        }
        assertEquals(GameStatus.ENDED, game.getStatus());
    }
}