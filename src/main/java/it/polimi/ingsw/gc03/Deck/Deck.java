package it.polimi.ingsw.gc03.Deck;

import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Enumerations.Value;


/**
 * This class represents a deck of cards in the game.
 */
public class Deck {

    /**
     * Constructor for the Deck class.
    */
    public Deck() {
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
