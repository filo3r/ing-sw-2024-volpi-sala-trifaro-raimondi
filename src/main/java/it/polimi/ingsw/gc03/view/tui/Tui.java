package it.polimi.ingsw.gc03.view.tui;

import it.polimi.ingsw.gc03.model.Codex;
import it.polimi.ingsw.gc03.model.Desk;
import it.polimi.ingsw.gc03.model.Player;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackStarter;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import it.polimi.ingsw.gc03.view.flow.UI;
import javafx.scene.SubScene;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static it.polimi.ingsw.gc03.view.tui.AsyncPrint.*;

public class Tui extends UI {

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
        resizeScreenView(screenWidth, screenHeight);
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
        codex.insertIntoCodex(new FrontResource(Kingdom.PLANT, Value.NULL, Value.FUNGI, Value.ANIMAL,Value.FUNGI, 1), 40-1,40-1);

        displayCodex(codex);

        codex.insertIntoCodex(new FrontResource(Kingdom.FUNGI, Value.INSECT, Value.INSECT, Value.INSECT,Value.INSECT, 7), 40+3,40+1);

        resizeScreenView(200, 40);

        moveScreenView(0,-5);
        displayCodex(codex);

        codex.insertIntoCodex(new FrontResource(Kingdom.FUNGI, Value.INSECT, Value.INSECT, Value.INSECT,Value.INSECT, 7), 40+3,40-1);

        moveScreenView(0,-2);
        displayCodex(codex);

        Desk desk = null;
        Player player = null;

        try {
            desk = new Desk();
            player = new Player("ABC", 1, desk);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        resizeScreenView(221, 70);
        showDesk(desk, player);
        while(true){
        }
    }

    public void displayCodex(Codex codex){
        translateCodexToScreenSim(codex);
        generateAvailablePositions(codex);
        refreshScreen(screenSimX, screenSimY);
    }

    // moveScreenView will move the view of the table x positions left and y positions up
    public void moveScreenView(int x, int y){
        screenSimX-=x;
        screenSimY-=y;
    }

    public void resizeScreenView(int width, int height){
        screenWidth=width;
        screenHeight=height;
        middleScreen = new CharSpecial[screenHeight][screenWidth];
        screenToPrint = new String[screenHeight][screenWidth*3];
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth * 3; j++) {
                screenToPrint[i][j] = "";
            }
        }
        clearScreen(' ');
    }

    public void refreshScreen(int centerX, int centerY) {
        getScreenToPrint(centerX, centerY);
        StringBuilder screenText = new StringBuilder();
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth; j++) {
                screenText.append(screenToPrint[i][j * 3]).append(screenToPrint[i][j * 3 + 1]).append(screenToPrint[i][j * 3 + 2]);
            }
            screenText.append("\n");
        }
        asyncPrint(screenText);
        clearScreen(' ');
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
            } catch (Exception ignored) {
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

    public void showDesk(Desk desk, Player player) {
        // Clear the main screen
        clearScreen(' ');

        Side topCardGold = desk.getDeckGold().getFirst().getFrontGold();
        Side topCardResource = desk.getDeckResource().getFirst().getFrontResource();
        ArrayList<Card> displayedResource = desk.getDisplayedResource();
        ArrayList<Card> displayedGold = desk.getDisplayedGold();
        ArrayList<CardObjective> publicObjectives = desk.getDisplayedObjective();
        CardObjective firstPublicObjective = publicObjectives.getFirst();
        CardObjective secondPublicObjective = publicObjectives.getLast();
        // The player must have already chosen the private card objective
        CardObjective privateObjective = player.getCardObjective().getFirst();

        // Show top card of deck gold
        int topLeftX = 1093-27-13-2;
        int topLeftY = 364-16;
        String textDeckGold = "Deck Gold";
        int textDeckGoldX = topLeftX+textDeckGold.length();
        int textDeckGoldY = topLeftY-2;
        generateTextOnScreen(textDeckGold, CharColor.WHITE, textDeckGoldX, textDeckGoldY);
        showSide(topCardGold, topLeftY, topLeftX);

        // Show top card of deck resource
        topLeftX = 1093-27-13-2;
        topLeftY = 364-2;
        String textDeckResource = "Deck Resource";
        int textDeckResourceX = topLeftX+textDeckGold.length();
        int textDeckResourceY = topLeftY-2;
        generateTextOnScreen(textDeckResource, CharColor.WHITE, textDeckResourceX, textDeckResourceY);
        showSide(topCardResource, topLeftY, topLeftX);

        Side firstCardGold = null;
        Side secondCardGold = null;
        Side firstCardResource = null;
        Side secondCardResource = null;


        // Show the displayed cards
        if(displayedGold.getFirst() instanceof CardResource){
            firstCardGold = (FrontResource) ((CardResource) displayedGold.getFirst()).getFrontResource();
        } else {
            firstCardGold = (FrontGold) ((CardGold) displayedGold.getFirst()).getFrontGold();
        }
        if(displayedGold.size()>1){
            if(displayedGold.getLast() instanceof CardGold){
                secondCardGold = (FrontGold) ((CardGold) displayedGold.getLast()).getFrontGold();
            } else {
                secondCardGold = (FrontResource) ((CardResource) displayedGold.getLast()).getFrontResource();
            }
        }

        if(displayedResource.getFirst() instanceof CardResource){
            firstCardResource = (FrontResource) ((CardResource) displayedResource.getFirst()).getFrontResource();
        } else {
            firstCardResource = (FrontGold) ((CardGold) displayedResource.getFirst()).getFrontGold();
        }

        if(displayedResource.size()>1){
            if(displayedResource.getLast() instanceof CardGold){
                secondCardResource = (FrontGold) ((CardGold) displayedResource.getLast()).getFrontGold();
            } else {
                secondCardResource = (FrontResource) ((CardResource) displayedResource.getLast()).getFrontResource();
            }
        }

        // Show the first displayed gold
        topLeftX = 1093-13+4;
        topLeftY = 364-16;
        String textDisplayedGold = "Displayed Gold";
        int textDisplayedGoldX = topLeftX+textDeckGold.length()+13;
        int textDisplayedGoldY = topLeftY-2;
        generateTextOnScreen(textDisplayedGold, CharColor.WHITE, textDisplayedGoldX, textDisplayedGoldY);
        showSide(firstCardGold, topLeftY, topLeftX);
        showSide(secondCardGold, topLeftY, topLeftX+27+1);

        // Show the first displayed resource
        topLeftX = 1093-13+4;
        topLeftY = 364-2;
        String textDisplayedResource = "Displayed Resource";
        int textDisplayedResourceX = topLeftX+2*textDeckGold.length();
        int textDisplayedResourceY = topLeftY-2;
        generateTextOnScreen(textDisplayedResource, CharColor.WHITE, textDisplayedResourceX, textDisplayedResourceY);
        showSide(firstCardResource, topLeftY, topLeftX);
        showSide(secondCardResource, topLeftY, topLeftX+27+1);

        String text = "Public Objectives:";
        generateTextOnScreen("Public Objectives:", CharColor.GOLD, 1093-text.length()/2, textDisplayedResourceY+12);
        if(firstPublicObjective.getObjective().length()/2>=screenWidth-1){
            text = firstPublicObjective.getObjective();
            generateTextOnScreen(firstPublicObjective.getObjective().substring(0,text.length()/2), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+14);
            generateTextOnScreen(firstPublicObjective.getObjective().substring(text.length()/2), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+15);
        } else{
            text = firstPublicObjective.getObjective();
            generateTextOnScreen(firstPublicObjective.getObjective(), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+14);
        }

        if(secondPublicObjective.getObjective().length()/2>=screenWidth-1){
            text = secondPublicObjective.getObjective();
            generateTextOnScreen(secondPublicObjective.getObjective().substring(0,text.length()/2), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+17);
            generateTextOnScreen(secondPublicObjective.getObjective().substring(text.length()/2), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+18);
        } else{
            text = secondPublicObjective.getObjective();
            generateTextOnScreen(secondPublicObjective.getObjective(), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+17);
        }
        text = "Private Objective:";
        generateTextOnScreen("Private Objective:", CharColor.GOLD, 1093-text.length()/2, textDisplayedResourceY+20);

        if(privateObjective.getObjective().length()/2>=screenWidth-1){
            text = privateObjective.getObjective();
            generateTextOnScreen(privateObjective.getObjective().substring(0,text.length()/2), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+22);
            generateTextOnScreen(privateObjective.getObjective().substring(text.length()/2), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+23);
        } else{
            text = privateObjective.getObjective();
            generateTextOnScreen(privateObjective.getObjective(), CharColor.WHITE, 1093-text.length()/2, textDisplayedResourceY+22);
        }

        refreshScreen(1093, 364);
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