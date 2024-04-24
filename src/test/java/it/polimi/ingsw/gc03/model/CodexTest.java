package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;


class CodexTest {
    private Codex codex;
    private Side side=new Side(
            Kingdom.NULL,
            Value.FUNGI,
            Value.INSECT,
            Value.PLANT,
            Value.EMPTY);




    @BeforeEach
    void setUp() {
        codex= new Codex();
    }

    @AfterEach
    void tearDown() {
        codex=null;
    }
    /**
     * Check if insertStarterIntoCodex inserts correctly the starter card into the codex
     */
    @Test
    void insertStarterIntoCodex() {
        codex.insertStarterIntoCodex(side);
        assertEquals(side, codex.getCodex()[40][40]);
        assertTrue(codex.getCardStarterInserted());
        assertEquals(40, codex.getMinRow());
        assertEquals(40, codex.getMaxRow());
        assertEquals(40, codex.getMinColumn());
        assertEquals(40, codex.getMaxColumn());
    }

    /**
     * Check if insertIntoCodex inserts correctly a side of the card into the codex
     */
    @Test
    void insertIntoCodex() {
        assertFalse(codex.insertIntoCodex(side, -1, 0));
        assertFalse(codex.insertIntoCodex(side, 0, -1));
        assertFalse(codex.insertIntoCodex(side, 81, 0));
        assertFalse(codex.insertIntoCodex(side, 0, 81));
        assertFalse(codex.insertIntoCodex(side, 40, 40));
        assertFalse(codex.insertIntoCodex(side, 1, 1));
        assertFalse(codex.insertIntoCodex(side, 2, 2));
        codex.insertStarterIntoCodex(side);
        assertFalse(codex.insertIntoCodex(side, 40, 40));
        assertTrue(codex.insertIntoCodex(side, 40, 41));


    }
/**
 * Check if it simulates the insertion of a card into the Codex.*/
    @Test
    void simulateInsertIntoCodex(){
        assertThrows(Exception.class, () -> codex.simulateInsertIntoCodex(side, 0, 0));
        assertThrows(Exception.class, () -> codex.simulateInsertIntoCodex(side, 1, 1));
        assertThrows(Exception.class, () -> codex.simulateInsertIntoCodex(side, 40, 40));
        assertThrows(Exception.class, () -> codex.simulateInsertIntoCodex(side, 40, 41));
    }

    @Test
    void getCodex() {
    }

    @Test
    void setCodex() {
    }

    @Test
    void getCounterCodex() {
    }

    @Test
    void setCounterCodex() {
    }

    @Test
    void getPointCodex() {
    }

    @Test
    void setPointCodex() {
    }

    @Test
    void getMinRow() {
    }

    @Test
    void setMinRow() {
    }

    @Test
    void getMaxRow() {
    }

    @Test
    void setMaxRow() {
    }

    @Test
    void getMinColumn() {
    }

    @Test
    void setMinColumn() {
    }

    @Test
    void getMaxColumn() {
    }

    @Test
    void setMaxColumn() {
    }

    @Test
    void getCardStarterInserted() {
    }

    @Test
    void setCardStarterInserted() {
    }

    /** Check if the codex table is correctly printed*/
    @Test
    void printCodexTable() {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));


        codex.printCodexTable();


        String printedOutput = outputStream.toString();

        assertTrue(printedOutput.contains("CODEX:"));

        assertTrue(printedOutput.contains("  -3   -2   -1    0    1    2    3"));

        assertTrue(printedOutput.contains("+-----+-----+-----+-----+-----+-----+-----+"));

        assertTrue(printedOutput.contains("|     |     |     |     |     |     |     |"));

        assertTrue(printedOutput.contains("|  C  |  C  |  C  |  C  |  C  |  C  |  C  |"));

        System.setOut(System.out);
    }


/** Check if the codex card is correctly printed*/
    @Test
    void printCodexCard() {
        Codex codex = new Codex();
        codex.insertStarterIntoCodex(new Side(Kingdom.NULL,
                Value.FUNGI,
                Value.INSECT,
                Value.PLANT,
                Value.EMPTY));


        PrintStream originalOut = System.out;

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream printStream = new PrintStream(outputStream);

            System.setOut(printStream);

            codex.printCodexCard();

            String printedOutput = outputStream.toString();

            assertTrue(printedOutput.contains("CARD IN COORDINATES:"));
            assertTrue(printedOutput.contains("Kingdom:"));
            assertTrue(printedOutput.contains("Top Left Corner:"));
            assertTrue(printedOutput.contains("Bottom Left Corner:"));
            assertTrue(printedOutput.contains("Top Right Corner:"));
            assertTrue(printedOutput.contains("Bottom Right Corner:"));
            assertTrue(printedOutput.contains("Center:"));

        } finally {

            System.setOut(originalOut);
        }
    }}

