package it.polimi.ingsw.gc03.model.side.front;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import java.io.Serializable;

/**
 * This class represents the front of a Resource card.
 */
public class FrontResource extends Side implements Serializable {

    /**
     * Points on the card
     */
    private int point;

    /**
     * Constructor of the class FrontResource.
     * @param kingdom The kingdom of the card.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     * @param point Points on the card.
     */
    public FrontResource(Kingdom kingdom, Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                         Value bottomRightCorner, int point) {
        super(kingdom, topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner);
        this.point = point;
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