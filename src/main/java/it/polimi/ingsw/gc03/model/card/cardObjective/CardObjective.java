package it.polimi.ingsw.gc03.model.card.cardObjective;

import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.enumerations.Value;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents a Objective card.
 */
public class CardObjective extends Card implements Serializable {

    /**
     * Textual description of the card's objective.
     */
    private String objective;

    /**
     * Card points.
     */
    private int point;

    /**
     * Parameters used to calculate the points.
     * In the case of Objective cards that use diagonal, the values in the array must be written in order from left to
     * right of the card.
     * In the case of Objective cards that use the pile, the values in the array must be written starting from the card
     * in the codex that gives rise to the pile.
     */
    private ArrayList<Value> parameters;

    /**
     * Strategy to apply for calculating the points given by the objective.
     */
    private CalculateScoreStrategy scoreStrategy;

    /**
     * Image of the card.
     */
    private String image;

    /**
     * Path to the image folder.
     */
    private static final String IMAGE_PATH = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "main" + File.separator + "resources" + File.separator + "it" + File.separator + "polimi"
            + File.separator + "ingsw" + File.separator + "gc03" + File.separator + "images" + File.separator +
            "cards" + File.separator + "frontSide" + File.separator;

    /**
     * Constructor for the CardObjective class.
     * @param idCard The unique identifier of the card.
     * @param objective Textual description of the card's objective.
     * @param point The points of the card.
     * @param parameters The parameters used to calculate the points.
     * @param scoreStrategy Strategy to apply for calculating the points given by the objective.
     */
    public CardObjective(String idCard, String objective, int point, ArrayList<Value> parameters,
                         CalculateScoreStrategy scoreStrategy) {
        super(idCard, false);
        this.objective = objective;
        this.point = point;
        this.parameters = new ArrayList<>(parameters);
        this.scoreStrategy = scoreStrategy;
        this.image = IMAGE_PATH + idCard + "_front.png";
    }

    /**
     * Method for calculating points.
     * @param codex The codex on which points must be calculated.
     * @param point The points of the card.
     * @param parameters Parameters used to calculate the points.
     * @return The calculated points.
     */
    public int calculateScore(Codex codex, int point, ArrayList<Value> parameters) {
        return scoreStrategy.calculateScore(codex, point, parameters);
    }

    /**
     * Method to get the textual description of the card's objective.
     * @return The textual description of the card's objective.
     */
    public String getObjective() {
        return this.objective;
    }

    /**
     * Method to set the textual description of the card's objective.
     * @param objective The textual description of the card's objective.
     */
    public void setObjective(String objective) {
        this.objective = objective;
    }

    /**
     * Method to get the strategy to apply for calculating the points given by the objective.
     * @return The strategy to apply for calculating the points given by the objective.
     */
    public CalculateScoreStrategy getScoreStrategy() {
        return this.scoreStrategy;
    }

    /**
     * Method to set the strategy to apply for calculating the points given by the objective.
     * @param scoreStrategy The strategy to apply for calculating the points given by the objective.
     */
    public void setScoreStrategy(CalculateScoreStrategy scoreStrategy) {
        this.scoreStrategy = scoreStrategy;
    }

    /**
     * Getter method to retrieve the points on the card.
     * @return The points on the card.
     */
    public int getPoint() {
        return point;
    }

    /**
     * Setter method to set the points on the card.
     * @param point The points to be set for the card.
     */
    public void setPoint(int point) {
        this.point = point;
    }

    /**
     * Getter method to retrieve the parameters of the card.
     * @return The parameters of the card.
     */
    public ArrayList<Value> getParameters() {
        return parameters;
    }

    /**
     * Setter method to set the parameters of the card.
     * @param parameters The parameters to be set for the card.
     */
    public void setParameters(ArrayList<Value> parameters) {
        this.parameters = parameters;
    }

    /**
     * Getter method to retrieve the image of the card.
     * @return The image of the card.
     */
    public String getImage() {
        return image;
    }

    /**
     * Setter method to set the image of the card.
     * @param image The image to be set for the card.
     */
    public void setImage(String image) {
        this.image = image;
    }
}