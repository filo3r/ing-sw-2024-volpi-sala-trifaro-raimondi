package it.polimi.ingsw.gc03.model.Side.Back;

import it.polimi.ingsw.gc03.model.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.Side.Side;
import it.polimi.ingsw.gc03.model.Enumerations.Value;
import java.util.ArrayList;

/**
 * This class represents the back of a card.
 */
public class BackSide extends Side {

    /**
     * Values contained in the center.
     */
    private ArrayList<Value> center;


    /**
     * Constructor of the class BackSide.
     * @param kingdom The kingdom of the card.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     * @param center Value contained in the center.
     */
    public BackSide(Kingdom kingdom, Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                    Value bottomRightCorner, ArrayList<Value> center) {
        super(kingdom, topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner);
        this.center = new ArrayList<>(center);
    }


    /**
     * Getter method to retrieve the values contained in the center.
     * @return The values contained in the center.
     */
    public ArrayList<Value> getCenter() {
        return center;
    }


    /**
     * Setter method to set the values contained in the center.
     * @param center The values to be set in the center.
     */
    public void setCenter(ArrayList<Value> center) {
        this.center = center;
    }


}
