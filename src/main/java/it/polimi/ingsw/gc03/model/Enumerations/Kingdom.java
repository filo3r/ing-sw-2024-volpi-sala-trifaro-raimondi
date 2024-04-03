package it.polimi.ingsw.gc03.model.Enumerations;

/**
 * This enumeration class represents the available kingdoms in the game.
 * Resource cards and Gold cards can belong to one of the kingdoms defined here.
 */
public enum Kingdom {

    /**
     * Fungi kingdom.
     */
    FUNGI,

    /**
     * Plant kingdom.
     */
    PLANT,

    /**
     * Animal kingdom.
     */
    ANIMAL,

    /**
     * Insect kingdom.
     */
    INSECT,

    /**
     * No kingdom.
     */
    NULL;


    /**
     * Method for converting a Value value to a Kingdom value.
     * @param value The value to convert.
     * @return The corresponding value Kingdom.
     */
    public static Kingdom fromValue(Value value) {
        switch (value) {
            case FUNGI:
                return FUNGI;
            case PLANT:
                return PLANT;
            case ANIMAL:
                return ANIMAL;
            case INSECT:
                return INSECT;
            default:
                return NULL;
        }
    }


}
