package it.polimi.ingsw.gc03.view.flow.utilities.events;

import it.polimi.ingsw.gc03.model.GameImmutable;

public class Event {
    private GameImmutable model;
    private EventType type;

    /**
     * Init
     *
     * @param model
     * @param type
     */
    public Event(GameImmutable model, EventType type) {
        this.model = model;
        this.type = type;
    }

    /**
     * @return model
     */
    public GameImmutable getModel() {
        return model;
    }

    /**
     * @return event type
     */
    public EventType getType() {
        return type;
    }
}
