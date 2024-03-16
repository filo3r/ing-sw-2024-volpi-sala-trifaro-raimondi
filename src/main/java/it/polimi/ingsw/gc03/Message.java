package it.polimi.ingsw.gc03;

/**
 * This class represents a message in the chat.
 */
public class Message {
    /**
     * The unique message's id.
     */
    private int idMessage;

    /**
     * The message's text.
     */
    private String text;

    /**
     * The message's timestamp.
     */
    private int timestamp;

    /**
     * The message's sender.
     */
    private Player sender;

    /**
     * Constructor for the Message class.
     * @param idMessage The message's unique id.
     * @param text The message's text.
     * @param timestamp The message's timestamp.
     * @param sender The message's sender.
     */
    public Message(int idMessage, String text, int timestamp, Player sender) {
        this.idMessage = idMessage;
        this.text = text;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    /**
     * Getter method for retrieving the unique id of the message.
     * @return The unique id of the message.
     */
    public int getIdMessage() {
        return idMessage;
    }

    /**
     * Setter method for setting the unique id of the message.
     * @param idMessage The unique id of the message to set.
     */
    public void setIdMessage(int idMessage) {
        this.idMessage = idMessage;
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
    public int getTimestamp() {
        return timestamp;
    }

    /**
     * Setter method for setting the timestamp of the message.
     * @param timestamp The timestamp of the message to set.
     */
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Getter method for retrieving the sender of the message.
     * @return The sender of the message.
     */
    public Player getSender() {
        return sender;
    }

    /**
     * Setter method for setting the sender of the message.
     * @param sender The sender of the message to set.
     */
    public void setSender(Player sender) {
        this.sender = sender;
    }
}
