package it.polimi.ingsw.gc03.view.ui.events;

import it.polimi.ingsw.gc03.model.GameImmutable;

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
     * Event constructor class
     *
     * @param gameImmutable
     * @param type
     */
    public Event(GameImmutable gameImmutable, EventType type) {
        this.gameImmutable = gameImmutable;
        this.type = type;
    }

    /**
     * @return gameImmutable
     */
    public GameImmutable getModel() {
        return gameImmutable;
    }

    /**
     * @return event type
     */
    public EventType getType() {
        return type;
    }
}
