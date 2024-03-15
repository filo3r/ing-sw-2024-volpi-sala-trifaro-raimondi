package it.polimi.ingsw.gc03;

import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * This class represents a deck of Starter cards.
 */
public class DeckStarter extends Deck{

    /**
     * Deck of Starter cards.
     */
    private CardStarter[] deckStarter;


    /**
     * File containing all information about Starter cards.
     */
    private static final String FILE_CARD_STARTER = "FileCardStarter.txt";


    /**
     * Constructor of the DeckStarter class.
     */
    public DeckStarter() {
        super();
        this.deckStarter = new CardStarter[NUM_STA];
        // File path
        String filePath = System.getProperty("user.dir");
        // Full path to the file
        String fullFilePath = filePath + File.separator + "src" + File.separator + "main" + File.separator + "java" +
                File.separator + "it" + File.separator + "polimi" + File.separator + "ingsw" +
                File.separator + "gc03" + File.separator + FILE_CARD_STARTER;
        // Opening and reading the file
        try {
            Scanner inputStream = new Scanner(new File(fullFilePath));
            // Index to build the array
            int index = 0;
            // Read the file until the end
            while (inputStream.hasNextLine()) {
                // Read all values of a card
                String idCard = inputStream.nextLine();
                Value frontTopLeftCorner = convertToValue(inputStream.nextLine());
                Value frontBottomLeftCorner = convertToValue(inputStream.nextLine());
                Value frontTopRightCorner = convertToValue(inputStream.nextLine());
                Value frontBottomRightCorner = convertToValue(inputStream.nextLine());
                Value backTopLeftCorner = convertToValue(inputStream.nextLine());
                Value backBottomLeftCorner = convertToValue(inputStream.nextLine());
                Value backTopRightCorner = convertToValue(inputStream.nextLine());
                Value backBottomRightCorner = convertToValue(inputStream.nextLine());
                Value center1 = convertToValue(inputStream.nextLine());
                Value center2 = convertToValue(inputStream.nextLine());
                Value center3 = convertToValue(inputStream.nextLine());
                // Create the front and back of the card
                FrontStarter frontStarter = new FrontStarter(frontTopLeftCorner, frontBottomLeftCorner,
                        frontTopRightCorner, frontBottomRightCorner);
                BackSide backStarter = new BackSide(backTopLeftCorner, backBottomLeftCorner, backTopRightCorner,
                        backBottomRightCorner, center1, center2, center3);
                // Create the complete card
                CardStarter cardStarter = new CardStarter(idCard, frontStarter, backStarter);
                this.deckStarter[index] = cardStarter;
                index++;
            }
            // Close the file
            inputStream.close();
            // Error opening the file
        } catch (FileNotFoundException e) {
            System.err.println("Error opening the file: " + FILE_CARD_STARTER + " " + e.getMessage());
        }
        // Shuffle the deck
        shuffleDeckStarter();
    }


    /**
     * Method for shuffling the Starter card deck.
     */
    public void shuffleDeckStarter() {
        Random random = new Random();
        for (int i = NUM_STA - 1; i > 0; i--) {
            int indexRandom = random.nextInt(i + 1);
            // Swap
            CardStarter temp = deckStarter[indexRandom];
            deckStarter[indexRandom] = deckStarter[i];
            deckStarter[i] = temp;
        }
    }


    /**
     * Getter method to retrieve the cards contained in the deck.
     * @return The cards contained in the deck.
     */
    public CardStarter[] getDeckStarter() {
        return deckStarter;
    }


    /**
     * Method for printing a deck card.
     * @param indexStarter The index of the card you want to print.
     */
    public void printCardStarterAtIndex(int indexStarter) {
        CardStarter cardStarter = deckStarter[indexStarter];
        cardStarter.printCardStarter(cardStarter);
    }


    /**
     * Method for printing the entire deck.
     */
    public void printDeckStarter() {
        for (int i = 0; i < NUM_STA; i++){
            CardStarter cardStarter = deckStarter[i];
            cardStarter.printCardStarter(cardStarter);
        }
    }


    /**
     * Method that checks whether all cards in the deck have been drawn.
     * @return A true or false Boolean.
     */
    public boolean deckStarterIsEmpty() {
        if (getDeckIndex() == NUM_STA)
            setEmptyDeck(true);
        return getEmptyDeck();
    }


    /**
     * Method for drawing a card from the Starter deck.
     * @return The drawn Starter card.
     */
    public CardStarter drawCardStarter() {
        if (!getEmptyDeck()){
            CardStarter drawCard = deckStarter[getDeckIndex()];
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
            return deckStarter[getDeckIndex()].getBackStarter();
        } else {
            return null;
        }
    }


}
