package it.polimi.ingsw.gc03.view.tui;

import com.sun.source.util.TaskListener;
import it.polimi.ingsw.gc03.model.Game;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
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
        screenToPrint = new String[screenHeight][screenWidth*75];
        for (int i = 0; i < screenHeight; i++) {
            for (int j = 0; j < screenWidth * 3; j++) {
                screenToPrint[i][j] = "";
            }
        }
        clearScreen(' ');

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

//          STILL HAVE TO WORK ON THIS
//    public static void showSide(Side side, int col, int row){
//        StringBuilder sideText = new StringBuilder();
//        if(side instanceof FrontResource){
//            if(((FrontResource) side).getKingdom().equals(Kingdom.ANIMAL)){
//                sideText.append("\u001B[34m");
//            } else if(((FrontResource) side).getKingdom().equals(Kingdom.FUNGI)){
//                sideText.append("\u001B[31m");
//            } else if(((FrontResource) side).getKingdom().equals(Kingdom.PLANT)){
//                sideText.append("\\u001B[32m");
//            } else if(((FrontResource) side).getKingdom().equals(Kingdom.INSECT)){
//                sideText.append("\\u001B[35m");
//            }
//        }
//        String TopLeftValue = " ";
//        String TopRightValue = " ";
//        String BottomLeftValue = " ";
//        String BottomRightValue = " ";
//        String middleValues = "   ";
//        String bottomValues = "     ";
//        String topCenterNum = " ";
//        String topCenterValue = " ";
//        sideText.append("╔═╦════════╦═╦═╦══════╦═╗\n");
//        sideText.append("║"+TopLeftValue+"║        ║"+topCenterNum+"║"+topCenterValue+"║      ║"+TopRightValue+"║\n");
//        sideText.append("╠═╩════════╩═╩═╩══════╩═╣\n");
//        sideText.append("║         ╔═══╗         ║\n");
//        sideText.append("║         ║"+middleValues+"║         ║\n");
//        sideText.append("║         ╚═══╝         ║\n");
//        sideText.append("╠═╗      ╔═════╗      ╔═╣\n");
//        sideText.append("║"+BottomLeftValue+"║      ║"+bottomValues+"║      ║"+BottomRightValue+"║\n");
//        sideText.append("╚═╩══════╩═════╩══════╩═╝\n");
//        asyncPrint(sideText);
//    }


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