package it.polimi.ingsw.gc03.model.Enumerations;

/**
 * This enumeration class represents the available statuses of the game.
 */
public enum GameStatus {
    /**
     * Game's starting: the game is ready to start: every player joined.
     */
    STARTING,
    /**
     * Game's running.
     */
    RUNNING,
    /**
     * Game's ending: a player reached at least 20 as score.
     */
    ENDING,
    /**
     * Game's waiting for the first player to set the Game size and players to join .
     */
    WAITING
}
