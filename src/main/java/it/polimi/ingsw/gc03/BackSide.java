package it.polimi.ingsw.gc03;

/**
 * This class represents the back of a card.
 */
public class BackSide extends Side {

    /**
     * Values contained in the center.
     */
    private Value[] center;


    /**
     * Constructor of the class BackSide.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     * @param center1 First value contained in the center.
     * @param center2 Second value contained in the center.
     * @param center3 Third value contained in the center.
     */
    public BackSide(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner, Value bottomRightCorner,
                         Value center1, Value center2, Value center3) {
        super(topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner);
        this.center = new Value[3];
        this.center[0] = center1;
        this.center[1] = center2;
        this.center[2] = center3;
    }


    /**
     * Getter method to retrieve the values contained in the center.
     * @return The values contained in the center.
     */
    public Value[] getCenter() {
        return center;
    }


    /**
     * Setter method to set the values contained in the center.
     * @param center1 The first value to be set in the center.
     * @param center2 The second value to be set in the center.
     * @param center3 The third value to be set in the center.
     */
    public void setCenter(Value center1, Value center2, Value center3) {
        this.center = new Value[3];
        this.center[0] = center1;
        this.center[1] = center2;
        this.center[2] = center3;
    }


}
