package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontStarter;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.util.ArrayList;

/**
 * This class represents a Starter card.
 */
public class CardStarter extends Card {

    /**
     * The front side of the card.
     */
    private FrontStarter frontStarter;

    /**
     * The back side of the card.
     */
    private BackSide backStarter;


    /**
     * Constructor for the CardStarter class.
     * @param idCard The unique identifier of the card.
     * @param frontStarter The front side of the card.
     * @param backStarter The back side of the card.
     */
    public CardStarter(String idCard, FrontStarter frontStarter, BackSide backStarter) {
        super(idCard, true);
        this.frontStarter = frontStarter;
        this.backStarter = backStarter;
    }


    /**
     * Getter method to retrieve the front side of the card.
     * @return The front side of the card.
     */
    public FrontStarter getFrontStarter() {
        return frontStarter;
    }


    /**
     * Setter method to set the front side of the card.
     * @param frontStarter front of the StarterCard.
     */
    public void setFrontStarter(FrontStarter frontStarter) {
        this.frontStarter = frontStarter;
    }


    /**
     * Getter method to retrieve the back side of the card.
     * @return The back side of the card.
     */
    public BackSide getBackStarter() {
        return backStarter;
    }


    /**
     * Setter method to set the back side of the card.
     * @param backStarter back of the StarterCard.
     */
    public void setBackStarter(BackSide backStarter) {
        this.backStarter=backStarter;
    }


    /**
     * Method for printing all the information on a Starter card.
     * @param cardStarter The Starter card you want to print.
     */
    public void printCardStarter(CardStarter cardStarter) {
        // General information
        System.out.println("STARTER CARD:");
        System.out.println("Card ID: " + getIdCard());
        // Information on the front
        System.out.println("FRONT SIDE:");
        System.out.println("Top Left Corner: " + frontStarter.getTopLeftCorner());
        System.out.println("Bottom Left Corner: " + frontStarter.getBottomLeftCorner());
        System.out.println("Top Right Corner: " + frontStarter.getTopRightCorner());
        System.out.println("Bottom Right Corner: " + frontStarter.getBottomRightCorner());
        // Information on the back
        System.out.println("BACK SIDE:");
        System.out.println("Top Left Corner: " + backStarter.getTopLeftCorner());
        System.out.println("Bottom Left Corner: " + backStarter.getBottomLeftCorner());
        System.out.println("Top Right Corner: " + backStarter.getTopRightCorner());
        System.out.println("Bottom Right Corner: " + backStarter.getBottomRightCorner());
        System.out.print("Center: ");
        ArrayList<Value> center = backStarter.getCenter();
        for (int i = 0; i < center.size(); i++) {
            if (i != 0) {
                System.out.print(", ");
            }
            System.out.print(center.get(i));
        }
        System.out.println();
    }


}
