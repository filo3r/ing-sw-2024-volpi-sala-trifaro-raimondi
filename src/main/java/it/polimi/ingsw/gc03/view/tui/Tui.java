package it.polimi.ingsw.gc03.view.tui;

import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import it.polimi.ingsw.gc03.model.side.front.FrontStarter;


import java.util.ArrayList;
import java.util.Arrays;

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
        // The following is just an initial test: it shows 5 cards, a possible starter card and 4 null cards
        showSide(new FrontResource(Kingdom.NULL, Value.INSECT, Value.EMPTY, Value.EMPTY, Value.COVERED, 0), 23,74);
        showSide(new FrontResource(Kingdom.NULL, Value.EMPTY, Value.COVERED, Value.EMPTY,Value.EMPTY, 0), 23,119);
        showSide(new FrontGold(Kingdom.NULL, Value.COVERED, Value.EMPTY, Value.EMPTY,Value.EMPTY, 0, Value.COVERED, new ArrayList<>(Arrays.asList(Value.ANIMAL, Value.FUNGI))), 35,119);
        showSide(new BackSide(Kingdom.ANIMAL, Value.EMPTY, Value.EMPTY, Value.COVERED,Value.EMPTY, new ArrayList<>(Arrays.asList(Value.ANIMAL))), 35,74);
        showSide(new FrontResource(Kingdom.PLANT, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 29,97); // STARTER


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
}