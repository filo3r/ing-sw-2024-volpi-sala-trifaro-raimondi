package it.polimi.ingsw.gc03.model.card.card.objective;

import com.google.gson.annotations.JsonAdapter;
import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.io.Serializable;
import java.util.ArrayList;


/**
 * This class calculates the points of Objective cards that use the number of values.
 */
@JsonAdapter(CalculateScoreStrategyAdapter.class)
public class RequirementStrategy implements CalculateScoreStrategy, Serializable {

    /**
     * Method for calculating points.
     * @param codex The codex on which points must be calculated.
     * @param point The points of the card.
     * @param parameters Parameters used to calculate the points.
     * @return The calculated points.
     */
    // The method does not implement the case where a set has some equal values and some different values.
    // Not required by Objective cards in this version of the game.
    @Override
    public int calculateScore(Codex codex, int point, ArrayList<Value> parameters) {
        int index;
        int minimum = 0;
        int pointCalculated;
        int multiplier;
        boolean equalValue = false;
        int counterDifferentValue = 0;
        for (int i = 0; i < parameters.size(); i++) {
            if (i == 0) {
                index = calculateIndex(parameters.get(i));
                minimum = codex.getCounterCodex()[index];
            } else {
                index = calculateIndex(parameters.get(i));
                minimum = Math.min(minimum, codex.getCounterCodex()[index]);
                if (parameters.get(i) != parameters.get(i - 1))
                    counterDifferentValue++;
            }
        }
        if (counterDifferentValue == 0)
            equalValue = true;
        // All values in the "parameters" array are the same
        if (equalValue) {
            multiplier = minimum / parameters.size();
            pointCalculated = multiplier * point;
        // All values in the "parameters" array are different
        } else {
            pointCalculated = minimum * point;
        }
        return pointCalculated;
    }


    /**
     * Method for calculating the index of a value Value.
     * @param value The value of which you want to obtain the index.
     * @return The value index.
     */
    private int calculateIndex(Value value) {
        switch (value) {
            case Value.FUNGI:
                return 0;
            case Value.PLANT:
                return 1;
            case Value.ANIMAL:
                return 2;
            case Value.INSECT:
                return 3;
            case Value.QUILL:
                return 4;
            case Value.INKWELL:
                return 5;
            case Value.MANUSCRIPT:
                return 6;
            case Value.COVERED:
                return 7;
            case Value.EMPTY:
            case Value.NULL:
            default:
                return -1;
        }
    }


}
