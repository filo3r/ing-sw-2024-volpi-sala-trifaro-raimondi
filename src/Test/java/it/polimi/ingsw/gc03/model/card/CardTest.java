package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tester for Card Class (Getter and Setter).
 */
class CardTest {

    private Card card;
    @BeforeEach
    void setUp() {
         card = new Card( "idCard",true);
    }

    @AfterEach
    void tearDown() {
        card = null;
    }

    @Test
    void getIdCard() {
        assertEquals("idCard",card.getIdCard());
    }

    @Test
    void setIdCard() {
        String idCard ="NewId";
        card.setIdCard(idCard);
        assertEquals(idCard,card.getIdCard());
    }

    @Test
    void isPlayable() {
        assertEquals(true,card.isPlayable());
    }

    @Test
    void setPlayable() {
        boolean playable =false;
        card.setPlayable(playable);
        assertEquals(playable,card.isPlayable());
    }
}