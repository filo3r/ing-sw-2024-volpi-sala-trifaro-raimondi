package it.polimi.ingsw.gc03;

public class FrontObjective {
    /**
     * Objective of the card.
     */
    private Objective cardObjective;

    /**
     * Constructs a new FrontObjective with the specified objective.
     *
     * @param cardObjective The card's objective
     */
    public FrontObjective(Objective cardObjective) {
        this.cardObjective = cardObjective;
    }

    /**
     * Returns the card's objective.
     *
     * @return The card's objective.
     */
    public Objective getCardObjective() {
        return cardObjective;
    }

    /**
     * Sets the card's objective.
     *
     * @param cardObjective The card's objective.
     */
    public void setCardObjective(Objective cardObjective) {
        this.cardObjective = cardObjective;
    }
}
