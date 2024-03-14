package it.polimi.ingsw.gc03;

/**
 * This class represents the front of a Gold card.
 */
public class FrontGold {

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
     * Requirement for the points.
     */
    private Value requirementPoint;

    /**
     * Requirements for card placement.
     */
    private Value[] requirementPlacement;


    /**
     * Constructor of the class FrontGold.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     * @param point Points on the card.
     * @param requirementPoint Requirement for the points.
     * @param requirementPlacement1 The first placement requirement.
     * @param requirementPlacement2 The second placement requirement.
     * @param requirementPlacement3 The third placement requirement.
     * @param requirementPlacement4 The fourth placement requirement.
     * @param requirementPlacement5 The fifth placement requirement.
     */
    public FrontGold(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner, Value bottomRightCorner,
                     int point, Value requirementPoint, Value requirementPlacement1, Value requirementPlacement2,
                     Value requirementPlacement3, Value requirementPlacement4, Value requirementPlacement5) {
        this.topLeftCorner = topLeftCorner;
        this.bottomLeftCorner = bottomLeftCorner;
        this.topRightCorner = topRightCorner;
        this.bottomRightCorner = bottomRightCorner;
        this.point = point;
        this.requirementPoint = requirementPoint;
        this.requirementPlacement = new Value[5];
        this.requirementPlacement[0] = requirementPlacement1;
        this.requirementPlacement[1] = requirementPlacement2;
        this.requirementPlacement[2] = requirementPlacement3;
        this.requirementPlacement[3] = requirementPlacement4;
        this.requirementPlacement[4] = requirementPlacement5;
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


    /**
     * Getter method to retrieve the requirement for the points.
     * @return The requirement for the points.
     */
    public Value getRequirementPoint() {
        return requirementPoint;
    }


    /**
     * Setter method to set the requirement for the points.
     * @param requirementPoint The requirement to be set for the points.
     */
    public void setRequirementPoint(Value requirementPoint) {
        this.requirementPoint = requirementPoint;
    }


    /**
     * Getter method to retrieve the requirements for card placement.
     * @return The requirements for card placement.
     */
    public Value[] getRequirementPlacement() {
        return requirementPlacement;
    }


    /**
     * Setter method to set the requirements for the points.
     * @param requirementPlacement1 The first placement requirement.
     * @param requirementPlacement2 The second placement requirement.
     * @param requirementPlacement3 The third placement requirement.
     * @param requirementPlacement4 The fourth placement requirement.
     * @param requirementPlacement5 The fifth placement requirement.
     */
    public void setRequirementPlacement(Value requirementPlacement1, Value requirementPlacement2,
                                        Value requirementPlacement3, Value requirementPlacement4,
                                        Value requirementPlacement5) {
        this.requirementPlacement = new Value[5];
        this.requirementPlacement[0] = requirementPlacement1;
        this.requirementPlacement[1] = requirementPlacement2;
        this.requirementPlacement[2] = requirementPlacement3;
        this.requirementPlacement[3] = requirementPlacement4;
        this.requirementPlacement[4] = requirementPlacement5;
    }


}
