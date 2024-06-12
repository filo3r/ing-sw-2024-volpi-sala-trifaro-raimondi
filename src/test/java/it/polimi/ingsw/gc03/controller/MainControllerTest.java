package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.DeckType;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.model.side.back.BackResource;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;

import it.polimi.ingsw.gc03.networking.rmi.GameControllerInterface;
import javafx.scene.SubScene;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class MainControllerTest {

    private MainController mainController;

    private GameListener listener;
    private Card makeCard(){
        FrontResource front = new FrontResource(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY,Value.EMPTY, 0);
        BackResource back = new BackResource(Kingdom.ANIMAL, Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY, new ArrayList<>() {{add(Value.ANIMAL);}});
        return new CardResource("TEST", Kingdom.ANIMAL, front, back);
    }

    private ArrayList<Card> makeHand(){
        ArrayList<Card> hand = new ArrayList<Card>();
        for(int i = 0; i<3; i++){
            hand.add(makeCard());
        }
        return hand;
    }

    @BeforeEach
    void setUp() {
        mainController = MainController.getInstance();
        listener = mock(GameListener.class);
    }

    @AfterEach
    void tearDown(){
        MainController.resetInstance();
    }
    @Test
    @DisplayName("Simulate a player trying to join when there are no active games")
    void joinAndCreateGame() throws  RemoteException {
        mainController.joinFirstAvailableGame(listener,"Player1");
        assertEquals(1, mainController.getGameControllers().size());
        GameController gc = mainController.getGameControllers().get(0);
        // The first player tries to place a card
        Player p1 = gc.getGame().getPlayers().getFirst();
        assertEquals(GameStatus.WAITING, gc.getGame().getStatus());
        assertThrows(Exception.class,() -> gc.placeCardOnCodex(p1, 0, false, 39, 41));
        assertThrows(Exception.class,() -> gc.placeStarterOnCodex(p1, p1.getCardStarter().getBackStarter()));
        // The first player sets the game's size
        gc.getGame().setSize(2);
        mainController.joinFirstAvailableGame(listener,"Player2");
        Player p2 = gc.getGame().getPlayers().getLast();
        assertEquals(GameStatus.STARTING, gc.getGame().getStatus());
    }

    @Test
    @DisplayName("Game ending and winner")
    void gameEnd() throws Exception {
        mainController.joinFirstAvailableGame(listener, "Player1");
        GameController gc = mainController.getGameControllers().get(0);
        gc.getGame().setSize(2);
        mainController.joinSpecificGame(listener, "Player2", gc.getGame().getIdGame());
        Player tempP1 = gc.getGame().getPlayers().stream().filter(p->p.getNickname().equals("Player1")).toList().getFirst();
        Player tempP2 = gc.getGame().getPlayers().stream().filter(p->p.getNickname().equals("Player2")).toList().getFirst();
        gc.placeStarterOnCodex(tempP1, tempP1.getCardStarter().getFrontStarter());
        gc.placeStarterOnCodex(tempP2, tempP2.getCardStarter().getFrontStarter());
        gc.selectCardObjective(tempP1, 0);
        gc.selectCardObjective(tempP2, 0);

        Player p1 = gc.getGame().getPlayers().get(gc.getGame().getCurrPlayer());
        Player p2 = gc.getGame().getPlayers().stream().filter(p->!p.getNickname().equals(p1.getNickname())).toList().getFirst();

        gc.placeCardOnCodex(p1, 0, false, 39, 39);
        gc.drawCardDisplayed(p1, DeckType.DISPLAYED_RESOURCE, 0);

        gc.placeCardOnCodex(p2, 0, false, 39, 39);
        gc.drawCardDisplayed(p2, DeckType.DISPLAYED_RESOURCE, 0);

        gc.placeCardOnCodex(p1, 0, false, 41, 41);
        // Simulate end game condition. (GameStatus is now ending after this turn ends, the last turn will start).
        p1.setScore(22);
        gc.drawCardDisplayed(p1, DeckType.DISPLAYED_RESOURCE, 0);
        assertEquals(GameStatus.ENDING, gc.getGame().getStatus());
        gc.placeCardOnCodex(p2, 0, false, 41, 41);
        gc.drawCardDisplayed(p2, DeckType.DISPLAYED_RESOURCE, 0);
        // Now it's the last round
        assertEquals(GameStatus.LASTROUND, gc.getGame().getStatus());

        gc.placeCardOnCodex(p1, 0, false, 39, 41);

        gc.placeCardOnCodex(p2, 0, false, 39, 41);

        assertEquals(GameStatus.ENDED, gc.getGame().getStatus());
    }

    @Test
    void deleteNotExistentGame() throws NoSuchGameException, RemoteException {
        mainController.joinFirstAvailableGame(listener,"Player");
        int idGame = mainController.getGameControllers().getFirst().getGame().getIdGame();
        mainController.deleteGame(idGame);
        assertThrows(NoSuchGameException.class,()->mainController.deleteGame(idGame));
    }
}