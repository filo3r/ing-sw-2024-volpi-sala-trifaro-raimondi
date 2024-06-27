package it.polimi.ingsw.gc03.view.tui;

/**
 * Class representing a special character with a specific color.
 */
public class CharSpecial {

    /**
     * The color of the character.
     */
    CharColor color;

    /**
     * The actual character.
     */
    char c;

    /**
     * Constructs a CharSpecial with the specified color and character.
     * @param color The color of the character.
     * @param c The actual character.
     */
    public CharSpecial(CharColor color, char c) {
        this.color = color;
        this.c = c;

    }

}