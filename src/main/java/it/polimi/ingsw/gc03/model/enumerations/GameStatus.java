package it.polimi.ingsw.gc03.model.enumerations;

import java.io.Serializable;

/**
 * This enumeration class represents the available statuses of the game.
 */
public enum GameStatus implements Serializable {

    /**
     * Game's waiting for the first player to set the Game size and players to join.
     */
    WAITING,

    /**
     * Game's starting: the game is ready to start: every player joined.
     */
    STARTING,

    /**
     * Game's running.
     */
    RUNNING,

    /**
     * Game's in pause, because only one player is currently online.
     */
    HALTED,

    /**
     * Game's ending: a player reached at least 20 as score or all decks are empty.
     */
    ENDING,

    /**
     * Game's last round: this is the final round of the game.
     */
    LASTROUND,

    /**
     * Game's ended.
     */
    ENDED

}