package it.polimi.ingsw.gc03.view.tui.print;


/**
 * Utility class for asynchronous printing to the console using a shared queue.
 */
public class AsyncPrint {

    /**
     * Asynchronously prints the provided text to the console.
     * @param text The text to print.
     */
    public static void asyncPrint(StringBuilder text) {
        AsyncHandler.getQueue().offer(() -> System.out.print(text + "\n"));
    }


    /**
     * Asynchronously prints the provided text to the console at the specified row and column.
     * @param text The text to print.
     * @param row  The row position to start printing.
     * @param col  The column position to start printing.
     */
    public static void asyncPrint(StringBuilder text, int row, int col) {
        AsyncHandler.getQueue().offer(() -> System.out.print("\033[" + row + ";" + col + "H" + text));
    }


    /**
     * Asynchronously prints the provided text to the console.
     * @param text The text to print.
     */
    public static void asyncPrint(String text) {
        AsyncHandler.getQueue().offer(() -> System.out.print(text));
    }


}
