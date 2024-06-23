package it.polimi.ingsw.gc03.view.ui.events;

import it.polimi.ingsw.gc03.model.Model;

public class Event {
    /**
     * Immutable object of the game at the time of the event creation.
     */
    private Model model;

    /**
     * Type of the event.
     */
    private EventType type;

    /**
     * Event constructor class
     *
     * @param model
     * @param type
     */
    public Event(Model model, EventType type) {
        this.model = model;
        this.type = type;
    }

    /**
     * @return model
     */
    public Model getModel() {
        return model;
    }

    /**
     * @return event type
     */
    public EventType getType() {
        return type;
    }
}
