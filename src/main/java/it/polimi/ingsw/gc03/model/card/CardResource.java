package it.polimi.ingsw.gc03.model.card;

import it.polimi.ingsw.gc03.model.side.back.BackResource;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import java.io.Serializable;

/**
 * This class represents a Resource card.
 */
public class CardResource extends Card implements Serializable {

    /**
     * Kingdom to which the card belongs.
     */
    private Kingdom kingdom;

    /**
     * The front side of the card.
     */
    private FrontResource frontResource;

    /**
     * The back side of the card.
     */
    private BackResource backResource;


    /**
     * Constructor for the CardResource class.
     * @param idCard The unique identifier of the card.
     * @param kingdom The kingdom to which the card belongs.
     * @param frontResource The front side of the card.
     * @param backResource The back side of the card.
     */
    public CardResource(String idCard, Kingdom kingdom, FrontResource frontResource, BackResource backResource) {
        super(idCard, true);
        this.kingdom = kingdom;
        this.frontResource = frontResource;
        this.backResource = backResource;
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
    public FrontResource getFrontResource() {
        return frontResource;
    }

    /**
     * Setter method to set the front side of the card.
     * @param frontResource front of ResourceCard.
     */
    public void setFrontResource(FrontResource frontResource) {
        this.frontResource = frontResource;
    }

    /**
     * Getter method to retrieve the back side of the card.
     * @return The back side of the card.
     */
    public BackResource getBackResource() {
        return backResource;
    }

    /**
     * Setter method to set the back side of the card.
     * @param backResource back of ResourceCard.
     */
    public void setBackResource(BackResource backResource){
        this.backResource=backResource;
    }
}
