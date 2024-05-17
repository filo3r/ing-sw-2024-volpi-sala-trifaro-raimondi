package it.polimi.ingsw.gc03.view.tui;

import it.polimi.ingsw.gc03.controller.GameController;
import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.GameStatus;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackGold;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.back.BackStarter;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import static it.polimi.ingsw.gc03.view.tui.AsyncPrint.*;

public class Tui {

    private CharSpecial[][] screenSim;
    private int screenWidth;
    private int screenHeight;

    private int screenSimY = 364;
    private int screenSimX = 1093;
    private boolean[][] visited = new boolean[81][81];

    private ArrayList<Coords> occupiedPositions = new ArrayList<Coords>();
    private CharSpecial[][] middleScreen;
    private String[][] screenToPrint;

    public void Tui(int width, int height){
        screenWidth = width;
        screenHeight = height;
        // screenSim represents the whole codex: 81x81 -> 81*9 x 81*27 = 728x2187 since every side is 9x27
        screenSim = new CharSpecial[729][2187];
        middleScreen = new CharSpecial[screenHeight][screenWidth];
        screenToPrint = new String[screenHeight][screenWidth*3];
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth * 3; j++) {
                screenToPrint[i][j] = "";
            }
        }
        clearScreen(' ');
        // Create a fake codex with multiple sequential additions to test codex view
        Codex codex = new Codex();
        try {
            codex.insertStarterIntoCodex(new BackStarter(Kingdom.NULL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY, new ArrayList<>(Arrays.asList(Value.ANIMAL))));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        codex.insertIntoCodex(new FrontResource(Kingdom.FUNGI, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 40-1,40+1);
        codex.insertIntoCodex(new FrontResource(Kingdom.ANIMAL, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 40-2,40+2);
        codex.insertIntoCodex(new FrontResource(Kingdom.PLANT, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 40-2,40);
        codex.insertIntoCodex(new FrontResource(Kingdom.INSECT, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 40+1,40-1);
        codex.insertIntoCodex(new FrontGold(Kingdom.PLANT, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.INSECT, 1, Value.COVERED, new ArrayList<>(Arrays.asList(Value.FUNGI,Value.FUNGI,Value.FUNGI))), 40+2,40);
        codex.insertIntoCodex(new FrontResource(Kingdom.PLANT, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 40+1,40+1);
        codex.insertIntoCodex(new FrontResource(Kingdom.PLANT, Value.ANIMAL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 40-1,40-1);
        translateCodexToScreenSim(codex);
        generateAvailablePositions(codex);
        refreshScreen();
        codex.insertIntoCodex(new FrontResource(Kingdom.FUNGI, Value.INSECT, Value.INSECT, Value.INSECT,Value.INSECT, 7), 40+3,40+1);
        translateCodexToScreenSim(codex);
        generateAvailablePositions(codex);
        refreshScreen();
        codex.insertIntoCodex(new FrontResource(Kingdom.FUNGI, Value.INSECT, Value.INSECT, Value.INSECT,Value.INSECT, 7), 40+3,40-1);
        translateCodexToScreenSim(codex);
        generateAvailablePositions(codex);
        refreshScreen();
        while(true){
        }
    }

    public void refreshScreen() {
        getScreenToPrint(screenSimX, screenSimY);
        StringBuilder screenText = new StringBuilder();
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screenText.append(screenToPrint[i][j * 3]).append(screenToPrint[i][j * 3 + 1]).append(screenToPrint[i][j * 3 + 2]);
            }
            screenText.append("\n");
        }
        asyncPrint(screenText);
        for(int i = 0; i < 729; i++){
            for(int j = 0; j < 2187; j++){
                screenSim[i][j] = new CharSpecial(CharColor.WHITE, ' ');
            }
        }
    }

    public void clearScreen(char fillChar) {
        for(int i = 0; i < 729; i++) {
            for(int j = 0; j < 2187; j++) {
                screenSim[i][j] = new CharSpecial(CharColor.WHITE, fillChar);
            }
        }char topLeft = '┌';
        char topRight = '┐';
        char bottomLeft = '└';
        char bottomRight = '┘';
        char horizontalLine = '─';
        char verticalLine = '│';

        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                if (i == 0) {
                    if (j == 0) {
                        middleScreen[i][j] = new CharSpecial(CharColor.WHITE, topLeft);
                    } else if (j == screenWidth - 1) {
                        middleScreen[i][j] = new CharSpecial(CharColor.WHITE, topRight);
                    } else {
                        middleScreen[i][j] = new CharSpecial(CharColor.WHITE, horizontalLine);
                    }
                } else if (i == screenHeight - 1) {
                    if (j == 0) {
                        middleScreen[i][j] = new CharSpecial(CharColor.WHITE, bottomLeft);
                    } else if (j == screenWidth - 1) {
                        middleScreen[i][j] = new CharSpecial(CharColor.WHITE, bottomRight);
                    } else {
                        middleScreen[i][j] = new CharSpecial(CharColor.WHITE, horizontalLine);
                    }
                } else if (j == 0 || j == screenWidth - 1) {
                    middleScreen[i][j] = new CharSpecial(CharColor.WHITE, verticalLine);
                } else {
                    middleScreen[i][j] = new CharSpecial(CharColor.WHITE, fillChar);
                }
            }
        }
        middleScreen[screenHeight - 2][1] = new CharSpecial(CharColor.WHITE, '>');
    }

    public void showSide(Side side, int row, int col){
        CharSpecial[][] sideArray = new SideView().getSideView(side);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 27; j++) {
                int rowIndex = row + i;
                int colIndex = col + j;
                if (rowIndex > 0 && rowIndex < 729 && colIndex > 0 && colIndex < 2187) {
                    if(screenSim[rowIndex][colIndex].c == ' '){
                        screenSim[rowIndex][colIndex] = sideArray[i][j];
                    }
                }
            }
        }
    }

    private void translateCodexToScreenSim(Codex codex){
        if(!codex.getCardStarterInserted()){
            return;
        }
        recursiveShowSide(codex.getCodex(), 40, 40);
        for(int i = 0; i<visited.length; i++){
            for(int j = 0; j< visited[0].length; j++){
                visited[i][j] = false;
            }
        }
    }

    private  void recursiveShowSide(Side[][] codex, int i, int j) {
        if (i < 0 || i >= 81 || j < 0 || j >= 81 || visited[i][j] || codex[i][j] == null) {
            return;
        }
        visited[i][j] = true;

        int toMoveY = (40-i)*3;
        int toMoveX = (40-j)*5;
        showSide(codex[i][j], i*9+toMoveY, j*27+toMoveX);
        occupiedPositions.add(new Coords(j,i));

        recursiveShowSide(codex, i-1, j-1);
        recursiveShowSide(codex, i-1, j+1);
        recursiveShowSide(codex, i+1, j-1);
        recursiveShowSide(codex, i+1, j+1);
    }

    private void generateAvailablePositions(Codex codex){
        Iterator<Coords> occupiedPositionsIterator = occupiedPositions.iterator();
        BackStarter test = new BackStarter(Kingdom.NULL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY, new ArrayList<>());
        while(occupiedPositionsIterator.hasNext()){
            Coords current = occupiedPositionsIterator.next();
            int y1 =current.y*9+(40-current.y)*3;
            int x1 = current.x*27+(40-current.x)*5;
            try {
                // put free space coords on top-left position
                if(codex.simulateInsertIntoCodex(test, current.y-1, current.x-1)){
                    int actualX = current.x-1;
                    int actualY = current.y-1;
                    generateTextOnScreen(actualX+" "+actualY, CharColor.WHITE, x1-5, y1-1);
                }
                // put free space coords on top-right position
                if(codex.simulateInsertIntoCodex(test, current.y-1, current.x+1)){
                    int actualX = current.x+1;
                    int actualY = current.y-1;
                    generateTextOnScreen(actualX+" "+actualY, CharColor.WHITE, x1+27, y1-1);
                }
                // put free space coords on bottom-left position
                if(codex.simulateInsertIntoCodex(test, current.y+1, current.x-1)){
                    int actualX = current.x-1;
                    int actualY = current.y+1;
                    generateTextOnScreen(actualX+" "+actualY, CharColor.WHITE, x1-5, y1+9);
                }
                // put free space coords on bottom-right position
                if(codex.simulateInsertIntoCodex(test, current.y+1, current.x+1)){
                    int actualX = current.x+1;
                    int actualY = current.y+1;
                    generateTextOnScreen(actualX+" "+actualY, CharColor.WHITE, x1+27, y1+9);
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        occupiedPositions.clear();
    }

    private void generateTextOnScreen(String text, CharColor color, int x, int y){
        for (int i = 0; i < text.length(); i++) {
            screenSim[y][x+i] = new CharSpecial(color, text.charAt(i));
        }
    }

    // (x,y) is the top left point of screenSim that will be showed to the user.
    private void getScreenToPrint(int x , int y) {
        if (y + screenHeight > 729) {
            y = 729 - screenHeight;
        }
        if (x + screenWidth > 2187) {
            x = 2187 - screenWidth;
        }
        if(x<0){
            x=0;
        }
        if(y<0){
            y=0;
        }

        // replace the middleScreen with the right portion on screenSim
        for (int i = 1; i < screenHeight - 1; i++) {
            for (int j = 1; j < screenWidth - 1; j++) {
                middleScreen[i][j] = screenSim[i + y - screenHeight / 2][j + x - screenWidth / 2];
            }
        }

        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                CharSpecial charSpecial = middleScreen[i][j];
                char c = charSpecial.c;
                String ansiCode = getAnsiCode(charSpecial.color);
                int index = j * 3;
                screenToPrint[i][index] = ansiCode;
                screenToPrint[i][index + 1] = String.valueOf(c);
                screenToPrint[i][index + 2] = "\u001B[0m";
            }
        }
    }

    private static String getAnsiCode(CharColor color){
        String ansiCode = switch (color) {
            case GREEN -> "\u001B[32m";
            case RED -> "\u001B[31m";
            case BLUE -> "\u001B[34m";
            case MAGENTA -> "\u001B[35m";
            case GOLD -> "\u001B[33m";
            case WHITE -> "\u001B[37m";
            case BLACK -> "\u001B[30m";
        };
        return ansiCode;
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

    public void showNotification(String message){
        StringBuilder sb = new StringBuilder(message);
        asyncPrint(sb);
    }
}