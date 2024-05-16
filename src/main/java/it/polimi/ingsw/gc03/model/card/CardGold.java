package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.side.back.BackGold;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import java.io.Serializable;


/**
 * This class represents a Gold card.
 */
public class CardGold extends Card implements Serializable {

    /**
     * Kingdom to which the card belongs.
     */
    private Kingdom kingdom;

    /**
     * The front side of the card.
     */
    private FrontGold frontGold;

    /**
     * The back side of the card.
     */
    private BackGold backGold;


    /**
     * Constructor for the CardGold class.
     * @param idCard The unique identifier of the card.
     * @param kingdom The kingdom to which the card belongs.
     * @param frontGold The front side of the card.
     * @param backGold The back side of the card.
     */
    public CardGold(String idCard, Kingdom kingdom, FrontGold frontGold, BackGold backGold) {
        super(idCard, true);
        this.kingdom = kingdom;
        this.frontGold = frontGold;
        this.backGold = backGold;
    }


    /**
     * Getter method to retrieve the kingdom to which the card belongs.
     * @return The kingdom of the card.
     */
    public Kingdom getKingdom() {
        return kingdom;
    }


    /**
     * Setter method to set the kingdom of the card.
     * @param kingdom The kingdom to set.
     */
    public void setKingdom(Kingdom kingdom) {
        this.kingdom = kingdom;
    }


    /**
     * Getter method to retrieve the front side of the card.
     * @return The front side of the card.
     */
    public FrontGold getFrontGold() {
        return frontGold;
    }


    /**
     * Setter method to set the front side of the card.
     * @param frontGold new front of the GoldCard.
     */
    public void setFrontGold(FrontGold frontGold) {
        this.frontGold =frontGold;
    }


    /**
     * Getter method to retrieve the back side of the card.
     * @return The back side of the card.
     */
    public BackGold getBackGold() {
        return backGold;
    }


    /**
     * Setter method to set the back side of the card.
     * @param backGold new back of the GoldCard.
     */
    public void setBackGold(BackGold backGold){
        this.backGold=backGold;
    }


    /**
     * Method for printing all the information on a Gold card.
     * @param cardGold The Gold card you want to print.
     */
   /* public void printCardGold(CardGold cardGold) {
        // General information
        System.out.println("GOLD CARD:");
        System.out.println("Card ID: " + getIdCard());
        System.out.println("Kingdom: " + cardGold.getKingdom());
        // Information on the front
        System.out.println("FRONT SIDE:");
        System.out.println("Top Left Corner: " + frontGold.getTopLeftCorner());
        System.out.println("Bottom Left Corner: " + frontGold.getBottomLeftCorner());
        System.out.println("Top Right Corner: " + frontGold.getTopRightCorner());
        System.out.println("Bottom Right Corner: " + frontGold.getBottomRightCorner());
        System.out.println("Points: " + frontGold.getPoint());
        System.out.println("Points requirement: " + frontGold.getRequirementPoint());
        System.out.print("Placement requirement: ");
        ArrayList<Value> requirementPlacement = frontGold.getRequirementPlacement();
        for (int i = 0; i < requirementPlacement.size(); i++) {
            if (i != 0) {
                System.out.print(", ");
            }
            System.out.print(requirementPlacement.get(i));
        }
        System.out.println();
        // Information on the back
        System.out.println("BACK SIDE:");
        System.out.println("Top Left Corner: " + backGold.getTopLeftCorner());
        System.out.println("Bottom Left Corner: " + backGold.getBottomLeftCorner());
        System.out.println("Top Right Corner: " + backGold.getTopRightCorner());
        System.out.println("Bottom Right Corner: " + backGold.getBottomRightCorner());
        ArrayList<Value> center = backGold.getCenter();
        System.out.println("Center: " + center.get(0));
    }

    */


}
