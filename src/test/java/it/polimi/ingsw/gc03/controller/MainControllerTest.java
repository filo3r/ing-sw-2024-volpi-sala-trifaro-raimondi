package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.exceptions.NoSuchGameException;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import it.polimi.ingsw.gc03.networking.rmi.old.RmiClient;
import it.polimi.ingsw.gc03.networking.rmi.old.VirtualView;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MainControllerTest {

    private MainController mainController;


    private Card makeCard(){
        FrontResource front = new FrontResource(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY,Value.EMPTY, 0);
        BackSide back = new BackSide(Kingdom.ANIMAL, Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY, new ArrayList<>() {{add(Value.ANIMAL);}});
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
    }

    @AfterEach
    void tearDown(){
        MainController.resetInstance();
    }
    @Test
    @DisplayName("Simulate a player trying to join when there are no active games")
    void joinAndCreateGame() throws NoSuchGameException, RemoteException {
        VirtualView listener = new RmiClient(null);
        mainController.joinGame("Player1", listener);
        assertEquals(1, mainController.getGameControllers().size());
        GameController gc = mainController.getGameControllers().get(0);
        // The first player tries to place a card
        Player p1 = gc.getGame().getPlayers().getFirst();
        assertEquals(GameStatus.WAITING, gc.getGame().getStatus());
        assertThrows(Exception.class,() -> gc.placeCardOnCodex(p1, 0, false, 39, 41));
        assertThrows(Exception.class,() -> gc.placeStarterOnCodex(p1, p1.getCardStarter().getBackStarter()));
        // A new player tries join while the first player hasn't decided
        // yet the game's size
        assertThrows(Exception.class, () -> mainController.joinGame("Player2", listener));
        // The first player sets the game's size
        gc.getGame().setSize(2);
        //Player already joined
        assertThrows(RuntimeException.class,()->mainController.joinGame("Player1", listener));
        mainController.joinGame("Player2", listener);
        Player p2 = gc.getGame().getPlayers().getLast();
        assertEquals(GameStatus.STARTING, gc.getGame().getStatus());
    }
    @Test
    @DisplayName("Game ending and winner")
    void gameEnd() throws Exception {
        VirtualView listener = new RmiClient(null);
        mainController.joinGame("Player1", listener);
        GameController gameController= mainController.getGameControllers().getFirst();
        Game game = gameController.getGame();
        game.setSize(2);
        gameController.addPlayerToGame("Player2", listener);
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

        p1.setHand(makeHand());
        p2.setHand(makeHand());
        gameController.selectCardObjective(p1, 1);
        gameController.selectCardObjective(p2, 1);
        gameController.placeStarterOnCodex(p1, p1.getCardStarter().getFrontStarter());
        gameController.placeStarterOnCodex(p2, p2.getCardStarter().getFrontStarter());

        assertEquals(GameStatus.RUNNING, game.getStatus());

        gameController.placeCardOnCodex(p1, 0, false, 39, 41);
        p1.setScore(21);
        gameController.drawCardDisplayed(p1, game.getDesk().getDisplayedResource(), 1);
        p1.setHand(makeHand());
        if(game.getPlayers().indexOf(p1)==0){
            assertEquals(GameStatus.ENDING, game.getStatus());
            gameController.placeCardOnCodex(p2, 0, false, 39, 41);
            gameController.drawCardDisplayed(p2, game.getDesk().getDisplayedResource(), 1);
            p2.setHand(makeHand());
            gameController.placeCardOnCodex(p1, 1, false, 41, 39);
            try{ gameController.placeCardOnCodex(p2, 1, false, 41, 39);}
            catch (Exception e){
                return;
            }
            return;
        } else {
            assertEquals(GameStatus.LASTROUND, game.getStatus());
            gameController.placeCardOnCodex(p2, 0, false, 39, 41);
            gameController.placeCardOnCodex(p1, 1, false, 41, 39);


        }
        assertEquals(GameStatus.ENDED, game.getStatus());
    }

    @Test
    void deleteNotExistentGame() throws NoSuchGameException, RemoteException {
        VirtualView listener = new RmiClient(null);
        mainController.joinGame("Player", listener);
        int idGame = mainController.getGameControllers().getFirst().getGame().getIdGame();
        mainController.deleteGame(idGame);
        assertThrows(NoSuchGameException.class,()->mainController.deleteGame(idGame));
    }
}