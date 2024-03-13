package it.polimi.ingsw.gc03;

/**
 * This class represents a player in the game.
 */
public class Player {

    /**
     * Player's nickname.
     */
    private String nickname;

    /**
     * Player's starting card.
     */
    private CardStarter cardStarter;

    /**
     * Player's hand.
     */
    private Card[] hand;

    /**
     * Player's personal objective card.
     */
    private CardObjective cardObjective;

    /**
     * Player's codex.
     */
    private Codex codex;

    /**
     * Player's score.
     */
    private int score;

    /**
     * Constructs a new Player with the specified parameters.
     *
     * @param nickname The player's nickname.
     * @param cardStarter The player's starting card.
     * @param hand The player's hand.
     * @param cardObjective The player's personal objective card.
     * @param codex The player's codex.
     * @param score The player's score.
     */
    public Player(String nickname, CardStarter cardStarter, Card[] hand, CardObjective cardObjective, Codex codex, int score) {
        this.nickname = nickname;
        this.cardStarter = cardStarter;
        this.hand = hand;
        this.cardObjective = cardObjective;
        this.codex = codex;
        this.score = score;
    }

    /**
     * Returns the player's nickname.
     *
     * @return the player's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Sets the player's nickname.
     *
     * @param nickname the player's nickname.
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Returns the player's starting card.
     *
     * @return the player's starting card.
     */
    public CardStarter getCardStarter() {
        return cardStarter;
    }

    /**
     * Sets the player's starting card.
     *
     * @param cardStarter the player's starting card.
     */
    public void setCardStarter(CardStarter cardStarter) {
        this.cardStarter = cardStarter;
    }

    /**
     * Returns the player's hand.
     *
     * @return the player's hand.
     */
    public Card[] getHand() {
        return hand;
    }

    /**
     * Sets the player's hand.
     *
     * @param hand the player's hand.
     */
    public void setHand(Card[] hand) {
        this.hand = hand;
    }

    /**
     * Returns the player's personal objective card.
     *
     * @return the player's personal objective card.
     */
    public CardObjective getCardObjective() {
        return cardObjective;
    }

    /**
     * Sets the player's personal objective card.
     *
     * @param cardObjective the player's personal objective card.
     */
    public void setCardObjective(CardObjective cardObjective) {
        this.cardObjective = cardObjective;
    }

    /**
     * Returns the player's codex.
     *
     * @return the player's codex.
     */
    public Codex getCodex() {
        return codex;
    }

    /**
     * Sets the player's codex.
     *
     * @param codex the player's codex.
     */
    public void setCodex(Codex codex) {
        this.codex = codex;
    }

    /**
     * Returns the player's score.
     *
     * @return the player's score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Sets the player's score.
     *
     * @param score the player's score.
     */
    public void setScore(int score) {
        this.score = score;
    }
}
