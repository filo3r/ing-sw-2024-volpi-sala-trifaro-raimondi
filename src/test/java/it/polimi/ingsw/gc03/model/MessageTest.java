package it.polimi.ingsw.gc03.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private Message message;

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
        message = new Message(sender,text,timestamp);
    }

    @AfterEach
    void tearDown() {
        text = null;
        desk = null;
        sender = null;
        timestamp = null;
        message = null;
    }

    @Test
    void getText() {
        assertEquals(text,message.getText());
    }

    @Test
    void setText() {
        String newText ="Goodbye";
        String oldText = message.getText();
        message.setText(newText);
        assertEquals(newText,message.getText());
        assertNotEquals(oldText,newText);
    }

    @Test
    void getTimestamp() {
        assertEquals(timestamp,message.getTimestamp());
    }

    @Test
    void setTimestamp() {
        LocalTime newTime = LocalTime.of(21,0,0);
        LocalTime oldTime = message.getTimestamp();
        message.setTimestamp(newTime);
        assertEquals(newTime,message.getTimestamp());
        assertNotEquals(oldTime,newTime);
    }

    @Test
    void getSender() {
        assertEquals(sender,message.getSender());
    }

    @Test
    void setSender() {
        desk1 = new Desk();
        Player newSender = new Player("TestName2",4,desk1);
        Player oldSender = message.getSender();
        message.setSender(newSender);
        assertEquals(newSender,message.getSender());
        assertNotEquals(oldSender,newSender);
    }
}