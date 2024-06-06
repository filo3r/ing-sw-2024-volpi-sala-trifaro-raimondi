package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.listeners.GameListener;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    private ChatMessage chatMessage;

    private String text;

    private Player sender;

    private LocalTime timestamp;

    private Desk desk, desk1;

    private Game game;
    private GameListener gameListener;


    @BeforeEach
    void setUp() throws RemoteException {
        text ="Hello";
        game = new Game(5686686);
        desk = new Desk(game);
        sender = new Player("TestName",1,desk,game,gameListener);
        timestamp = LocalTime.of(12,0,0);
        chatMessage = new ChatMessage("everyone","TestName",text,timestamp);
    }

    @AfterEach
    void tearDown() {
        text = null;
        desk = null;
        sender = null;
        timestamp = null;
        chatMessage = null;
    }

    @Test
    void getText() {
        assertEquals(text, chatMessage.getText());
    }

    @Test
    void setText() {
        String newText ="Goodbye";
        String oldText = chatMessage.getText();
        chatMessage.setText(newText);
        assertEquals(newText, chatMessage.getText());
        assertNotEquals(oldText,newText);
    }

    @Test
    void getTimestamp() {
        assertEquals(timestamp, chatMessage.getTimestamp());
    }

    @Test
    void setTimestamp() {
        LocalTime newTime = LocalTime.of(21,0,0);
        LocalTime oldTime = chatMessage.getTimestamp();
        chatMessage.setTimestamp(newTime);
        assertEquals(newTime, chatMessage.getTimestamp());
        assertNotEquals(oldTime,newTime);
    }

    @Test
    void getSender() {
        assertEquals(sender.getNickname(), chatMessage.getSender());
    }

    @Test
    void setSender() throws RemoteException {
        Game game1 = new Game(15749449);
        desk1 = new Desk(game1);
        String newSender = "TestName2";
        String oldSender = chatMessage.getSender();
        chatMessage.setSender(newSender);
        assertEquals(newSender, chatMessage.getSender());
        assertNotEquals(oldSender,newSender);
    }

    @Test
    void setReceiver(){
        String newReceiver = "NewReceiver";
        String oldReceiver = chatMessage.getReceiver();
        chatMessage.setReceiver(newReceiver);
        assertEquals(chatMessage.getReceiver(),newReceiver);
        assertNotEquals(chatMessage.getReceiver(),oldReceiver);
    }
}