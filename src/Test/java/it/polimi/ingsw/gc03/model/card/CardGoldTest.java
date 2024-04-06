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

class CardGoldTest {

    private CardGold cardGold;

    private FrontGold front;

    private BackSide back;

    private ArrayList<Value> list,list1;

    @BeforeEach
    void setUp() {
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list1.add(Value.FUNGI);
        list.add(Value.FUNGI);
        list.add(Value.FUNGI);
        list.add(Value.FUNGI);
        front = new FrontGold(Kingdom.FUNGI, Value.FUNGI,Value.FUNGI,Value.FUNGI,Value.FUNGI,2,Value.INKWELL,list);
        back = new BackSide(Kingdom.FUNGI,Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY,list1);
        cardGold = new CardGold("idCard", Kingdom.FUNGI,front,back);
    }

    @AfterEach
    void tearDown() {
        cardGold = null;
        front = null;
        back = null;
        list1 = null;
        list = null;
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
        FrontGold frontGold =cardGold.getFrontGold();
        FrontGold newFront = new FrontGold(Kingdom.FUNGI,Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY,0,Value.NULL,list);
        cardGold.setFrontGold(newFront);
        assertEquals(newFront,cardGold.getFrontGold());
        assertNotEquals(newFront,frontGold);
    }

    @Test
    void getBackGold() {
        assertEquals(back,cardGold.getBackGold());
    }

    @Test
    void setBackGold() {
        BackSide backSide = cardGold.getBackGold();
        BackSide newBack =  new BackSide(Kingdom.FUNGI,Value.PLANT,Value.EMPTY,Value.EMPTY,Value.EMPTY,list);
        cardGold.setBackGold(newBack);
        assertEquals(newBack,cardGold.getBackGold());
        assertNotEquals(backSide,newBack);
    }

}