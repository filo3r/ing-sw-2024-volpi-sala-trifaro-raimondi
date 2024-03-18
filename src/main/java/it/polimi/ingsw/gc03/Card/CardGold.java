package it.polimi.ingsw.gc03.Card;

import it.polimi.ingsw.gc03.Side.Back.BackSide;
import it.polimi.ingsw.gc03.Side.Front.FrontGold;
import it.polimi.ingsw.gc03.Enumerations.Kingdom;
import it.polimi.ingsw.gc03.Enumerations.Value;

/**
 * This class represents a Gold card.
 */
public class CardGold extends Card {

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
    private BackSide backGold;


    /**
     * Constructor for the CardGold class.
     * @param idCard The unique identifier of the card.
     * @param kingdom The kingdom to which the card belongs.
     * @param frontGold The front side of the card.
     * @param backGold The back side of the card.
     */
    public CardGold(String idCard, Kingdom kingdom, FrontGold frontGold, BackSide backGold) {
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
     * @param topLeftCorner Value contained in the top-left corner to be set.
     * @param bottomLeftCorner Value contained in the bottom-left corner to be set.
     * @param topRightCorner Value contained in the top-right corner to be set.
     * @param bottomRightCorner Value contained in the bottom-right corner to be set.
     * @param point Points on the card to be set.
     * @param requirementPoint Requirement for the points.
     * @param requirementPlacement1 The first placement requirement.
     * @param requirementPlacement2 The second placement requirement.
     * @param requirementPlacement3 The third placement requirement.
     * @param requirementPlacement4 The fourth placement requirement.
     * @param requirementPlacement5 The fifth placement requirement.
     */
    public void setFrontGold(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                             Value bottomRightCorner, int point, Value requirementPoint, Value requirementPlacement1,
                             Value requirementPlacement2, Value requirementPlacement3, Value requirementPlacement4,
                             Value requirementPlacement5) {
        this.frontGold.setTopLeftCorner(topLeftCorner);
        this.frontGold.setBottomLeftCorner(bottomLeftCorner);
        this.frontGold.setTopRightCorner(topRightCorner);
        this.frontGold.setBottomRightCorner(bottomRightCorner);
        this.frontGold.setPoint(point);
        this.frontGold.setRequirementPoint(requirementPoint);
        this.frontGold.setRequirementPlacement(requirementPlacement1, requirementPlacement2, requirementPlacement3,
                                               requirementPlacement4, requirementPlacement5);
    }


    /**
     * Getter method to retrieve the back side of the card.
     * @return The back side of the card.
     */
    public BackSide getBackGold() {
        return backGold;
    }


    /**
     * Setter method to set the back side of the card.
     * @param topLeftCorner Value contained in the top-left corner to be set.
     * @param bottomLeftCorner Value contained in the bottom-left corner to be set.
     * @param topRightCorner Value contained in the top-right corner to be set.
     * @param bottomRightCorner Value contained in the bottom-right corner to be set.
     * @param center1 First value contained in the center to be set.
     * @param center2 Second value contained in the center to be set.
     * @param center3 Third value contained in the center to be set.
     */
    public void setBackGold(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                                Value bottomRightCorner, Value center1, Value center2, Value center3) {
        this.backGold.setTopLeftCorner(topLeftCorner);
        this.backGold.setBottomLeftCorner(bottomLeftCorner);
        this.backGold.setTopRightCorner(topRightCorner);
        this.backGold.setBottomRightCorner(bottomRightCorner);
        this.backGold.setCenter(center1, center2, center3);
    }


    /**
     * Method for printing all the information on a Gold card.
     * @param cardGold The Gold card you want to print.
     */
    public void printCardGold(CardGold cardGold) {
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
        Value[] requirementPlacement = frontGold.getRequirementPlacement();
        boolean firstRequirementPlacement = true;
        for (int i = 0; i < 5; i++) {
            if (requirementPlacement[i] != Value.NULL){
                if (firstRequirementPlacement) {
                    System.out.println(requirementPlacement[i]);
                    firstRequirementPlacement = false;
                } else {
                    System.out.println(", " + requirementPlacement[i]);
                }
            }
        }
        // Information on the back
        System.out.println("BACK SIDE:");
        System.out.println("Top Left Corner: " + backGold.getTopLeftCorner());
        System.out.println("Bottom Left Corner: " + backGold.getBottomLeftCorner());
        System.out.println("Top Right Corner: " + backGold.getTopRightCorner());
        System.out.println("Bottom Right Corner: " + backGold.getBottomRightCorner());
        System.out.println("Center: " + backGold.getCenter()[0]);

    }


}
