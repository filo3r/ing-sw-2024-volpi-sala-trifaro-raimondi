package it.polimi.ingsw.gc03.view.tui;

import it.polimi.ingsw.gc03.model.*;
import it.polimi.ingsw.gc03.model.card.Card;
import it.polimi.ingsw.gc03.model.card.CardGold;
import it.polimi.ingsw.gc03.model.card.CardResource;
import it.polimi.ingsw.gc03.model.card.CardStarter;
import it.polimi.ingsw.gc03.model.card.cardObjective.CardObjective;
import it.polimi.ingsw.gc03.model.enumerations.Kingdom;
import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackStarter;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import it.polimi.ingsw.gc03.model.side.front.FrontStarter;
import it.polimi.ingsw.gc03.view.ui.UI;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static it.polimi.ingsw.gc03.view.tui.print.AsyncPrint.*;

/**
 * The TUI user interface class.
 */
public class Tui extends UI {

    /**
     * A large array representing the whole possible codex.
     */
    private CharSpecial[][] screenSim;

    /**
     * The user's selected screen width.
     */
    private int screenWidth;

    /**
     * The user's selected screen height.
     */
    private int screenHeight;

    /**
     * The user's nickname.
     */
    private String nickname;

    /**
     * The center of the screenSim y's coordinate.
     */
    private int screenSimY = 364;

    /**
     * The center of the screenSim x's coordinate.
     */
    private int screenSimX = 1093;

    /**
     * Double array used to create the codex view.
     */
    private boolean[][] visited = new boolean[81][81];

    /**
     * Double array used to create the codex view.
     */
    private ArrayList<Coords> occupiedPositions = new ArrayList<Coords>();

    /**
     * Double array used to print on the terminal.
     */
    private CharSpecial[][] middleScreen;

    /**
     * Double array that represents the terminal view.
     */
    private String[][] screenToPrint;

    /**
     * The user's chat.
     */
    private ArrayList<ChatMessage> personalChat = new ArrayList<>();

    /**
     * Class constructor.
     *
     * @param width  The width of the screen.
     * @param height The height of the screen.
     */
    public Tui(int width, int height) {
        screenWidth = width;
        screenHeight = height;
        // screenSim represents the whole codex: 81x81 -> 81*9 x 81*27 = 728x2187 since every side is 9x27
        screenSim = new CharSpecial[729][2187];
        middleScreen = new CharSpecial[height][width];
        screenToPrint = new String[height][width * 3];
        clearScreen(' ');
        refreshScreen(screenSimX, screenSimY);
    }

    /**
     * Method to display the codex of the player.
     *
     * @param gameImmutable the immutable version of the game.
     */
    protected void showCodex(GameImmutable gameImmutable) {
        clearScreen(' ');
        Codex codex = gameImmutable.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst().getCodex();
        translateCodexToScreenSim(codex);
        generateAvailablePositions(codex);
        refreshScreen(screenSimX, screenSimY);
    }

    /**
     * Method to move around the codex.
     */
    public void moveScreenView(int x, int y) {
        screenSimX -= x;
        screenSimY -= y;
        refreshScreen(screenSimX, screenSimY);
    }

    /**
     * Method to resize the screen width and height.
     *
     * @param width  the new screen's width.
     * @param height the new screen's height.
     */
    public void resizeScreenView(int width, int height) {
        this.screenHeight = height;
        this.screenWidth = width;
        middleScreen = new CharSpecial[height][width];
        screenToPrint = new String[height][width * 3];
        CharSpecial[][] screenSeemCopy = new CharSpecial[729][2187];
        for (int i = 0; i < 729; i++) {
            for (int j = 0; j < 2187; j++) {
                screenSeemCopy[i][j] = screenSim[i][j];
            }
        }
        clearScreen(' ');
        for (int i = 0; i < 729; i++) {
            for (int j = 0; j < 2187; j++) {
                screenSim[i][j] = screenSeemCopy[i][j];
            }
        }
        refreshScreen(screenSimX, screenSimY);

    }

    /**
     * Method to refresh the terminal screen.
     *
     * @param centerX the position of the X coordinate's center of the screen that will be displayed.
     * @param centerY the position of the Y coordinate's center of the screen that will be displayed.
     */
    public void refreshScreen(int centerX, int centerY) {
        getScreenToPrint(centerX, centerY);
        StringBuilder screenText = new StringBuilder();
        for (int i = 0; i < this.screenHeight; i++) {
            for (int j = 0; j < this.screenWidth; j++) {
                screenText.append(this.screenToPrint[i][j * 3]).append(this.screenToPrint[i][j * 3 + 1]).append(this.screenToPrint[i][j * 3 + 2]);
            }
            screenText.append("\n");
        }
        asyncPrint(screenText);
    }

    /**
     * Method to delete all the chars on the screen.
     *
     * @param fillChar the char that will used as background.
     */
    public void clearScreen(char fillChar) {
        for (int i = 0; i < 729; i++) {
            for (int j = 0; j < 2187; j++) {
                screenSim[i][j] = new CharSpecial(CharColor.WHITE, fillChar);
            }
        }
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

    /**
     * Method to add a Side of a card on the screenSim.
     *
     * @param side The card's side to display.
     * @param row  The row on the codex of the card.
     * @param col  The col on the codex of the card.
     */
    public void showSide(Side side, int row, int col) {
        CharSpecial[][] sideArray = new SideView().getSideView(side);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 27; j++) {
                int rowIndex = row + i;
                int colIndex = col + j;
                if (rowIndex > 0 && rowIndex < 729 && colIndex > 0 && colIndex < 2187) {
                    if (screenSim[rowIndex][colIndex].c == ' ') {
                        screenSim[rowIndex][colIndex] = sideArray[i][j];
                    }
                }
            }
        }
    }

    /**
     * Method to put the codex on the screenSim
     *
     * @param codex the codex to show.
     */
    private void translateCodexToScreenSim(Codex codex) {
        if (!codex.getCardStarterInserted()) {
            return;
        }
        recursiveShowSide(codex.getCodex(), 40, 40);
        for (int i = 0; i < visited.length; i++) {
            for (int j = 0; j < visited[0].length; j++) {
                visited[i][j] = false;
            }
        }
    }

    /**
     * Recursive method to display on the screenSim in a "three-path".
     *
     * @param codex The codex.
     * @param i     The i index of the recursive method.
     * @param j     The j index of the recursive method.
     */
    private void recursiveShowSide(Side[][] codex, int i, int j) {
        if (i < 0 || i >= 81 || j < 0 || j >= 81 || visited[i][j] || codex[i][j] == null) {
            return;
        }
        visited[i][j] = true;

        int toMoveY = (40 - i) * 3;
        int toMoveX = (40 - j) * 5;
        showSide(codex[i][j], i * 9 + toMoveY, j * 27 + toMoveX);
        occupiedPositions.add(new Coords(j, i));

        recursiveShowSide(codex, i - 1, j - 1);
        recursiveShowSide(codex, i - 1, j + 1);
        recursiveShowSide(codex, i + 1, j - 1);
        recursiveShowSide(codex, i + 1, j + 1);
    }

    /**
     * Method to generate on the screenSim the available position to place cards on the codex.
     *
     * @param codex the codex.
     */
    private void generateAvailablePositions(Codex codex) {
        Iterator<Coords> occupiedPositionsIterator = occupiedPositions.iterator();
        BackStarter test = new BackStarter(Kingdom.NULL, Value.EMPTY, Value.EMPTY, Value.EMPTY, Value.EMPTY, new ArrayList<>());
        while (occupiedPositionsIterator.hasNext()) {
            Coords current = occupiedPositionsIterator.next();
            int y1 = current.y * 9 + (40 - current.y) * 3;
            int x1 = current.x * 27 + (40 - current.x) * 5;
            try {
                // put free space coords on top-left position
                if (codex.simulateInsertIntoCodex(test, current.y - 1, current.x - 1)) {
                    int actualX = current.x - 1;
                    int actualY = current.y - 1;
                    generateTextOnScreen(actualX + " " + actualY, CharColor.WHITE, x1 - 5, y1 - 1);
                }
                // put free space coords on top-right position
                if (codex.simulateInsertIntoCodex(test, current.y - 1, current.x + 1)) {
                    int actualY = current.y - 1;
                    int actualX = current.x + 1;
                    generateTextOnScreen(actualX + " " + actualY, CharColor.WHITE, x1 + 27, y1 - 1);
                }
                // put free space coords on bottom-left position
                if (codex.simulateInsertIntoCodex(test, current.y + 1, current.x - 1)) {
                    int actualY = current.y + 1;
                    int actualX = current.x - 1;
                    generateTextOnScreen(actualX + " " + actualY, CharColor.WHITE, x1 - 5, y1 + 9);
                }
                // put free space coords on bottom-right position
                if (codex.simulateInsertIntoCodex(test, current.y + 1, current.x + 1)) {
                    int actualY = current.y + 1;
                    int actualX = current.x + 1;
                    generateTextOnScreen(actualX + " " + actualY, CharColor.WHITE, x1 + 27, y1 + 9);
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        occupiedPositions.clear();
    }

    /**
     * Method to show a text on the screen.
     *
     * @param text  The text to show.
     * @param color The color of the text.
     * @param x     The x position on the screen.
     * @param y     The y position on the screen.
     */
    private void generateTextOnScreen(String text, CharColor color, int x, int y) {
        for (int i = 0; i < text.length(); i++) {
            screenSim[y][x + i] = new CharSpecial(color, text.charAt(i));
        }
    }

    /**
     * Method used to get the screenToPrint from the middleScreen.
     *
     * @param x the top left x position of the screen.
     * @param y the top left y position of the screen.
     */
    private void getScreenToPrint(int x, int y) {
        if (y + screenHeight > 729) {
            y = 729 - screenHeight;
        }
        if (x + screenWidth > 2187) {
            x = 2187 - screenWidth;
        }
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
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

    /**
     * Method to get the Ansi code from the enumeration.
     *
     * @param color the color to get.
     * @return the String of the code.
     */
    private static String getAnsiCode(CharColor color) {
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

    /**
     * Method to show the desk, containing the decks, displayed cards and the public and private objectives.
     *
     * @param gameImmutable the immutable object of the game.
     * @param nickname      the player's nickname.
     */
    @Override
    protected void showDesk(GameImmutable gameImmutable, String nickname) {
        // Clear the main screen
        clearScreen(' ');

        Player player = gameImmutable.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst();
        Desk desk = gameImmutable.getDesk();
        Side topCardGold = desk.getDeckGold().getFirst().getBackGold();
        Side topCardResource = desk.getDeckResource().getFirst().getBackResource();
        ArrayList<Card> displayedResource = desk.getDisplayedResource();
        ArrayList<Card> displayedGold = desk.getDisplayedGold();
        ArrayList<CardObjective> publicObjectives = desk.getDisplayedObjective();
        CardObjective firstPublicObjective = publicObjectives.getFirst();
        CardObjective secondPublicObjective = publicObjectives.getLast();
        // The player must have already chosen the private card objective
        CardObjective privateObjective = player.getCardObjective().getFirst();

        // Show top card of deck gold
        int topLeftX = 1093 - 27 - 13 - 2;
        int topLeftY = 364 - 16;
        String textDeckGold = "Deck Gold";
        int textDeckGoldX = topLeftX + textDeckGold.length();
        int textDeckGoldY = topLeftY - 2;
        generateTextOnScreen(textDeckGold, CharColor.WHITE, textDeckGoldX, textDeckGoldY);
        showSide(topCardGold, topLeftY, topLeftX);

        // Show top card of deck resource
        topLeftX = 1093 - 27 - 13 - 2;
        topLeftY = 364 - 2;
        String textDeckResource = "Deck Resource";
        int textDeckResourceX = topLeftX + textDeckGold.length();
        int textDeckResourceY = topLeftY - 2;
        generateTextOnScreen(textDeckResource, CharColor.WHITE, textDeckResourceX, textDeckResourceY);
        showSide(topCardResource, topLeftY, topLeftX);

        Side firstCardGold = null;
        Side secondCardGold = null;
        Side firstCardResource = null;
        Side secondCardResource = null;


        // Show the displayed cards
        if (displayedGold.getFirst() instanceof CardResource) {
            firstCardGold = (FrontResource) ((CardResource) displayedGold.getFirst()).getFrontResource();
        } else {
            firstCardGold = (FrontGold) ((CardGold) displayedGold.getFirst()).getFrontGold();
        }
        if (displayedGold.size() > 1) {
            if (displayedGold.getLast() instanceof CardGold) {
                secondCardGold = (FrontGold) ((CardGold) displayedGold.getLast()).getFrontGold();
            } else {
                secondCardGold = (FrontResource) ((CardResource) displayedGold.getLast()).getFrontResource();
            }
        }

        if (displayedResource.getFirst() instanceof CardResource) {
            firstCardResource = (FrontResource) ((CardResource) displayedResource.getFirst()).getFrontResource();
        } else {
            firstCardResource = (FrontGold) ((CardGold) displayedResource.getFirst()).getFrontGold();
        }

        if (displayedResource.size() > 1) {
            if (displayedResource.getLast() instanceof CardGold) {
                secondCardResource = (FrontGold) ((CardGold) displayedResource.getLast()).getFrontGold();
            } else {
                secondCardResource = (FrontResource) ((CardResource) displayedResource.getLast()).getFrontResource();
            }
        }

        // Show the first displayed gold
        topLeftX = 1093 - 13 + 4;
        topLeftY = 364 - 16;
        String textDisplayedGold = "Displayed Gold";
        int textDisplayedGoldX = topLeftX + textDeckGold.length() + 13;
        int textDisplayedGoldY = topLeftY - 2;
        generateTextOnScreen(textDisplayedGold, CharColor.WHITE, textDisplayedGoldX, textDisplayedGoldY);
        showSide(firstCardGold, topLeftY, topLeftX);
        showSide(secondCardGold, topLeftY, topLeftX + 27 + 1);

        // Show the first displayed resource
        topLeftX = 1093 - 13 + 4;
        topLeftY = 364 - 2;
        String textDisplayedResource = "Displayed Resource";
        int textDisplayedResourceX = topLeftX + 2 * textDeckGold.length();
        int textDisplayedResourceY = topLeftY - 2;
        generateTextOnScreen(textDisplayedResource, CharColor.WHITE, textDisplayedResourceX, textDisplayedResourceY);
        showSide(firstCardResource, topLeftY, topLeftX);
        showSide(secondCardResource, topLeftY, topLeftX + 27 + 1);

        String text = "Public Objectives:";
        generateTextOnScreen("Public Objectives:", CharColor.GOLD, 1093 - text.length() / 2, textDisplayedResourceY + 12);
        if (firstPublicObjective.getObjective().length() / 2 >= screenWidth - 1) {
            text = firstPublicObjective.getObjective();
            generateTextOnScreen(firstPublicObjective.getObjective().substring(0, text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 14);
            generateTextOnScreen(firstPublicObjective.getObjective().substring(text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 15);
        } else {
            text = firstPublicObjective.getObjective();
            generateTextOnScreen(firstPublicObjective.getObjective(), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 14);
        }

        if (secondPublicObjective.getObjective().length() / 2 >= screenWidth - 1) {
            text = secondPublicObjective.getObjective();
            generateTextOnScreen(secondPublicObjective.getObjective().substring(0, text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 17);
            generateTextOnScreen(secondPublicObjective.getObjective().substring(text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 18);
        } else {
            text = secondPublicObjective.getObjective();
            generateTextOnScreen(secondPublicObjective.getObjective(), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 17);
        }
        text = "Private Objective:";
        generateTextOnScreen("Private Objective:", CharColor.GOLD, 1093 - text.length() / 2, textDisplayedResourceY + 20);

        if (privateObjective.getObjective().length() / 2 >= screenWidth - 1) {
            text = privateObjective.getObjective();
            generateTextOnScreen(privateObjective.getObjective().substring(0, text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 22);
            generateTextOnScreen(privateObjective.getObjective().substring(text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 23);
        } else {
            text = privateObjective.getObjective();
            generateTextOnScreen(privateObjective.getObjective(), CharColor.WHITE, 1093 - text.length() / 2, textDisplayedResourceY + 22);
        }

        refreshScreen(1093, 364);
    }

    /**
     * Method to show game title.
     */
    protected void show_GameTitle() {
        StringBuilder sb = new StringBuilder("\n\n" +
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
        asyncPrint("\nPress enter to continue...\n\n");
    }

    /**
     * Method to show that the chosen nickname is not valid.
     *
     * @param nickname the invalid nickname.
     */
    @Override

    protected void showInvalidNickname(String nickname) {
        showNotification("Invalid nickname");
    }

    /**
     * Method to show that a personal objective has been chosen.
     *
     * @param game the game instance.
     * @param card the chosen card.
     */
    @Override
    protected void showObjectiveChosen(Game game, Card card) {
        clearScreen(' ');
        String text = "You have selected successfully your personal objective, good luck!";
        generateTextOnScreen(text, CharColor.GOLD, 1093 - text.length() / 2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a notification message.
     *
     * @param message the message to display.
     */
    public void showNotification(String message) {
        message+="\n";
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        String finalMessage = message;
        ScheduledFuture<?> future = executor.schedule(() -> System.out.println(finalMessage), 200, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }

    /**
     * Displays the chat messages on the screen.
     *
     * @param game the game instance.
     */
    public void showChat(GameImmutable game) {
        CharSpecial[][] screenSimBackup = new CharSpecial[729][2187];
        for (int i = 0; i < 729; i++) {
            for (int j = 0; j < 2187; j++) {
                screenSimBackup[i][j] = screenSim[i][j];
            }
        }
        clearScreen(' ');
        int yPos = 364 - screenHeight / 2 + 1;
        int xPos = 1093 - screenWidth / 2 + 2;
        for (ChatMessage msg : personalChat) {
            generateTextOnScreen(msg.getSender(), CharColor.WHITE, xPos, yPos);
            generateTextOnScreen("→", CharColor.WHITE, xPos + msg.getSender().length() + 1, yPos);
            xPos += msg.getSender().length() + 1;
            generateTextOnScreen(msg.getReceiver(), CharColor.WHITE, xPos + 2, yPos);
            xPos += 2;
            generateTextOnScreen(msg.getText(), CharColor.WHITE, xPos + msg.getReceiver().length() + 1, yPos);
            yPos += 1;
            xPos = 1093 - screenWidth / 2 + 2;
        }

        refreshScreen(1093, 364);
        for (int i = 0; i < 729; i++) {
            for (int j = 0; j < 2187; j++) {
                screenSim[i][j] = screenSimBackup[i][j];
            }
        }
    }

   // @Override
    //public void showDrawnCard(GameImmutable model) {
      //  AsyncPrint.asyncPrint(model.getPlayers().get(model.getCurrPlayer()-1).getHand().getLast().getIdCard());
    //}

    //@Override
    //public void showPoints(GameImmutable model) {
    //    for(int i=0;i<model.getPlayers().size();i++){
    //        AsyncPrint.asyncPrint("\n" + model.getPlayers().get(i).getNickname()+" has " + model.getPlayers().get(i).getCodex().getPointCodex() + " points.\n");
    //    }
    //}

    /**
     * Method not used in the TUI.
     */
    @Override
    public void init() {

    }

    /**
     * Displays the menu options.
     */
    @Override
    protected void show_menuOptions() {
        clearScreen(' ');
        generateTextOnScreen("Please choose an option", CharColor.WHITE, 1093-"Please choose an option".length()/2, 364);
        generateTextOnScreen("c -> Create a game", CharColor.WHITE, 1093-"c -> Create a game".length()/2, 365);
        generateTextOnScreen("j -> Join a random game", CharColor.WHITE, 1093-"j -> Join a random game".length()/2, 366);
        generateTextOnScreen("js -> Join a specific game", CharColor.WHITE, 1093-"js -> Join a specific game".length()/2, 367);
        generateTextOnScreen("r -> Reconnect to a game", CharColor.WHITE, 1093-"r -> Reconnect to a game".length()/2, 368);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a message indicating that a new game is being created.
     *
     * @param nickname the nickname of the player creating the game.
     */
    @Override
    protected void show_creatingNewGameMsg(String nickname) {
        clearScreen(' ');
        generateTextOnScreen("Creating a new game...", CharColor.GOLD, 1093-"Creating a new game...".length()/2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a message asking the user to choose the game size.
     *
     * @param gameImmutable the game instance.
     */
    @Override
    protected void showAskSize(GameImmutable gameImmutable) {
        clearScreen(' ');
        generateTextOnScreen("Please choose the game size (2,3,4)", CharColor.WHITE, 1093-"Please choose the game size (2,3,4)".length()/2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a message showing that the player is joining the first available game.
     *
     * @param nickname the nickname of the player.
     */
    @Override
    protected void show_joiningFirstAvailableMsg(String nickname) {
        clearScreen(' ');
        generateTextOnScreen("Joining the first available game...", CharColor.GOLD, 1093-"Joining the first available game...".length()/2, 364);
        refreshScreen(1093, 364);
    }

    /**
     *  Displays a message showing that the player is joining a specific game.
     *
     * @param idGame   the ID of the game.
     * @param nickname the nickname of the player.
     */
    @Override
    protected void show_joiningToGameIdMsg(int idGame, String nickname) {
        clearScreen(' ');
        String text = "Joining the game with id " + idGame;
        generateTextOnScreen(text, CharColor.GOLD, 1093-text.length()/2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Prompts the user to input the game ID.
     */
    @Override
    protected void show_inputGameIdMsg() {
        clearScreen(' ');
        generateTextOnScreen("Please write the game id", CharColor.WHITE, 1093-"Please write the game id".length()/2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Prompts the user to choose a nickname.
     */
    @Override
    protected void show_insertNicknameMsg() {
        clearScreen(' ');
        generateTextOnScreen("Please choose your nickname", CharColor.WHITE, 1093-"Please choose your nickname".length()/2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a greeting with the chosen nickname.
     *
     * @param nickname the chosen nickname.
     */
    @Override
    protected void show_chosenNickname(String nickname) {
        clearScreen(' ');
        String text = "Hi, " + nickname;
        generateTextOnScreen(text, CharColor.GOLD, 1093-text.length()/2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a message indicating that the game has started.
     *
     * @param gameImmutable the game instance.
     */
    @Override
    protected void show_gameStarted(GameImmutable gameImmutable) {
        clearScreen(' ');
        generateTextOnScreen("The game has started", CharColor.GOLD, 1093, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a message indicating that there are no available games to join.
     *
     * @param msgToVisualize the message to display.
     */
    @Override
    protected void show_noAvailableGamesToJoin(String msgToVisualize) {
        clearScreen(' ');
        generateTextOnScreen("No available games to join", CharColor.GOLD, 1093, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a message indicating that the game has ended.
     *
     * @param gameImmutable the game instance.
     */
    @Override
    protected void show_gameEnded(GameImmutable gameImmutable) {
        clearScreen(' ');
        String text = "The game has ended";
        generateTextOnScreen(text, CharColor.GOLD, 1093 - text.length() / 2, 364);
        text = "press enter to continue";
        generateTextOnScreen(text, CharColor.WHITE, 1093 - text.length() / 2, 364 + 6);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a notification that a player has joined the game.
     *
     * @param gameImmutable the game instance.
     * @param nick      the nickname of the player who joined.
     */
    @Override
    protected void show_playerJoined(GameImmutable gameImmutable, String nick) {
        showNotification(nick + " joined the game");
    }

    /**
     * Displays a notification indicating the next player's turn.
     *
     * @param gameImmutable    the game instance.
     * @param nickname the nickname of the next player.
     */
    @Override
    protected void showNextTurn(GameImmutable gameImmutable, String nickname) {
        showNotification("It's the turn of " + nickname);
    }

    /**
     * Displays the player's hand.
     *
     * @param gameImmutable the game instance.
     * @param nickname  the nickname of the player.
     */
    @Override
    protected void show_playerHand(GameImmutable gameImmutable, String nickname) {
        clearScreen(' ');
        List<Card> playerHand = gameImmutable.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst().getHand();
        int posX = 1093 - 27 - 13;
        for (Card card : playerHand) {
            if (card instanceof CardResource) {
                showSide(((CardResource) card).getFrontResource(), 360, posX);
                showSide(((CardResource) card).getBackResource(), 369, posX);
            }
            if (card instanceof CardGold) {
                showSide(((CardGold) card).getFrontGold(), 360, posX);
                showSide(((CardGold) card).getBackGold(), 369, posX);
            }
            posX += 28;
        }
        refreshScreen(1093, 364);
    }

    /**
     * Displays the last sent chat message.
     *
     * @param gameImmutable    the game instance.
     * @param nickname the nickname of the player who sent the message.
     */
    @Override
    protected void show_sentMessage(GameImmutable gameImmutable, String nickname) {
        showNotification(gameImmutable.getChat().getLast().getText());
    }

    /**
     * Displays a notification for invalid input.
     */
    @Override
    protected void show_NaNMsg() {
        showNotification("NaN");
    }

    /**
     * Displays a notification to return to the menu.
     */
    @Override
    protected void show_returnToMenuMsg() {
        showNotification("return to menu.");
    }

    /**
     * Adds an latest event to be displayed.
     *
     * @param input the event message.
     */
    @Override
    public void addLatestEvent(String input, GameImmutable gameImmutable) {
        showNotification(input);
    }

    /**
     * Returns the length of the longest message in the game's chat. (not used in the tui)
     *
     * @param gameImmutable the game instance.
     * @return the length of the longest message.
     */
    @Override
    protected int getLengthLongestMessage(GameImmutable gameImmutable) {
        return 0;
    }

    /**
     * Adds a chat message to the personal chat and displays a notification.
     *
     * @param msg   the chat message.
     * @param gameImmutable the game instance.
     */
    @Override
    protected void addMessage(ChatMessage msg, GameImmutable gameImmutable) {
        if (msg.getReceiver().equals(nickname) || msg.getReceiver().equals("everyone")) {
            personalChat.add(msg);
            showNotification("[CHAT] " + msg.getSender() + "→" + msg.getReceiver() + ": " + msg.getText());
        }
    }

    /**
     * Resets the list of latest events. (not used in the tui)
     */
    @Override
    protected void resetLatestEvents() {
    }

    /**
     * Displays a notification that the game size has been updated.
     *
     * @param size          the new game size.
     * @param gameImmutable the game instance.
     */
    @Override
    protected void show_sizeSetted(int size, GameImmutable gameImmutable) {
        clearScreen(' ');
        String text = "Game size updated to " + size + ", game's id: " + gameImmutable.getIdGame();
        generateTextOnScreen(text, CharColor.GOLD, 1093 - text.length() / 2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a notification that a card has been added to the player's hand.
     *
     * @param gameImmutable the game instance.
     * @param card  the card added to the hand.
     */
    @Override
    protected void showCardAddedToHand(GameImmutable gameImmutable, Card card) {
        showNotification("Card added to hand.");
    }

    /**
     * Displays the winner of the game.
     *
     * @param gameImmutable the game instance.
     */
    @Override
    protected void showWinner(GameImmutable gameImmutable) {
        clearScreen(' ');
        List<Player> winners = gameImmutable.getWinner();
        String text = "";
        for(int i = 0; i<winners.size(); i++){
            text += winners.get(i).getNickname()+" ";
        }
        text += " won the game!";
        generateTextOnScreen(text, CharColor.GOLD, 1093 - text.length() / 2, 364);
        text = "press enter to go back to the menu";
        generateTextOnScreen(text, CharColor.WHITE, 1093 - text.length() / 2, 364 + 6);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a notification that a player has chosen their personal objective.
     *
     * @param gameImmutable         the game instance.
     * @param cardObjective the card objective chosen.
     * @param nickname      the nickname of the player who chose the objective.
     */
    @Override
    protected void showObjectiveChosen(GameImmutable gameImmutable, CardObjective cardObjective, String nickname) {
        showNotification(nickname + " chose his personal objective");
    }

    /**
     * Displays a notification for no connection error.
     */
    @Override
    protected void show_noConnectionError() {
        clearScreen(' ');
        String text = "No connection.";
        generateTextOnScreen(text, CharColor.RED, 1093 - text.length() / 2, 364);
        refreshScreen(1093, 364);
    }

    /**
     * Prompts the user to choose the index of a card from their hand.
     *
     * @param gameImmutable the game instance.
     */
    @Override
    protected void showAskIndex(GameImmutable gameImmutable) {
        clearScreen(' ');
        List<Card> playerHand = gameImmutable.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst().getHand();
        int posX = 1093 - 27 - 13;
        for (Card card : playerHand) {
            if (card instanceof CardResource) {
                showSide(((CardResource) card).getFrontResource(), 360, posX);
                showSide(((CardResource) card).getBackResource(), 369, posX);
            }
            if (card instanceof CardGold) {
                showSide(((CardGold) card).getFrontGold(), 360, posX);
                showSide(((CardGold) card).getBackGold(), 369, posX);
            }
            posX += 28;
        }
        String text = "Please choose the card to place from your hand (0: first card, 1: second card, 2: third card), you'll choose the side later";
        generateTextOnScreen(text, CharColor.WHITE, 1093 - text.length() / 2, 358);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a notification for an incorrect card selection.
     */
    @Override
    protected void show_wrongSelectionHandMsg() {
        showNotification("Wrong selection!");
    }

    /**
     * Prompts the user to choose coordinates to place a card.
     *
     * @param gameImmutable the game instance.
     */
    @Override
    protected void showAskCoordinates(GameImmutable gameImmutable) {
        clearScreen(' ');
        showCodex(gameImmutable);
        showNotification("Please choose the coordinates where to place the card (x y): ");
    }

    /**
     * Prompts the user to choose a deck.
     */
    @Override
    protected void showAskToChooseADeck() {
        showNotification("Please, choose a deck from the following:\nrD: Deck Resource, gD: Deck Gold\ng1: the first gold displayed card, g2: the second gold displayed card\nr1: the first resource displayed card, r2: the second resource displayed card:\n");
    }

    /**
     * Displays a notification that the card cannot be placed.
     *
     * @param gameImmutable    the game instance.
     * @param nickname the nickname of the player.
     */
    @Override
    protected void showCardCannotBePlaced(GameImmutable gameImmutable, String nickname) {
        showNotification("This position is not available!");
    }


    /**
     * Displays a notification for invalid input.
     */
    @Override
    protected void showInvalidInput() {
        showNotification("Invalid input!");
    }

    /**
     * Prompts the user to choose the side of a card.
     *
     * @param gameImmutable the game instance.
     * @param card  the card to place.
     */
    @Override
    protected void showAskSide(GameImmutable gameImmutable, Card card) {
        clearScreen(' ');
        int posX = 1093 - 13;
        if (card instanceof CardResource) {
            showSide(((CardResource) card).getFrontResource(), 360, posX);
            showSide(((CardResource) card).getBackResource(), 369, posX);
        }
        if (card instanceof CardGold) {
            showSide(((CardGold) card).getFrontGold(), 360, posX);
            showSide(((CardGold) card).getBackGold(), 369, posX);
        }
        String text = "Choose the side you will place (f: front, b: back)";
        generateTextOnScreen(text, CharColor.WHITE, 1093 - text.length() / 2, 358);
        refreshScreen(1093, 364);
    }

    /**
     * Prompts the user to choose their personal objective.
     *
     * @param gameImmutable the game instance.
     */
    @Override
    protected void showObjectiveNotChosen(GameImmutable gameImmutable) {
        clearScreen(' ');
        String text = "Choose your personal objective (0: the first one, 1: the second one)";
        generateTextOnScreen(text, CharColor.WHITE, 1093 - text.length() / 2, 364 - 6);
        Player player = gameImmutable.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst();
        CardObjective firstPrivateObjective = player.getCardObjective().getFirst();
        CardObjective secondPrivateObjective = player.getCardObjective().getLast();

        if (firstPrivateObjective.getObjective().length() / 2 >= screenWidth - 1){
            text = firstPrivateObjective.getObjective();
            generateTextOnScreen(firstPrivateObjective.getObjective().substring(0, text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, 364 - 4);
            generateTextOnScreen(firstPrivateObjective.getObjective().substring(text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, 364 - 3);
        } else{
            text = firstPrivateObjective.getObjective();
            generateTextOnScreen(firstPrivateObjective.getObjective(), CharColor.WHITE, 1093 - text.length() / 2, 364 - 4);
        }

        if (secondPrivateObjective.getObjective().length() / 2 >= screenWidth - 1) {
            text = secondPrivateObjective.getObjective();
            generateTextOnScreen(secondPrivateObjective.getObjective().substring(0, text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, 364 - 1);
            generateTextOnScreen(secondPrivateObjective.getObjective().substring(text.length() / 2), CharColor.WHITE, 1093 - text.length() / 2, 364 - 0);
        } else {
            text = secondPrivateObjective.getObjective();
            generateTextOnScreen(secondPrivateObjective.getObjective(), CharColor.WHITE, 1093 - text.length() / 2, 364 - 1);
        }

        refreshScreen(1093, 364);
    }

    /**
     * Displays a notification that the requirements for card placement were not respected.
     *
     * @param gameImmutable         the game instance.
     * @param requirementsPlacement the list of requirements not respected.
     */
    @Override
    protected void showReqNotRespected(GameImmutable gameImmutable, ArrayList<Value> requirementsPlacement) {
        String text = "Requirements not respected try to place another Card.";
        showNotification(text);
    }

    /**
     * Prompts the user to choose a side of the starter card.
     *
     * @param game     the game instance.
     * @param nickname the nickname of the player.
     */
    @Override
    protected void show_askSideStarter(GameImmutable game, String nickname) {
        clearScreen(' ');
        this.nickname = nickname;
        String text = "Please choose a side of the starter card (f: front, b: back)";
        generateTextOnScreen(text, CharColor.GOLD, 1093 - text.length() / 2, 364 - 5);
        Player player = game.getPlayers().stream().filter(p -> p.getNickname().equals(nickname)).toList().getFirst();
        CardStarter cardStarter = player.getCardStarter();
        FrontStarter frontStarter = cardStarter.getFrontStarter();
        BackStarter backStarter = cardStarter.getBackStarter();
        showSide(frontStarter, 364 + 1 - 5, 1093 - 28);
        showSide(backStarter, 364 + 1 - 5, 1093);
        refreshScreen(1093, 364);
    }

    /**
     * Displays a notification that the player has left the game.
     */
    @Override
    protected void showYouLeft() {
        clearScreen(' ');
        String text = "You left the game.";
        generateTextOnScreen(text, CharColor.GOLD, 1093 - text.length() / 2, 364);
        text = "press enter to go back to the menu";
        generateTextOnScreen(text, CharColor.WHITE, 1093 - text.length() / 2, 364 + 6);
        refreshScreen(1093, 364);
    }

    /**
     * Sets the player's nickname.
     *
     * @param nickname the player's nickname.
     */
    @Override
    protected void setNickname(String nickname) {
        this.nickname = nickname;
    }
}