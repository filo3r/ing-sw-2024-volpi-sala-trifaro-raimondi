package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.listeners.GameListener;
import it.polimi.ingsw.gc03.listeners.ListenersHandler;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Color;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.PlayerAction;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.back.BackSide;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;

import static it.polimi.ingsw.gc03.model.enumerations.Color.createColorArrayList;


/**
 * This class represents a player in the game.
 */
public class Player implements Serializable {

    /**
     * Player's nickname.
     */
    private String nickname;

    /**
     * Player's number.
     */
    private int number;

    /**
     * Player's color.
     */
    private Color color;

    /**
     * Player's starting card.
     */
    private CardStarter cardStarter;

    /**
     * Player's personal objective cards.
     * Before the game begins, the player must choose one.
     */
    private ArrayList<CardObjective> cardObjective;

    /**
     * Player's hand.
     */
    private ArrayList<Card> hand;

    /**
     * Player's codex.
     */
    private Codex codex;

    /**
     * Points obtained from Objective cards.
     */
    private int pointObjective;

    /**
     * Player's score.
     */
    private int score;

    /**
     * Player's online status
     */
    private boolean online;

    /**
     * The player can no longer play because he cannot place any cards.
     */
    private boolean skipTurn;

    /**
     * Player's action: what can the player currently do.
     */
    private PlayerAction action;

    /**
     * Number of Resource cards initially in hand.
     */
    private static final int INITIAL_CARD_RESOURCE = 2;

    /**
     * Number of Gold cards initially in hand.
     */
    private static final int INITIAL_CARD_GOLD = 1;

    /**
     * Number of Objective cards initially in hand.
     */
    private static final int INITIAL_CARD_OBJECTIVE = 2;

    /**
     * Number of Objective cards the player must choose to keep.
     */
    public static final int FINAL_CARD_OBJECTIVE = 1;

    /**
     * Player's game listener.
     */
    private transient GameListener selfListener;

    /**
     * Constructor of the Player class.
     * @param nickname Player's nickname.
     * @param number Player's number.
     * @param desk The game desk.
     */
    public Player(String nickname, int number, Desk desk, Game game, GameListener gameListener) throws RemoteException {
        selfListener = gameListener;
        this.nickname = nickname;
        this.number = number++;
        this.color = createColorArrayList().get(number - 1);
        // Assignment of the Starter card
        this.cardStarter = (CardStarter) desk.drawCardDeck(desk.getDeckStarter());
        // Assignment of Objective cards
        this.cardObjective = new ArrayList<>(INITIAL_CARD_OBJECTIVE);
        for (int i = 0; i < INITIAL_CARD_OBJECTIVE; i++) {
            this.cardObjective.add((CardObjective) desk.drawCardDeck(desk.getDeckObjective()));
        }
        // Assignment of Resource cards and Gold cards in the hand
        this.hand = new ArrayList<>(INITIAL_CARD_RESOURCE + INITIAL_CARD_GOLD);
        for (int i = 0; i < INITIAL_CARD_RESOURCE; i++) {
            addCardToHand((CardResource) desk.drawCardDeck(desk.getDeckResource()));
        }
        for (int i = 0; i < INITIAL_CARD_GOLD; i++) {
            addCardToHand((CardGold) desk.drawCardDeck(desk.getDeckGold()));
        }
        this.codex = new Codex();
        this.pointObjective = 0;
        this.score = 0;
        this.online = true;
        this.skipTurn = false;
        this.action = PlayerAction.FIRSTMOVES;
    }


    /**
     * Method for making the player choose his Objective card.
     * @param index The index of the card the player wants to keep.
     * @return A boolean indicating whether the operation was successful or not.
     */
    public boolean selectObjectiveCard(int index, Game game) {
        if (index < 0 || index >= this.cardObjective.size()) {
            return false;
        } else {
            ArrayList<CardObjective> newCardObjective = new ArrayList<>(FINAL_CARD_OBJECTIVE);
            for (int i = 0; i < this.cardObjective.size(); i++) {
                if (i == index) {
                    newCardObjective.add(this.cardObjective.get(i));
                }
            }
            this.cardObjective.clear();
            this.cardObjective.addAll(newCardObjective);
            game.getListener().notifyObjectiveCardChosen(game, this.cardObjective.getLast(), this.getNickname());
            return true;
        }
    }


    /**
     * Method to check if the player can no longer place cards in his Codex. If the player can no longer place cards,
     * he skips his turns until the end of the game.
     */
    public void checkSkipTurn() {
        int skip = 0;
        // The sides of the cards that have fewer requirements to be placed are the BackSide
        ArrayList<Value> center = new ArrayList<>(1);
        center.add(Value.EMPTY);
        BackSide backSide = new BackSide(Kingdom.NULL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY, center);
        // Check all cells of the Codex
        for (int i = this.codex.getMinRow() - 1; i <= this.codex.getMaxRow() + 1; i++) {
            for (int j = this.codex.getMinColumn() - 1; j <= this.codex.getMaxColumn() + 1; j++) {
                boolean insertionPossible = false;
                try {
                insertionPossible = this.codex.simulateInsertIntoCodex(backSide, i, j);
                } catch (Exception ignored){}
                if (insertionPossible)
                    skip++;
                if (skip != 0)
                    return;
            }
        }
        // If you can't insert the card into any cell
        if (skip == 0)
            this.skipTurn = true;
    }


    /**
     * Method for calculating the points scored by the player with the Objective cards.
     * @param desk The game desk.
     */
    public void calculatePointObjective(Desk desk) {
        // Points earned with the personal Objective card
        for (int i = 0; i < FINAL_CARD_OBJECTIVE; i++) {
            this.pointObjective = this.pointObjective + this.cardObjective.get(i).calculateScore(this.codex,
                    this.cardObjective.get(i).getPoint(), this.cardObjective.get(i).getParameters());
        }
        // Points earned with common Objective cards
        ArrayList<CardObjective> displayedObjective = desk.getDisplayedObjective();
        for (CardObjective objective : displayedObjective) {
            this.pointObjective = this.pointObjective + objective.calculateScore(this.codex, objective.getPoint(),
                    objective.getParameters());
        }
    }


    /**
     * Method for calculating the total score made by the player.
     */
    public void calculatePlayerScore() {
        this.score = this.score + this.codex.getPointCodex() + this.pointObjective;
    }


    /**
     * Method to get the player's nickname.
     * @return The player's nickname.
     */
    public String getNickname() {
        return nickname;
    }


    /**
     * Method to set the player's nickname.
     * @param nickname The nickname to set
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    /**
     * Method to get the player's number.
     * @return The player's number.
     */
    public int getNumber() {
        return number;
    }


    /**
     * Method to set the player's number.
     * @param number The player's number.
     */
    public void setNumber(int number) {
        this.number = number;
    }


    /**
     * Method to get the player's color.
     * @return The player's color.
     */
    public Color getColor() {
        return color;
    }


    /**
     * Method to set the player's color.
     * @param color The player's color.
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Method to get the player's Starter card.
     * @return The player's Starter card.
     */
    public CardStarter getCardStarter() {
        return cardStarter;
    }


    /**
     * Method to set the player's Starter card.
     * @param cardStarter The player's Starter card.
     */
    public void setCardStarter(CardStarter cardStarter) {
        this.cardStarter = cardStarter;
    }


    /**
     * Method to get the player's Objective cards.
     * @return The player's Objective cards.
     */
    public ArrayList<CardObjective> getCardObjective() {
        return cardObjective;
    }


    /**
     * Method to set the player's Objective cards.
     * @param cardObjective The player's Objective cards.
     */
    public void setCardObjective(ArrayList<CardObjective> cardObjective) {
        this.cardObjective = cardObjective;
    }


    /**
     * Method to get the player's hand.
     * @return The player's hand.
     */
    public ArrayList<Card> getHand() {
        return hand;
    }


    /**
     * Method to set the player's hand.
     * @param hand The player's hand.
     */
    public void setHand(ArrayList<Card> hand) {
        this.hand = hand;
    }


    /**
     * Method to get the player's codex.
     * @return The player's codex.
     */
    public Codex getCodex() {
        return codex;
    }


    /**
     * Method to set the player's codex.
     * @param codex The player's codex.
     */
    public void setCodex(Codex codex) {
        this.codex = codex;
    }


    /**
     * Method to get player's points obtained with Objective cards.
     * @return The player's points.
     */
    public int getPointObjective() {
        return pointObjective;
    }


    /**
     * Method to set player's points obtained with Objective cards.
     * @param pointObjective The player's points.
     */
    public void setPointObjective(int pointObjective) {
        this.pointObjective = pointObjective;
    }


    /**
     * Method to get the player's score.
     * @return The player's score.
     */
    public int getScore() {
        return score;
    }


    /**
     * Method to set the player's score.
     * @param score The player's score.
     */
    public void setScore(int score) {
        this.score = score;
    }


    /**
     * Method to get the player's online status.
     * @return The player's online status.
     */
    public boolean getOnline() {
        return online;
    }


    /**
     * Method to set the player's online status.
     * @param online The player's online status.
     */
    public void setOnline(Game game, boolean online, GameController gameController) {
        if(this.online && !online){
            game.getListener().notifyPlayerDisconnected(game, this.getNickname());

        }
        if(!this.online && online){
            game.getListener().notifyPlayerReconnected(game, this.getNickname());
        }
        this.online = online;
    }


    /**
     * Method to get if the player skips his turns until the end of the game.
     * @return The player's skipTurn.
     */
    public boolean getSkipTurn() {
        return skipTurn;
    }


    /**
     * Method to set if the player skips his turns until the end of the game.
     * @param skipTurn The player's skipTurn.
     */
    public void setSkipTurn(boolean skipTurn) {
        this.skipTurn = skipTurn;
    }


    /**
     * Method to get the player's action.
     * @return The player's action.
     */
    public PlayerAction getAction() {
        return action;
    }


    /**
     * Method to set the player's action.
     * @param action The player's action.
     */
    public void setAction(PlayerAction action) {
        this.action = action;
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
     * @return the player self listener
     */
    public GameListener getSelfListener() {
        return selfListener;
    }

}