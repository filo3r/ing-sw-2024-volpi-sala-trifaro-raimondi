package it.polimi.ingsw.gc03.model;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * This class represents a message in the chat.
 */
public class ChatMessage implements Serializable {

    /**
     * The message's sender.
     */
    private String sender;

    /**
     * The message's text.
     */
    private String text;

    /**
     * The message's timestamp.
     */
    private LocalTime timestamp;

    /**
     * The message's receiver(s).
     */
    private String receiver;

    /**
     * Constructor for the Message class.
     * @param sender The message's sender.
     * @param text The message's text.
     * @param timestamp The message's timestamp.
     */
    public ChatMessage(String receiver, String sender, String text, LocalTime timestamp) {
        this.text = text;
        this.timestamp = timestamp;
        this.sender = sender;
        this.receiver = receiver;
    }

    /**
     * Getter method for retrieving the text of the message.
     * @return The text of the message.
     */
    public String getText() {
        return text;
    }

    /**
     * Setter method for setting the text of the message.
     * @param text The text of the message to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter method for retrieving the timestamp of the message.
     * @return The timestamp of the message.
     */
    public LocalTime getTimestamp() {
        return timestamp;
    }

    /**
     * Setter method for setting the timestamp of the message.
     * @param timestamp The timestamp of the message to set.
     */
    public void setTimestamp(LocalTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter method for retrieving the sender of the message.
     * @return The sender of the message.
     */
    public String getSender() {
        return sender;
    }

    /**
     * Setter method for setting the sender of the message.
     * @param sender The sender of the message to set.
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * Getter method for getting the receiver of the message.
     * @return The receiver of the message.
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * Setter method for setting the receiver of the message.
     * @param receiver The sender of the message to set.
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
