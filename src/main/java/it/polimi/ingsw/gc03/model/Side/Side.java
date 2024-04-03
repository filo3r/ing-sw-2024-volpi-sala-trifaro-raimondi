package it.polimi.ingsw.gc03.model.Side;

import it.polimi.ingsw.gc03.model.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.Enumerations.Value;

/**
 * This class manages the information present on both the front and back of a card.
 */
public class Side {

    /**
     * Kingdom of the card.
     */
    private Kingdom kingdom;

    /**
     * Value contained in the top-left corner.
     */
    private Value topLeftCorner;

    /**
     * Value contained in the bottom-left corner.
     */
    private Value bottomLeftCorner;

    /**
     * Value contained in the top-right corner.
     */
    private Value topRightCorner;

    /**
     * Value contained in the bottom-right corner.
     */
    private Value bottomRightCorner;


    /**
     * Constructor of the class Side.
     * @param kingdom The kingdom of the card.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     */
    public Side(Kingdom kingdom, Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                Value bottomRightCorner) {
        this.kingdom = kingdom;
        this.topLeftCorner = topLeftCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomRightCorner = bottomRightCorner;
    }


    /**
     * Getter method to retrieve the kingdom of the card.
     * @return The kingdom of the card.
     */
    public Kingdom getKingdom() {
        return kingdom;
    }


    /**
     * Setter method to set the kingdom of the card.
     * @param kingdom The kingdom of the card.
     */
    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }


    /**
     * Getter method to retrieve the value contained in the top-left corner.
     * @return The value contained in the top-left corner.
     */
    public Value getTopLeftCorner() {
        return topLeftCorner;
    }


    /**
     * Setter method to set the value contained in the top-left corner.
     * @param topLeftCorner The value to be set in the top-left corner.
     */
    public void setTopLeftCorner(Value topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }


    /**
     * Getter method to retrieve the value contained in the bottom-left corner.
     * @return The value contained in the bottom-left corner.
     */
    public Value getBottomLeftCorner() {
        return bottomLeftCorner;
    }


    /**
     * Setter method to set the value contained in the bottom-left corner.
     * @param bottomLeftCorner The value to be set in the bottom-left corner.
     */
    public void setBottomLeftCorner(Value bottomLeftCorner) {
        this.bottomLeftCorner = bottomLeftCorner;
    }


    /**
     * Getter method to retrieve the value contained in the top-right corner.
     * @return The value contained in the top-right corner.
     */
    public Value getTopRightCorner() {
        return topRightCorner;
    }


    /**
     * Setter method to set the value contained in the top-right corner.
     * @param topRightCorner The value to be set in the top-right corner.
     */
    public void setTopRightCorner(Value topRightCorner) {
        this.topRightCorner = topRightCorner;
    }


    /**
     * Getter method to retrieve the value contained in the bottom-right corner.
     * @return The value contained in the bottom-right corner.
     */
    public Value getBottomRightCorner() {
        return bottomRightCorner;
    }


    /**
     * Setter method to set the value contained in the bottom-right corner.
     * @param bottomRightCorner The value to be set in the bottom-right corner.
     */
    public void setBottomRightCorner(Value bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }


}
