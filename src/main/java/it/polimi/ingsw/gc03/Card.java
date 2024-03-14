package it.polimi.ingsw.gc03;

/**
 * This class represents a card in the game.
 */
public class Card {
    /**
     * The unique identifier of the card.
     */
    private int id;

    /**
     * Indicates whether the card is playable or not.
     */
    private boolean playable;

    /**
     * Constructs a new Card with the specified identifier and playability.
     *
     * @param id The unique identifier of the card.
     * @param playable Indicates whether the card is playable or not.
     */
    public Card(int id, boolean playable) {
        this.id = id;
        this.playable = playable;
    }

    /**
     * Returns the unique identifier of the card.
     *
     * @return The unique identifier of the card.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the card.
     *
     * @param id The unique identifier of the card.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns whether the card is playable or not.
     *
     * @return {@code true} if the card is playable, {@code false} otherwise.
     */
    public boolean isPlayable() {
        return playable;
    }

    /**
     * Sets whether the card is playable or not.
     *
     * @param playable {@code true} if the card is playable, {@code false} otherwise.
     */
    public void setPlayable(boolean playable) {
        this.playable = playable;
    }
}
