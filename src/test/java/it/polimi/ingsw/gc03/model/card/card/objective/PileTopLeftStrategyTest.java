package it.polimi.ingsw.gc03.model.card.card.objective;

import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
class PileTopLeftStrategyTest {
    private Codex codex;

    CardObjective cardObjective;

    @BeforeEach
    void setup(){
        this.codex = new Codex();
        CalculateScoreStrategy scoreStrategy = new PileTopLeftStrategy();
        this.cardObjective = new CardObjective("OBJ091",
                "3 points for each pile of cards belonging to the fungi kingdom that starts from the top-left corner of a card belonging to the plant kingdom.",
                3,
                new ArrayList<Value>() {
                        {add(Value.PLANT);
                        {add(Value.FUNGI);
                        {add(Value.FUNGI);}
                        }}},
                scoreStrategy
        );
    }

    @Test
    @DisplayName("A single correct pile")
    void singlePile(){
        Side topSide = new Side(Kingdom.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide = new Side(Kingdom.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side helperSide = new Side(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        codex.insertStarterIntoCodex(midSide);
        codex.insertIntoCodex(helperSide, 39, 39);
        codex.insertIntoCodex(topSide, 38,40);
        codex.insertIntoCodex(bottomSide, 41,41);

        codex.setPointCodex(cardObjective.calculateScore(codex,
                3,
                new ArrayList<Value>() {
                        {add(Value.PLANT);
                        {add(Value.FUNGI);
                        {add(Value.FUNGI);
                        }}}}));
        assertEquals(3, codex.getPointCodex());
    }

    @Test
    @DisplayName("Two correct piles")
    void twoPiles(){
        Side topSide = new Side(Kingdom.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide = new Side(Kingdom.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        Side topSide2 = new Side(Kingdom.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide2 = new Side(Kingdom.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide2 = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        codex.insertStarterIntoCodex(midSide);
        codex.insertIntoCodex(topSide2, 39,39);
        codex.insertIntoCodex(topSide, 38,40);
        codex.insertIntoCodex(midSide2, 41,39);
        codex.insertIntoCodex(bottomSide2, 42,40);
        codex.insertIntoCodex(bottomSide, 41,41);



        codex.setPointCodex(cardObjective.calculateScore(codex,
                3,
                new ArrayList<Value>() {
                        {add(Value.PLANT);
                        {add(Value.FUNGI);
                        {add(Value.FUNGI);
                        }}}}));
        assertEquals(6, codex.getPointCodex());
    }

}