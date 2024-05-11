package it.polimi.ingsw.gc03.view.tui;

import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;

public class SideView {
    private CharSpecial[][] sideView = new CharSpecial[9][27];

    public CharSpecial[][] getSideView(Side side) {
        Kingdom kingdom = side.getKingdom();
        generateFrame(kingdom);
        putCornersOnFrame(side);
        return sideView;
    }

    public void putTopLeftValue(Side side){
        switch (side.getTopLeftCorner()) {
            case COVERED -> {
                for(int i = 0; i < 3; i++) {
                    for(int j = 0; j < 6; j++) {
                        sideView[i][j] = new CharSpecial(getColorFromKingdom(side.getKingdom()), ' ');
                    }
                }
                sideView[3][0] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╔');
                for(int j = 0; j < 5; j++) {
                    sideView[3][j+1] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                sideView[3][6] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╝');
                for(int i = 0; i < 2; i++) {
                    sideView[2-i][6] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[0][6] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╔');
            }
            case FUNGI, ANIMAL, PLANT, INSECT -> {
                for(int j = 0; j < 4; j++) {
                    sideView[2][j+1] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                for(int i = 0; i < 2; i++) {
                    sideView[2-i][4] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[2][0] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╠');
                sideView[2][4] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╝');
                sideView[0][4] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╦');
                sideView[1][2] = new CharSpecial(getColorFromKingdom(side.getKingdom()), getCharFromValue(side.getTopLeftCorner()));
            }
        }
    }

    public void putTopRightValue(Side side){
        switch (side.getTopRightCorner()) {
            case COVERED -> {
                for(int i = 0; i < 3; i++) {
                    for(int j = 21; j < 27; j++) {
                        sideView[i][j] = new CharSpecial(getColorFromKingdom(side.getKingdom()), ' ');
                    }
                }
                sideView[0][21] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╗');
                sideView[3][26] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╗');
                for(int j = 0; j < 4; j++) {
                    sideView[3][j+22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                sideView[3][21] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╚');
                for(int i = 0; i < 2; i++) {
                    sideView[2-i][21] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[3][21] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╚');
            }
            case FUNGI, ANIMAL, PLANT, INSECT -> {
                for(int j = 0; j < 4; j++) {
                    sideView[2][j+22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                for(int i = 0; i < 2; i++) {
                    sideView[2-i][22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[0][22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╦');
                sideView[2][22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╚');
                sideView[2][26] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╣');
                sideView[1][24] = new CharSpecial(getColorFromKingdom(side.getKingdom()), getCharFromValue(side.getTopLeftCorner()));
            }
        }
    }

    public void putBottomLeftValue(Side side){
        switch (side.getBottomLeftCorner()) {
            case COVERED -> {
                for(int i = 5; i < 9; i++) {
                    for(int j = 0; j < 6; j++) {
                        sideView[i][j] = new CharSpecial(getColorFromKingdom(side.getKingdom()), ' ');
                    }
                }
                sideView[5][0] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╚');
                for(int j = 0; j < 5; j++) {
                    sideView[5][j+1] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                sideView[5][6] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╗');
                for(int i = 0; i < 2; i++) {
                    sideView[7-i][6] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[8][6] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╚');
            }
            case FUNGI, ANIMAL, PLANT, INSECT -> {
                for(int j = 0; j < 4; j++) {
                    sideView[6][j+1] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                for(int i = 0; i < 2; i++) {
                    sideView[7-i][4] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[6][0] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╠');
                sideView[6][4] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╗');
                sideView[8][4] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╩');
                sideView[7][2] = new CharSpecial(getColorFromKingdom(side.getKingdom()), getCharFromValue(side.getTopLeftCorner()));
            }
        }
    }

    public void putBottomRightValue(Side side){
        switch (side.getBottomRightCorner()) {
            case COVERED -> {
                for(int i = 5; i < 9; i++) {
                    for(int j = 21; j < 27; j++) {
                        sideView[i][j] = new CharSpecial(getColorFromKingdom(side.getKingdom()), ' ');
                    }
                }
                sideView[5][26] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╝');
                for(int j = 0; j < 4; j++) {
                    sideView[5][j+22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                sideView[5][21] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╔');
                for(int i = 0; i < 2; i++) {
                    sideView[7-i][21] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[8][21] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╝');
            }
            case FUNGI, ANIMAL, PLANT, INSECT -> {
                for(int j = 0; j < 4; j++) {
                    sideView[6][j+22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '═');
                }
                for(int i = 0; i < 2; i++) {
                    sideView[7-i][22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '║');
                }
                sideView[6][22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╔');
                sideView[6][26] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╣');
                sideView[8][26] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╝');
                sideView[8][22] = new CharSpecial(getColorFromKingdom(side.getKingdom()), '╩');
                sideView[7][24] = new CharSpecial(getColorFromKingdom(side.getKingdom()), getCharFromValue(side.getTopLeftCorner()));
            }
        }
    }

    private void generateFrame(Kingdom kingdom){
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 27; j++){
                sideView[i][j] = new CharSpecial(getColorFromKingdom(kingdom), ' ');
            }
        }

        //Make all the lines without corners
        for(int i = 0; i<27; i++){
            sideView[0][i] = new CharSpecial(getColorFromKingdom(kingdom), '═');
        }
        for(int i = 0; i<27; i++){
            sideView[8][i] = new CharSpecial(getColorFromKingdom(kingdom), '═');
        }

        for(int i = 0; i<9; i++){
            sideView[i][0] = new CharSpecial(getColorFromKingdom(kingdom), '║');
        }
        for(int i = 0; i<9; i++){
            sideView[i][26] = new CharSpecial(getColorFromKingdom(kingdom), '║');
        }
        sideView[0][0] = new CharSpecial(getColorFromKingdom(kingdom), '╔');
        sideView[0][26] = new CharSpecial(getColorFromKingdom(kingdom), '╗');
        sideView[8][0] = new CharSpecial(getColorFromKingdom(kingdom), '╚');
        sideView[8][26] = new CharSpecial(getColorFromKingdom(kingdom), '╝');
    }

    private void putCornersOnFrame(Side side) {
        putTopLeftValue(side);
        putTopRightValue(side);
        putBottomLeftValue(side);
        putBottomRightValue(side);
    }

    private static CharSpecial[][] generateCorner(Kingdom kingdom, char topLeft, char topRight, char bottomLeft, char bottomRight) {
        CharSpecial[][] square = new CharSpecial[3][5];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 5; j++) {
                square[i][j] = new CharSpecial(getColorFromKingdom(kingdom), ' ');
            }
        }
        square[0][0] = new CharSpecial(getColorFromKingdom(kingdom), topLeft);
        square[0][4] = new CharSpecial(getColorFromKingdom(kingdom), topRight);
        square[2][0] = new CharSpecial(getColorFromKingdom(kingdom), bottomLeft);
        square[2][4] = new CharSpecial(getColorFromKingdom(kingdom), bottomRight);
        for (int j = 1; j < 4; j++) {
            square[0][j] = new CharSpecial(getColorFromKingdom(kingdom), '═');
            square[2][j] = new CharSpecial(getColorFromKingdom(kingdom), '═');
        }
        for (int i = 1; i < 2; i++) {
            square[i][0] = new CharSpecial(getColorFromKingdom(kingdom), '║');
            square[i][4] = new CharSpecial(getColorFromKingdom(kingdom), '║');
        }

        return square;
    }

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
