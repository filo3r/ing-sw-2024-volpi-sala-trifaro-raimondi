package it.polimi.ingsw.gc03.model.exceptions;

/**
 * Exception thrown when an attempt to join a game fails because the game's status is not WAITING.
 */
public class CannotJoinGameException extends Exception {

    /**
     * Constructs a CannotJoinGameException with a default error message.
     */
    public CannotJoinGameException() {
        super("Error: It's not possible to join this game, because its status is not WAITING");
    }

    /**
     * Constructs a CannotJoinGameException with a specified error message.
     * @param message The detail message.
     */
    public CannotJoinGameException(String message) {
        super(message);
    }

}