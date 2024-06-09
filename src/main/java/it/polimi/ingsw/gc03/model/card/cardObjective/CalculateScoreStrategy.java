package it.polimi.ingsw.gc03.model.card.cardObjective;

import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import java.util.ArrayList;

/**
 * This interface defines the method for calculating Objective card points.
 */
public interface CalculateScoreStrategy {

    /**
     * Method for calculating points.
     * @param codex The codex on which points must be calculated.
     * @param point The points of the card.
     * @param parameters Parameters used to calculate the points.
     * @return The calculated points.
     */
    int calculateScore(Codex codex, int point, ArrayList<Value> parameters);
}
