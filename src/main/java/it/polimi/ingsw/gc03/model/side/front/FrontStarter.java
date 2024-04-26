package it.polimi.ingsw.gc03.model.side.front;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.io.Serializable;


/**
 * This class represents the front of a Starter card.
 */
public class FrontStarter extends Side implements Serializable {

    /**
     * Constructor of the class FrontStarter.
     * @param kingdom The kingdom of the card.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     */
    public FrontStarter(Kingdom kingdom, Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                        Value bottomRightCorner) {
        super(kingdom, topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner);
    }

}
