package it.polimi.ingsw.gc03;

public class CardObjective {
    /**
     * Objective card's front
     */
    private FrontObjective front;

    /**
     * Objective card's back
     */
    private BackObjective back;

    /**
     * Constructs a new CardObjective with the specified front and back.
     *
     * @param front The card's front.
     * @param back The card's back.
     */
    public CardObjective(FrontObjective front, BackObjective back) {
        this.front = front;
        this.back = back;
    }

    /**
     * Returns the card's front.
     *
     * @return The card's front.
     */
    public FrontObjective getFront() {
        return front;
    }

    /**
     * Sets the card's front.
     *
     * @param front The card's front.
     */
    public void setFront(FrontObjective front) {
        this.front = front;
    }

    /**
     * Returns the card's back.
     *
     * @return The card's front.
     */
    public BackObjective getBack() {
        return back;
    }

    /**
     * Sets the card's back.
     *
     * @param back The card's front.
     */
    public void setBack(BackObjective back) {
        this.back = back;
    }
}
