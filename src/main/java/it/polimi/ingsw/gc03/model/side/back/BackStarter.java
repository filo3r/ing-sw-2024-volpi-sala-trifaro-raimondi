package it.polimi.ingsw.gc03.model.side.back;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import java.util.ArrayList;

/**
 * This class represents the back of a Starter card.
 */
public class BackStarter extends BackSide {

    /**
     * Constructor of the class BackStarter.
     * @param kingdom The kingdom of the card.
     * @param topLeftCorner Value contained in the top-left corner.
     * @param bottomLeftCorner Value contained in the bottom-left corner.
     * @param topRightCorner Value contained in the top-right corner.
     * @param bottomRightCorner Value contained in the bottom-right corner.
     * @param center Value contained in the center.
     */
    public BackStarter(Kingdom kingdom, Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner, Value bottomRightCorner, ArrayList<Value> center) {
        super(kingdom, topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner, center);
    }

}