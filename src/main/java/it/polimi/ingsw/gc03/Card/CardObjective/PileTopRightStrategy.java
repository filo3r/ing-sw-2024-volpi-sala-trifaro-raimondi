package it.polimi.ingsw.gc03.Card.CardObjective;

import it.polimi.ingsw.gc03.Codex;
import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Enumerations.Value;
import it.polimi.ingsw.gc03.Side.Side;
import java.util.ArrayList;


/**
 * This class calculates the points of Objective cards that use the pile starting from the top right corner of a
 * card.
 */
public class PileTopRightStrategy implements CalculateScoreStrategy {

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
        ArrayList<Kingdom> pileCard = new ArrayList<>();
        for (Value parameter : parameters) {
            Kingdom card = Kingdom.fromValue(parameter);
            pileCard.add(card);
        }
        // Calculation on the codex
        int pointCalculated = 0;
        int counterKingdom = 0;
        int[][] counterCard = new int[81][81];
        for (int i = codex.getMaxRow(); i >= codex.getMinRow() + (((pileCard.size() - 1) * 2) - 1); i--) {
            for (int j = codex.getMinColumn(); j < codex.getMaxColumn(); j++) {
                if (codex.getCodex()[i][j] != null) {
                    Side side = codex.getCodex()[i][j];
                    if (side.getKingdom() == pileCard.getFirst() && counterCard[i][j] == 0) {
                        counterKingdom++;
                        for (int k = 0; k < pileCard.size() - 1; k++) {
                            if (codex.getCodex()[(i - 1) - (k * 2)][j + 1] != null) {
                                side = codex.getCodex()[(i - 1) - (k * 2)][j + 1];
                                if (side.getKingdom() == pileCard.get(k + 1) && counterCard[(i - 1) - (k * 2)][j + 1] == 0) {
                                    counterKingdom++;
                                }
                            }
                        }
                        if (counterKingdom == pileCard.size()) {
                            pointCalculated = pointCalculated + point;
                            counterCard[i][j]++;
                            for (int k = 0; k < pileCard.size() - 1; k++) {
                                counterCard[(i - 1) - (k * 2)][j + 1]++;
                            }
                        }
                    }
                }
                counterKingdom = 0;
            }
        }
        return pointCalculated;
    }


}
