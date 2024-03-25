package it.polimi.ingsw.gc03.Card.CardObjective;

import it.polimi.ingsw.gc03.Codex;
import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Enumerations.Value;
import it.polimi.ingsw.gc03.Side.Side;
import java.util.ArrayList;


/**
 * This class calculates the points of Objective cards that use the pile starting from the bottom left corner of a
 * card.
 */
public class PileBottomLeftStrategy implements CalculateScoreStrategy {

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
        for (int i = codex.getMinRow(); i <= codex.getMaxRow() - (((diagonalCard.size() - 1) * 2) - 1); i++) {
            for (int j = codex.getMaxColumn(); j > codex.getMinColumn(); j--) {
                if (codex.getCodex()[i][j] != null) {
                    Side side = codex.getCodex()[i][j];
                    if (side.getKingdom() == diagonalCard.getFirst() && counterCard[i][j] == 0) {
                        counterKingdom++;
                        for (int k = 0; k < diagonalCard.size() - 1; k++) {
                            if (codex.getCodex()[(i + 1) + (k * 2)][j - 1] != null) {
                                side = codex.getCodex()[(i + 1) + (k * 2)][j - 1];
                                if (side.getKingdom() == diagonalCard.get(k + 1) && counterCard[(i + 1) + (k * 2)][j - 1] == 0) {
                                    counterKingdom++;
                                }
                            }
                        }
                        if (counterKingdom == diagonalCard.size()) {
                            pointCalculated = pointCalculated + point;
                            counterCard[i][j]++;
                            for (int k = 0; k < diagonalCard.size() - 1; k++) {
                                counterCard[(i + 1) + (k * 2)][j - 1]++;
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
