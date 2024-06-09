package it.polimi.ingsw.gc03.model.exceptions;

/**
 * Exception thrown when an attempt is made to add a player to a game when the player is already part of the game.
 */
public class PlayerAlreadyJoinedException extends Exception {

    /**
     * Constructs a PlayerAlreadyJoinedException with a default error message.
     */
    public PlayerAlreadyJoinedException() {
        super("This player is already in the game");
    }

    /**
     * Constructs a PlayerAlreadyJoinedException with a specified error message.
     *
     * @param message the detail message
     */
    public PlayerAlreadyJoinedException(String message) {
        super(message);
    }
}
