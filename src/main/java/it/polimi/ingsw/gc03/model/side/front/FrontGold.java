package it.polimi.ingsw.gc03.model.side.front;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the front of a Gold card.
 */
public class FrontGold extends Side implements Serializable {

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
    private ArrayList<Value> requirementPlacement;

    /**
     * Constructor of the class FrontGold.
     * @param kingdom The kingdom of the card.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     * @param point Points on the card.
     * @param requirementPoint Requirement for the points.
     * @param requirementPlacement Requirements for placing the card.
     */
    public FrontGold(Kingdom kingdom, Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                     Value bottomRightCorner, int point, Value requirementPoint, ArrayList<Value> requirementPlacement) {
        super(kingdom, topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner);
        this.point = point;
        this.requirementPoint = requirementPoint;
        this.requirementPlacement = new ArrayList<>(requirementPlacement);
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
    public ArrayList<Value> getRequirementPlacement() {
        return requirementPlacement;
    }

    /**
     * Setter method to set the requirements for the points.
     * @param requirementPlacement The requirements for card placement.
     */
    public void setRequirementPlacement(ArrayList<Value> requirementPlacement) {
        this.requirementPlacement = requirementPlacement;
    }
}
