package it.polimi.ingsw.gc03.model.side.front;

import it.polimi.ingsw.gc03.model.Desk;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class FrontGoldTest {

    private Desk desk;

    private FrontGold frontGold;


    @BeforeEach
    void setUp() throws RemoteException {
        desk = new Desk(new Game(15444949));
        Card card= desk.drawCardDeck(desk.getDeckGold());
        CardGold cardGold = (CardGold) card;
        frontGold = cardGold.getFrontGold();
    }

    @AfterEach
    void tearDown() {
        desk = null;

    }

    @Test
    void getPoint() {
        int point = frontGold.getPoint();
        assertEquals(point,frontGold.getPoint());
    }

    @Test
    void setPoint() {
        int point = 4;
        int oldPoint = frontGold.getPoint();
        frontGold.setPoint(point);
        assertEquals(point,frontGold.getPoint());
        assertNotEquals(oldPoint,frontGold.getPoint());

    }

    @Test
    void getRequirementPoint() {
    }

    @Test
    void setRequirementPoint() {
    }

    @Test
    void getRequirementPlacement() {
    }

    @Test
    void setRequirementPlacement() {
    }
}