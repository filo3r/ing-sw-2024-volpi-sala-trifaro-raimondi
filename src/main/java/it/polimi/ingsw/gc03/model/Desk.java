package it.polimi.ingsw.gc03.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.cardObjective.CalculateScoreStrategy;
import it.polimi.ingsw.gc03.model.card.cardObjective.CalculateScoreStrategyAdapter;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.DeckType;

import java.io.*;
import java.lang.reflect.Type;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the game board.
 */
public class Desk implements Serializable {

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
     * Path to json files.
     */
    private static final String FILE_JSON = "/it/polimi/ingsw/gc03/json/";

    /**
     * Path to the front images folder.
     */
    private static final String IMAGE_PATH_FRONT = "/it/polimi/ingsw/gc03/gui/images/cards/frontSide/";

    /**
     * Path to the back images folder.
     */
    private static final String IMAGE_PATH_BACK = "/it/polimi/ingsw/gc03/gui/images/cards/backSide/";

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
     * The game object of the desk.
     */
    private Game game;

    /**
     * Constructor of the Desk class.
     */
    public Desk(Game game) throws RemoteException {
        // Create decks of cards
        if (!createDeckStarter() || !createDeckResource() || !createDeckGold() || !createDeckObjective())
            System.exit(1);
        // Initialize visible cards
        initializeDisplayedCard();
        this.game = game;
    }

    /**
     * Method for creating the Starter card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckStarter() {
        this.deckStarter = new ArrayList<>(NUM_CARD_STARTER);
        // Load the JSON file containing the Starter cards
        try (InputStream inputStream = getClass().getResourceAsStream(FILE_JSON + "fileCardStarter.json");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            // Use Gson to parse the JSON data
            Gson gson = new Gson();
            Type starterCardType = new TypeToken<ArrayList<CardStarter>>(){}.getType();
            ArrayList<CardStarter> starterCards = gson.fromJson(inputStreamReader, starterCardType);
            this.deckStarter.addAll(starterCards);
            inputStream.close();
            // Associate the images with the sides of the card
            for (CardStarter cardStarter : this.deckStarter) {
                cardStarter.getFrontStarter().setImage(IMAGE_PATH_FRONT + cardStarter.getIdCard() + "_front.png");
                cardStarter.getBackStarter().setImage(IMAGE_PATH_BACK + cardStarter.getIdCard() + "_back.png");
            }
            // Shuffle the deck
            Collections.shuffle(this.deckStarter);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_JSON + "fileCardStarter.json", e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_JSON + "fileCardStarter.json", e);
        }
        return false;
    }

    /**
     * Method for creating the Resource card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckResource() {
        this.deckResource = new ArrayList<>(NUM_CARD_RESOURCE);
        // Load the JSON file containing the Resource cards
        try (InputStream inputStream = getClass().getResourceAsStream(FILE_JSON + "fileCardResource.json");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            // Use Gson to parse the JSON data
            Gson gson = new Gson();
            Type resourceCardType = new TypeToken<ArrayList<CardResource>>(){}.getType();
            ArrayList<CardResource> resourceCards = gson.fromJson(inputStreamReader, resourceCardType);
            this.deckResource.addAll(resourceCards);
            inputStream.close();
            // Associate the images with the sides of the card
            for (CardResource cardResource : this.deckResource) {
                cardResource.getFrontResource().setImage(IMAGE_PATH_FRONT + cardResource.getIdCard() + "_front.png");
                cardResource.getBackResource().setImage(IMAGE_PATH_BACK + cardResource.getIdCard() + "_back.png");
            }
            // Shuffle the deck
            Collections.shuffle(this.deckResource);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_JSON + "fileCardResource.json", e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_JSON + "fileCardResource.json", e);
        }
        return false;
    }

    /**
     * Method for creating the Gold card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckGold() {
        this.deckGold = new ArrayList<>(NUM_CARD_GOLD);
        // Load the JSON file containing the Gold cards
        try (InputStream inputStream = getClass().getResourceAsStream(FILE_JSON + "fileCardGold.json");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            // Use Gson to parse the JSON data
            Gson gson = new Gson();
            Type goldCardType = new TypeToken<ArrayList<CardGold>>(){}.getType();
            ArrayList<CardGold> goldCards = gson.fromJson(inputStreamReader, goldCardType);
            this.deckGold.addAll(goldCards);
            inputStream.close();
            // Associate the images with the sides of the card
            for (CardGold cardGold : this.deckGold) {
                cardGold.getFrontGold().setImage(IMAGE_PATH_FRONT + cardGold.getIdCard() + "_front.png");
                cardGold.getBackGold().setImage(IMAGE_PATH_BACK + cardGold.getIdCard() + "_back.png");
            }
            // Shuffle the deck
            Collections.shuffle(this.deckGold);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_JSON + "fileCardGold.json", e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_JSON + "fileCardGold.json", e);
        }
        return false;
    }

    /**
     * Method for creating the Objective card deck.
     * @return A boolean indicating whether the operation was successful or not.
     */
    private boolean createDeckObjective() {
        this.deckObjective = new ArrayList<>(NUM_CARD_OBJECTIVE);
        // Load the JSON file containing the Objective cards
        try (InputStream inputStream = getClass().getResourceAsStream(FILE_JSON + "fileCardObjective.json");
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream)) {
            // Create GsonBuilder and register the adapter
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(CalculateScoreStrategy.class, new CalculateScoreStrategyAdapter());
            Gson gson = gsonBuilder.create();
            // Use Gson to parse the JSON data
            Type objectiveCardType = new TypeToken<ArrayList<CardObjective>>(){}.getType();
            ArrayList<CardObjective> objectiveCards = gson.fromJson(inputStreamReader, objectiveCardType);
            this.deckObjective.addAll(objectiveCards);
            inputStream.close();
            // Associate the images with the card
            for (CardObjective cardObjective : this.deckObjective) {
                cardObjective.setImage(IMAGE_PATH_FRONT + cardObjective.getIdCard() + "_front.png");
            }
            // Shuffle the deck
            Collections.shuffle(this.deckObjective);
            return true;
        } catch (FileNotFoundException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Could not find the file: " + FILE_JSON + "fileCardObjective.json", e);
        } catch (IOException e) {
            // Log the exception using the logger
            logger.log(Level.SEVERE, "Error reading the file: " + FILE_JSON + "fileCardObjective.json", e);
        }
        return false;
    }

    /**
     * Method for initializing visible cards.
     */
    private void initializeDisplayedCard() throws RemoteException {
        // Resource cards
        this.displayedResource = new ArrayList<>(NUM_CARD_DISPLAYED);
        for (int i = 0; i < NUM_CARD_DISPLAYED; i++) {
            CardResource cardResource = (CardResource) drawCardDeck(DeckType.DECK_RESOURCE);
            this.displayedResource.add(cardResource);
        }
        // Gold cards
        this.displayedGold = new ArrayList<>(NUM_CARD_DISPLAYED);
        for (int i = 0; i < NUM_CARD_DISPLAYED; i++) {
            CardGold cardGold = (CardGold) drawCardDeck(DeckType.DECK_GOLD);
            this.displayedGold.add(cardGold);
        }
        // Objective cards
        this.displayedObjective = new ArrayList<>(NUM_CARD_DISPLAYED);
        for (int i = 0; i < NUM_CARD_DISPLAYED; i++) {
            CardObjective cardObjective = (CardObjective) drawCardDeck(DeckType.DECK_OBJECTIVE);
            this.displayedObjective.add(cardObjective);
        }
    }

    /**
     * Method for drawing a card.
     * @param deck The deck from which you want to draw a card.
     * @return The drawn card.
     */
    public Card drawCardDeck(DeckType deck) {
        List<? extends Card> actualDeck = null;
        switch (deck) {
            case DECK_GOLD -> actualDeck = this.deckGold;
            case DECK_RESOURCE -> actualDeck = this.deckResource;
            case DECK_OBJECTIVE -> actualDeck = this.deckObjective;
            case DECK_STARTER -> actualDeck = this.deckStarter;
        }
        boolean emptyDeck = actualDeck.isEmpty();
        // Empty deck
        if (emptyDeck) {
            return null;
        } else{
            Card drawnCard =  actualDeck.removeFirst();
            if(game != null && game.getPlayers().size()>=1){
                game.getListener().notifyCardAddedToHand(this.game, drawnCard);
            }
            return drawnCard;
        }
    }

    /**
     * Method for drawing a card from those visible.
     * @param deck The visible cards from which you want to draw.
     * @param index The index of the card you want to take.
     * @return The card taken.
     */
    public Card drawCardDisplayed(DeckType deck, int index) {
        List<? extends Card> actualDeck = null;
        switch (deck) {
            case DISPLAYED_GOLD -> actualDeck = this.displayedGold;
            case DISPLAYED_RESOURCE -> actualDeck = this.displayedResource;
        }
        // Invalid index
        if (index < 0 || index >= actualDeck.size() || actualDeck.isEmpty()){
            checkDisplayed();
            return null;
        }    // Valid index
        else {
            Card card = actualDeck.remove(index);
            checkDisplayed();
            if(game != null && game.getPlayers().size()>=1){
                game.getListener().notifyCardAddedToHand(this.game, card);
            }
            return card;
        }
    }

    /**
     * Method for checking that the visible cards are always in the correct number.
     */
    private void checkDisplayed() {
        // Resource cards: Resource deck not empty
        while (this.displayedResource.size() < NUM_CARD_DISPLAYED && !this.deckResource.isEmpty()) {
            CardResource cardResource = (CardResource) drawCardDeck(DeckType.DECK_RESOURCE);
            this.displayedResource.add(cardResource);
        }
        // Resource cards: Resource deck empty
        while (this.displayedResource.size() < NUM_CARD_DISPLAYED && this.deckResource.isEmpty() && !this.deckGold.isEmpty()) {
            CardGold cardGold = (CardGold) drawCardDeck(DeckType.DECK_GOLD);
            this.displayedResource.add(cardGold);
        }
        // Gold cards: Gold deck not empty
        while (this.displayedGold.size() < NUM_CARD_DISPLAYED && !this.deckGold.isEmpty()) {
            CardGold cardGold = (CardGold) drawCardDeck(DeckType.DECK_GOLD);
            this.displayedGold.add(cardGold);
        }
        // Gold cards: Gold deck empty
        while (this.displayedGold.size() < NUM_CARD_DISPLAYED && this.deckGold.isEmpty() && !this.deckResource.isEmpty()) {
            CardResource cardResource = (CardResource) drawCardDeck(DeckType.DECK_GOLD);
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
}