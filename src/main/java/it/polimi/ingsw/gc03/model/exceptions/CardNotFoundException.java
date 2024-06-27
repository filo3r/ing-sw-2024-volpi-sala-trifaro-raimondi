package it.polimi.ingsw.gc03.model.exceptions;

/**
 * Exception thrown when a specified card is not found in the game.
 */
public class CardNotFoundException extends Exception {

    /**
     * Constructs a CardNotFoundException with a default error message.
     */
    public CardNotFoundException() {
        super("Error: Card not found");
    }

    /**
     * Constructs a CardNotFoundException with a specified error message.
     * @param message The detail message.
     */
    public CardNotFoundException(String message) {
        super(message);
    }

}