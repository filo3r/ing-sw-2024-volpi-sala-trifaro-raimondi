package it.polimi.ingsw.gc03.model;

import it.polimi.ingsw.gc03.model.enumerations.Value;
import it.polimi.ingsw.gc03.model.side.Side;
import it.polimi.ingsw.gc03.model.side.back.BackSide;
import it.polimi.ingsw.gc03.model.side.front.FrontGold;
import it.polimi.ingsw.gc03.model.side.front.FrontResource;
import java.util.ArrayList;


/**
 * This class represents a Codex.
 */
public class Codex {

    /**
     * Data structure of the codex.
     */
    private Side[][] codex;

    /**
     * Counter for values in the codex.
     * 0 = FUNGI
     * 1 = PLANT
     * 2 = ANIMAL
     * 3 = INSECT
     * 4 = QUILL
     * 5 = INKWELL
     * 6 = MANUSCRIPT
     * 7 = COVERED
     */
    private int[] counterCodex;

    /**
     * Count the points derived from the placement of the cards.
     */
    private int pointCodex;

    /**
     * The topmost occupied row.
     */
    private int minRow;

    /**
     * The bottommost occupied row.
     */
    private int maxRow;

    /**
     * The leftmost occupied column.
     */
    private int minColumn;

    /**
     * The rightmost occupied column.
     */
    private int maxColumn;

    /**
     * Boolean indicating whether the Starter card has been inserted.
     */
    private boolean cardStarterInserted;


    /**
     * Constructor for the Codex class.
     */
    public Codex() {
        this.codex = new Side[81][81];
        this.counterCodex = new int[8];
        for (int i = 0; i < 8; i++) {
            this.counterCodex[i] = 0;
        }
        this.pointCodex = 0;
        this.minRow = 0;
        this.maxRow = 81;
        this.minColumn = 0;
        this.maxColumn = 81;
        this.cardStarterInserted = false;
    }


    /**
     * Method for checking that the card you want to insert is connected to some corner of some previously inserted
     * card.
     * @param row The insertion row.
     * @param column The insertion column.
     * @return A boolean indicating whether the card can be inserted.
     */
    private boolean checkPreviousCardConnection(int row, int column) {
        if (this.codex[row - 1][column - 1] != null || this.codex[row + 1][column - 1] != null ||
                this.codex[row - 1][column + 1] != null || this.codex[row + 1][column + 1] != null)
            return true;
        else
            return false;
    }


    /**
     * Method to check if the card can be inserted or if there are NULL values in the previously inserted cards.
     * @param row The insertion row.
     * @param column The insertion column.
     * @return A boolean indicating whether the card can be inserted.
     */
    private boolean checkPreviousCardNULL(int row, int column) throws IllegalStateException {
        try {
            if (this.codex[row - 1][column - 1] != null) {
                Side topLeft = this.codex[row - 1][column - 1];
                if (topLeft.getBottomRightCorner() == Value.NULL)
                    throw new IllegalStateException("Top left corner contains NULL value.");
            }
            if (this.codex[row + 1][column - 1] != null) {
                Side bottomLeft = this.codex[row + 1][column - 1];
                if (bottomLeft.getTopRightCorner() == Value.NULL)
                    throw new IllegalStateException("Bottom left corner contains NULL value.");
            }
            if (this.codex[row - 1][column + 1] != null) {
                Side topRight = this.codex[row - 1][column + 1];
                if (topRight.getBottomLeftCorner() == Value.NULL)
                    throw new IllegalStateException("Top right corner contains NULL value.");
            }
            if (this.codex[row + 1][column + 1] != null) {
                Side bottomRight = this.codex[row + 1][column + 1];
                if (bottomRight.getTopLeftCorner() == Value.NULL)
                    throw new IllegalStateException("Bottom right corner contains NULL value.");
            }
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            // Catch ArrayIndexOutOfBoundsException and rethrow it as IllegalStateException
            throw new IllegalStateException("Invalid row or column.");
        }
    }



    /**
     * Method to verify that a Gold card can be placed.
     * @param side The side of the card that you want to insert into the codex.
     * @return A boolean that says whether insertion is possible.
     */
    private boolean checkRequirementPlacement(Side side) {
        // If the card side is FrontGold
        if (side instanceof FrontGold) {
            ArrayList<Value> requirementPlacement = ((FrontGold) side).getRequirementPlacement();
            int[] counterRequirementPlacement = new int[8];
            int comparison = 0;
            for (Value value : requirementPlacement) {
                switch (value) {
                    case Value.FUNGI:
                        counterRequirementPlacement[0]++;
                        break;
                    case Value.PLANT:
                        counterRequirementPlacement[1]++;
                        break;
                    case Value.ANIMAL:
                        counterRequirementPlacement[2]++;
                        break;
                    case Value.INSECT:
                        counterRequirementPlacement[3]++;
                        break;
                    case Value.QUILL:
                        counterRequirementPlacement[4]++;
                        break;
                    case Value.INKWELL:
                        counterRequirementPlacement[5]++;
                        break;
                    case Value.MANUSCRIPT:
                        counterRequirementPlacement[6]++;
                        break;
                    case Value.COVERED:
                        counterRequirementPlacement[7]++;
                        break;
                    case Value.EMPTY:
                        break;
                    case Value.NULL:
                        break;
                    default:
                        break;
                }
            }
            for (int i = 0; i < this.counterCodex.length; i++) {
                if (this.counterCodex[i] < counterRequirementPlacement[i])
                    comparison++;
            }
            if (comparison != 0)
                return false;
            else
                return true;
            // If the card side is not FrontGold
        } else {
            return true;
        }
    }


    /**
     * Method for inserting the card side into the codex.
     * @param side The side of the card that you want to insert into the codex.
     * @param row The row to insert the side of the card.
     * @param column The column to insert the side of the card.
     */
    private void insertSide(Side side, int row, int column) {
        this.codex[row][column] = side;
        // Set the corners that are covered by the inserted card.
        if (this.codex[row - 1][column - 1] != null) {
            Side topLeft = this.codex[row - 1][column - 1];
            downgradeCounterCodex(topLeft.getBottomRightCorner());
            topLeft.setBottomRightCorner(Value.COVERED);
            this.counterCodex[7]++;
        }
        if (this.codex[row + 1][column - 1] != null) {
            Side bottomLeft = this.codex[row + 1][column - 1];
            downgradeCounterCodex(bottomLeft.getTopRightCorner());
            bottomLeft.setTopRightCorner(Value.COVERED);
            this.counterCodex[7]++;
        }
        if (this.codex[row - 1][column + 1] != null) {
            Side topRight = this.codex[row - 1][column + 1];
            downgradeCounterCodex(topRight.getBottomLeftCorner());
            topRight.setBottomLeftCorner(Value.COVERED);
            this.counterCodex[7]++;
        }
        if (this.codex[row + 1][column + 1] != null) {
            Side bottomRight = this.codex[row + 1][column + 1];
            downgradeCounterCodex(bottomRight.getTopLeftCorner());
            bottomRight.setTopLeftCorner(Value.COVERED);
            this.counterCodex[7]++;
        }
        // Update minimums and maximums of rows and columns
        if (row <= 40)
            this.minRow = Math.min(this.minRow, row);
        if (row > 40)
            this.maxRow = Math.max(this.maxRow, row);
        if (column <= 40)
            this.minColumn = Math.min(this.minColumn, column);
        if (column > 40)
            this.maxColumn = Math.max(this.maxColumn, column);
    }


    /**
     * Method to lower the codex counter.
     * @param value The value contained in the corner that is covered.
     */
    private void downgradeCounterCodex(Value value) {
        switch (value) {
            case Value.FUNGI:
                this.counterCodex[0]--;
                break;
            case Value.PLANT:
                this.counterCodex[1]--;
                break;
            case Value.ANIMAL:
                this.counterCodex[2]--;
                break;
            case Value.INSECT:
                this.counterCodex[3]--;
                break;
            case Value.QUILL:
                this.counterCodex[4]--;
                break;
            case Value.INKWELL:
                this.counterCodex[5]--;
                break;
            case Value.MANUSCRIPT:
                this.counterCodex[6]--;
                break;
            case Value.COVERED:
                break;
            case Value.EMPTY:
                break;
            case Value.NULL:
                break;
            default:
                break;
        }
    }


    /**
     * Method to raise the codex counter.
     * @param value The value by which you want to increase the count.
     */
    private void upgradeCounterCodex(Value value) {
        switch (value) {
            case Value.FUNGI:
                this.counterCodex[0]++;
                break;
            case Value.PLANT:
                this.counterCodex[1]++;
                break;
            case Value.ANIMAL:
                this.counterCodex[2]++;
                break;
            case Value.INSECT:
                this.counterCodex[3]++;
                break;
            case Value.QUILL:
                this.counterCodex[4]++;
                break;
            case Value.INKWELL:
                this.counterCodex[5]++;
                break;
            case Value.MANUSCRIPT:
                this.counterCodex[6]++;
                break;
            case Value.COVERED:
                break;
            case Value.EMPTY:
                break;
            case Value.NULL:
                break;
            default:
                break;
        }
    }


    /**
     * Method for updating the counter of values present in the codex.
     * @param side The side of the inserted card.
     */
    private void updateCounterCodex(Side side) {
        upgradeCounterCodex(side.getTopLeftCorner());
        upgradeCounterCodex(side.getBottomLeftCorner());
        upgradeCounterCodex(side.getTopRightCorner());
        upgradeCounterCodex(side.getBottomRightCorner());
        if (side instanceof BackSide) {
            ArrayList<Value> center = ((BackSide) side).getCenter();
            for (Value value : center) {
                upgradeCounterCodex(value);
            }
        }
    }


    /**
     * Method for updating points made by inserting cards into the codex.
     * @param side The side of the inserted card.
     */
    private void calculatePointCodex(Side side) {
        if (side instanceof FrontResource) {
            this.pointCodex = this.pointCodex + ((FrontResource) side).getPoint();
        } else if (side instanceof FrontGold) {
            if (((FrontGold) side).getRequirementPoint() == Value.NULL) {
                this.pointCodex = this.pointCodex + ((FrontGold) side).getPoint();
            } else {
                switch (((FrontGold) side).getRequirementPoint()) {
                    case Value.FUNGI:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[0]);
                        break;
                    case Value.PLANT:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[1]);
                        break;
                    case Value.ANIMAL:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[2]);
                        break;
                    case Value.INSECT:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[3]);
                        break;
                    case Value.QUILL:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[4]);
                        break;
                    case Value.INKWELL:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[5]);
                        break;
                    case Value.MANUSCRIPT:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[6]);
                        break;
                    case Value.COVERED:
                        this.pointCodex = this.pointCodex + (((FrontGold) side).getPoint() * this.counterCodex[7]);
                        break;
                    case Value.EMPTY:
                        break;
                    case Value.NULL:
                        break;
                    default:
                        break;
                }
            }
        }
    }


    /**
     * Method for inserting the Starter card into the codex.
     * @param side The side of the card to insert.
     */
    public void insertStarterIntoCodex(Side side) {
        this.codex[40][40] = side;
        this.cardStarterInserted = true;
        // Update minimums and maximums of rows and columns
        this.minRow = 40;
        this.maxRow = 40;
        this.minColumn = 40;
        this.maxColumn = 40;
        // Update counter for values in the codex
        updateCounterCodex(side);
    }


    /**
     * Method of inserting one side of a card into the codex.
     * @param side The side of the card to insert.
     * @param row The row to insert the side of the card.
     * @param column The column to insert the side of the card.
     * @return true if the insertion was successful, otherwise false.
     */
    public boolean insertIntoCodex(Side side, int row, int column) {
        try {
            // Check the row and column and whether the Starter card has already been inserted
            if (row < 0 || row >= 81 || column < 0 || column >= 81 || !this.cardStarterInserted) {
                throw new IllegalArgumentException("Invalid row or column or Starter card not inserted.");
            }
            // Check if the coordinates are valid
            else if (((row % 2) == 0 && (column % 2) != 0) || ((row % 2) != 0 && (column % 2) == 0)) {
                throw new IllegalArgumentException("Invalid coordinates.");
            }
            // Check if the coordinates are free
            else if (this.codex[row][column] != null) {
                throw new IllegalArgumentException("Coordinates already occupied.");
            }
            // Check the connection with some previous card
            else if (!checkPreviousCardConnection(row, column)) {
                throw new IllegalStateException("Previous card connection failed.");
            }
            // Check for NULL values in previous cards
            else if (!checkPreviousCardNULL(row, column)) {
                throw new IllegalStateException("Previous card contains NULL values.");
            }
            // Check that the placement requirements for Gold cards are verified
            else if (!checkRequirementPlacement(side)) {
                throw new IllegalStateException("Placement requirements for Gold cards not met.");
            } else {
                // Proceed with the insertion
                insertSide(side, row, column);
                updateCounterCodex(side);
                calculatePointCodex(side);
                this.counterCodex[7] = 0;
                return true;
            }
        } catch (Exception e) {
            // Handle the exception here, you can log it or take other actions as required
            e.printStackTrace(); // or any other way you want to handle it
            return false;
        }
    }



    /**
     * Method to simulate the insertion of a card into the Codex.
     * @param side The side of the card to simulate insertion of.
     * @param row The row in which to simulate the insertion of the side of the card.
     * @param column The column in which to simulate the insertion of the side of the card.
     * @return A boolean indicating whether the card could be inserted in that position.
     */
    public boolean simulateInsertIntoCodex(Side side, int row, int column) {
        // Check if the coordinates are valid and if they are free
        if (((row % 2) == 0 && (column % 2) != 0) || ((row % 2) != 0 && (column % 2) == 0) || this.codex[row][column] != null) {
            return false;
        } else {
            // Check the connection with some previous card
            boolean checkPreviousCardConnection = checkPreviousCardConnection(row, column);
            if (!checkPreviousCardConnection)
                return false;
            // Check for NULL values in previous cards
            boolean checkPreviousCardNULL = checkPreviousCardNULL(row, column);
            if (!checkPreviousCardNULL)
                return false;
            // Check that the placement requirements for Gold cards are verified
            boolean checkRequirementPlacement = checkRequirementPlacement(side);
            if (!checkRequirementPlacement)
                return false;
            // If you can insert the card side
            return true;
        }
    }


    /**
     * Method to retrieve the codex.
     * @return The codex.
     */
    public Side[][] getCodex() {
        return this.codex;
    }


    /**
     * Method to set the codex.
     * @param codex The codex to set.
     */
    public void setCodex(Side[][] codex) {
        this.codex = codex;
    }


    /**
     * Method to retrieve the counterCodex.
     * @return The counterCodex.
     */
    public int[] getCounterCodex() {
        return this.counterCodex;
    }


    /**
     * Method to set the counterCodex.
     * @param counterCodex The counterCodex to set.
     */
    public void setCounterCodex(int[] counterCodex) {
        this.counterCodex = counterCodex;
    }


    /**
     * Method to retrieve the pointCodex.
     * @return The pointCodex.
     */
    public int getPointCodex() {
        return this.pointCodex;
    }


    /**
     * Method to set the pointCodex.
     * @param pointCodex The pointCodex to set.
     */
    public void setPointCodex(int pointCodex) {
        this.pointCodex = pointCodex;
    }


    /**
     * Method to retrieve the minRow.
     * @return The minRow.
     */
    public int getMinRow() {
        return this.minRow;
    }


    /**
     * Method to set the minRow.
     * @param minRow The minRow to set.
     */
    public void setMinRow(int minRow) {
        this.minRow = minRow;
    }


    /**
     * Method to retrieve the maxRow.
     * @return The maxRow.
     */
    public int getMaxRow() {
        return this.maxRow;
    }


    /**
     * Method to set the maxRow.
     * @param maxRow The maxRow to set.
     */
    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }


    /**
     * Method to retrieve the minColumn.
     * @return The minColumn.
     */
    public int getMinColumn() {
        return this.minColumn;
    }


    /**
     * Method to set the minColumn.
     * @param minColumn The minColumn to set.
     */
    public void setMinColumn(int minColumn) {
        this.minColumn = minColumn;
    }


    /**
     * Method to retrieve the maxColumn.
     * @return The maxColumn.
     */
    public int getMaxColumn() {
        return this.maxColumn;
    }


    /**
     * Method to set the maxColumn.
     * @param maxColumn The maxColumn to set.
     */
    public void setMaxColumn(int maxColumn) {
        this.maxColumn = maxColumn;
    }


    /**
     * Method to retrieve the cardStarterInserted.
     * @return The cardStarterInserted.
     */
    public boolean getCardStarterInserted() {
        return this.cardStarterInserted;
    }


    /**
     * Method to set the cardStarterInserted.
     * @param cardStarterInserted The cardStarterInserted to set.
     */
    public void setCardStarterInserted(boolean cardStarterInserted) {
        this.cardStarterInserted = cardStarterInserted;
    }


    /**
     * Method to print the codex table.
     */
    public void printCodexTable() {
        System.out.println("CODEX:");
        // Maximum length of column coordinates
        int columnWidth = String.valueOf(this.codex[0].length - 1).length();
        // Print coordinates above
        System.out.print(" ".repeat(columnWidth + 4));
        for (int i = minColumn - 3; i < maxColumn + 3; i++) {
            System.out.printf("%-6d", i);
        }
        System.out.println();
        // Print top lines
        System.out.print(" ".repeat(columnWidth) + " +");
        for (int i = minColumn - 3; i < maxColumn + 3; i++) {
            System.out.print("-----+");
        }
        System.out.println();
        // Print table with lines and cells filled
        for (int i = minRow - 3; i < maxRow + 3; i++) {
            // Print the row coordinate
            System.out.printf("%-" + columnWidth + "d |", i);
            // Print the contents of the row
            for (int j = minColumn - 3; j < maxColumn + 3; j++) {
                if (this.codex[i][j] != null) {
                    System.out.print("  C  |");
                } else {
                    System.out.print("     |");
                }
            }
            // Return after each row
            System.out.println();
            // Print the internal rows of the table
            System.out.print(" ".repeat(columnWidth) + " +");
            for (int k = minColumn - 3; k < maxColumn + 3; k++) {
                System.out.print("-----+");
            }
            System.out.println();
        }
    }


    /**
     * Method for printing card information contained in the codex.
     */
    public void printCodexCard() {
        for (int i = minRow - 3; i < maxRow + 3; i++) {
            for (int j = minColumn - 3; j < maxColumn + 3; j++) {
                if (this.codex[i][j] != null) {
                    Side cardCodex = this.codex[i][j];
                    System.out.println("CARD IN COORDINATES: (" + i + " , " + j + ")");
                    System.out.println("Kingdom: " + cardCodex.getKingdom());
                    System.out.println("Top Left Corner: " + cardCodex.getTopLeftCorner());
                    System.out.println("Bottom Left Corner: " + cardCodex.getBottomLeftCorner());
                    System.out.println("Top Right Corner: " + cardCodex.getTopRightCorner());
                    System.out.println("Bottom Right Corner: " + cardCodex.getBottomRightCorner());
                    if (cardCodex instanceof BackSide) {
                        System.out.print("Center: ");
                        ArrayList<Value> center = ((BackSide) cardCodex).getCenter();
                        for (int k = 0; k < center.size(); k++) {
                            if (k != 0) {
                                System.out.print(", ");
                            }
                            System.out.print(center.get(k));
                        }
                        System.out.println();
                    }
                    System.out.println();
                }
            }
        }
    }


}
