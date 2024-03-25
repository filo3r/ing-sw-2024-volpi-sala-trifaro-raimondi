package it.polimi.ingsw.gc03.Card.CardObjective;

import it.polimi.ingsw.gc03.Codex;
import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Enumerations.Value;
import it.polimi.ingsw.gc03.Side.Side;
import java.util.ArrayList;


/**
 * This class calculates the points of Objective cards that use the secondary diagonal.
 */
public class SecondaryDiagonalStrategy implements CalculateScoreStrategy {

    /**
     * Method for calculating points.
     * @param codex The codex on which points must be calculated.
     * @param point The points of the card.
     * @param parameters Parameters used to calculate the points.
     * @return The calculated points.
     */
    @Override
    public int calculateScore(Codex codex, int point, ArrayList<Value> parameters) {
        // Convert Value to Kingdom
        ArrayList<Kingdom> diagonalCard = new ArrayList<>();
        for (Value parameter : parameters) {
            Kingdom card = Kingdom.fromValue(parameter);
            diagonalCard.add(card);
        }
        // Calculation on the codex
        int pointCalculated = 0;
        int counterKingdom = 0;
        int[][] counterCard = new int[81][81];
        for (int i = codex.getMaxRow(); i >= codex.getMinRow() + (diagonalCard.size() - 1); i--) {
            for (int j = codex.getMinColumn(); j <= codex.getMaxColumn() - (diagonalCard.size() - 1); j++) {
                if (codex.getCodex()[i][j] != null) {
                    for (int k = 0; k < diagonalCard.size(); k++) {
                        if (codex.getCodex()[i - k][j + k] != null) {
                            Side side = codex.getCodex()[i - k][j + k];
                            if (side.getKingdom() == diagonalCard.get(k) && counterCard[i - k][j + k] == 0) {
                                counterKingdom++;
                            }
                        }
                    }
                    if (counterKingdom == diagonalCard.size()) {
                        pointCalculated = pointCalculated + point;
                        for (int k = 0; k < diagonalCard.size(); k++) {
                            counterCard[i - k][j + k]++;
                        }
                    }
                }
                counterKingdom = 0;
            }
        }
        return pointCalculated;
    }


}
