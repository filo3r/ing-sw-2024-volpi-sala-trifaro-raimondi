package it.polimi.ingsw.gc03.view.tui;

public class CharSpecial {
    CharColor color;
    // The actual char
    char c;
    // width position
    int x;
    // height position
    int y;

    public CharSpecial(CharColor color, char c, int x, int y) {
        this.color = color;
        this.c = c;
        this.x = x;
        this.y = y;
    }
}
