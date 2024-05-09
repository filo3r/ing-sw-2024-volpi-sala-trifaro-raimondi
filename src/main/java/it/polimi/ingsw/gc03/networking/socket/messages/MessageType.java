package it.polimi.ingsw.gc03.networking.socket.messages;


/**
 * Enum MessageType defines the types of messages that can be sent from the client to the server.
 * This enumeration provides a type-safe way of handling different message purposes in the networking code.
 */
public enum MessageType {

    /**
     * MAIN_CONTROLLER messages are intended for handling general game session operations,
     * such as joining a game, starting a session, or updating player status.
     */
    MAIN_CONTROLLER,

    /**
     * GAME_CONTROLLER messages are specific to game logic and actions,
     * such as making a move, updating game state, or fetching current game data.
     */
    GAME_CONTROLLER,

    /**
     * PING messages are used to keep the connection alive between the client and server.
     * These are periodic signals sent to ensure that the connection is still active and responsive.
     */
    PING

}
