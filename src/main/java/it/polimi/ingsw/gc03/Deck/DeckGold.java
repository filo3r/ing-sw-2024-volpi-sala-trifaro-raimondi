package it.polimi.ingsw.gc03.Deck;

import it.polimi.ingsw.gc03.Side.Back.BackSide;
import it.polimi.ingsw.gc03.Card.CardGold;
import it.polimi.ingsw.gc03.Exceptions.NoMoreCardException;
import it.polimi.ingsw.gc03.Side.Front.FrontGold;
import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Enumerations.Value;

import java.io.File;
import java.util.*;
import java.io.FileNotFoundException;

/**
 * This class represents a deck of Gold cards.
 */
public class DeckGold extends Deck{

    /**
     * Deck of Gold cards.
     */
    private final List<CardGold> deckGold;



    /**
     * File containing all information about Gold cards.
     */
    private static final String FILE_CARD_GOLD = "FileCardGold.txt";


    /**
     * Constructor of the DeckGold class.
     */
    public DeckGold() {
        super();
        deckGold = new ArrayList<>();
        // File path
        String filePath = System.getProperty("user.dir");
        // Full path to the file
        String fullFilePath = filePath + File.separator + "src" + File.separator + "main" + File.separator + "java" +
                File.separator + "it" + File.separator + "polimi" + File.separator + "ingsw" +
                File.separator + "gc03" + File.separator + FILE_CARD_GOLD;
        // Opening and reading the file
        try {
            Scanner inputStream = new Scanner(new File(fullFilePath));
            // Read the file until the end
            while (inputStream.hasNextLine()) {
                // Read all values of a card
                String idCard = inputStream.nextLine();
                Kingdom kingdom = convertToKingdom(inputStream.nextLine());
                Value frontTopLeftCorner = convertToValue(inputStream.nextLine());
                Value frontBottomLeftCorner = convertToValue(inputStream.nextLine());
                Value frontTopRightCorner = convertToValue(inputStream.nextLine());
                Value frontBottomRightCorner = convertToValue(inputStream.nextLine());
                int point = Integer.parseInt(inputStream.nextLine());
                Value requirementPoint = convertToValue(inputStream.nextLine());
                Value requirementPlacement1 = convertToValue(inputStream.nextLine());
                Value requirementPlacement2 = convertToValue(inputStream.nextLine());
                Value requirementPlacement3 = convertToValue(inputStream.nextLine());
                Value requirementPlacement4 = convertToValue(inputStream.nextLine());
                Value requirementPlacement5 = convertToValue(inputStream.nextLine());
                Value backTopLeftCorner = convertToValue(inputStream.nextLine());
                Value backBottomLeftCorner = convertToValue(inputStream.nextLine());
                Value backTopRightCorner = convertToValue(inputStream.nextLine());
                Value backBottomRightCorner = convertToValue(inputStream.nextLine());
                Value center1 = convertToValue(inputStream.nextLine());
                Value center2 = convertToValue(inputStream.nextLine());
                Value center3 = convertToValue(inputStream.nextLine());
                // Create the front and back of the card
                FrontGold frontGold = new FrontGold(frontTopLeftCorner, frontBottomLeftCorner,
                                                    frontTopRightCorner, frontBottomRightCorner, point,
                                                    requirementPoint, requirementPlacement1, requirementPlacement2,
                                                    requirementPlacement3, requirementPlacement4, requirementPlacement5);
                BackSide backGold = new BackSide(backTopLeftCorner, backBottomLeftCorner, backTopRightCorner,
                        backBottomRightCorner, center1, center2, center3);
                // Create the complete card
                CardGold cardGold = new CardGold(idCard, kingdom, frontGold, backGold);
                this.deckGold.add(cardGold);
            }
            // Close the file
            inputStream.close();
            // Error opening the file
        } catch (FileNotFoundException e) {
            System.err.println("Error opening the file: " + FILE_CARD_GOLD + " " + e.getMessage());
        }
        // Shuffle the deck
        shuffleDeckGold();
    }


    /**
     * Method for shuffling the Gold card deck.
     */
    public void shuffleDeckGold(){
        Collections.shuffle(this.deckGold);
    }

    /**
     * Getter method to retrieve the cards contained in the deck.
     * @return The cards contained in the deck.
     */
    public List<CardGold> getDeckGold(){
        return deckGold;
    }

    /**
     * Method for printing a deck card.
     * @param indexGold The index of the card you want to print.
     */
    public void printCardGoldAtIndex(int indexGold) {
        CardGold cardGold = deckGold.get(indexGold);
        cardGold.printCardGold(cardGold);
    }


    /**
     * Method for printing the entire deck.
     */
    public void printDeckGold() {
        for (CardGold cardGold : this.deckGold) {
            cardGold.printCardGold(cardGold);
        }
    }

    /**
     * Method for drawing a card from the Gold deck.
     * @return The drawn Gold card.
     */
    public CardGold drawCardGold() throws NoMoreCardException {
        if(!this.deckGold.isEmpty()){
            return this.deckGold.removeFirst();
        }else throw new NoMoreCardException("Error: GoldCards are finished");
    }

    /**
     * Method that returns the back of the top card of the deck.
     * @return The back of the top card of the deck.
     */
    public BackSide SideCard()throws NoMoreCardException{
        if(!this.deckGold.isEmpty()){
            return this.deckGold.getFirst().getBackGold();
        }
        else throw new NoMoreCardException("Error: GoldCards are finished");
    }
}
