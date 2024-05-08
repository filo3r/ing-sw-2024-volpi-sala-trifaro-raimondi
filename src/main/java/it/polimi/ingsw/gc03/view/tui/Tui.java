package it.polimi.ingsw.gc03.view.tui;

import com.sun.source.util.TaskListener;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import it.polimi.ingsw.gc03.model.side.front.FrontStarter;


import static it.polimi.ingsw.gc03.view.tui.AsyncPrint.*;

public class Tui {

    private String nickname;
    private CharSpecial[][] screenSim;
    private int screenWidth;
    private int screenHeight;

    private String[][] screenToPrint;

    public void showCodex(Game game){
        StringBuilder codexText = new StringBuilder();

    }

    public void Tui(int width, int height){
        screenWidth = width;
        screenHeight = height;
        screenSim = new CharSpecial[screenHeight][screenWidth];
        screenToPrint = new String[screenHeight][screenWidth*3];
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth * 3; j++) {
                screenToPrint[i][j] = "";
            }
        }
        clearScreen(' ');
        // The following is just a test to print two cards one near the other one.
        showSide(new FrontResource(Kingdom.ANIMAL, Value.FUNGI, Value.FUNGI, Value.QUILL,Value.FUNGI, 1), 1,1);
        showSide(new FrontResource(Kingdom.ANIMAL, Value.ANIMAL, Value.EMPTY, Value.INSECT,Value.FUNGI, 9), 1,25);

    }

    public void refreshScreen() {
        getScreenToPrint();
        StringBuilder screenText = new StringBuilder();
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screenText.append(screenToPrint[i][j * 3]).append(screenToPrint[i][j * 3 + 1]).append(screenToPrint[i][j * 3 + 2]);
            }
            screenText.append("\n");
        }
        System.out.print(screenText);
    }

    public void clearScreen(char fillChar) {
        char topLeft = '┌';
        char topRight = '┐';
        char bottomLeft = '└';
        char bottomRight = '┘';
        char horizontalLine = '─';
        char verticalLine = '│';

        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                if (i == 0) {
                    if (j == 0) {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, topLeft,j,i);
                    } else if (j == screenWidth - 1) {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, topRight,j,i);
                    } else {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, horizontalLine,j,i);
                    }
                } else if (i == screenHeight - 1) {
                    if (j == 0) {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, bottomLeft,j,i);
                    } else if (j == screenWidth - 1) {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, bottomRight,j,i);
                    } else {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, horizontalLine,j,i);
                    }
                } else if (j == 0 || j == screenWidth - 1) {
                    screenSim[i][j] = new CharSpecial(CharColor.WHITE, verticalLine,j,i);
                } else {
                    screenSim[i][j] = new CharSpecial(CharColor.WHITE, fillChar,j,i);
                }
            }
        }
        screenSim[screenHeight - 2][1] = new CharSpecial(CharColor.WHITE, '>',screenHeight - 2,1);
        refreshScreen();
    }

    public void showSide(Side side, int row, int col){
        StringBuilder sideText = new StringBuilder();
        CharSpecial[][] sideArray = new CharSpecial[9][23];
        Kingdom kingdom;
        String sideTemplate = "╔═╦══════╦═══╦══════╦═╗║ ║      ║   ║      ║ ║╠═╩══════╩═══╩══════╩═╣║        ╔═══╗        ║║        ║   ║        ║║        ╚═══╝        ║╠═╗                 ╔═╣║ ║                 ║ ║╚═╩═════════════════╩═╝";
        if (side instanceof FrontResource) {
            side = (FrontResource) side;
            kingdom = side.getKingdom();
            if (kingdom.equals(Kingdom.ANIMAL)) {
                CharColor charColor = CharColor.BLUE;
                // Create an empty template of the side
                int index = 0;
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 23; j++) {
                        char currentChar = sideTemplate.charAt(index++);
                        if (currentChar != '\n') {
                            sideArray[i][j] = new CharSpecial(charColor, currentChar, i, j);
                        }
                    }
                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 23; j++) {
                        if (sideArray[i][j].c == ' ') {
                            sideArray[i][j] = new CharSpecial(CharColor.BLUE, ' ', i, j);
                        }
                    }
                }
                // Insert the values in the empty spots
                sideArray[1][1] = new CharSpecial(CharColor.BLUE, getCharFromValue(side.getTopLeftCorner()), 1, 1);
                sideArray[1][21] = new CharSpecial(CharColor.BLUE, getCharFromValue(side.getTopRightCorner()), 1, 21);
                sideArray[7][1] = new CharSpecial(CharColor.BLUE, getCharFromValue(side.getBottomLeftCorner()), 7, 1);
                sideArray[7][21] = new CharSpecial(CharColor.BLUE, getCharFromValue(side.getBottomRightCorner()), 7, 21);
                if(((FrontResource) side).getPoint()!=0){
                    sideArray[1][11] = new CharSpecial(CharColor.BLUE, String.valueOf(((FrontResource) side).getPoint()).charAt(0), 7, 21);
                } else {

                }
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 23; j++) {
                        int rowIndex = row + i;
                        int colIndex = col + j;
                        if (rowIndex > 0 && rowIndex < screenHeight && colIndex > 0 && colIndex < screenWidth) {
                            screenSim[rowIndex][colIndex] = sideArray[i][j];
                        }
                    }
                }
            }
        }
        refreshScreen();
    }

    private static char getCharFromValue(Value value) {
        if (value == Value.FUNGI) {
            return 'F';
        } else if (value == Value.PLANT) {
            return 'P';
        } else if (value == Value.ANIMAL) {
            return 'A';
        } else if (value == Value.INSECT) {
            return 'I';
        } else if (value == Value.QUILL) {
            return 'Q';
        } else if (value == Value.INKWELL) {
            return 'K';
        } else if (value == Value.MANUSCRIPT) {
            return 'M';
        } else if (value == Value.COVERED) {
            return 'C';
        } else if (value == Value.EMPTY) {
            return ' ';
        } else if (value == Value.NULL) {
            return 'C';
        } else {
            return 'C';
        }
    }


    private void getScreenToPrint() {
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                CharSpecial charSpecial = screenSim[i][j];
                char c = charSpecial.c;
                CharColor color = charSpecial.color;

                String ansiCode;
                switch (color) {
                    case GREEN:
                        ansiCode = "\u001B[32m";
                        break;
                    case RED:
                        ansiCode = "\u001B[31m";
                        break;
                    case BLUE:
                        ansiCode = "\u001B[34m";
                        break;
                    case MAGENTA:
                        ansiCode = "\u001B[35m";
                        break;
                    case GOLD:
                        ansiCode = "\u001B[33m";
                        break;
                    case WHITE:
                        ansiCode = "\u001B[37m";
                        break;
                    case BLACK:
                        ansiCode = "\u001B[30m";
                        break;
                    default:
                        ansiCode = "\u001B[0m";
                        break;
                }


                int index = j * 3;
                screenToPrint[i][index] = ansiCode;
                screenToPrint[i][index + 1] = String.valueOf(c);
                screenToPrint[i][index + 2] = "\u001B[0m";
            }
        }
    }
}