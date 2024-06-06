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

class PileTopRightStrategyTest {
    private Codex codex;

    CardObjective cardObjective;

    private Game game;

    @BeforeEach
    void setup() throws RemoteException {
        this.codex = new Codex();
        CalculateScoreStrategy scoreStrategy = new PileTopRightStrategy();
        this.cardObjective = new CardObjective("OBJ092",
                "3 points for each pile of cards belonging to the plant kingdom that starts from the top-right corner of a card belonging to the insect kingdom.",
                3,
                new ArrayList<Value>() {
                    {add(Value.INSECT);
                        {add(Value.PLANT);
                            {add(Value.PLANT);}
                        }}},
                scoreStrategy
        );
        game = new Game(464161);
    }

    @Test
    @DisplayName("A single correct pile")
    void singlePile() throws RemoteException {
        Side topSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side helperSide = new Side(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        codex.insertStarterIntoCodex(midSide,game,"TestName");
        codex.insertIntoCodex(game,helperSide, 39, 41);
        codex.insertIntoCodex(game,topSide, 38,40);
        codex.insertIntoCodex(game,bottomSide, 41,39);

        codex.setPointCodex(cardObjective.calculateScore(codex,
                3,
                new ArrayList<Value>() {
                    {add(Value.INSECT);
                        {add(Value.PLANT);
                            {add(Value.PLANT);
                            }}}}));
        assertEquals(3, codex.getPointCodex());
    }

    @Test
    @DisplayName("Two correct piles")
    void twoPiles() throws RemoteException {
        Side topSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        Side topSide2 = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side midSide2 = new Side(Kingdom.PLANT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        Side bottomSide2 = new Side(Kingdom.INSECT, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);

        codex.insertStarterIntoCodex(midSide,game,"TestName");
        codex.insertIntoCodex(game,topSide2, 39,41);
        codex.insertIntoCodex(game,topSide, 38,40);
        codex.insertIntoCodex(game,midSide2, 41,41);
        codex.insertIntoCodex(game,bottomSide, 41,39);
        codex.insertIntoCodex(game,bottomSide2, 42,40);



        codex.setPointCodex(cardObjective.calculateScore(codex,
                3,
                new ArrayList<Value>() {
                    {add(Value.INSECT);
                        {add(Value.PLANT);
                            {add(Value.PLANT);
                            }}}}));
        assertEquals(6, codex.getPointCodex());
    }


}