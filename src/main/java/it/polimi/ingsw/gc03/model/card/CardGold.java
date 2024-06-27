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

}