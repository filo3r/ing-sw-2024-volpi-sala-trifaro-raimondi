package it.polimi.ingsw.gc03.controller;

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

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    private GameController gameController;

    @BeforeEach
    void setUp() throws RemoteException {
        gameController = new GameController();
    }

    @Test
    @DisplayName("Players added to the game")
    void addPlayerToGame() throws CannotJoinGameException, PlayerAlreadyJoinedException, DeskIsFullException, RemoteException {
        // A player is added to the game
        VirtualViewOld listener = new RmiClientOld(null);
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
        VirtualViewOld listener = new RmiClientOld(null);
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
        gameController.drawCardFromDeck(firstPlayer, gameDesk.getDeckResource());
        // Player1 tries to draw again from deck
        assertThrows(Exception.class, () -> gameController.drawCardFromDeck(firstPlayer, gameDesk.getDeckResource()));
        // Player1 tries to draw from displayed
        assertThrows(Exception.class, () -> gameController.drawCardDisplayed(firstPlayer, gameDesk.getDisplayedGold(), 0));
        assertEquals(2, gameDesk.getDisplayedGold().size());
        assertEquals(3, firstPlayer.getHand().size());
        assertEquals(PlayerAction.PLACE, secondPlayer.getAction());
        assertEquals(PlayerAction.WAIT, firstPlayer.getAction());
    }

    @Test
    public void testReconnectPlayer() throws CannotJoinGameException, DeskIsFullException, PlayerAlreadyJoinedException, RemoteException {
        Game game = gameController.getGame();
        VirtualViewOld listener = new RmiClientOld(null);
        gameController.addPlayerToGame("Player1", listener);
        game.setSize(2);
        gameController.addPlayerToGame("Player2", listener);
        Player player1 = game.getPlayers().get(0);
        Player player2 = game.getPlayers().get(1);

        player1.setOnline(false);
        game.setStatus(GameStatus.HALTED);
        gameController.reconnectPlayer("Player1");


        assertTrue(player1.getOnline());
        assertEquals(GameStatus.RUNNING, game.getStatus());
    }

    @Test
    void checkFinalActionTestCall() throws Exception {
        VirtualViewOld listener = new RmiClientOld(null);
        gameController.addPlayerToGame("Player1", listener);
        gameController.updateSize(2);
        gameController.addPlayerToGame("Player2", listener);
        Game game = gameController.getGame();
        game.setStatus(GameStatus.RUNNING);
        Card card;
        //Empty the decks
        while(!game.getDesk().getDisplayedGold().isEmpty()){
            card =game.getDesk().drawCardDisplayed(game.getDesk().getDisplayedGold(),0);
        }
        while(!game.getDesk().getDisplayedResource().isEmpty()){
            card =game.getDesk().drawCardDisplayed(game.getDesk().getDisplayedResource(),0);
        }
        while(!game.getDesk().getDeckResource().isEmpty()){
            card =game.getDesk().drawCardDeck(game.getDesk().getDeckResource());
        }
        while(!game.getDesk().getDeckGold().isEmpty()){
            card =game.getDesk().drawCardDeck(game.getDesk().getDeckGold());
        }
        game.getPlayers().getFirst().setAction(PlayerAction.DRAW);

        gameController.drawCardFromDeck(game.getPlayers().getFirst(),game.getDesk().getDeckGold());
        assertEquals(game.getStatus(),GameStatus.ENDING);
    }
}