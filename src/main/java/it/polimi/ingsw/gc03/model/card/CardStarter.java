package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.side.back.BackStarter;
import it.polimi.ingsw.gc03.model.side.front.FrontStarter;
import java.io.Serializable;

/**
 * This class represents a Starter card.
 */
public class CardStarter extends Card implements Serializable {

    /**
     * The front side of the card.
     */
    private FrontStarter frontStarter;

    /**
     * The back side of the card.
     */
    private BackStarter backStarter;

    /**
     * Constructor for the CardStarter class.
     * @param idCard The unique identifier of the card.
     * @param frontStarter The front side of the card.
     * @param backStarter The back side of the card.
     */
    public CardStarter(String idCard, FrontStarter frontStarter, BackStarter backStarter) {
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
    public BackStarter getBackStarter() {
        return backStarter;
    }

    /**
     * Setter method to set the back side of the card.
     * @param backStarter back of the StarterCard.
     */
    public void setBackStarter(BackStarter backStarter) {
        this.backStarter=backStarter;
    }

}