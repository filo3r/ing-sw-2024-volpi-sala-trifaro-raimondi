package it.polimi.ingsw.gc03;

/**
 * This class represents a deck of cards in the game.
 */
public class Deck {

    /**
     * Number of Resource cards in a deck.
     */
    protected static final int NUM_RES = 40;

    /**
     * Number of Gold cards in a deck.
     */
    protected static final int NUM_GOL = 40;

    /**
     * Number of Starter cards in a deck.
     */
    protected static final int NUM_STA = 6;

    /**
     * Number of Objective cards in a deck.
     */
    protected static final int NUM_OBJ = 16;

    /**
     * Index to keep track of the cards drawn.
     */
    private int deckIndex;

    /**
     * Boolean indicating whether all cards in the deck have been drawn.
     */
    private boolean emptyDeck;


    /**
     * Constructor for the Deck class.
     */
    public Deck() {
        this.deckIndex = 0;
        this.emptyDeck = false;
    }


    /**
     * Getter method to retrieve the index.
     * @return The index that keeps track of the cards drawn.
     */
    public int getDeckIndex() {
        return deckIndex;
    }


    /**
     * Setter method to set the index.
     * @param deckIndex The index to be set.
     */
    public void setDeckIndex(int deckIndex) {
        this.deckIndex = deckIndex;
    }


    /**
     * Method for incrementing the index by one.
     */
    public void incrementDeckIndex() {
        this.deckIndex++;
    }


    /**
     * Method to get if the deck is empty.
     * @return The Boolean true or false.
     */
    public boolean getEmptyDeck() {
        return emptyDeck;
    }


    /**
     * Method to set if the deck is empty.
     * @param emptyDeck The Boolean true or false.
     */
    public void setEmptyDeck(boolean emptyDeck) {
        this.emptyDeck = emptyDeck;
    }


    /**
     * Method for converting values read from the file (enum Value).
     * @param valueString Value read from the file.
     * @return Converted value.
     */
    public Value convertToValue(String valueString) {
        switch (valueString) {
            case "PLANT": return Value.PLANT;
            case "ANIMAL": return Value.ANIMAL;
            case "FUNGI": return Value.FUNGI;
            case "INSECT": return Value.INSECT;
            case "QUILL": return Value.QUILL;
            case "INKWELL": return Value.INKWELL;
            case "MANUSCRIPT": return Value.MANUSCRIPT;
            case "EMPTY": return Value.EMPTY;
            case "NULL": return Value.NULL;
            case "COVERED": return Value.COVERED;
            default: return null;
        }
    }


    /**
     * Method for converting values read from the file (enum Kingdom).
     * @param kingdomString Value read from the file.
     * @return Converted value.
     */
    public Kingdom convertToKingdom(String kingdomString) {
        switch (kingdomString) {
            case "PLANT": return Kingdom.PLANT;
            case "ANIMAL": return Kingdom.ANIMAL;
            case "FUNGI": return Kingdom.FUNGI;
            case "INSECT": return Kingdom.INSECT;
            default: return null;
        }
    }


}
