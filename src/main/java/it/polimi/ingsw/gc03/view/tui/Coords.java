package it.polimi.ingsw.gc03.view.tui;

import java.io.Serializable;

/**
 * Class representing coordinates with x and y values.
 */
public class Coords implements Serializable {

    /**
     * The x-coordinate.
     */
    int x;

    /**
     * The y-coordinate.
     */
    int y;

    /**
     * Constructs a Coords object with the specified x and y coordinates.
     * @param x The x-coordinate.
     * @param y The y-coordinate.
     */
    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Method to retrieve the x-coordinate.
     * @return The x-coordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Method to retrieve the y-coordinate.
     * @return The y-coordinate.
     */
    public int getY() {
        return y;
    }

}