package it.polimi.ingsw.gc03;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * This class represents a deck of Resource cards.
 */
public class DeckResource extends Deck{

    /**
     * Deck of Resource cards.
     */
    private CardResource[] deckResource;


    /**
     * File containing all information about Resource cards.
     */
    private static final String FILE_CARD_RESOURCE = "FileCardResource.txt";


    /**
     * Constructor of the DeckResource class.
     */
    public DeckResource() {
        super();
        this.deckResource = new CardResource[NUM_RES];
        // File path
        String filePath = System.getProperty("user.dir");
        // Full path to the file
        String fullFilePath = filePath + File.separator + "src" + File.separator + "main" + File.separator + "java" +
                              File.separator + "it" + File.separator + "polimi" + File.separator + "ingsw" +
                              File.separator + "gc03" + File.separator + FILE_CARD_RESOURCE;
        // Opening and reading the file
        try {
            Scanner inputStream = new Scanner(new File(fullFilePath));
            // Index to build the array
            int index = 0;
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
                Value backTopLeftCorner = convertToValue(inputStream.nextLine());
                Value backBottomLeftCorner = convertToValue(inputStream.nextLine());
                Value backTopRightCorner = convertToValue(inputStream.nextLine());
                Value backBottomRightCorner = convertToValue(inputStream.nextLine());
                Value center1 = convertToValue(inputStream.nextLine());
                Value center2 = convertToValue(inputStream.nextLine());
                Value center3 = convertToValue(inputStream.nextLine());
                // Create the front and back of the card
                FrontResource frontResource = new FrontResource(frontTopLeftCorner, frontBottomLeftCorner,
                                                                frontTopRightCorner, frontBottomRightCorner, point);
                BackSide backResource = new BackSide(backTopLeftCorner, backBottomLeftCorner, backTopRightCorner,
                                                     backBottomRightCorner, center1, center2, center3);
                // Create the complete card
                CardResource cardResource = new CardResource(idCard, kingdom, frontResource, backResource);
                this.deckResource[index] = cardResource;
                index++;
            }
            // Close the file
            inputStream.close();
        // Error opening the file
        } catch (FileNotFoundException e) {
            System.err.println("Error opening the file: " + FILE_CARD_RESOURCE + " " + e.getMessage());
        }
        // Shuffle the deck
        shuffleDeckResource();
    }


    /**
     * Method for shuffling the Resource card deck.
     */
    public void shuffleDeckResource() {
        Random random = new Random();
        for (int i = NUM_RES - 1; i > 0; i--) {
            int indexRandom = random.nextInt(i + 1);
            // Swap
            CardResource temp = deckResource[indexRandom];
            deckResource[indexRandom] = deckResource[i];
            deckResource[i] = temp;
        }
    }


    /**
     * Getter method to retrieve the cards contained in the deck.
     * @return The cards contained in the deck.
     */
    public CardResource[] getDeckResource() {
        return deckResource;
    }


    /**
     * Method for printing a deck card.
     * @param indexResource The index of the card you want to print.
     */
    public void printCardResourceAtIndex(int indexResource) {
        CardResource cardResource = deckResource[indexResource];
        cardResource.printCardResource(cardResource);
    }


    /**
     * Method for printing the entire deck.
     */
    public void printDeckResource() {
        for (int i = 0; i < NUM_RES; i++){
            CardResource cardResource = deckResource[i];
            cardResource.printCardResource(cardResource);
        }
    }


    /**
     * Method that checks whether all cards in the deck have been drawn.
     * @return A true or false Boolean.
     */
    public boolean deckResourceIsEmpty() {
        if (getDeckIndex() == NUM_RES)
            setEmptyDeck(true);
        return getEmptyDeck();
    }


    /**
     * Method for drawing a card from the Resource deck.
     * @return The drawn Resource card.
     */
    public CardResource drawCardResource() {
        if (!getEmptyDeck()){
            CardResource drawCard = deckResource[getDeckIndex()];
            incrementDeckIndex();
            return drawCard;
        } else {
            return null;
        }
    }


    /**
     * Method that returns the back of the top card of the deck.
     * @return The back of the top card of the deck.
     */
    public BackSide backSideCard() {
        if (!getEmptyDeck()){
            return deckResource[getDeckIndex()].getBackResource();
        } else {
            return null;
        }
    }


}
