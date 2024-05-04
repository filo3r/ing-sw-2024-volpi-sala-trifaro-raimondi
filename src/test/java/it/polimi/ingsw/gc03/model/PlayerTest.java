package it.polimi.ingsw.gc03.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player player;

    private Desk desk;



    @BeforeEach
    void setUp() throws RemoteException {
        desk = new Desk();
        player= new Player("Testname",1,desk);
    }

    @AfterEach
    void tearDown() {
        desk = null;
        player = null;
    }
    /**
     * Check if selectObjectiveCard returns false when the index is over CardObjective size
     */
    @Test
    void selectObjectiveCardFalseOver() {
        int index = 3;
        assertFalse(player.selectObjectiveCard(index));
    }

    /**
     * Check if selectObjectiveCard returns false when the index is under CardObjective size
     */
    @Test
    void selectObjectiveCardFalseUnder() {
        int index = -1;
        assertFalse(player.selectObjectiveCard(index));
    }
    /**
     * Check if selectObjectiveCard returns true when the index is correct
     */
    @Test
    void selectObjectiveCardTrue() {
        int index = 1;
        assertTrue(player.selectObjectiveCard(index));
    }
    //Bisogna trovare combinazione di carte per bloccare il gioco
    @Test
    void checkSkipTurn() {
    }

    @Test
    void calculatePointObjective() {
    }

    /**
     * Check if the score is calculated correctly
     */
    @Test
    void calculatePlayerScore() {
        player.setScore(5);
        player.getCodex().setPointCodex(8);
        player.setPointObjective(7);
        player.calculatePlayerScore();
        assertEquals(player.getScore(),5+8+7);
    }

    @Test
    void getNickname() {
    }

    @Test
    void setNickname() {
    }

    @Test
    void getNumber() {
    }

    @Test
    void setNumber() {
    }

    @Test
    void getColor() {
    }

    @Test
    void setColor() {
    }

    @Test
    void getCardStarter() {
    }

    @Test
    void setCardStarter() {
    }

    @Test
    void getCardObjective() {
    }

    @Test
    void setCardObjective() {
    }

    @Test
    void getHand() {
    }

    @Test
    void setHand() {
    }

    @Test
    void getCodex() {
    }

    @Test
    void setCodex() {
    }

    @Test
    void getPointObjective() {
    }

    @Test
    void setPointObjective() {
    }

    @Test
    void getScore() {
    }

    @Test
    void setScore() {
    }

    @Test
    void getOnline() {
    }

    @Test
    void setOnline() {
    }

    @Test
    void getSkipTurn() {
    }

    @Test
    void setSkipTurn() {
    }

    @Test
    void getAction() {
    }

    @Test
    void setAction() {
    }

    @Test
    void addCardToHand() {
    }

    @Test
    void removeCardFromHand() {
    }
}