package it.polimi.ingsw.gc03.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.card.objective.CalculateScoreStrategy;
import it.polimi.ingsw.gc03.model.card.card.objective.CalculateScoreStrategyAdapter;
import it.polimi.ingsw.gc03.model.card.card.objective.CardObjective;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * This class represents the game board.
 */
public class Desk extends Observable implements Serializable{

    /**
     * Deck of Starter cards.
     */
    private ArrayList<CardStarter> deckStarter;

    /**
     * Deck of Resource cards.
     */
    private ArrayList<CardResource> deckResource;

    /**
     * Deck of Gold cards.
     */
    private ArrayList<CardGold> deckGold;

    /**
     * Deck of Objective cards.
     */
    private ArrayList<CardObjective> deckObjective;

    /**
     * Visible Resource cards.
     */
    private ArrayList<Card> displayedResource;

    /**
     * Visible Gold cards.
     */
    private ArrayList<Card> displayedGold;

    /**
     * Visible Objective cards.
     */
    private ArrayList<CardObjective> displayedObjective;



    /**
     * File with information about Starter cards.
     */
    private static final String FILE_CARD_STARTER = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "polimi"
            + File.separator + "ingsw" + File.separator + "gc03" + File.separator + "json" + File.separator +
            "fileCardStarter.json";

    /**
     * File with information about Resource cards.
     */
    private static final String FILE_CARD_RESOURCE = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "polimi"
            + File.separator + "ingsw" + File.separator + "gc03" + File.separator + "json" + File.separator +
            "fileCardResource.json";

    /**
     * File with information about Gold cards.
     */
    private static final String FILE_CARD_GOLD = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "polimi"
            + File.separator + "ingsw" + File.separator + "gc03" + File.separator + "json" + File.separator +
            "fileCardGold.json";

    /**
     * File with information about Objective cards.
     */
    private static final String FILE_CARD_OBJECTIVE = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "polimi"
            + File.separator + "ingsw" + File.separator + "gc03" + File.separator + "json" + File.separator +
            "fileCardObjective.json";

    /**
     * Number of Starter cards.
     */
    private static final int NUM_CARD_STARTER = 6;

    /**
     * Number of Resource cards.
     */
    private static final int NUM_CARD_RESOURCE = 40;

    /**
     * Number of Gold cards.
     */
    private static final int NUM_CARD_GOLD = 40;

    /**
     * Number of Objective cards.
     */
    private static final int NUM_CARD_OBJECTIVE = 16;

    /**
     * Number of cards displayed.
     */
    private static final int NUM_CARD_DISPLAYED = 2;

    /**
     * Define a logger for the class.
     */
    private static final Logger logger = Logger.getLogger(Desk.class.getName());


    /**
     * Constructor of the Desk class.
     */
    public Desk() {
        // Create decks of cards
        if (!createDeckStarter() || !createDeckResource() || !createDeckGold() || !createDeckObjective())
            System.exit(1);
        // Initialize visible cards
        initializeDisplayedCard();
    }


    /**
     * Method for creating the Starter card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckStarter() {
        this.deckStarter = new ArrayList<>(NUM_CARD_STARTER);
        try {
            // Load the JSON file containing the Starter cards
            InputStream inputStream = new FileInputStream(FILE_CARD_STARTER);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // Use Gson to parse the JSON data
            Gson gson = new Gson();
            Type starterCardType = new TypeToken<ArrayList<CardStarter>>(){}.getType();
            ArrayList<CardStarter> starterCards = gson.fromJson(inputStreamReader, starterCardType);
            this.deckStarter.addAll(starterCards);
            inputStream.close();
            // shuffle the deck
            Collections.shuffle(this.deckStarter);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_CARD_STARTER, e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_CARD_STARTER, e);
        }
        return false;
    }


    /**
     * Method for creating the Resource card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckResource() {
        this.deckResource = new ArrayList<>(NUM_CARD_RESOURCE);
        try {
            // Load the JSON file containing the Resource cards
            InputStream inputStream = new FileInputStream(FILE_CARD_RESOURCE);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // Use Gson to parse the JSON data
            Gson gson = new Gson();
            Type resourceCardType = new TypeToken<ArrayList<CardResource>>(){}.getType();
            ArrayList<CardResource> resourceCards = gson.fromJson(inputStreamReader, resourceCardType);
            this.deckResource.addAll(resourceCards);
            inputStream.close();
            // shuffle the deck
            Collections.shuffle(this.deckResource);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_CARD_RESOURCE, e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_CARD_RESOURCE, e);
        }
        return false;
    }


    /**
     * Method for creating the Gold card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckGold() {
        this.deckGold = new ArrayList<>(NUM_CARD_GOLD);
        try {
            // Load the JSON file containing the Gold cards
            InputStream inputStream = new FileInputStream(FILE_CARD_GOLD);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // Use Gson to parse the JSON data
            Gson gson = new Gson();
            Type goldCardType = new TypeToken<ArrayList<CardGold>>(){}.getType();
            ArrayList<CardGold> goldCards = gson.fromJson(inputStreamReader, goldCardType);
            this.deckGold.addAll(goldCards);
            inputStream.close();
            // shuffle the deck
            Collections.shuffle(this.deckGold);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_CARD_GOLD, e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_CARD_GOLD, e);
        }
        return false;
    }


    /**
     * Method for creating the Objective card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckObjective() {
        this.deckObjective = new ArrayList<>(NUM_CARD_OBJECTIVE);
        try {
            // Load the JSON file containing the Objective cards
            InputStream inputStream = new FileInputStream(FILE_CARD_OBJECTIVE);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            // Create GsonBuilder and register the adapter
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(CalculateScoreStrategy.class, new CalculateScoreStrategyAdapter());
            Gson gson = gsonBuilder.create();
            // Use Gson to parse the JSON data
            Type objectiveCardType = new TypeToken<ArrayList<CardObjective>>(){}.getType();
            ArrayList<CardObjective> objectiveCards = gson.fromJson(inputStreamReader, objectiveCardType);
            this.deckObjective.addAll(objectiveCards);
            inputStream.close();
            // shuffle the deck
            Collections.shuffle(this.deckObjective);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_CARD_OBJECTIVE, e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_CARD_OBJECTIVE, e);
        }
        return false;
    }


    /**
     * Method for initializing visible cards.
     */
    private void initializeDisplayedCard() {
        // Resource cards
        this.displayedResource = new ArrayList<>(NUM_CARD_DISPLAYED);
        for (int i = 0; i < NUM_CARD_DISPLAYED; i++) {
            CardResource cardResource = (CardResource) drawCardDeck(this.deckResource);
            this.displayedResource.add(cardResource);
        }
        // Gold cards
        this.displayedGold = new ArrayList<>(NUM_CARD_DISPLAYED);
        for (int i = 0; i < NUM_CARD_DISPLAYED; i++) {
            CardGold cardGold = (CardGold) drawCardDeck(this.deckGold);
            this.displayedGold.add(cardGold);
        }
        // Objective cards
        this.displayedObjective = new ArrayList<>(NUM_CARD_DISPLAYED);
        for (int i = 0; i < NUM_CARD_DISPLAYED; i++) {
            CardObjective cardObjective = (CardObjective) drawCardDeck(this.deckObjective);
            this.displayedObjective.add(cardObjective);
        }
    }


    /**
     * Method for drawing a card.
     * @param deck The deck from which you want to draw a card.
     * @return The drawn card.
     */
    public Card drawCardDeck(ArrayList<? extends Card> deck) {
        boolean emptyDeck = deck.isEmpty();
        // Empty deck
        if (emptyDeck)
            return null;
        else
            return deck.removeFirst();
    }


    /**
     * Method for drawing a card from those visible.
     * @param displayed The visible cards from which you want to draw.
     * @param index The index of the card you want to take.
     * @return The card taken.
     */
    public Card drawCardDisplayed(ArrayList<? extends Card> displayed, int index) {
        // Invalid index
        if (index < 0 || index >= displayed.size() || displayed.isEmpty()){
            checkDisplayed();
            return null;
        }    // Valid index
        else {
            Card card = displayed.remove(index);
            checkDisplayed();
            return card;
        }
    }


    /**
     * Method for checking that the visible cards are always in the correct number.
     */
    private void checkDisplayed() {
        // Resource cards: Resource deck not empty
        while (this.displayedResource.size() < NUM_CARD_DISPLAYED && !this.deckResource.isEmpty()) {
            CardResource cardResource = (CardResource) drawCardDeck(this.deckResource);
            this.displayedResource.add(cardResource);
        }
        // Resource cards: Resource deck empty
        while (this.displayedResource.size() < NUM_CARD_DISPLAYED && this.deckResource.isEmpty() && !this.deckGold.isEmpty()) {
            CardGold cardGold = (CardGold) drawCardDeck(this.deckGold);
            this.displayedResource.add(cardGold);
        }
        // Gold cards: Gold deck not empty
        while (this.displayedGold.size() < NUM_CARD_DISPLAYED && !this.deckGold.isEmpty()) {
            CardGold cardGold = (CardGold) drawCardDeck(this.deckGold);
            this.displayedGold.add(cardGold);
        }
        // Gold cards: Gold deck empty
        while (this.displayedGold.size() < NUM_CARD_DISPLAYED && this.deckGold.isEmpty() && !this.deckResource.isEmpty()) {
            CardResource cardResource = (CardResource) drawCardDeck(this.deckResource);
            this.displayedGold.add(cardResource);
        }
    }


    /**
     * Method for obtaining the Starter card deck.
     * @return The Starter card deck.
     */
    public ArrayList<CardStarter> getDeckStarter() {
        return deckStarter;
    }


    /**
     * Method for setting the Starter card deck.
     * @param deckStarter The Starter card deck.
     */
    public void setDeckStarter(ArrayList<CardStarter> deckStarter) {
        this.deckStarter = deckStarter;
    }


    /**
     * Method for obtaining the Resource card deck.
     * @return The Resource card deck.
     */
    public ArrayList<CardResource> getDeckResource() {
        return deckResource;
    }


    /**
     * Method for setting the Resource card deck.
     * @param deckResource The Resource card deck.
     */
    public void setDeckResource(ArrayList<CardResource> deckResource) {
        this.deckResource = deckResource;
    }


    /**
     * Method for obtaining the Gold card deck.
     * @return The Gold card deck.
     */
    public ArrayList<CardGold> getDeckGold() {
        return deckGold;
    }


    /**
     * Method for setting the Gold card deck.
     * @param deckGold The Gold card deck.
     */
    public void setDeckGold(ArrayList<CardGold> deckGold) {
        this.deckGold = deckGold;
    }


    /**
     * Method for obtaining the Objective card deck.
     * @return The Objective card deck.
     */
    public ArrayList<CardObjective> getDeckObjective() {
        return deckObjective;
    }


    /**
     * Method for setting the Objective card deck.
     * @param deckObjective The Objective card deck.
     */
    public void setDeckObjective(ArrayList<CardObjective> deckObjective) {
        this.deckObjective = deckObjective;
    }


    /**
     * Method for obtaining the displayed Resource cards.
     * @return The displayed Resource cards.
     */
    public ArrayList<Card> getDisplayedResource() {
        return displayedResource;
    }


    /**
     * Method for setting the displayed Resource cards.
     * @param displayedResource The displayed Resource cards.
     */
    public void setDisplayedResource(ArrayList<Card> displayedResource) {
        this.displayedResource = displayedResource;
    }


    /**
     * Method for obtaining the displayed Gold cards.
     * @return The Gold cards.
     */
    public ArrayList<Card> getDisplayedGold() {
        return displayedGold;
    }


    /**
     * Method for setting the displayed Gold cards.
     * @param displayedGold The displayed Gold cards.
     */
    public void setDisplayedGold(ArrayList<Card> displayedGold) {
        this.displayedGold = displayedGold;
    }


    /**
     * Method for obtaining the Objective cards.
     * @return The Objective cards.
     */
    public ArrayList<CardObjective> getDisplayedObjective() {
        return displayedObjective;
    }


    /**
     * Method for setting the displayed Objective cards.
     * @param displayedObjective The displayed Objective cards.
     */
    public void setDisplayedObjective(ArrayList<CardObjective> displayedObjective) {
        this.displayedObjective = displayedObjective;
    }


    // Questi metodi vanno spostati nella view
/*    public void printDeckStarter() {
        System.out.println("STARTER DECK:");
        for (CardStarter card : deckStarter) {
            card.printCardStarter(card);
        }
    }

    public void printDeckResource() {
        System.out.println("RESOURCE DECK:");
        for (CardResource card : deckResource) {
            card.printCardResource(card);
        }
    }

    public void printDeckGold() {
        System.out.println("GOLD DECK:");
        for (CardGold card : deckGold) {
            card.printCardGold(card);
        }
    }

    public void printDeckObjective() {
        System.out.println("OBJECTIVE DECK:");
        for (CardObjective card : deckObjective) {
            card.printCardObjective(card);
        }
    }

    public void printDisplayedResourceCards() {
        System.out.println("DISPLAYED RESOURCE CARDS:");
        ArrayList<CardResource> convertedResource = new ArrayList<>();
        for(Card card :displayedResource){
            if(card instanceof CardResource){
                CardResource cardResource = (CardResource) card;
                convertedResource.add(cardResource);
            }
        }
        for (Card card : convertedResource) {
            //card.printCardResource(card);
        }
    }

    public void printDisplayedGoldCards() {
        System.out.println("DISPLAYED GOLD CARDS:");
        ArrayList<CardGold> convertedGold = new ArrayList<>();
        for(Card card :displayedGold){
            if(card instanceof CardGold){
                CardGold cardGold = (CardGold) card;
                convertedGold.add(cardGold);
            }
        }
        for (CardGold cardGold : convertedGold) {
            //cardGold.printCardGold(cardGold);
        }
    }

    public void printDisplayedObjectiveCards() {
        System.out.println("DISPLAYED OBJECTIVE CARDS:");
        for (CardObjective card : displayedObjective) {
            //card.printCardObjective(card);
        }
    }
*/

}
