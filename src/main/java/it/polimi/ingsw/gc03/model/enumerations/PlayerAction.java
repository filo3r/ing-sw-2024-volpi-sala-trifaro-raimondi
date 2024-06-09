package it.polimi.ingsw.gc03.model.enumerations;

import java.io.Serializable;

/**
 * This is the enumeration of the available player's statuses (or actions).
 */
public enum PlayerAction implements Serializable {
    /**
     * This is the first action a player has to do: place the starter card and choose an objective.
     */
    FIRSTMOVES,

    /**
     * This is the status when it's not his turn.
     */
    WAIT,

    /**
     * This is the status when he has to place a card.
     */
    PLACE,

    /**
     * This is the status when he has to draw a card.
     */
    DRAW,

    /**
     * This is the status when he has disconnected from the game.
     */
    DISCONNECTED,

    /**
     * This is the status when he cannot do anything more (either because he cannot place any more cards on the codex or
     * because he has done his turn in the GameStatus.LASTROUND.
     */
    ENDED
}
