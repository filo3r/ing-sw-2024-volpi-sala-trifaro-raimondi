package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.back.BackStarter;
import it.polimi.ingsw.gc03.model.side.front.FrontStarter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Test for CardStarter Methods (Setter and Getter)
 */
class CardStarterTest {

    private CardStarter cardStarter;

    private FrontStarter front;

    private BackStarter back;

    private ArrayList<Value> center;
    @BeforeEach
    void setUp() {
        center = new ArrayList<>();
        center.add(Value.FUNGI);
        center.add(Value.PLANT);
        front = new FrontStarter(Kingdom.NULL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY);
        back = new BackStarter(Kingdom.NULL, Value.INSECT, Value.ANIMAL, Value.PLANT, Value.FUNGI,center);
        cardStarter = new CardStarter("idCard",front,back);

    }

    @AfterEach
    void tearDown() {
        center = null;
        front = null;
        back = null;
        cardStarter = null;
    }

    @Test
    void getFrontStarter() {
        assertEquals(front,cardStarter.getFrontStarter());
    }

    @Test
    void setFrontStarter() {
        FrontStarter newFront = new FrontStarter(Kingdom.NULL,Value.EMPTY,Value.FUNGI,Value.PLANT,Value.ANIMAL);
        FrontStarter oldFront = cardStarter.getFrontStarter();
        cardStarter.setFrontStarter(newFront);
        assertEquals(newFront,cardStarter.getFrontStarter());
        assertNotEquals(oldFront,cardStarter.getFrontStarter());
    }

    @Test
    void getBackStarter() {
        assertEquals(back,cardStarter.getBackStarter());
    }

    @Test
    void setBackStarter() {
        BackStarter newBack = new BackStarter(Kingdom.NULL,Value.EMPTY,Value.FUNGI,Value.PLANT,Value.ANIMAL,center);
        BackStarter oldBack = cardStarter.getBackStarter();
        cardStarter.setBackStarter(newBack);
        assertEquals(newBack,cardStarter.getBackStarter());
        assertNotEquals(oldBack,cardStarter.getBackStarter());

    }
}