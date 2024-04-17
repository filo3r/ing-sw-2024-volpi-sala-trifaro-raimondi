package it.polimi.ingsw.gc03.model.enumerations;


/**
 * Player's action: FIRSTMOVES (place the starter and the select the objective) -> WAIT (wait your turn) || PLACE || DRAW -> ENDED
 */
public enum PlayerAction {
    WAIT,
    PLACE,
    FIRSTMOVES, // first moves stands for "placing the starter and selecting the objective"
    DRAW,
    ENDED
}
