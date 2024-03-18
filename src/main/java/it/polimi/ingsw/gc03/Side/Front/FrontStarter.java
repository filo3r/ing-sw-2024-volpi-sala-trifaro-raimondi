package it.polimi.ingsw.gc03.Side.Front;

import it.polimi.ingsw.gc03.Side.Side;
import it.polimi.ingsw.gc03.Enumerations.Value;

/**
 * This class represents the front of a Starter card.
 */
public class FrontStarter extends Side {

    /**
     * Constructor of the class FrontStarter.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     */
    public FrontStarter(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner, Value bottomRightCorner) {
        super(topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner);
    }

}
