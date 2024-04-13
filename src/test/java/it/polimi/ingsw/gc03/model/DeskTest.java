package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.card.objective.CardObjective;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DeskTest {

    private Desk desk;


    @BeforeEach
    void setUp() {
        desk = new Desk();
    }

    @AfterEach
    void tearDown() {
        desk = null;
    }

    /**
     * Check if drawCard get the right card from ResourceDeck and if it removes the card from the ResourceDeck
     */
    @Test
    void drawCardDeckResource() {
        Card firstCard = desk.getDeckResource().getFirst();
        Card cardResource = desk.drawCardDeck(desk.getDeckResource());
        assertEquals(cardResource,firstCard);
        assertFalse(desk.getDeckResource().contains(cardResource));
    }

    /**
     * Check if drawCard get the right card from GoldDeck and if it removes the card from the GoldDeck
     */
    @Test
    void drawCardDeckGold(){
        Card firstCard = desk.getDeckGold().getFirst();
        Card cardGold = desk.drawCardDeck(desk.getDeckGold());
        assertEquals(cardGold,firstCard);
        assertFalse(desk.getDeckGold().contains(cardGold));
    }

    /**
     * Check if drawCard get the right card from StarterDeck and if it removes the card from the StarterDeck
     */
    @Test
    void drawCardDeckStarter(){
        Card firstCard = desk.getDeckStarter().getFirst();
        Card cardStarter = desk.drawCardDeck(desk.getDeckStarter());
        assertEquals(cardStarter,firstCard);
        assertFalse(desk.getDeckStarter().contains(cardStarter));
    }

    /**
     * Check if drawCard get the right card from ObjectiveDeck and if it removes the card from the ObjectiveDeck
     */
    @Test
    void drawCardDeckObjective(){
        Card firstCard = desk.getDeckObjective().getFirst();
        Card cardObjective = desk.drawCardDeck(desk.getDeckObjective());
        assertEquals(cardObjective,firstCard);
        assertFalse(desk.getDeckObjective().contains(cardObjective));
    }

    /**
     * Check what happens when the deck is empty
     */

    @Test
    void drawCardDeckEmpty(){
        Card card;
        //Empty the Deck;
        while(!desk.getDeckGold().isEmpty()){
            desk.drawCardDeck(desk.getDeckGold());
        }
        card = desk.drawCardDeck(desk.getDeckGold());
        assertEquals(null,card);
    }

    /**
     * Check if drawCardDisplayed returns the correct GoldCard
     */
    @Test
    void drawCardDisplayedGold() {
        int i = 1;
        Card displayedCard = desk.getDisplayedGold().get(i);
        Card card = desk.drawCardDisplayed(desk.getDisplayedGold(),i);
        assertEquals(card,displayedCard);
    }

    /**
     * Check if drawCardDisplayed returns the correct ResourceCard
     */
    @Test
    void drawCardDisplayedResource() {
        int i = 1;
        Card displayedCard = desk.getDisplayedResource().get(i);
        Card card = desk.drawCardDisplayed(desk.getDisplayedResource(),i);
        assertEquals(card,displayedCard);
    }

    /**
     * Check if drawCardDisplayed returns the correct GoldCard;
     */
    @Test
    void drawCardDisplayedGoldEmpty(){
        Card card;
        int i=1;
        while(!desk.getDisplayedGold().isEmpty()){
            card = desk.drawCardDisplayed(desk.getDisplayedGold(),i);
        }
        card = desk.drawCardDisplayed(desk.getDisplayedGold(),i);
        assertEquals(null,card);
    }

    /**
     * Check if drawCardDisplayed return null if displayedResource is Empty
     */
    @Test
    void drawCardDisplayedResourceEmpty(){
        Card card;
        int i=1;
        while(!desk.getDisplayedResource().isEmpty()){
            card = desk.drawCardDisplayed(desk.getDisplayedResource(),i);
        }
        card = desk.drawCardDisplayed(desk.getDisplayedResource(),i);
        assertEquals(null,card);
    }

    /**
     * Check if drawCardDisplayed return null if index is higher than displayed.size()
     */
    @Test
    void drawCardDisplayedWrongIndexOver(){
        int i=3;
        Card card = desk.drawCardDisplayed(desk.getDisplayedGold(),i);
        assertEquals(card,null);
    }

    /**
     * Check if drawCardDisplayed return null if index is lower than displayed.size()
     */
    @Test
    void drawCardDisplayedWrongIndexUnder(){
        int i=-1;
        Card card = desk.drawCardDisplayed(desk.getDisplayedGold(),i);
        assertEquals(card,null);
    }

    @Test
    void checkDisplayedGoldIfDeckGoldIsEmpty() {
    }
    @Test
    void checkDisplayedResourceIfDeckResourceIsEmpty() {
    }
    @Test
    void checkDisplayedGoldIfDeckGoldIsNotEmpty() {
    }
    @Test
    void checkDisplayedResourceIfDeckResourceIsNotEmpty() {
    }

    @Test
    void getDeckStarter() {
    }

    @Test
    void setDeckStarter() {
        Desk desk1 = new Desk();
        ArrayList<CardStarter> deckStarter = desk.getDeckStarter();
        desk.setDeckStarter(desk1.getDeckStarter());
        assertEquals(desk.getDeckStarter(),desk1.getDeckStarter());
        assertNotEquals(deckStarter,desk.getDeckStarter());
    }

    @Test
    void getDeckResource() {
    }

    @Test
    void setDeckResource() {
        Desk desk1 = new Desk();
        ArrayList<CardResource> deckResource = desk.getDeckResource();
        desk.setDeckResource(desk1.getDeckResource());
        assertEquals(desk.getDeckResource(),desk1.getDeckResource());
        assertNotEquals(deckResource,desk.getDeckResource());
    }

    @Test
    void getDeckGold() {
    }

    @Test
    void setDeckGold() {
        Desk desk1 = new Desk();
        ArrayList<CardGold> deckGold = desk.getDeckGold();
        desk.setDeckGold(desk1.getDeckGold());
        assertEquals(desk.getDeckGold(),desk1.getDeckGold());
        assertNotEquals(deckGold,desk.getDeckGold());
    }

    @Test
    void getDeckObjective() {
    }

    @Test
    void setDeckObjective() {
        Desk desk1 = new Desk();
        ArrayList<CardObjective> deckObjective = desk.getDeckObjective();
        desk.setDeckObjective(desk1.getDeckObjective());
        assertEquals(desk.getDeckObjective(),desk1.getDeckObjective());
        assertNotEquals(deckObjective,desk.getDeckObjective());
    }

    @Test
    void getDisplayedResource() {
    }

    @Test
    void setDisplayedResource() {
        Desk desk1 = new Desk();
        ArrayList<Card> displayedResource= desk.getDisplayedResource();
        desk.setDisplayedResource(desk1.getDisplayedResource());
        assertEquals(desk.getDisplayedResource(),desk1.getDisplayedResource());
        assertNotEquals(displayedResource,desk.getDisplayedResource());
    }

    @Test
    void getDisplayedGold() {
    }

    @Test
    void setDisplayedGold() {
        Desk desk1 = new Desk();
        ArrayList<Card> displayedGold = desk.getDisplayedGold();
        desk.setDisplayedGold(desk1.getDisplayedGold());
        assertEquals(desk.getDisplayedGold(),desk1.getDisplayedGold());
        assertNotEquals(displayedGold,desk.getDisplayedGold());
    }

    @Test
    void getDisplayedObjective() {
    }

    @Test
    void setDisplayedObjective() {
        Desk desk1 = new Desk();
        ArrayList<CardObjective> displayedObjective = desk.getDisplayedObjective();
        desk.setDisplayedObjective(desk1.getDisplayedObjective());
        assertEquals(desk.getDisplayedObjective(),desk1.getDisplayedObjective());
        assertNotEquals(displayedObjective,desk.getDisplayedObjective());
    }
}