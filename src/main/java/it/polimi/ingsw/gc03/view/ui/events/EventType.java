package it.polimi.ingsw.gc03.view.ui.events;

/**
 * Enumeration of event types used in the game.
 * This enum defines the various types of events that can occur during the game's lifecycle.
 */
public enum EventType {

    /**
     * Event for displaying the application menu.
     */
    APP_MENU,

    /**
     * Event for a player joining the game.
     */
    PLAYER_JOINED,

    /**
     * Event when a player is unable to join because the game is full.
     */
    JOIN_UNABLE_GAME_FULL,

    /**
     * Event when a player is unable to join because the nickname is already in use.
     */
    JOIN_UNABLE_NICKNAME_ALREADY_IN_USE,

    /**
     * Event when the specified game ID does not exist.
     */
    GAME_ID_NOT_EXISTS,

    /**
     * Event for an error occurring when entering the game.
     */
    ERROR_WHEN_ENTERING_GAME,

    /**
     * Event for the game starting.
     */
    GAMESTARTED,

    /**
     * Event for the game ending.
     */
    GAMEENDED,

    /**
     * Event for a sent message.
     */
    SENT_MESSAGE,

    /**
     * Event for the next turn in the game.
     */
    NEXT_TURN,

    /**
     * Event for a player disconnecting.
     */
    PLAYER_DISCONNECTED,

    /**
     * Event for a player reconnecting.
     */
    PLAYER_RECONNECTED,

    /**
     * Event for placing the starter card on the codex.
     */
    PLACE_STARTER_ON_CODEX,

    /**
     * Event for drawing a card.
     */
    DRAW_CARD,

    /**
     * Event for placing a card on the codex.
     */
    PLACE_CARD_ON_CODEX,

    /**
     * Event when a card cannot be placed.
     */
    CARD_CANNOT_BE_PLACED,

    /**
     * Event for positioning a card.
     */
    POSITIONED_CARD,

    /**
     * Event for choosing an objective card.
     */
    CHOOSE_OBJECTIVE_CARD,

    /**
     * Event for invalid coordinates.
     */
    INVALID_COORDINATES,

    /**
     * Event for extracting common cards.
     */
    COMMON_CARDS_EXTRACTED,

    /**
     * Event for a player leaving the game.
     */
    PLAYER_LEFT,

    /**
     * Event for creating a game.
     */
    GAMECREATED,

    /**
     * Event for showing the chat.
     */
    SHOW_CHAT,

    /**
     * Event for displaying the game title.
     */
    GAME_TITLE

}