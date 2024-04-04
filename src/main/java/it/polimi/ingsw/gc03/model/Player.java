package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.card.objective.CardObjective;
import it.polimi.ingsw.gc03.model.card.CardStarter;

import java.util.List;

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
    private List<Card> hand;

    /**
     * Player's personal objective card.
     */
    private CardObjective cardObjective;

    /**
     * Player's color.
     */
    private char color;


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
     * @param nickname Player's nickname.
     * @param cardStarter Player's starting card.
     * @param hand Player's hand.
     * @param cardObjective Player's personal objective card.
     * @param codex Player's codex.
     * @param score Player's score.
     */
    public Player(String nickname, CardStarter cardStarter, List<Card> hand, CardObjective cardObjective, Codex codex, int score, char color) {
        this.nickname = nickname;
        this.cardStarter = cardStarter;
        this.hand = hand;
        this.cardObjective = cardObjective;
        this.codex = codex;
        this.score = score;
        this.color = color;
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
    public List<Card> getHand() {
        return hand;
    }

    /**
     * Sets the player's hand.
     *
     * @param hand the player's hand.
     */
    public void setHand(List<Card> hand) {
        this.hand = hand;
    }


    /**
     * Add a card to the player's hand.
     * @param card the card to add to the player's hand.
     */
    public void addCardToHand(Card card){
        this.hand.add(card);
    }

    /**
     * Remove a card to the player's hand.
     * @param CardPositionInHand The position of the card in the player's hand.
     */
    public void removeCardFromHand(int CardPositionInHand){
        this.hand.remove(CardPositionInHand);
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

    /**
     * Calculates the player's score based on his codex at the end of the game.
     *
     * @return The player's score.
     */
    public int calculateScore(){
        // Metodo per calcolare il punteggio del player.
        return 0;
    }

    /**
     * Place a card in a determinate position on the codex.
     */
    public void place(){
    }

    /**
     * Select the personal cardObjectives.
     */
    public void selectObjective(){
    }

    /**
     * Choose whether place the starting card front or back.
     */
    public void selectCardStarter(){
    }

    /**
     * Returns the player's color.
     * @return the player's color.
     */
    public char getColor() {
        return color;
    }
    /**
     * Sets the player's color.
     * @param color the player's color.
     */
    public void setColor(char color) {
        this.color = color;
    }
}
