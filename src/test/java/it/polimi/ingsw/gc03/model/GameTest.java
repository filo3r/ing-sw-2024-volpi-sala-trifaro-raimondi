package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.exceptions.CannotJoinGameException;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class GameTest {

    private Game game;

    private Desk desk;

    private GameListener listener;

    @BeforeEach
    void setUp() throws RemoteException {
        game = new Game(14547);
        desk = new Desk(game);
        listener = mock(GameListener.class);
    }

    @AfterEach
    void tearDown() {
        game = null;
        desk= null;
    }

    @Test
    void addPlayer() throws PlayerAlreadyJoinedException, DeskIsFullException, RemoteException, CannotJoinGameException {
        int playerNum = game.getNumPlayer();
        game.addPlayer("newNick", listener);
        assertEquals(game.getNumPlayer(),playerNum+1);
        game.setSize(2);
        assertThrows(PlayerAlreadyJoinedException.class, ()-> game.addPlayer("newNick", listener));
        game.addPlayer("newNick1", listener);
        assertThrows(DeskIsFullException.class, ()->game.addPlayer("newNick2", listener));
        game.setSize(4);
        game.addPlayer("newNick2", listener);
        game.addPlayer("newNick3", listener);
        assertThrows(IndexOutOfBoundsException.class, ()-> game.addPlayer("newNick4", listener));

    }

    @Test
    void removePlayer() throws PlayerAlreadyJoinedException, DeskIsFullException, RemoteException, CannotJoinGameException {
        game.addPlayer("newNick", listener);
        int newPlayerNum = game.getNumPlayer();
        assertTrue(game.removePlayer("newNick"));
        assertEquals(newPlayerNum-1,game.getNumPlayer());
        assertFalse(game.removePlayer("newNick"));
    }

    @Test
    void removePlayerOnePlayerLeft() throws PlayerAlreadyJoinedException, DeskIsFullException, RemoteException, CannotJoinGameException {
        game.addPlayer("newNick",listener);
        game.setSize(2);
        game.addPlayer("newNick1",listener);
        game.removePlayer("newNick");
        assertEquals(game.getStatus(), GameStatus.ENDED);
        assertEquals(game.getWinner(),game.getPlayers());
    }

    @Test
    void addMessage() throws RemoteException {
        String text = "Hello";
        game.addMessage("Player1", "Player2",text);
        assertEquals(game.getChat().getLast().getSender(),"Player2");
        assertEquals(game.getChat().getLast().getReceiver(),"Player1");
        assertEquals(game.getChat().getLast().getText(),text);
        assertNotEquals(game.getChat().getLast().getTimestamp(),LocalTime.now());
    }

    @Test
    void updateCurrPlayer() throws RemoteException {
        game.setCurrPlayer(1);
        game.setNumPlayer(3);
        game.updateCurrPlayer();
        assertEquals(game.getCurrPlayer(), 1);
    }
    @Test
    void updateCurrPlayerRestart() throws RemoteException {
        game.setCurrPlayer(2);
        game.setNumPlayer(2);
        game.updateCurrPlayer();
        assertEquals(game.getCurrPlayer(),1);
    }

    @Test
    void getOnlinePlayers() throws PlayerAlreadyJoinedException, DeskIsFullException, RemoteException, CannotJoinGameException {
        game.setSize(2);
        game.addPlayer("Test1", listener);
        game.addPlayer("Test2", listener);
        game.getPlayers().get(0).setOnline(game,true,listener);
        game.getPlayers().get(1).setOnline(game,false,listener);
        int flag=0;
        ArrayList<Player> onlinePlayers= game.getOnlinePlayers();
        if(onlinePlayers.contains(game.getPlayers().get(0))){ flag=1;}
        assertEquals(flag,1);
        if(!onlinePlayers.contains(game.getPlayers().get(1))){ flag=2;}
        assertEquals(flag,2);

    }

    @Test
    void stopGamePoints() throws PlayerAlreadyJoinedException, DeskIsFullException, RemoteException, CannotJoinGameException {
        game.addPlayer("Test", listener);
        game.getPlayers().get(0).getCodex().setPointCodex(25);
        assertTrue(game.stopGame());
    }

    @Test
    void stopGameCards() throws PlayerAlreadyJoinedException, DeskIsFullException, RemoteException, CannotJoinGameException {
        game.addPlayer("Test", listener);
        ArrayList<CardResource> deckResource= new ArrayList<>();
        ArrayList<CardGold> deckGold= new ArrayList<>();
        game.getDesk().setDeckResource(deckResource);
        game.getDesk().setDeckGold(deckGold);
        assertTrue(game.stopGame());
    }

    @Test
    void getIdGame() {
    }

    @Test
    void setIdGame() {
        int idGame = 1234;
        game.setIdGame(1234);
        assertEquals(idGame,game.getIdGame());
    }

}