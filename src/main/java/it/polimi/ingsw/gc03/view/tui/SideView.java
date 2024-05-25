package it.polimi.ingsw.gc03.view.tui;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackGold;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;


/**
 * Class responsible for generating a visual representation of a side of a card.
 */
public class SideView {

    /**
     * 2D array representing the visual appearance of a side of a card.
     */
    private CharSpecial[][] sideView = new CharSpecial[9][27];


    /**
     * Generates the visual representation of a given side.
     * @param side The side of the card to visualize.
     * @return The 2D array of CharSpecial representing the visual appearance of the side.
     */
    public CharSpecial[][] getSideView(Side side) {
        Kingdom kingdom = side.getKingdom();
        generateAndPutBox(getColorFromKingdom(kingdom), 0,0, 27,9, '╔', '╗', '╚', '╝');
        putCornersOnFrame(side);
        return sideView;
    }


    /**
     * Puts the top-left value of the side on the frame.
     * @param side The side of the card.
     */
    public void putTopLeftValue(Side side){
        CharColor color = getColorFromSide(side);
        switch (side.getTopLeftCorner()) {
            case NULL -> {
                generateAndPutBox(color, 0,0,5,3,'╔', '═','║','║');
                for(int i = 0; i<2;i++){
                    for(int j = 0; j<4;j++){
                        sideView[i+1][j+1]=new CharSpecial(CharColor.WHITE,' ');
                    }
                }
            }
            case COVERED -> {
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 6; j++) {
                        sideView[i][j] = new CharSpecial(color, ' ');
                    }
                }
                sideView[3][0] = new CharSpecial(color, '╔');
                for(int j = 0; j < 5; j++) {
                    sideView[3][j+1] = new CharSpecial(color, '═');
                }
                sideView[3][6] = new CharSpecial(color, '╝');
                for(int i = 0; i < 2; i++) {
                    sideView[2-i][6] = new CharSpecial(color, '║');
                }
                sideView[0][6] = new CharSpecial(color, '╔');
            }
            case FUNGI, ANIMAL, PLANT, INSECT, QUILL, INKWELL, MANUSCRIPT -> {
                generateAndPutBox(color,0,0, 5,3,'╔','╦','╠','╝');
                sideView[1][2] = new CharSpecial(color, getCharFromValue(side.getTopLeftCorner()));
            }
            case EMPTY -> {
                generateAndPutBox(color,0,0, 5,3,'╔','╦','╠','╝');
            }
        }
    }


    /**
     * Puts the top-right value of the side on the frame.
     * @param side The side of the card.
     */
    public void putTopRightValue(Side side){
        CharColor color = getColorFromSide(side);
        switch (side.getTopRightCorner()) {
            case NULL -> {
                generateAndPutBox(color, 22,0,5,3,'═', '╗','╚','║');
                for(int i = 0; i<2;i++){
                    for(int j = 0; j<4;j++){
                        sideView[i+1][j+22]=new CharSpecial(CharColor.WHITE,' ');
                    }
                }
            }
            case COVERED -> {
                for(int i = 0; i < 3; i++) {
                    for(int j = 21; j < 27; j++) {
                        sideView[i][j] = new CharSpecial(color, ' ');
                    }
                }
                sideView[0][21] = new CharSpecial(color, '╗');
                sideView[3][26] = new CharSpecial(color, '╗');
                for(int j = 0; j < 4; j++) {
                    sideView[3][j+22] = new CharSpecial(color, '═');
                }
                sideView[3][21] = new CharSpecial(color, '╚');
                for(int i = 0; i < 2; i++) {
                    sideView[2-i][21] = new CharSpecial(color, '║');
                }
                sideView[3][21] = new CharSpecial(color, '╚');
            }
            case FUNGI, ANIMAL, PLANT, INSECT, QUILL, INKWELL, MANUSCRIPT -> {
                generateAndPutBox(color,22, 0, 5,3,'╦','╗','╚','╣');
                sideView[1][24] = new CharSpecial(color, getCharFromValue(side.getTopRightCorner()));
            }
            case EMPTY -> {
                generateAndPutBox(color,22, 0, 5,3,'╦','╗','╚','╣');
            }
        }
    }


    /**
     * Puts the bottom-left value of the side on the frame.
     * @param side The side of the card.
     */
    public void putBottomLeftValue(Side side){
        CharColor color = getColorFromSide(side);
        switch (side.getBottomLeftCorner()) {
            case NULL -> {
                generateAndPutBox(color, 0,6,5,3,'║', '.','╚','═');
                for(int i = 0; i<2;i++){
                    for(int j = 0; j<4;j++){
                        sideView[i+6][j+1]=new CharSpecial(CharColor.WHITE,' ');
                    }
                }
            }
            case COVERED -> {
                for(int i = 5; i < 9; i++) {
                    for(int j = 0; j < 6; j++) {
                        sideView[i][j] = new CharSpecial(color, ' ');
                    }
                }
                sideView[5][0] = new CharSpecial(color, '╚');
                for(int j = 0; j < 5; j++) {
                    sideView[5][j+1] = new CharSpecial(color, '═');
                }
                sideView[5][6] = new CharSpecial(color, '╗');
                for(int i = 0; i < 2; i++) {
                    sideView[7-i][6] = new CharSpecial(color, '║');
                }
                sideView[8][6] = new CharSpecial(color, '╚');
            }
            case FUNGI, ANIMAL, PLANT, INSECT, QUILL, INKWELL, MANUSCRIPT -> {
                generateAndPutBox(color,0, 6, 5,3,'╠','╗','╚','╩');
                sideView[7][2] = new CharSpecial(color, getCharFromValue(side.getBottomLeftCorner()));
            }
            case EMPTY -> {
                generateAndPutBox(color,0, 6, 5,3,'╠','╗','╚','╩');
            }
        }
    }


    /**
     * Puts the bottom-right value of the side on the frame.
     * @param side The side of the card.
     */
    public void putBottomRightValue(Side side){
        CharColor color = getColorFromSide(side);
        switch (side.getBottomRightCorner()) {
            case NULL -> {
                generateAndPutBox(color, 22,6,5,3,'.', '║','═','╝');
                for(int i = 0; i<2;i++){
                    for(int j = 0; j<4;j++){
                        sideView[i+6][j+22]=new CharSpecial(CharColor.WHITE,' ');
                    }
                }
            }
            case COVERED -> {
                for(int i = 5; i < 9; i++) {
                    for(int j = 21; j < 27; j++) {
                        sideView[i][j] = new CharSpecial(color, ' ');
                    }
                }
                sideView[5][26] = new CharSpecial(color, '╝');
                for(int j = 0; j < 4; j++) {
                    sideView[5][j+22] = new CharSpecial(color, '═');
                }
                sideView[5][21] = new CharSpecial(color, '╔');
                for(int i = 0; i < 2; i++) {
                    sideView[7-i][21] = new CharSpecial(color, '║');
                }
                sideView[8][21] = new CharSpecial(color, '╝');
            }
            case FUNGI, ANIMAL, PLANT, INSECT, QUILL, INKWELL, MANUSCRIPT -> {
                generateAndPutBox(color,22, 6, 5,3,'╔','╣','╩','╝');
                sideView[7][24] = new CharSpecial(color, getCharFromValue(side.getBottomRightCorner()));
            }
            case EMPTY -> {
                generateAndPutBox(color,22, 6, 5,3,'╔','╣','╩','╝');
            }
        }
    }


    /**
     * Puts the central boxes on the side.
     * @param side The side of the card.
     */
    private void putCentralBoxes(Side side){
        CharColor color = getColorFromSide(side);
        if(side instanceof FrontResource){
            if(((FrontResource) side).getPoint() != 0){
                generateAndPutBox(color,11, 0, 5,3,'╦','╦','╚','╝');
                char pointChar = (char) ((char)((FrontResource) side).getPoint()+'0');
                sideView[1][13] = new CharSpecial(color, pointChar);
            }
        }
        if(side instanceof BackSide){
            int count = ((BackSide) side).getCenter().size();
            int centerX = ((2*count+3)-1)/2;
            generateAndPutBox(color,13-centerX, 3, 2*count+3,3,'╔','╗','╚','╝');
            int iterativeCenterPos = 13-centerX+2;
            for(int i = 0; i<count; i++){
                char c = getCharFromValue(((BackSide) side).getCenter().get(i));
                sideView[4][iterativeCenterPos] =  new CharSpecial(color, c);
                iterativeCenterPos++;
                sideView[4][iterativeCenterPos] =  new CharSpecial(color, ' ');
                iterativeCenterPos++;
            }
        }
        if(side instanceof FrontGold){
            int count = ((FrontGold) side).getRequirementPlacement().size();
            int centerX = ((2*count+3)-1)/2;
            generateAndPutBox(color,13-centerX, 6, 2*count+3,3,'╔','╗','╩','╩');
            int iterativeCenterPos = 13-centerX+2;
            for(int i = 0; i<count; i++){
                char c = getCharFromValue(((FrontGold) side).getRequirementPlacement().get(i));
                sideView[7][iterativeCenterPos] =  new CharSpecial(color, c);
                iterativeCenterPos++;
                sideView[7][iterativeCenterPos] =  new CharSpecial(color, ' ');
                iterativeCenterPos++;
            }
            generateAndPutBox(color,9, 0, 5,3,'╦','╦','╚','╩');
            sideView[1][11] = new CharSpecial(color, (char) ('0' + ((FrontGold) side).getPoint()));
            generateAndPutBox(color,13, 0, 5,3,'╦','╦','╩','╝');
            sideView[1][15] = new CharSpecial(color, getCharFromValue(((FrontGold) side).getRequirementPoint()));
        }
    }


    /**
     * Puts the corner values on the frame.
     * @param side The side of the card.
     */
    private void putCornersOnFrame(Side side) {
        putTopLeftValue(side);
        putTopRightValue(side);
        putBottomLeftValue(side);
        putBottomRightValue(side);
        putCentralBoxes(side);
    }


    /**
     * Generates and puts a box on the side view.
     * @param color The color of the box.
     * @param x The x-coordinate of the top-left corner of the box.
     * @param y The y-coordinate of the top-left corner of the box.
     * @param width The width of the box.
     * @param height The height of the box.
     * @param topLeft The character for the top-left corner of the box.
     * @param topRight The character for the top-right corner of the box.
     * @param bottomLeft The character for the bottom-left corner of the box.
     * @param bottomRight The character for the bottom-right corner of the box.
     */
    private void generateAndPutBox(CharColor color, int x, int y, int width, int height, char topLeft, char topRight, char bottomLeft, char bottomRight) {
        CharSpecial[][] box = new CharSpecial[height][width];
        for(int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                box[i][j] = new CharSpecial(color, ' ');
            }
        }
        for (int j = 1; j < width-1; j++) {
            box[0][j] = new CharSpecial(color, '═');
            box[height-1][j] = new CharSpecial(color, '═');
        }
        for (int i = 1; i < height-1; i++) {
            box[i][0] = new CharSpecial(color, '║');
            box[i][width-1] = new CharSpecial(color, '║');
        }
        box[0][0] = new CharSpecial(color, topLeft);
        box[0][width-1] = new CharSpecial(color, topRight);
        box[height-1][0] = new CharSpecial(color, bottomLeft);
        box[height-1][width-1] = new CharSpecial(color, bottomRight);

        for (int j = 0; j < height; j++) {
            for (int k = 0; k < width; k++) {
                sideView[y+j][x+k] = box[j][k];
            }
        }
    }


    /**
     * Converts a Value enum to its corresponding character representation.
     * @param value The value to convert.
     * @return The corresponding character.
     */
    private static char getCharFromValue(Value value) {
        switch (value) {
            case FUNGI:
                return 'F';
            case PLANT:
                return 'P';
            case ANIMAL:
                return 'A';
            case INSECT:
                return 'I';
            case QUILL:
                return 'Q';
            case INKWELL:
                return 'K';
            case MANUSCRIPT:
                return 'M';
            case COVERED:
                return 'C';
            case EMPTY:
                return ' ';
            case NULL:
            default:
                return 'N';
        }
    }


    /**
     * Converts a Kingdom enum to its corresponding character representation.
     * @param kingdom The kingdom to convert.
     * @return The corresponding character.
     */
    private static char getCharFromKingdom(Kingdom kingdom) {
        switch (kingdom) {
            case FUNGI:
                return 'F';
            case PLANT:
                return 'P';
            case ANIMAL:
                return 'A';
            case INSECT:
                return 'I';
            default:
                return 'N'; // NULL
        }
    }


    /**
     * Gets the color corresponding to a side.
     * @param side The side to get the color for.
     * @return The corresponding color.
     */
    private static CharColor getColorFromSide(Side side) {
        CharColor color = getColorFromKingdom(side.getKingdom());
        if(side instanceof FrontGold){
            return CharColor.GOLD;
        }
        if(side instanceof BackGold){
            return CharColor.GOLD;
        }
        return color;
    }


    /**
     * Gets the color corresponding to a kingdom.
     * @param kingdom The kingdom to get the color for.
     * @return The corresponding color.
     */
    private static CharColor getColorFromKingdom(Kingdom kingdom) {
        CharColor color;
        switch (kingdom) {
            case ANIMAL:
                color = CharColor.BLUE;
                break;
            case PLANT:
                color = CharColor.GREEN;
                break;
            case FUNGI:
                color = CharColor.RED;
                break;
            case INSECT:
                color = CharColor.MAGENTA;
                break;
            case null:
            default:
                color = CharColor.WHITE;
                break;
        }
        return color;
    }


}
