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
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;


class CodexTest {
    private Codex codex;
    private Side side=new Side(
            Kingdom.NULL,
            Value.FUNGI,
            Value.INSECT,
            Value.PLANT,
            Value.EMPTY);

    private Game game;
    @BeforeEach
    void setUp() throws RemoteException {
        codex= new Codex();
        game = new Game(15484);
    }

    @AfterEach
    void tearDown() {
        codex=null;
        game = null;
    }
    /**
     * Check if insertStarterIntoCodex inserts correctly the starter card into the codex
     */
    @Test
    void insertStarterIntoCodex() throws RemoteException {
        codex.insertStarterIntoCodex(side,game,"TestName");
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
    void insertIntoCodex() throws RemoteException {
        codex.insertStarterIntoCodex(side,game,"TestName");
        assertFalse(codex.insertIntoCodex(game,side, -1, 0));
        assertFalse(codex.insertIntoCodex(game,side, 0, -1));
        assertFalse(codex.insertIntoCodex(game,side, 81, 0));
        assertFalse(codex.insertIntoCodex(game,side, 0, 81));
        assertFalse(codex.insertIntoCodex(game,side, 40, 40));
        assertFalse(codex.insertIntoCodex(game,side, 1, 1));
        assertFalse(codex.insertIntoCodex(game,side, 2, 2));
        assertFalse(codex.insertIntoCodex(game,side, 40, 40));
        assertFalse(codex.insertIntoCodex(game,side, 40, 41));
        assertTrue(codex.insertIntoCodex(game,side,41,41));
    }

}

