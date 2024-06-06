package it.polimi.ingsw.gc03.model.card.cardObjective;

import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class RequirementStrategyTest {
    private Codex codex;

    CardObjective cardObjective;

    private Game game;
    @BeforeEach
    void setup() throws RemoteException {
        this.codex = new Codex();
        CalculateScoreStrategy scoreStrategy = new RequirementStrategy();
        this.cardObjective = new CardObjective("OBJ095",
                "2 points for each set consisting of 3 fungi resources visible in the playing area.",
                2,
                new ArrayList<Value>() {
                    {add(Value.FUNGI);
                        {add(Value.FUNGI);
                            {add(Value.FUNGI);}
                        }}},
                scoreStrategy
        );
        game = new Game(445445);
    }

    @Test
    @DisplayName("A single triplet with the requirements")
    void singleTriplet() throws RemoteException {
        Side side1 = new Side(Kingdom.FUNGI, Value.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side side2 = new Side(Kingdom.FUNGI, Value.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side side3 = new Side(Kingdom.FUNGI, Value.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY);



        codex.insertStarterIntoCodex(side1,game,"TestName");
        codex.insertIntoCodex(game,side2, 41, 41);
        codex.insertIntoCodex(game,side3, 42,42);

        codex.setPointCodex(cardObjective.calculateScore(codex,
                2,
                new ArrayList<Value>() {
                    {add(Value.FUNGI);
                        {add(Value.FUNGI);
                            {add(Value.FUNGI);
                            }}}}));
        assertEquals(2, codex.getPointCodex());
    }

    @Test
    @DisplayName("Two triplets with the requirements")
    void twoTriplets() throws RemoteException {
        Side side1 = new Side(Kingdom.FUNGI, Value.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side side2 = new Side(Kingdom.FUNGI, Value.FUNGI, Value.FUNGI, Value.EMPTY, Value.EMPTY);
        Side side3 = new Side(Kingdom.FUNGI, Value.FUNGI, Value.FUNGI, Value.FUNGI, Value.EMPTY);

        codex.insertStarterIntoCodex(side1,game,"TestName");

        codex.insertIntoCodex(game,side2, 41,41);
        codex.insertIntoCodex(game,side3, 42,42);


        codex.setPointCodex(cardObjective.calculateScore(codex,
                2,
                new ArrayList<Value>() {
                    {add(Value.FUNGI);
                        {add(Value.FUNGI);
                            {add(Value.FUNGI);
                            }}}}));
        assertEquals(4, codex.getPointCodex());
    }

}