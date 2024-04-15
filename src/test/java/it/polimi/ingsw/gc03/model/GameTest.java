package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.model.exceptions.PlayerAlreadyJoinedException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    private Game game;

    private Desk desk;

    @BeforeEach
    void setUp() {
        game = new Game(14547);
        desk = new Desk();
    }

    @AfterEach
    void tearDown() {
        game = null;
        desk= null;
    }

    @Test
    void addPlayer() throws PlayerAlreadyJoinedException, DeskIsFullException {
        int playerNum = game.getNumPlayer();
        game.addPlayer("newNick");
        assertEquals(game.getNumPlayer(),playerNum+1);
        game.setSize(2);
        assertThrows(PlayerAlreadyJoinedException.class, ()-> game.addPlayer("newNick"));
        game.addPlayer("newNick1");
        assertThrows(DeskIsFullException.class, ()->game.addPlayer("newNick2"));
        game.setSize(4);
        game.addPlayer("newNick2");
        game.addPlayer("newNick3");
        assertThrows(DeskIsFullException.class, ()-> game.addPlayer("newNick4"));

    }

    @Test
    void addMessage() {
        LocalTime time = LocalTime.of(12,0,0);
        ArrayList<Message> chat = game.getChat();
        Message newMessage = new Message(new Player("Example",1,desk),"Hello",time);
        chat.add(newMessage);
        assertEquals(game.getChat().getLast(),newMessage);

    }

    @Test
    void updateCurrPlayer() {
        game.setCurrPlayer(1);
        game.setNumPlayer(3);
        game.updateCurrPlayer();
        assertEquals(game.getCurrPlayer(), 2);
    }
    @Test
    void updateCurrPlayerRestart() {
        game.setCurrPlayer(2);
        game.setNumPlayer(2);
        game.updateCurrPlayer();
        assertEquals(game.getCurrPlayer(),0);
    }

    @Test
    void createTurnOrder() throws PlayerAlreadyJoinedException, DeskIsFullException {
        game.setSize(4);
        game.addPlayer("1");
        game.addPlayer("2");
        game.addPlayer("3");
        game.addPlayer("4");
        game.createTurnOrder();
        assertEquals(game.getTurnOrder().size(),game.getPlayers().size());
    }

    @Test
    void getOnlinePlayers() throws PlayerAlreadyJoinedException, DeskIsFullException {
        game.setSize(2);
        game.addPlayer("Test1");
        game.addPlayer("Test2");
        game.getPlayers().get(0).setOnline(true);
        game.getPlayers().get(1).setOnline(false);
        int flag=0;
        ArrayList<Player> onlinePlayers= game.getOnlinePlayers();
        if(onlinePlayers.contains(game.getPlayers().get(0))){ flag=1;}
        assertEquals(flag,1);
        if(!onlinePlayers.contains(game.getPlayers().get(1))){ flag=2;}
        assertEquals(flag,2);

    }

    @Test
    void stopGamePoints() throws PlayerAlreadyJoinedException, DeskIsFullException {
        game.addPlayer("Test");
        game.getPlayers().get(0).getCodex().setPointCodex(25);
        assertTrue(game.stopGame());
    }

    @Test
    void stopGameCards() throws PlayerAlreadyJoinedException, DeskIsFullException {
        game.addPlayer("Test");
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
    }

    @Test
    void setSize() {
    }

    @Test
    void getSize() {
    }

    @Test
    void getStatus() {
    }

    @Test
    void setStatus() {
    }

    @Test
    void getDesk() {
    }

    @Test
    void setDesk() {
    }

    @Test
    void getNumPlayer() {
    }

    @Test
    void setNumPlayer() {
    }

    @Test
    void getPlayers() {
    }

    @Test
    void setPlayers() {
    }

    @Test
    void getTurnOrder() {
    }

    @Test
    void setTurnOrder() {
    }

    @Test
    void getCurrPlayer() {
    }

    @Test
    void setCurrPlayer() {
    }

    @Test
    void getChat() {
    }

    @Test
    void setChat() {
    }

    @Test
    void getWinner() {
    }

    @Test
    void setWinner() {
    }
}