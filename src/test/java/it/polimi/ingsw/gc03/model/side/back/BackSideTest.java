package it.polimi.ingsw.gc03.model.side.back;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BackSideTest {

    private BackSide backSide;

    private ArrayList<Value> center;
    @BeforeEach
    void setUp() {
        center = new ArrayList<>();
        center.add(Value.PLANT);
        center.add(Value.PLANT);
        backSide = new BackSide(Kingdom.FUNGI, Value.EMPTY,Value.EMPTY,Value.EMPTY,Value.EMPTY,center);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCenter() {
        assertEquals(backSide.getCenter(),center);
    }

    @Test
    void setCenter() {
    }
}