package it.polimi.ingsw.gc03.model.enumerations;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This enumeration class represents the colors players can have.
 */
public enum Color implements Serializable {

    /**
     * Red color.
     */
    RED,

    /**
     * Blue color.
     */
    BLUE,

    /**
     * Green color.
     */
    GREEN,

    /**
     * Yellow color.
     */
    YELLOW;

    /**
     * Method for creating an ArrayList of Color values.
     * @return An ArrayList of Color values.
     */
    public static ArrayList<Color> createColorArrayList() {
        ArrayList<Color> colors = new ArrayList<>();
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.GREEN);
        colors.add(Color.YELLOW);
        return colors;
    }

}