package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.back.BackResource;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for CardResource Methods (Setter and Getter)
 */
class CardResourceTest {

    private CardResource cardResource;

    private FrontResource front;

    private BackResource back;

    private ArrayList<Value> center;


    @BeforeEach
    void setUp() {
        center = new ArrayList<>();
        center.add(Value.FUNGI);
        front = new FrontResource(Kingdom.FUNGI, Value.FUNGI, Value.EMPTY, Value.EMPTY, Value.FUNGI,0);
        back = new BackResource(Kingdom.FUNGI, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY,center);
        cardResource = new CardResource("idCard", Kingdom.FUNGI,front,back);
    }

    @AfterEach
    void tearDown() {
       center = null;
        front = null;
        back = null;
        cardResource = null;
    }

    @Test
    void getKingdom() {
        assertEquals(Kingdom.FUNGI,cardResource.getKingdom());
    }

    @Test
    void setKingdom() {
        Kingdom newKingdom = Kingdom.ANIMAL;
        cardResource.setKingdom(newKingdom);
        assertEquals(newKingdom,cardResource.getKingdom());

    }

    @Test
    void getFrontResource() {
        assertEquals(front,cardResource.getFrontResource());
    }

    @Test
    void setFrontResource() {
        FrontResource newFront = new FrontResource(Kingdom.PLANT, Value.ANIMAL, Value.ANIMAL, Value.PLANT, Value.EMPTY,1);
        FrontResource oldFront = cardResource.getFrontResource();
        cardResource.setFrontResource(newFront);
        assertEquals(newFront,cardResource.getFrontResource());
        assertNotEquals(oldFront,cardResource.getFrontResource());
    }

    @Test
    void getBackResource() {
        assertEquals(back,cardResource.getBackResource());
    }

    @Test
    void setBackResource() {
        BackResource newBack = new BackResource(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY,center);
        BackResource oldBack = cardResource.getBackResource();
        cardResource.setBackResource(newBack);
        assertEquals(newBack,cardResource.getBackResource());
        assertNotEquals(oldBack,cardResource.getBackResource());
    }
}