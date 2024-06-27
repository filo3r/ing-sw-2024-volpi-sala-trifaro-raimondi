package it.polimi.ingsw.gc03.model.exceptions;

/**
 * Exception thrown when an attempt is made to draw a card from an empty deck.
 */
public class NoMoreCardException extends Exception {

    /**
     * Constructs a NoMoreCardException with a default error message.
     */
    public NoMoreCardException() {
        super("The cards in the deck are finished");
    }

    /**
     * Constructs a NoMoreCardException with a specified error message.
     * @param message The detail message.
     */
    public NoMoreCardException(String message) {
        super(message);
    }

}