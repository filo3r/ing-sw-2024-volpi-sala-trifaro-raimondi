package it.polimi.ingsw.gc03.model.exceptions;

/**
 * Exception thrown when an attempt is made to add a card to a desk that is already full.
 */
public class DeskIsFullException extends Exception {

    /**
     * Constructs a DeskIsFullException with a default error message.
     */
    public DeskIsFullException() {
        super("Error: The Desk is already full");
    }

    /**
     * Constructs a DeskIsFullException with a specified error message.
     *
     * @param message the detail message
     */
    public DeskIsFullException(String message) {
        super(message);
    }
}
