package it.polimi.ingsw.gc03.view.tui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Utility class for asynchronous printing to the console.
 * This class uses a fixed thread pool to perform print operations in separate threads.
 */
public class AsyncPrint {

    /**
     * Executor service with a fixed thread pool of 10 threads.
     */
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);


    /**
     * Asynchronously prints the provided text to the console.
     * @param text The text to print.
     */
    public static void asyncPrint(StringBuilder text) {
        executorService.submit(() -> {
            System.out.print(text+"\n");
        });
    }


    /**
     * Asynchronously prints the provided text to the console at the specified row and column.
     * @param text The text to print.
     * @param row  The row position to start printing.
     * @param col  The column position to start printing.
     */
    public static void asyncPrint(StringBuilder text, int row, int col) {
        executorService.submit(() -> {
            System.out.print("\033[" + row + ";" + col + "H" + text);
        });
    }


    /**
     * Asynchronously prints the provided text to the console.
     * @param text The text to print.
     */
    public static void asyncPrint(String text) {
        executorService.submit(() -> {
            System.out.print(text);
        });
    }


}
