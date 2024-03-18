package it.polimi.ingsw.gc03.Deck;

import it.polimi.ingsw.gc03.Side.Back.BackSide;
import it.polimi.ingsw.gc03.Card.CardStarter;
import it.polimi.ingsw.gc03.Exceptions.NoMoreCardException;
import it.polimi.ingsw.gc03.Side.Front.FrontStarter;
import it.polimi.ingsw.gc03.Enumerations.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.List;
/**
 * This class represents a deck of Starter cards.
 */
public class DeckStarter extends Deck{

    /**
     * Deck of Starter cards.
     */
    private final List<CardStarter> deckStarter;


    /**
     * File containing all information about Starter cards.
     */
    private static final String FILE_CARD_STARTER = "FileCardStarter.txt";


    /**
     * Constructor of the DeckStarter class.
     */
    public DeckStarter() {
        super();
        deckStarter = new ArrayList<>();
        // File path
        String filePath = System.getProperty("user.dir");
        // Full path to the file
        String fullFilePath = filePath + File.separator + "src" + File.separator + "main" + File.separator + "java" +
                File.separator + "it" + File.separator + "polimi" + File.separator + "ingsw" +
                File.separator + "gc03" + File.separator + FILE_CARD_STARTER;
        // Opening and reading the file
        try {
            Scanner inputStream = new Scanner(new File(fullFilePath));
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
                this.deckStarter.add(cardStarter);
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
    public void shuffleDeckStarter(){
        Collections.shuffle(this.deckStarter);
    }


    /**
     * Getter method to retrieve the cards contained in the deck.
     * @return The cards contained in the deck.
     */
    public List<CardStarter> getDeckStarter(){
        return this.deckStarter;
    }


    /**
     * Method for printing a deck card.
     * @param indexStarter The index of the card you want to print.
     */
    public void printCardStarterAtIndex(int indexStarter) {
        CardStarter cardStarter = deckStarter.get(indexStarter);
        cardStarter.printCardStarter(cardStarter);
    }


    /**
     * Method for printing the entire deck.
     */
    public void printDeckStarter() {
        for (CardStarter cardStarter : this.deckStarter){
            cardStarter.printCardStarter(cardStarter);
        }
    }


    /**
     * Method for drawing a card from the Starter deck.
     * @return The drawn Starter card.
     */
    public CardStarter drawCardStarter() throws NoMoreCardException {
        if(!this.deckStarter.isEmpty()){
            return this.deckStarter.removeFirst();
        }else throw new NoMoreCardException("Error: StarterCard are finished");
    }


    /**
     * Method that returns the back of the top card of the deck.
     * @return The back of the top card of the deck.
     */
    public BackSide backSideCard() throws NoMoreCardException{
        if(!this.deckStarter.isEmpty()){
            return deckStarter.getFirst().getBackStarter();
        }
        else throw new NoMoreCardException("Error: StarterCard are finished");
    }

}
