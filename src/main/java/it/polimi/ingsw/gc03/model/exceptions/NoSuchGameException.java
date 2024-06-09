package it.polimi.ingsw.gc03.model.exceptions;

/**
 * Exception thrown when an attempt is made to interact with a game that does not exist.
 */
public class NoSuchGameException extends Exception {

    /**
     * Constructs a NoSuchGameException with a default error message.
     */
    public NoSuchGameException() {
        super("This game doesn't exist");
    }

    /**
     * Constructs a NoSuchGameException with a specified error message.
     *
     * @param message the detail message
     */
    public NoSuchGameException(String message) {
        super(message);
    }
}
