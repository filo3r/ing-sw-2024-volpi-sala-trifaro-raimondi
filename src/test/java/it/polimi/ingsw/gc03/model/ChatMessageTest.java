package it.polimi.ingsw.gc03.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ChatMessageTest {

    private ChatMessage chatMessage;

    private String text;

    private Player sender;

    private LocalTime timestamp;

    private Desk desk, desk1;


    @BeforeEach
    void setUp() {
        text ="Hello";
        desk = new Desk();
        sender = new Player("TestName",1,desk);
        timestamp = LocalTime.of(12,0,0);
        chatMessage = new ChatMessage(sender,text,timestamp);
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
        assertEquals(sender, chatMessage.getSender());
    }

    @Test
    void setSender() {
        desk1 = new Desk();
        Player newSender = new Player("TestName2",4,desk1);
        Player oldSender = chatMessage.getSender();
        chatMessage.setSender(newSender);
        assertEquals(newSender, chatMessage.getSender());
        assertNotEquals(oldSender,newSender);
    }
}