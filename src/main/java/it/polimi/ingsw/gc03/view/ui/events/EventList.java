package it.polimi.ingsw.gc03.view.ui.events;

import it.polimi.ingsw.gc03.model.GameImmutable;
import java.util.ArrayDeque;
import java.util.Queue;

public class EventList {

    /**
     * FIFO queue of events.
     */
    private Queue<Event> lists;

    /**
     * If the player has joined a game.
     */
    private boolean joined = false;

    /**
     * Init
     */
    public EventList() {
        lists = new ArrayDeque<>();
    }

    /**
     * Adds a new event to the list
     * @param gameImmutable
     * @param type
     */
    public synchronized void add(GameImmutable gameImmutable, EventType type) {
        lists.add(new Event(gameImmutable, type));

        if (type.equals(EventType.APP_MENU)) {
            joined = false;
        } else {
            joined = true;
        }
    }

    /**
     *
     * @return an element from the queue(FIFO)
     */
    public synchronized Event pop() {
        Event event = lists.poll();
        if (event != null) {
        } else {
        }
        return event;
    }

    /**
     *
     * @return the list's size
     */
    public synchronized int size() {
        return lists.size();
    }

    /**
     *
     * @return true if the player has joined the game, false if not
     */
    public synchronized boolean isJoined() {
        return joined;
    }

    public void clearEventQueue() {
        while (!lists.isEmpty()) {
            lists.poll();
        }
    }
}
