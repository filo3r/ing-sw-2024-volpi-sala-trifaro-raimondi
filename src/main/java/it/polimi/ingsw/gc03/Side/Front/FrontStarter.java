package it.polimi.ingsw.gc03.Side.Front;

import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Side.Side;
import it.polimi.ingsw.gc03.Enumerations.Value;
import static it.polimi.ingsw.gc03.Enumerations.Kingdom.NULL;


/**
 * This class represents the front of a Starter card.
 */
public class FrontStarter extends Side {

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
