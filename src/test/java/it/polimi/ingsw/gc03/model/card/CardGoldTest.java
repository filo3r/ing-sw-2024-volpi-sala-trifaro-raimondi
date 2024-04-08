package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for CardGold Methods (Setter and Getter)
 */

class CardGoldTest {

    private CardGold cardGold;

    private FrontGold front;

    private BackSide back;

    private ArrayList<Value> center,center1;

    @BeforeEach
    void setUp() {
        center = new ArrayList<>();
        center1 = new ArrayList<>();
        center1.add(Value.FUNGI);
        center.add(Value.FUNGI);
        center.add(Value.FUNGI);
        center.add(Value.FUNGI);
        front = new FrontGold(Kingdom.FUNGI, Value.FUNGI,Value.FUNGI,Value.FUNGI,Value.FUNGI,2,Value.INKWELL,center);
        back = new BackSide(Kingdom.FUNGI,Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY,center1);
        cardGold = new CardGold("idCard", Kingdom.FUNGI,front,back);
    }

    @AfterEach
    void tearDown() {
        cardGold = null;
        front = null;
        back = null;
        center1 = null;
        center = null;
    }

    @Test
    void getKingdom() {
        assertEquals(Kingdom.FUNGI,cardGold.getKingdom());
    }

    @Test
    void setKingdom() {
        Kingdom newKingdom = Kingdom.ANIMAL;
        cardGold.setKingdom(newKingdom);
        assertEquals(newKingdom,cardGold.getKingdom());
    }

    @Test
    void getFrontGold() {
        assertEquals(front,cardGold.getFrontGold());
    }

    @Test
    void setFrontGold() {
        FrontGold oldFront =cardGold.getFrontGold();
        FrontGold newFront = new FrontGold(Kingdom.FUNGI,Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY,0,Value.NULL,center1);
        cardGold.setFrontGold(newFront);
        assertEquals(newFront,cardGold.getFrontGold());
        assertNotEquals(newFront,oldFront);
    }

    @Test
    void getBackGold() {
        assertEquals(back,cardGold.getBackGold());
    }

    @Test
    void setBackGold() {
        BackSide oldBack = cardGold.getBackGold();
        BackSide newBack =  new BackSide(Kingdom.FUNGI,Value.PLANT,Value.EMPTY,Value.EMPTY,Value.EMPTY,center);
        cardGold.setBackGold(newBack);
        assertEquals(newBack,cardGold.getBackGold());
        assertNotEquals(oldBack,newBack);
    }

}