package it.polimi.ingsw.gc03;

public class CardStarter extends Card {

    /**
     * The card's front.
     */
    private FrontStarter front;

    /**
     * The card's back.
     */
    private BackStarter back;

    /**
     * Constructs a new CardStarter using the Card's constructor and the card's own front and back.
     *
     * @param id The unique identifier of the card.
     * @param playable Indicates whether the card is playable or not.
     */
    public CardStarter(int id, boolean playable, FrontStarter front, BackStarter back) {
        super(id, playable);
        this.front = front;
        this.back = back;
    }
}
