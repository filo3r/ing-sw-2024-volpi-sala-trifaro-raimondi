package it.polimi.ingsw.gc03;

/**
 * This class represents the front of a Resource card.
 */
public class FrontResource {

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
     * Points on the card
     */
    private int point;


    /**
     * Constructor of the class FrontResource.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     * @param point Points on the card.
     */
    public FrontResource(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner, Value bottomRightCorner,
                         int point) {
        this.topLeftCorner = topLeftCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomRightCorner = bottomRightCorner;
        this.point = point;
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


    /**
     * Getter method to retrieve the points on the card.
     * @return The points on the card.
     */
    public int getPoint() {
        return point;
    }


    /**
     * Setter method to set the points on the card.
     * @param point The points to be set for the card.
     */
    public void setPoint(int point) {
        this.point = point;
    }


}
