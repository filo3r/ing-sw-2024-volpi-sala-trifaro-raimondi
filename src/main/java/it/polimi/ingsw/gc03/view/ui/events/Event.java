package it.polimi.ingsw.gc03.view.ui.events;

import it.polimi.ingsw.gc03.model.GameImmutable;

/**
 * This class represents an event in the game.
 * It holds the state of the game at the time the event was created and the type of the event.
 */
public class Event {

    /**
     * Immutable object of the game at the time of the event creation.
     */
    private GameImmutable gameImmutable;

    /**
     * Type of the event.
     */
    private EventType type;

    /**
     * Constructs an Event object with the specified game state and event type.
     * @param gameImmutable The state of the game when the event was created.
     * @param type The type of the event.
     */
    public Event(GameImmutable gameImmutable, EventType type) {
        this.gameImmutable = gameImmutable;
        this.type = type;
    }

    /**
     * Returns the state of the game at the time of the event creation.
     * @return The immutable game state.
     */
    public GameImmutable getModel() {
        return gameImmutable;
    }

    /**
     * Returns the type of the event.
     * @return The event type.
     */
    public EventType getType() {
        return type;
    }

}