package it.polimi.ingsw.gc03;

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
     * @param topLeftCorner Value contained in the top-left corner to be set.
     * @param bottomLeftCorner Value contained in the bottom-left corner to be set.
     * @param topRightCorner Value contained in the top-right corner to be set.
     * @param bottomRightCorner Value contained in the bottom-right corner to be set.
     */
    public void setFrontStarter(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                                 Value bottomRightCorner) {
        this.frontStarter.setTopLeftCorner(topLeftCorner);
        this.frontStarter.setBottomLeftCorner(bottomLeftCorner);
        this.frontStarter.setTopRightCorner(topRightCorner);
        this.frontStarter.setBottomRightCorner(bottomRightCorner);
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
     * @param topLeftCorner Value contained in the top-left corner to be set.
     * @param bottomLeftCorner Value contained in the bottom-left corner to be set.
     * @param topRightCorner Value contained in the top-right corner to be set.
     * @param bottomRightCorner Value contained in the bottom-right corner to be set.
     * @param center1 First value contained in the center to be set.
     * @param center2 Second value contained in the center to be set.
     * @param center3 Third value contained in the center to be set.
     */
    public void setBackStarter(Value topLeftCorner, Value bottomLeftCorner, Value topRightCorner,
                                Value bottomRightCorner, Value center1, Value center2, Value center3) {
        this.backStarter.setTopLeftCorner(topLeftCorner);
        this.backStarter.setBottomLeftCorner(bottomLeftCorner);
        this.backStarter.setTopRightCorner(topRightCorner);
        this.backStarter.setBottomRightCorner(bottomRightCorner);
        this.backStarter.setCenter(center1, center2, center3);
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
        System.out.println("Center: ");
        Value[] center = backStarter.getCenter();
        boolean firstCenter = true;
        for (int i = 0; i < 3; i++) {
            if (center[i] != Value.NULL){
                if (firstCenter) {
                    System.out.println(center[i]);
                    firstCenter = false;
                } else {
                    System.out.println(", " + center[i]);
                }
            }
        }

    }


}
