package it.polimi.ingsw.gc03.model.card;

import java.io.Serializable;

/**
 * This class represents a card in the game.
 */
public class Card implements Serializable {

    /**
     * The unique identifier of the card.
     */
    private String idCard;

    /**
     * Indicates whether the card is playable or not.
     */
    private boolean playable;

    /**
     * Constructs a new Card with the specified identifier and playability.
     * @param idCard The unique identifier of the card.
     * @param playable Indicates whether the card is playable or not.
     */
    public Card(String idCard, boolean playable) {
        this.idCard = idCard;
        this.playable = playable;
    }

    /**
     * Returns the unique identifier of the card.
     * @return The unique identifier of the card.
     */
    public String getIdCard() {
        return idCard;
    }

    /**
     * Sets the unique identifier of the card.
     * @param idCard The unique identifier of the card.
     */
    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    /**
     * Returns whether the card is playable or not.
     * @return True if the card is playable, false otherwise.
     */
    public boolean isPlayable() {
        return playable;
    }

    /**
     * Sets whether the card is playable or not.
     * @param playable True if the card is playable, false otherwise.
     */
    public void setPlayable(boolean playable) {
        this.playable = playable;
    }

}