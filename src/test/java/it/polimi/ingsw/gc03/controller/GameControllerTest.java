package it.polimi.ingsw.gc03.controller;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.*;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.side.Side;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.gc03.model.exceptions.*;
import org.mockito.Mock;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameControllerTest {

    private GameController gameController;
    private GameListener listener;

    @BeforeEach
    void setUp() throws RemoteException {
        gameController = new GameController();
        listener = mock(GameListener.class);
    }

    @Test
    @DisplayName("Players added to the game")
    void addPlayerToGame() throws CannotJoinGameException, PlayerAlreadyJoinedException, DeskIsFullException, RemoteException {
        // A player is added to the game
        gameController.addPlayerToGame("Player1", listener);
        // A player tries to join before the game's size is changed.
        assertThrows(DeskIsFullException.class, () -> gameController.addPlayerToGame("Player4", listener));
        // The player sets the game's size as 2
        gameController.getGame().setSize(2);
        // The same player tryes to join again
        assertThrows(PlayerAlreadyJoinedException.class, () -> gameController.addPlayerToGame("Player1", listener));
        // A second player tries to join
        gameController.addPlayerToGame("Player2", listener);
        // A third player tries to join
        assertThrows(CannotJoinGameException.class, ()->gameController.addPlayerToGame("Player3", listener));

        assertEquals(2, gameController.getGame().getPlayers().size());
        assertEquals(GameStatus.STARTING, gameController.getGame().getStatus());
        assertThrows(CannotJoinGameException.class, ()->gameController.addPlayerToGame("Player3", listener));
    }


    @Test
    @DisplayName("Placing cards on Codex and simple game")
    void placingCards() throws Exception {
        gameController.addPlayerToGame("Player1", listener);
        gameController.getGame().setSize(2);
        gameController.addPlayerToGame("Player2", listener);

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
        // A player tries again to select the card objective
        assertThrows(Exception.class, () -> gameController.selectCardObjective(firstPlayer, 1));

        assertEquals(PlayerAction.WAIT, firstPlayer.getAction());

        // The currPlayer tries to place again the starter card on the codex
        assertThrows(Exception.class,() ->  gameController.placeStarterOnCodex(firstPlayer, firstPlayer.getCardStarter().getFrontStarter()));
        // The currPlayer tries to place a card on the codex
        assertThrows(Exception.class, () -> gameController.placeCardOnCodex(firstPlayer, 1, false, 39, 41));

        // The second player sets up his desk
        gameController.placeStarterOnCodex(secondPlayer, firstPlayer.getCardStarter().getFrontStarter());
        gameController.selectCardObjective(secondPlayer, 1);
        assertEquals(PlayerAction.WAIT, secondPlayer.getAction());

        // A player tries again to select a card objective
        assertThrows(Exception.class, () -> gameController.selectCardObjective(secondPlayer, 1));
        // The second player tries to place a card
        assertThrows(Exception.class, () -> gameController.placeCardOnCodex(secondPlayer, 1, false, 39, 41));
        assertEquals(GameStatus.RUNNING, gameController.getGame().getStatus());
        assertEquals(PlayerAction.PLACE, firstPlayer.getAction());

        // The first player place a card on the codex
        gameController.placeCardOnCodex(firstPlayer, 1, false, 39, 41);
        assertEquals(PlayerAction.DRAW, firstPlayer.getAction());
        Side placedCard = firstPlayer.getCodex().getCodex()[39][41];
        assertEquals(firstPlayerCard.getBackResource(), placedCard);

        // Player1 draw from Displayed Gold
        gameController.drawCardFromDeck(firstPlayer, DeckType.DECK_GOLD);
        // Player1 tries to draw again from deck
        assertThrows(Exception.class, () -> gameController.drawCardFromDeck(firstPlayer, DeckType.DECK_RESOURCE));
        // Player1 tries to draw from displayed
        assertThrows(Exception.class, () -> gameController.drawCardDisplayed(firstPlayer, DeckType.DISPLAYED_GOLD, 0));
        assertEquals(2, gameDesk.getDisplayedGold().size());
        assertEquals(3, firstPlayer.getHand().size());
        assertEquals(PlayerAction.PLACE, secondPlayer.getAction());
        assertEquals(PlayerAction.WAIT, firstPlayer.getAction());
    }

    @Test
    public void testReconnectPlayer() throws Exception {
        Game game = gameController.getGame();
        gameController.addPlayerToGame("Player1", listener);
        game.setSize(2);
        gameController.addPlayerToGame("Player2", listener);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);
        player1.setOnline(game,false,listener);
        game.setStatus(GameStatus.HALTED);
        gameController.reconnectPlayer("Player1",listener);


        assertTrue(player1.getOnline());
        assertEquals(GameStatus.STARTING, game.getStatus());
    }

    @Test
    void checkFinalActionTestCall() throws Exception {
        gameController.addPlayerToGame("Player1", listener);
        gameController.updateGameSize(2);
        gameController.addPlayerToGame("Player2", listener);
        Player first = gameController.getGame().getPlayers().getFirst();
        Player second = gameController.getGame().getPlayers().getLast();
        gameController.placeStarterOnCodex(first,new Side(Kingdom.NULL, Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY));
        gameController.placeStarterOnCodex(second,new Side(Kingdom.NULL, Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY));
        gameController.selectCardObjective(first,0);
        gameController.selectCardObjective(second,0);
        Game game = gameController.getGame();
        //Empty the decks
        game.getDesk().getDeckGold().clear();
        game.getDesk().getDeckResource().clear();
        game.getDesk().getDisplayedGold().clear();
        game.getDesk().getDisplayedResource().clear();
        game.getPlayers().getFirst().setAction(PlayerAction.DRAW,game);

        gameController.drawCardFromDeck(game.getPlayers().get(game.getCurrPlayer()),DeckType.DECK_GOLD);
        assertEquals(game.getStatus(),GameStatus.ENDING);
    }
}