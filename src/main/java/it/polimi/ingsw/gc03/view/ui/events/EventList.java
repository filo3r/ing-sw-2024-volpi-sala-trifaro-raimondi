package it.polimi.ingsw.gc03.view.ui.events;

import it.polimi.ingsw.gc03.model.GameImmutable;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 * This class represents a list of events in the game, implemented as a FIFO queue.
 * It provides methods to add events, retrieve and remove the next event, get the size of the queue, and clear the queue.
 */
public class EventList {

    /**
     * FIFO queue of events.
     */
    private Queue<Event> lists;

    /**
     * Initializes the EventList with an empty queue.
     */
    public EventList() {
        lists = new ArrayDeque<>();
    }

    /**
     * Adds a new event to the list.
     * @param gameImmutable The state of the game when the event is created.
     * @param type The type of the event.
     */
    public synchronized void add(GameImmutable gameImmutable, EventType type) {
        lists.add(new Event(gameImmutable, type));

    }

    /**
     * Retrieves and removes the next event from the queue (FIFO).
     * @return The next event in the queue, or null if the queue is empty.
     */
    public synchronized Event pop() {
        Event event = lists.poll();
        if (event != null) {
        } else {
        }
        return event;
    }

    /**
     * Returns the size of the event list.
     * @return The number of events in the queue.
     */
    public synchronized int size() {
        return lists.size();
    }

    /**
     * Clears all events from the event list.
     */
    public void clearEventQueue() {
        while (!lists.isEmpty()) {
            lists.poll();
        }
    }

}