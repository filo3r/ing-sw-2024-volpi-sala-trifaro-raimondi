package it.polimi.ingsw.gc03.model.side.back;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.util.ArrayList;

public class BackStarter extends BackSide{
    public BackStarter(Kingdom kingdom, Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner, Value bottomRightCorner, ArrayList<Value> center) {
        super(kingdom, topLeftCorner, bottomLeftCorner, topRightCorner, bottomRightCorner, center);
    }
}
