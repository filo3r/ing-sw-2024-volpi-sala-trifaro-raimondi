package it.polimi.ingsw.gc03.view.inputHandler;

/**
 * Interface for reading input and providing an input queue.
 */
public interface InputReader {

    /**
     * Retrieves the input queue.
     * @return The input queue.
     */
    InputQueue getQueue();

}