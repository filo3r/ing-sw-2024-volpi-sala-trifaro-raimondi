package it.polimi.ingsw.gc03;


import it.polimi.ingsw.gc03.Card.*;

import it.polimi.ingsw.gc03.Card.CardObjective.CardObjective;
import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Enumerations.Value;
import it.polimi.ingsw.gc03.Exceptions.CardNotFoundException;
import it.polimi.ingsw.gc03.Exceptions.DeskIsFullException;
import it.polimi.ingsw.gc03.Exceptions.NoMoreCardException;
import it.polimi.ingsw.gc03.Exceptions.PlayerAlreadyJoinedException;
import it.polimi.ingsw.gc03.Side.Back.BackSide;
import it.polimi.ingsw.gc03.Side.Front.FrontGold;
import it.polimi.ingsw.gc03.Side.Front.FrontResource;
import it.polimi.ingsw.gc03.Side.Front.FrontStarter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class Desk {
    /**
     * The two resource cards that are visible on the table.
     */
    private List<CardResource> displayedResource;

    /**
     * The two gold cards that are visible on the table.
     */
    private List<CardGold> displayedGold;

    /**
     * The two public objective cards.
     */
    private List<CardObjective> displayedObjective;

    /**
     * The Resource Deck on the table
     */
    private List<CardResource> deckResource;

    /**
     * The Gold Deck on the table
     */
    private List<CardGold> deckGold;

    /**
     * The players whose codex are on the table
     */
    private List<Player> players;

    /**
     * Deck formed by the 6 Starter Card
     */
    private List<CardStarter> deckStarter;

    /**
     * File containing all information about Gold cards.
     */
    private static final String FILE_CARD_GOLD = "FileCardGold.txt";

    /**
     * File containing all information about Resource cards.
     */
    private static final String FILE_CARD_RESOURCE = "FileCardResource.txt";

    /**
     * File containing all information about Starter cards.
     */
    private static final String FILE_CARD_STARTER = "FileCardStarter.txt";

    /**
     * Constructs a new Desk
     * @param displayedResource ResourceCards shown on the table
     * @param displayedGold GoldCards shown on the table
     * @param displayedObjective ObjectiveCards shown on the table
     * @param players players at the Table
     */
    public Desk(List<CardResource> displayedResource,List<CardGold> displayedGold,List<CardObjective> displayedObjective,List<Player> players){
        deckGold = DeckGold();
        deckResource = DeckResource();
        deckStarter = DeckStarter();
        this.displayedGold = new ArrayList<>(displayedGold);
        this.displayedResource = new ArrayList<>(displayedResource);
        this.displayedObjective = new ArrayList<>(displayedObjective);
        this.players = new ArrayList<>(players);
    }

    /**
     * Setter DisplayedResources Card
     * @param displayedResource ResourceCards shown on the table
     */
    public void setDisplayedResource(List<CardResource> displayedResource){this.displayedResource = displayedResource;}

    /**
     * Getter DisplayedResources Card
     * @return the displayed Resources
     */
    public List<CardResource> getDisplayResource(){return this.displayedResource;}

    /**
     * Setter DisplayedGold Card
     * @param displayedGold GoldCards shown on the table
     */
    public void setDisplayedGold(List<CardGold> displayedGold){this.displayedGold = displayedGold;}

    /**
     * Getter DisplayedGold Card
     * @return the displayed GoldResources
     */
    public List<CardGold> getDisplayedGold(){return this.displayedGold;}

    /**
     * Setter Objective
     * @param displayedObjective ObjectiveCards shown on the table
     */
    public void setDisplayedObjective(List<CardObjective> displayedObjective){this.displayedObjective = displayedObjective;}

    /**
     * Getter DisplayedObjective Card
     * @return the displayed Common Objectives
     */
    public List<CardObjective> getDisplayedObjective(){return this.displayedObjective;}

    /**
     * Setter DeckResource
     * @param deckResource deckResource on the table
     */
    public void setDeckResource(List<CardResource> deckResource){this.deckResource = deckResource;}


    /**
     * Setter DeckGold
     * @param deckGold deckGold on the Table
     */
    public void setDeckGold(List<CardGold> deckGold){this.deckGold = deckGold;}


    /**
     * Setter Players
     * @param players players at the Table
     */
    public void setPlayers(List<Player> players) {this.players = players;}

    /**
     * Getter Players
     * @return a List of players at the table
     */
    public List<Player> getPlayers(){return this.players;}

    /**
     * Setter StarterDeck
     * @param deckStarter Deck of Starter Card
     */
    public void setDeckStarter(List<CardStarter> deckStarter){this.deckStarter=deckStarter;}

    /**
     * Add a player to the desk, throws two exceptions
     * @param player Player to be added
     * @throws PlayerAlreadyJoinedException Exception
     * @throws DeskIsFullException Exception
     */
    public void addPlayer(Player player) throws PlayerAlreadyJoinedException,DeskIsFullException{
       if(this.players.contains(player)) {
           throw new PlayerAlreadyJoinedException("Error: This Player is already at this table");
       }
       else if (this.players.size()<4) {
           this.players.add(player);
       }else throw new DeskIsFullException("Error: The desk is already full");
    }

    /**
     * Remove a player from the desk
     * @param player Player to be removed
     */
    public void removePlayer(Player player){
        this.players.remove(player);
    }

    /**
     * Draw a card from ResourceDeck and display it only if Displayed Resources are less than 2
     */
    public void drawResourceAndDisplay() throws NoMoreCardException {
        if(!deckResource.isEmpty()){
            if(this.displayedResource.size()<2) this.displayedResource.add(drawCardResource());
        } else throw new NoMoreCardException("Error: ResourceCard in the deck are finished");
    }

    /**
     * Chose a ResourceCard from the DisplayedResources and return it
     * @param i Index of the Displayed List
     * @return the chosen ResourceCard from the DisplayedResources
     * @throws CardNotFoundException Exception
     */
    public CardResource drawDisplayedResource(int i) throws CardNotFoundException{
        if(this.displayedResource.size()<i) throw new CardNotFoundException("Error: ResourceCard not found");
        else {
            return this.displayedResource.remove(i);
        }
    }
    /**
     * Draw a card from GoldDeck and display it, only if Displayed Gold are less than 2
     */
    public void drawGoldAndDisplay() throws NoMoreCardException {
        if (!this.deckGold.isEmpty()) {
            if (this.displayedGold.size() < 2) this.displayedGold.add(drawCardGold());
        } else throw new NoMoreCardException("Error: GoldCard in the deck are finished");
    }
    /**
     * Chose a GoldCard from the DisplayedGold and return it
     * @param i Index of the Displayed List
     * @return the chosen GoldCard from the DisplayedGold
     * @throws CardNotFoundException Exception
     */
    public CardGold drawDisplayedGold(int i) throws CardNotFoundException{
        if(this.displayedGold.size()<i) throw new CardNotFoundException("Error: GoldCard not found");
        else {
            return this.displayedGold.remove(i);
        }
    }

    /**
     * Sets a random StarterCard for every player at the desk, the shuffle has been made at the end of the construction of the deck
     */
    public void setRandomStarterCard() throws NoMoreCardException {
        for (Player player : this.players) {
            player.setCardStarter(drawCardStarter());
        }
    }


    /**
     * Method for converting values read from the file (enum Value).
     * @param valueString Value read from the file.
     * @return Converted value.
     */
    private Value convertToValue(String valueString) {
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
   private Kingdom convertToKingdom(String kingdomString) {
       switch (kingdomString) {
           case "PLANT":
               return Kingdom.PLANT;
           case "ANIMAL":
               return Kingdom.ANIMAL;
           case "FUNGI":
               return Kingdom.FUNGI;
           case "INSECT":
               return Kingdom.INSECT;
           default:
               return null;
       }
   }

    /**
     * Constructor of the DeckGold class.
     */
    private List<CardGold> DeckGold() {
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
                ArrayList<Value> requirementPlacement = new ArrayList<>();
                if(requirementPlacement1!=Value.NULL) requirementPlacement.add(requirementPlacement1);
                if(requirementPlacement2!=Value.NULL) requirementPlacement.add(requirementPlacement2);
                if(requirementPlacement3!=Value.NULL) requirementPlacement.add(requirementPlacement3);
                if(requirementPlacement4!=Value.NULL) requirementPlacement.add(requirementPlacement4);
                if(requirementPlacement5!=Value.NULL) requirementPlacement.add(requirementPlacement5);
                Value backTopLeftCorner = convertToValue(inputStream.nextLine());
                Value backBottomLeftCorner = convertToValue(inputStream.nextLine());
                Value backTopRightCorner = convertToValue(inputStream.nextLine());
                Value backBottomRightCorner = convertToValue(inputStream.nextLine());
                Value center1 = convertToValue(inputStream.nextLine());
                Value center2 = convertToValue(inputStream.nextLine());
                Value center3 = convertToValue(inputStream.nextLine());
                ArrayList<Value> center = new ArrayList<>();
                if(center1!=Value.NULL) center.add(center1);
                if(center2!=Value.NULL) center.add(center2);
                if(center3!=Value.NULL) center.add(center3);
                // Create the front and back of the card
                FrontGold frontGold = new FrontGold(frontTopLeftCorner, frontBottomLeftCorner,
                        frontTopRightCorner, frontBottomRightCorner, point,
                        requirementPoint, requirementPlacement);
                BackSide backGold = new BackSide(backTopLeftCorner, backBottomLeftCorner, backTopRightCorner,
                        backBottomRightCorner, center);
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
        Collections.shuffle(deckGold);

        return deckGold;
    }

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
    public BackSide backSideCardGold()throws NoMoreCardException{
        if(!this.deckGold.isEmpty()){
            return this.deckGold.getFirst().getBackGold();
        }
        else throw new NoMoreCardException("Error: GoldCards are finished");
    }



    /**
    * Constructor of the DeckResource class.
    */
    private List<CardResource> DeckResource() {
          deckResource = new ArrayList<>();
          // File path
          String filePath = System.getProperty("user.dir");
         // Full path to the file
         String fullFilePath = filePath + File.separator + "src" + File.separator + "main" + File.separator + "java" +
                 File.separator + "it" + File.separator + "polimi" + File.separator + "ingsw" +
                 File.separator + "gc03" + File.separator + FILE_CARD_RESOURCE;
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
                Value backTopLeftCorner = convertToValue(inputStream.nextLine());
                Value backBottomLeftCorner = convertToValue(inputStream.nextLine());
                Value backTopRightCorner = convertToValue(inputStream.nextLine());
                Value backBottomRightCorner = convertToValue(inputStream.nextLine());
                Value center1 = convertToValue(inputStream.nextLine());
                Value center2 = convertToValue(inputStream.nextLine());
                Value center3 = convertToValue(inputStream.nextLine());
                ArrayList<Value> center = new ArrayList<>();
                if(center1!=Value.NULL) center.add(center1);
                if(center2!=Value.NULL) center.add(center2);
                if(center3!=Value.NULL) center.add(center3);
                // Create the front and back of the card
                FrontResource frontResource = new FrontResource(frontTopLeftCorner, frontBottomLeftCorner,
                        frontTopRightCorner, frontBottomRightCorner, point);
                BackSide backResource = new BackSide(backTopLeftCorner, backBottomLeftCorner, backTopRightCorner,
                        backBottomRightCorner, center);
                // Create the complete card
                CardResource cardResource = new CardResource(idCard, kingdom, frontResource, backResource);
                this.deckResource.add(cardResource);
            }
            // Close the file
            inputStream.close();
            // Error opening the file
         } catch (FileNotFoundException e) {
             System.err.println("Error opening the file: " + FILE_CARD_RESOURCE + " " + e.getMessage());
         }
        // Shuffle the deck
        Collections.shuffle(deckResource);

         return deckResource;
    }

    /**
    * Getter method to retrieve the cards contained in the deck.
    * @return The cards contained in the deck.
    */
    public List<CardResource> getDeckResource(){
        return this.deckResource;
    }

    /**
    * Method for printing a deck card.
    * @param indexResource The index of the card you want to print.
    */
    public void printCardResourceAtIndex(int indexResource) {
            CardResource cardResource =this.deckResource.get(indexResource);
            cardResource.printCardResource(cardResource);
    }


    /**
    * Method for printing the entire deck.
    */
    public void printDeckResource() {
         for (CardResource cardResource : this.deckResource){
             cardResource.printCardResource(cardResource);
            }
    }

    /**
    * Method for drawing a card from the Resource deck.
    * @return The drawn Resource card.
    */
    public CardResource drawCardResource() throws NoMoreCardException {
        if(!this.deckResource.isEmpty()){
            return this.deckResource.removeFirst();
         } else throw new NoMoreCardException("Error: ResourceCards are finished");
    }

    /**
    * Method that returns the back of the top card of the deck.
    * @return The back of the top card of the deck.
    */
    public BackSide backSideCardResource() throws NoMoreCardException{
            if(!this.deckResource.isEmpty()){
             return deckResource.getFirst().getBackResource();
             }
            else throw new NoMoreCardException("Error: ResourceCards are finished");
    }

    /**
     * Constructor of the DeckStarter class.
     */
    private List<CardStarter> DeckStarter() {
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
                ArrayList<Value> center = new ArrayList<>();
                if(center1!=Value.NULL) center.add(center1);
                if(center2!=Value.NULL) center.add(center2);
                if(center3!=Value.NULL) center.add(center3);
                // Create the front and back of the card
                FrontStarter frontStarter = new FrontStarter(frontTopLeftCorner, frontBottomLeftCorner,
                        frontTopRightCorner, frontBottomRightCorner);
                BackSide backStarter = new BackSide(backTopLeftCorner, backBottomLeftCorner, backTopRightCorner,
                        backBottomRightCorner, center);
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
        Collections.shuffle(deckStarter);

        return deckStarter;
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
    public BackSide backSideCardStarter() throws NoMoreCardException{
        if(!this.deckStarter.isEmpty()){
            return deckStarter.getFirst().getBackStarter();
        }
        else throw new NoMoreCardException("Error: StarterCard are finished");
    }

}

