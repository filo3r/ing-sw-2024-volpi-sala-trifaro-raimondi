package it.polimi.ingsw.gc03.view.tui;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackGold;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;

import java.util.ArrayList;
import java.util.Arrays;

import static it.polimi.ingsw.gc03.view.tui.AsyncPrint.*;

public class Tui {

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
        // The following is just an initial test: it shows 5 cards, a possible starter card and 4 null cards
        showSide(new FrontResource(Kingdom.FUNGI, Value.INSECT, Value.ANIMAL, Value.QUILL, Value.COVERED, 0), 23,74);
        showSide(new BackGold(Kingdom.PLANT, Value.FUNGI, Value.COVERED, Value.INKWELL,Value.ANIMAL, new ArrayList<>(Arrays.asList(Value.ANIMAL))), 23,119);
        showSide(new FrontGold(Kingdom.FUNGI, Value.COVERED, Value.EMPTY, Value.EMPTY,Value.EMPTY, 2, Value.INKWELL, new ArrayList<>(Arrays.asList(Value.ANIMAL, Value.FUNGI))), 35,119);
        showSide(new BackSide(Kingdom.ANIMAL, Value.EMPTY, Value.QUILL, Value.COVERED,Value.EMPTY, new ArrayList<>(Arrays.asList(Value.ANIMAL))), 35,74);
        showSide(new FrontResource(Kingdom.PLANT, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 29,97); // STARTER
        while(true){
        }
    }

    public void refreshScreen() {
        asyncClean();
        getScreenToPrint();
        StringBuilder screenText = new StringBuilder();
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screenText.append(screenToPrint[i][j * 3]).append(screenToPrint[i][j * 3 + 1]).append(screenToPrint[i][j * 3 + 2]);
            }
            screenText.append("\n");
        }
        asyncPrint(screenText);
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
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, topLeft);
                    } else if (j == screenWidth - 1) {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, topRight);
                    } else {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, horizontalLine);
                    }
                } else if (i == screenHeight - 1) {
                    if (j == 0) {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, bottomLeft);
                    } else if (j == screenWidth - 1) {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, bottomRight);
                    } else {
                        screenSim[i][j] = new CharSpecial(CharColor.WHITE, horizontalLine);
                    }
                } else if (j == 0 || j == screenWidth - 1) {
                    screenSim[i][j] = new CharSpecial(CharColor.WHITE, verticalLine);
                } else {
                    screenSim[i][j] = new CharSpecial(CharColor.WHITE, fillChar);
                }
            }
        }
        screenSim[screenHeight - 2][1] = new CharSpecial(CharColor.WHITE, '>');
        refreshScreen();
    }

    public void showSide(Side side, int row, int col){
        CharSpecial[][] sideArray = new SideView().getSideView(side);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 27; j++) {
                int rowIndex = row + i;
                int colIndex = col + j;
                if (rowIndex > 0 && rowIndex < screenHeight && colIndex > 0 && colIndex < screenWidth) {
                    screenSim[rowIndex][colIndex] = sideArray[i][j];
                }
            }
        }
        refreshScreen();
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

    public void showTitle() {
        StringBuilder sb = new StringBuilder(
                "      ...                         ..                               ..   \n" +
                        "   xH88\"`~ .x8X                 dF                       .H88x.  :~)88: \n" +
                        " :8888   .f\"8888Hf        u.   '88bu.                   x888888X ~:8888 \n" +
                        ":8888>  X8L  ^\"\"`   ...ue888b  '*88888bu        .u     ~   \"8888X  %88\" \n" +
                        "X8888  X888h        888R Y888r   ^\"*8888N    ud8888.        X8888       \n" +
                        "88888  !88888.      888R I888>  beWE \"888L :888'8888.    .xxX8888xxxd>  \n" +
                        "88888   %88888      888R I888>  888E  888E d888 '88%\"   :88888888888\"   \n" +
                        "88888 '> `8888>     888R I888>  888E  888E 8888.+\"      ~   '8888       \n" +
                        "`8888L %  ?888   ! u8888cJ888   888E  888F 8888L       xx.  X8888:    . \n" +
                        " `8888  `-*\"\"   /   \"*888*P\"   .888N..888  '8888c. .+ X888  X88888x.x\"  \n" +
                        "   \"888.      :\"      'Y\"       `\"888*\"\"    \"88888%   X88% : '%8888\"    \n" +
                        "     `\"\"***~\"`                     \"\"         \"YP'     \"*=~    `\"\"      \n" +
                        "                                                                        \n" +
                        "                                                                        \n" +
                        "                                                                        \n");
    asyncPrint(sb);
    }
}